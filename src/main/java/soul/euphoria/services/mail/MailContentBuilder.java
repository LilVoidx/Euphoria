package soul.euphoria.services.mail;

public interface MailContentBuilder {
    String buildConfirmationEmail(String code);

    String buildResetPasswordEmail(String resetPasswordCode);
}
