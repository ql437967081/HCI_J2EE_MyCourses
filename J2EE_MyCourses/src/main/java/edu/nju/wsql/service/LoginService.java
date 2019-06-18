package edu.nju.wsql.service;

import edu.nju.wsql.beans.RegisterBean;
import edu.nju.wsql.service.results.EmailRegisterResult;
import edu.nju.wsql.service.results.LoginResult;
import edu.nju.wsql.service.results.ValidationResult;

public interface LoginService {
    LoginResult login(String loginValue, String password);

    EmailRegisterResult checkEmail(String email);

    void register(RegisterBean bean);

    ValidationResult validate(String token);

    void registerAgain(RegisterBean bean);
}
