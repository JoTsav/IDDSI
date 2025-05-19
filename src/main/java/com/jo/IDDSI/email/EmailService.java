package com.jo.IDDSI.email;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;


@Service
@AllArgsConstructor
public class EmailService implements EmailSender {


    private final static Logger LOGGER =
            LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailsender;


    @Override
    @Async // ideally use a queue for retrying with email sending
    public void send(String to, String email) {
        try {

            MimeMessage mimeMessage = mailsender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(email, true); // HTML email
            helper.setTo(to);
            helper.setSubject("Confirm your email");
            helper.setFrom("jotshava@gmail.com");

            mailsender.send(mimeMessage);
        } catch(MessagingException e) {
            LOGGER.error("Failed to send email", e);
            throw new IllegalStateException("Failed to send email");
        }

    }
}
