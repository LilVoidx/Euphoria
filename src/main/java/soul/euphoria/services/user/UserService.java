package soul.euphoria.services.user;

import soul.euphoria.dto.forms.ArtistForm;
import soul.euphoria.models.user.User;

public interface UserService {
    void registerAsArtist(User user, ArtistForm artistForm);
    void resetPassword(String email);
    User getUserById(Long userId);
    String generateToken();
}
