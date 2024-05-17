package soul.euphoria.services.user.impl;

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
    public void updateArtist(String username, ArtistForm artistForm) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getArtist() != null) {
                Artist artist = user.getArtist();
                artist.setStageName(artistForm.getStageName());
                artist.setBio(artistForm.getBio());
                artist.setGenre(artistForm.getGenre());
                artistRepository.save(artist);
            } else {
                throw new IllegalArgumentException("User is not registered as an artist");
            }
        }
    }

    @Override
    public ArtistForm convertArtistToForm(Artist artist) {
        return ArtistForm.builder()
                .stageName(artist.getStageName())
                .bio(artist.getBio())
                .genre(artist.getGenre())
                .build();
    }
    @Override
    public ArtistDTO getArtistBySongId(Long songId) {
        Song song = songRepository.findById(songId).orElse(null);
        if (song != null) {
            Artist artist = song.getArtist();
            return ArtistDTO.from(artist);
        }
        return null;
    }
}
