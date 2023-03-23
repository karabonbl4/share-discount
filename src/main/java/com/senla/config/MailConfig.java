package com.senla.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Base64;
import java.util.Properties;

@Configuration
public class MailConfig {

    @Value("${mail.host}")
    private String mailHost;

    @Value("${mail.port}")
    private Integer mailPort;

    @Value("${mail.username}")
    private String mailUsername;

    @Value("${mail.password}")
    private String mailPassword;

    @Value("${mail.properties.mail.smtp.auth}")
    private String mailSmtpAuth;

    @Value("${mail.properties.mail.smtp.starttls.enable}")
    private String mailStarttlsEnable;

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailHost);
        mailSender.setPort(mailPort);

        mailSender.setUsername(mailUsername);
        mailSender.setPassword(mailPassword);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", mailSmtpAuth);
        props.put("mail.smtp.starttls.enable", mailStarttlsEnable);


        return mailSender;
    }
}
