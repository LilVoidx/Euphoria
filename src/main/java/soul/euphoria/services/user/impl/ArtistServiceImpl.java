package soul.euphoria.services.user.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soul.euphoria.dto.forms.ArtistForm;
import soul.euphoria.dto.infos.ArtistDTO;
import soul.euphoria.models.Enum.Role;
import soul.euphoria.models.music.Song;
import soul.euphoria.models.user.Artist;
import soul.euphoria.models.user.User;
import soul.euphoria.repositories.music.SongRepository;
import soul.euphoria.repositories.user.ArtistRepository;
import soul.euphoria.repositories.user.UsersRepository;
import soul.euphoria.services.user.ArtistService;

import java.util.Optional;

@Service
public class ArtistServiceImpl implements ArtistService {

    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private SongRepository songRepository;

    private static final Logger logger = LoggerFactory.getLogger(ArtistServiceImpl.class);

    @Override
    public void registerAsArtist(User user, ArtistForm artistForm) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

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

        logger.info("User {} registered as artist with stage name {}", user.getUsername(), artistForm.getStageName());
    }

    @Override
    public void updateArtist(String username, ArtistForm artistForm) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            logger.warn("User not found with username: {}", username);
            throw new IllegalArgumentException("User not found with username: " + username);
        }

        User user = optionalUser.get();
        if (user.getArtist() == null) {
            throw new IllegalArgumentException("User is not registered as an artist");
        }

        Artist artist = user.getArtist();
        artist.setStageName(artistForm.getStageName());
        artist.setBio(artistForm.getBio());
        artist.setGenre(artistForm.getGenre());
        artistRepository.save(artist);

        logger.info("Artist {} updated for user {}", artist.getStageName(), username);
    }

    @Override
    public ArtistForm convertArtistToForm(Artist artist) {
        if (artist == null) {
            throw new IllegalArgumentException("Artist cannot be null");
        }

        return ArtistForm.builder()
                .stageName(artist.getStageName())
                .bio(artist.getBio())
                .genre(artist.getGenre())
                .build();
    }

    @Override
    public ArtistDTO getArtistBySongId(Long songId) {
        Optional<Song> optionalSong = songRepository.findById(songId);
        if (optionalSong.isEmpty()) {
            logger.warn("Song not found with ID: {}", songId);
            throw new IllegalArgumentException("Song not found with ID: " + songId);
        }

        Song song = optionalSong.get();
        Artist artist = song.getArtist();
        if (artist == null) {
            throw new IllegalArgumentException("Artist not found for song with ID: " + songId);
        }

        return ArtistDTO.from(artist);
    }
}
