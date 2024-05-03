package soul.euphoria.services.user;

import soul.euphoria.dto.forms.UserForm;
import soul.euphoria.models.user.User;

public interface RegisterService {
    User registerUser(UserForm userForm);
}
