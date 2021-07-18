package ac.id.polman.astra.futsal.repository;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.mail.internet.MimeMessage;
import java.io.InputStream;

public interface JavaMailSender extends MailSender {
    MimeMessage createMimeMessage();

    MimeMessage createMimeMessage(InputStream var1) throws MailException;

    void send(MimeMessage var1) throws MailException;

    void send(MimeMessage... var1) throws MailException;

    void send(MimeMessagePreparator var1) throws MailException;

    void send(MimeMessagePreparator... var1) throws MailException;
}