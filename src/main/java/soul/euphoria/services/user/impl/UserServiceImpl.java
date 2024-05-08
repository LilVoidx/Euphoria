package soul.euphoria.services.user.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import soul.euphoria.dto.forms.ArtistForm;
import soul.euphoria.dto.forms.UserForm;
import soul.euphoria.models.Enum.Role;
import soul.euphoria.models.user.Artist;
import soul.euphoria.models.user.User;
import soul.euphoria.repositories.user.ArtistRepository;
import soul.euphoria.repositories.user.UsersRepository;
import soul.euphoria.services.file.FileStorageService;
import soul.euphoria.services.mail.EmailSender;
import soul.euphoria.services.user.UserService;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private FileStorageService fileStorageService;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    @Override
    public void registerAsArtist(User user, ArtistForm artistForm) {
        if (user != null) {
            // Check if the user is already an artist
            if (user.getRole() == Role.ARTIST) {
                throw new IllegalArgumentException("User is already registered as an artist");
            }

            // Update user role to ARTIST
            user.setRole(Role.ARTIST);
            userRepository.save(user);

            // Create and save Artist object
            Artist artist = Artist.builder()
                    .user(user)
                    .stageName(artistForm.getStageName())
                    .bio(artistForm.getBio())
                    .genre(artistForm.getGenre())
                    .build();
            artistRepository.save(artist);
        } else {
            throw new IllegalArgumentException("User cannot be null");
        }
    }

    @Override
    public void resetPassword(String email) {
        // Generate a reset password token
        String resetPasswordToken = generateToken();

        // Find the user by email
        Optional<User> oUser = userRepository.findByEmail(email);

        if (oUser.isPresent()) {
            // Set the reset password token for the user
            User user = oUser.get();
            user.setConfirmationCode(resetPasswordToken);
            userRepository.save(user);

            // Send reset password email to the provided email address
            emailSender.sendEmailForResetPassword(email, resetPasswordToken);

            logger.info("Reset password email sent to: {}", email);
        } else {
            logger.warn("User not found for email: {}", email);
        }
    }

    @Override
    public UserForm convertUserToForm(User user) {
        return UserForm.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .confirmPassword(user.getPassword())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .profilePicture(null)
                .build();
    }

    @Override
    public void updateUser(String username, UserForm userForm, MultipartFile profilePicture) {
        // Retrieve user by username
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            // Save the new profile picture if provided
            String storageFileName = null;
            if (profilePicture != null && !profilePicture.isEmpty()) {
                storageFileName = fileStorageService.saveFile(profilePicture);
            }

            // Update user information using builder pattern
            user = User.builder()
                    .userId(user.getUserId())
                    .username(userForm.getUsername())
                    .email(userForm.getEmail())
                    .firstName(userForm.getFirstName())
                    .lastName(userForm.getLastName())
                    .phoneNumber(userForm.getPhoneNumber())
                    .password(user.getPassword())
                    .registrationDate(user.getRegistrationDate())
                    .role(user.getRole())
                    .state(user.getState())
                    .confirmationCode(user.getConfirmationCode())
                    .profilePicture(storageFileName != null ? fileStorageService.findByStorageName(storageFileName) : user.getProfilePicture())
                    .build();

            // Save the updated user
            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("User not found with username: " + username);
        }
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    @Override
    public String generateToken() {
        // Generate a random token for reset password
        return UUID.randomUUID().toString();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);

    }

    @Override
    public Optional<User> findByUserName(String userName) {
        return userRepository.findByUsername(userName);
    }

}
