package soul.euphoria.services.mail;

public interface EmailSender {
    void sendEmailForConfirm(String email, String code);

    void sendEmailForResetPassword(String email, String resetPasswordCode);
}
