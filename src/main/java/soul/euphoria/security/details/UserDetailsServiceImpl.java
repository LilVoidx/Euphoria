package soul.euphoria.security.details;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import soul.euphoria.models.user.User;
import soul.euphoria.repositories.user.UsersRepository;

import java.util.Optional;

@Component("customUserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = usersRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            return new UserDetailsImpl(userOptional.get());
        }
        else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}