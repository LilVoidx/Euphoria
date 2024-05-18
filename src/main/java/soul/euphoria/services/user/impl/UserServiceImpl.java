package soul.euphoria.services.user.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import soul.euphoria.dto.forms.UserForm;
import soul.euphoria.dto.infos.UserDTO;
import soul.euphoria.models.user.Artist;
import soul.euphoria.models.user.User;
import soul.euphoria.repositories.user.ArtistRepository;
import soul.euphoria.repositories.user.UsersRepository;
import soul.euphoria.services.file.FileStorageService;
import soul.euphoria.services.mail.EmailSender;
import soul.euphoria.services.user.UserService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static soul.euphoria.dto.infos.UserDTO.userList;

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
    public UserForm convertUserToForm(UserDTO userDto) {
        return UserForm.builder()
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .phoneNumber(userDto.getPhoneNumber())
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
    public Page<UserDTO> searchUsers(String query, int page, int size) {
        Page<User> usersPage = userRepository.searchUsersByQuery(query, PageRequest.of(page, size));
        return usersPage.map(UserDTO::from);
    }

    @Override
    public UserDTO getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return UserDTO.from(user);
    }

    @Override
    public String generateToken() {
        // Generate a random token for reset password
        return UUID.randomUUID().toString();
    }

    @Override
    public Optional<UserDTO> findByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.map(UserDTO::from);
    }

    @Override
    public Optional<UserDTO> findByUserName(String userName) {
        Optional<User> user = userRepository.findByUsername(userName);
        return user.map(UserDTO::from);
    }

    @Override
    public UserDTO getUserDTOByArtistId(Long artistId) {
        Artist artist = artistRepository.getOne(artistId);
        User user = artist.getUser();
        return UserDTO.from(user);
    }

    @Override
    public User getCurrentUser(Long userId) {
        return userRepository.getOne(userId);
    }

    @Override
    public boolean deleteUserByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            userRepository.delete(user);
            return true;
        } else {
            return false;
        }
    }
}
