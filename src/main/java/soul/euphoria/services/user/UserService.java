package soul.euphoria.services.user;

import org.springframework.web.multipart.MultipartFile;
import soul.euphoria.dto.forms.ArtistForm;
import soul.euphoria.dto.forms.UserForm;
import soul.euphoria.models.user.User;

import java.util.Optional;

public interface UserService {

    void resetPassword(String email);

    UserForm convertUserToForm(User user);

    void updateUser(String username, UserForm userForm, MultipartFile profilePicture);

    User getUserById(Long userId);

    String generateToken();

    Optional<User> findByEmail(String email);


    Optional<User> findByUserName(String userName);

}
