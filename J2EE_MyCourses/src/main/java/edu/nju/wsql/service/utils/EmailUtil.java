package edu.nju.wsql.service.utils;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.*;

class RunnableThreadTest implements Runnable {

    private int i;

    public void run() {
        for (i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + " " + i);
        }
    }
}

    public class EmailUtil {
    private static final String FROM_EMAIL_SMTP_ADDRESS = "smtp.qq.com";
    private static final int FROM_EMAIL_PORT = 587;
    private static final String FROM_EMAIL = "437967081@qq.com";
    private static final String FROM_EMAIL_PASSWORD = "zwmwjtuuokiccaha";

    public static void sendEmail(String toEmail, String subject, String content) {
        JavaMailSender mailSender = getJavaMailSender();
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setFrom(FROM_EMAIL);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        for(int i = 0;i< 100;i++)
        {
            System.out.println(Thread.currentThread().getName()+"  : "+i);
            if(i==20)
            {
                RunnableThreadTest rtt = new RunnableThreadTest();
                new Thread(rtt,"新线程1").start();
                new Thread(rtt,"新线程2").start();
            }
        }
        /*sendEmail("437967081@qq.com",
                "测试Spring发送附件-1",
                "请点击<a href='http://www.yuledanao.com/dl/PWA_INTRO.zip'><b>这里</b></a>" + "验证并登录!");*/
    }

    private static JavaMailSender getJavaMailSender() {
        //
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        // 参考QQ邮箱帮助中心
        mailSender.setHost(FROM_EMAIL_SMTP_ADDRESS); // QQ邮箱smtp发送服务器地址
        //mailSender.setPort(465); // QQ这个端口不可用? 为什么?
        mailSender.setPort(FROM_EMAIL_PORT);// 端口号
        mailSender.setUsername(FROM_EMAIL); // 使用你自己的账号
        mailSender.setPassword(FROM_EMAIL_PASSWORD); // 授权码-发短信获取
        //
        // 相关属性配置, 也可以不修改,使用默认值
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");// 协议
        props.put("mail.smtp.auth", "true");// 登录
        props.put("mail.smtp.starttls.enable", "true");//使用SSL
        props.put("mail.debug", "true");// 调试信息输出
        //
        return mailSender;
    }
}
