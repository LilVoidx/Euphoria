package soul.euphoria.services.mail.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import soul.euphoria.services.mail.EmailSender;
import soul.euphoria.services.mail.MailContentBuilder;

@Service
public class EmailSenderImpl implements EmailSender {

    private static final Logger logger = LoggerFactory.getLogger(EmailSenderImpl.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String mailFrom;

    @Autowired
    private MailContentBuilder mailContentBuilder;

    @Override
    public void sendEmailForConfirm(String email, String code) {
        // Build email content
        String mailText = mailContentBuilder.buildConfirmationEmail(code);
        // Prepare email message
        MimeMessagePreparator messagePreparator = getEmail(email, mailText, "Email Confirmation");
        // Send email
        sendEmail(messagePreparator, email);
    }

    @Override
    public void sendEmailForResetPassword(String email, String resetPasswordCode) {
        // Build email content
        String mailText = mailContentBuilder.buildResetPasswordEmail(resetPasswordCode);
        // Prepare email message
        MimeMessagePreparator messagePreparator = getEmail(email, mailText, "Password Reset");
        // Send email
        sendEmail(messagePreparator, email);
    }

    private MimeMessagePreparator getEmail(String email, String mailText, String subject) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(mailFrom);
            messageHelper.setTo(email);
            messageHelper.setSubject(subject);
            messageHelper.setText(mailText, true);
        };
    }

    private void sendEmail(MimeMessagePreparator messagePreparator, String email) {
        try {
            // Send email
            javaMailSender.send(messagePreparator);
            logger.info("Email sent to: {}", email);
        } catch (MailException e) {
            // Log error if sending fails
            logger.error("Error occurred while sending email to: {}", email, e);
        }
    }
}
