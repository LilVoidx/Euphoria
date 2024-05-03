package soul.euphoria.services.mail;

public interface ConfirmationService {
    boolean confirmUser(String code);
}
