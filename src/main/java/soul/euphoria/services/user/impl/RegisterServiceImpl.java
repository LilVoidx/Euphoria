package soul.euphoria.services.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import soul.euphoria.dto.forms.UserForm;
import soul.euphoria.models.Enum.Role;
import soul.euphoria.models.Enum.State;
import soul.euphoria.models.user.User;
import soul.euphoria.repositories.user.UsersRepository;
import soul.euphoria.services.file.FileStorageService;
import soul.euphoria.services.mail.EmailSender;
import soul.euphoria.services.user.RegisterService;
import soul.euphoria.services.user.UserService;

import java.time.LocalDate;
import java.util.regex.Pattern;

@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private UserService userService;

    @Autowired
    private FileStorageService fileStorageService;

    @Override
    public User registerUser(UserForm userForm) {
        // Check if passwords match
        if (!userForm.getPassword().equals(userForm.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        String phoneNumberPattern = "^7\\d{10}$";
        if (!Pattern.matches(phoneNumberPattern, userForm.getPhoneNumber())) {
            throw new IllegalArgumentException("Phone number Incorrect!");
        }

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
                .confirmationCode(userService.generateToken())
                .build();

        // Save user to repository
        user = usersRepository.save(user);

        return user;
    }

    @Override
    public void uploadProfilePicture(Long userId, MultipartFile profilePicture) {
        // Retrieve the user by ID
        User user = usersRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Save the profile picture and associate it with the user
        String storageFileName = fileStorageService.saveFile(profilePicture);
        user.setProfilePicture(fileStorageService.findByStorageName(storageFileName));

        // Update the user
        usersRepository.save(user);
        // Send confirmation email
        emailSender.sendEmailForConfirm(user.getEmail(), user.getConfirmationCode());
    }
}
