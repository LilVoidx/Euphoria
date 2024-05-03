package soul.euphoria.services.user;

import soul.euphoria.dto.forms.ArtistForm;
import soul.euphoria.models.user.User;

public interface UserService {
    void registerAsArtist(User user, ArtistForm artistForm);
    User getUserById(Long userId);
}
