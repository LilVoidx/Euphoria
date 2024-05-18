package soul.euphoria.services.user;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import soul.euphoria.dto.forms.ArtistForm;
import soul.euphoria.dto.forms.UserForm;
import soul.euphoria.dto.infos.UserDTO;
import soul.euphoria.models.user.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    void resetPassword(String email);

    UserForm convertUserToForm(UserDTO userDto);

    void updateUser(String username, UserForm userForm, MultipartFile profilePicture);

    Page<UserDTO> searchUsers(String query, int page, int size);

    UserDTO getUserById(Long userId);

    String generateToken();

    Optional<UserDTO> findByEmail(String email);


    Optional<UserDTO> findByUserName(String userName);

    UserDTO getUserDTOByArtistId(Long artistId);

    User getCurrentUser(Long userId);

    boolean deleteUserByUsername(String username);
}
