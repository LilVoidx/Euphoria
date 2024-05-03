package soul.euphoria.services.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soul.euphoria.dto.forms.ArtistForm;
import soul.euphoria.models.Enum.Role;
import soul.euphoria.models.user.Artist;
import soul.euphoria.models.user.User;
import soul.euphoria.repositories.user.ArtistRepository;
import soul.euphoria.repositories.user.UsersRepository;
import soul.euphoria.services.user.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private ArtistRepository artistRepository;

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
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

}
