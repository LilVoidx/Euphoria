package soul.euphoria.services.mail.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public boolean confirmUser(String code) {
        // Find user by confirmation code
        Optional<User> oUser = usersRepository.findByConfirmationCode(code);

        // If user exists, update its state to CONFIRMED
        if (oUser.isPresent()) {
            User user = oUser.get();
            user.setState(State.CONFIRMED);
            try {
                // Save the updated user
                usersRepository.save(user);
                logger.info("User confirmed: {}", user.getUsername());
                return true;
            } catch (Exception e) {
                // Log error if saving fails
                logger.error("Error occurred while saving user confirmation", e);
                return false;
            }
        } else {
            // Log warning if user not found
            logger.warn("User not found for confirmation code: {}", code);
            return false;
        }
    }
}
