package com.happiday.Happi_Day.domain.service;

import com.happiday.Happi_Day.exception.CustomException;
import com.happiday.Happi_Day.exception.ErrorCode;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String username;

    // 인증코드 만들기
    public String createNumber() {
        Random random = new Random();
        String code = String.format("%06d", random.nextInt(1000000));
        return code;
    }

    public void sendEmail(String to, String code) throws Exception {
        MimeMessage message = createMessage(to, code);
        try {
            javaMailSender.send(message);
        } catch (MailException es) {
            throw new CustomException(ErrorCode.MAIL_SEND_ERROR);
        }
    }

    public MimeMessage createMessage(String to, String code) throws Exception {
        MimeMessage message = javaMailSender.createMimeMessage();

        message.addRecipients(MimeMessage.RecipientType.TO, to);
        message.setSubject("HappiDay 이메일 인증 코드입니다.");
        message.setText("이메일 인증코드: " + code);
        message.setFrom(new InternetAddress(username + "@naver.com", "HappiDay"));
        return message;
    }


}
