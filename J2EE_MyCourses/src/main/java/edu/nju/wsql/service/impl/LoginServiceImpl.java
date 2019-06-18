package edu.nju.wsql.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import edu.nju.wsql.beans.RegisterBean;
import edu.nju.wsql.dao.UserDao;
import edu.nju.wsql.model.User;
import edu.nju.wsql.model.enums.UserType;
import edu.nju.wsql.service.LoginService;
import edu.nju.wsql.service.results.EmailRegisterResult;
import edu.nju.wsql.service.results.LoginResult;
import edu.nju.wsql.service.results.ValidationResult;
import edu.nju.wsql.service.utils.EmailUtil;
import edu.nju.wsql.service.utils.JwtUtil;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserDao userDao;

    @Override
    @Transactional
    public LoginResult login(String loginValue, String password) {
        String id = loginValue;
        if (checkNJUEmail(loginValue))
            id = getId(loginValue);
        User user = userDao.findOne(id);
        if (user == null || ! password.equals(user.getPassword()))
            return new LoginResult(LoginResult.Result.FAILURE);
        if (user.isValid())
            return new LoginResult(LoginResult.Result.SUCCESS, id, user.getType());
        return new LoginResult(LoginResult.Result.INVALID);
    }

    @Override
    @Transactional
    public EmailRegisterResult checkEmail(String email) {
        if (!checkNJUEmail(email))
            return EmailRegisterResult.NOT_NJU_EMAIL;
        User user = userDao.findOne(getId(email));
        if (user == null)
            return EmailRegisterResult.NOT_EXIST;
        if (user.isValid())
            return EmailRegisterResult.ALREADY_EXIST;
        return EmailRegisterResult.ALREADY_INVALID;
    }

    @Override
    @Transactional
    public void register(RegisterBean bean) {
        User user = new User();
        String email = bean.getEmail();
        String id = getId(email);
        user.setId(id);
        user.setPassword(bean.getPassword());
        user.setName(bean.getName());
        user.setType(getType(email));
        user.setEmail(email);
        user.setValid(false);
        user.setPortrait("default portrait.png");
        userDao.save(user);
        emailValidation(email, user, bean.isVue());
    }

    @Override
    @Transactional
    public ValidationResult validate(String token) {
        DecodedJWT jwt;
        try {
            jwt = JWT.decode(token);
        } catch (JWTDecodeException e) {
            e.printStackTrace();
            return new ValidationResult(ValidationResult.Result.ILLEGAL);
        }
        System.out.println(jwt.getIssuedAt()+" "+jwt.getExpiresAt());
        String email = jwt.getSubject();
        String id = getId(email);
        User user = userDao.findOne(id);
        Claims claims;
        try {
            claims = JwtUtil.parseJwt(token);
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
            return new ValidationResult(ValidationResult.Result.EXPIRED);
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            e.printStackTrace();
            return new ValidationResult(ValidationResult.Result.ILLEGAL);
        }
        if (claims.get("id").equals(id))
            if (claims.get("password").equals(user.getPassword()))
                if (claims.get("email").equals(email)) {
                    userDao.validate(id);
                    System.out.println("验证成功");
                    return new ValidationResult(ValidationResult.Result.SUCCESS, id, user.getType());
                }

        return new ValidationResult(ValidationResult.Result.ILLEGAL);
    }

    @Override
    @Transactional
    public void registerAgain(RegisterBean bean) {
        String email = bean.getEmail();
        String id = getId(email);
        userDao.update(id, bean.getPassword(), bean.getName());
        emailValidation(email, userDao.findOne(id), bean.isVue());
    }

    private void emailValidation(String email, User user, boolean isVue) {
        String url = isVue ? "http://localhost:8081/#/register/validate?" : "http://localhost:8080/login/validate?";
        EmailUtil.sendEmail(email,
                "MyCourses 邮箱验证",
                "请点击<a href='" + url +
                        "token=" + JwtUtil.createJwt(15 * 60 * 1000, user) +
                        "'><b>这里</b></a>" + "验证并登录（15分钟内有效）!");
    }

    private UserType getType(String email) {
        if (isStudentEmail(email))
            return UserType.STUDENT;
        if (isTeacherEmail(email))
            return UserType.TEACHER;
        return null;
    }

    private String getId(String email) {
        return email.split("@")[0];
    }

    private String getEmailType(String email) {
        String[] words = email.split("@");
        if (words.length == 2)
            return words[1];
        return "";
    }

    private boolean checkNJUEmail(String email) {
        if (! email.contains("@"))
            return false;
        if (email.indexOf("@") != email.lastIndexOf("@"))
            return false;
        if (isTeacherEmail(email))
            return true;
        if (isStudentEmail(email))
            return true;
        return false;
    }

    private static final String TEACHER_EMAIL_POSTFIX = "nju.edu.cn";
    private static final String STUDENT_EMAIL_POSTFIX = "smail.nju.edu.cn";

    private boolean isTeacherEmail(String email) {
        String prefix = getId(email);
        String postfix = getEmailType(email);
        return postfix.equals(TEACHER_EMAIL_POSTFIX) && ! isStudentId(prefix);
    }

    private boolean isStudentEmail(String email) {
        String prefix = getId(email);
        String postfix = getEmailType(email);
        return postfix.equals(STUDENT_EMAIL_POSTFIX) && isStudentId(prefix);
    }

    private boolean isStudentId(String id) {
        if (id.length() != 9)
            return false;
        if (notAllDigit(id.substring(2)))
            return false;
        if (notAllDigitOrLetter(id.substring(0, 2)))
            return false;
        return true;
    }

    private boolean notAllDigitOrLetter(String s) {
        for (char ch: s.toCharArray()) {
            if (notDigit(ch) && notLetter(ch))
                return true;
        }
        return false;
    }

    private boolean notAllDigit(String s) {
        for (char ch: s.toCharArray()) {
            if (notDigit(ch))
                return true;
        }
        return false;
    }

    private boolean notDigit(char ch) {
        return ch < '0' || ch > '9';
    }

    private boolean notLetter(char ch) {
        return (ch < 'a' || ch > 'z') && (ch < 'A' || ch > 'Z');
    }
}
