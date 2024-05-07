package soul.euphoria.services.user;

import soul.euphoria.dto.forms.ArtistForm;
import soul.euphoria.models.FileInfo;
import soul.euphoria.models.user.User;

import java.util.Optional;

public interface UserService {
    void registerAsArtist(User user, ArtistForm artistForm);
    void resetPassword(String email);
    User getUserById(Long userId);
    String generateToken();
    Optional<User> findByEmail(String email);
}
