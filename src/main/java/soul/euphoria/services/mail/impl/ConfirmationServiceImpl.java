package soul.euphoria.services.mail.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import soul.euphoria.models.Enum.State;
import soul.euphoria.models.user.User;
import soul.euphoria.repositories.user.UsersRepository;
import soul.euphoria.services.mail.ConfirmationService;

import java.util.Optional;

@Service
public class ConfirmationServiceImpl implements ConfirmationService {

    private static final Logger logger = LoggerFactory.getLogger(ConfirmationServiceImpl.class);

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public boolean confirmUser(String code) {
        Optional<User> oUser = usersRepository.findByConfirmationCode(code);
        if (oUser.isPresent()) {
            User user = oUser.get();
            user.setState(State.CONFIRMED);
            usersRepository.save(user);
            logger.info("User confirmed: {}", user.getUsername());
            return true;
        } else {
            logger.warn("User not found for confirmation code: {}", code);
            return false;
        }
    }

    @Override
    public void resetPassword(String resetPasswordToken, String newPassword) {
        Optional<User> oUser = usersRepository.findByConfirmationCode(resetPasswordToken);
        if (oUser.isPresent()) {
            User user = oUser.get();
            user.setPassword(passwordEncoder.encode(newPassword));
            usersRepository.save(user);
            logger.info("Password reset for user: {}", user.getUsername());
        } else {
            logger.warn("User not found for reset password token: {}", resetPasswordToken);
        }
    }
}
