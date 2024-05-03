package soul.euphoria.services.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import soul.euphoria.dto.forms.UserForm;
import soul.euphoria.models.Enum.Role;
import soul.euphoria.models.Enum.State;
import soul.euphoria.models.user.User;
import soul.euphoria.repositories.user.UsersRepository;
import soul.euphoria.services.mail.EmailSender;
import soul.euphoria.services.user.RegisterService;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class RegisterServiceImpl implements RegisterService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailSender emailSender;

    @Autowired
    public RegisterServiceImpl(UsersRepository usersRepository, PasswordEncoder passwordEncoder, EmailSender emailSender) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailSender = emailSender;
    }

    @Override
    public User registerUser(UserForm userForm) {
        User user = User.builder()
                .username(userForm.getUsername())
                .email(userForm.getEmail())
                .password(passwordEncoder.encode(userForm.getPassword()))
                .firstName(userForm.getFirstName())
                .lastName(userForm.getLastName())
                .phoneNumber(userForm.getPhoneNumber())
                .role(Role.LISTENER)  // Default
                .state(State.NOT_CONFIRMED)  // Default
                .registrationDate(LocalDate.now())
                .confirmationCode(UUID.randomUUID().toString())
                .build();

        // Save user to repository
        user = usersRepository.save(user);
        // Send confirmation email
        emailSender.sendEmailForConfirm(user.getEmail(), user.getConfirmationCode());

        return user;
    }
}
