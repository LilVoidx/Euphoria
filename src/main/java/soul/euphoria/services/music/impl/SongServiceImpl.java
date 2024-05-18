package soul.euphoria.services.music.impl;

import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import soul.euphoria.dto.infos.SongDTO;
import soul.euphoria.models.Enum.Genre;
import soul.euphoria.models.music.Song;
import soul.euphoria.models.user.Artist;
import soul.euphoria.models.user.User;
import soul.euphoria.repositories.music.SongRepository;
import soul.euphoria.repositories.user.ArtistRepository;
import soul.euphoria.repositories.user.UsersRepository;
import soul.euphoria.services.converters.StringToDateConverter;
import soul.euphoria.services.file.FileStorageService;
import soul.euphoria.dto.forms.SongForm;
import soul.euphoria.services.music.SongService;
import soul.euphoria.services.notification.impl.PusherService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static soul.euphoria.dto.infos.SongDTO.songList;

@Service
public class SongServiceImpl implements SongService {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private StringToDateConverter stringToDateConverter;

    @Autowired
    private PusherService pusherService;

    private static final Logger logger = LoggerFactory.getLogger(SongService.class);


    @Override
    public void uploadSong(SongForm songForm, MultipartFile songFile, MultipartFile imageFile, Long userId) {

        // Get the artist by user ID
        Optional<Artist> artistOptional = artistRepository.findArtistByUserUserId(userId);
        if (artistOptional.isPresent()) {
            Artist artist = artistOptional.get();

            // Save song file
            String songFileName = fileStorageService.saveFile(songFile);

            // Save image file
            String imageFileName = fileStorageService.saveFile(imageFile);

            // Convert releaseDate string from Form to Date for Song
            Date releaseDate = stringToDateConverter.convert(songForm.getReleaseDate());


            // Create and save the song
            Song song = Song.builder()
                    .title(songForm.getTitle())
                    .releaseDate(releaseDate)
                    .duration(songForm.getDuration())
                    .songFileInfo(fileStorageService.findByStorageName(songFileName))
                    .songImageInfo(fileStorageService.findByStorageName(imageFileName))
                    .genre(Genre.valueOf(songForm.getGenre()))
                    .artist(artist)
                    .build();
            songRepository.save(song);

            // Notify all users about the new song upload
            String message = "A new song has been uploaded by " + artist.getStageName();
            pusherService.sendNotification("music-channel", "new-song", message, song.getTitle());
        }
    }

    @Override
    public void deleteSong(Long songId) throws NotFoundException {
        Optional<Song> optionalSong = songRepository.findById(songId);
        if (optionalSong.isPresent()) {
            Song song = optionalSong.get();
            songRepository.delete(song);
        } else {
            throw new NotFoundException("Song not found with ID: " + songId);
        }
    }

    @Override
    public List<SongDTO> getAllUserFavorites(String username) {
        List<SongDTO> favoriteSongs =songList(songRepository.findAllFavoritesByUsername(username));
        logger.info("Found {} favorite songs for user: {}", favoriteSongs.size(), username);

        return favoriteSongs;
    }

    @Override
    public SongDTO favorite(Long userId, Long songId) {
        Optional<User> optionalUser = usersRepository.findById(userId);
        Optional<Song> optionalSong = songRepository.findById(songId);

        if (optionalUser.isPresent() && optionalSong.isPresent()) {
            User user = optionalUser.get();
            String username = user.getUsername();
            Song song = optionalSong.get();

            if (isSongFavoritedByCurrentUser(songId, username)) {
                user.getFavoriteSongs().remove(song);
            } else {
                user.getFavoriteSongs().add(song);
            }

            usersRepository.save(user);

            return SongDTO.from(song);
        } else {
            throw new IllegalArgumentException("User or song not found");
        }
    }

    @Override
    public boolean isSongFavoritedByCurrentUser(Long songId, String currentUsername) {
        Optional<User> optionalCurrentUser = usersRepository.findByUsername(currentUsername);
        User currentUser = optionalCurrentUser.orElseThrow(() -> new IllegalArgumentException("Current user not found"));

        return songRepository.existsBySongIdAndFavoritesContaining(songId, currentUser);
    }

    @Override
    public SongDTO findById(Long songId) {
        Song song = songRepository.findById(songId).orElse(null);
        assert song != null;
        return SongDTO.from(song);
    }

    @Override
    public List<SongDTO> getSongsByArtist(Artist artist) {
        return songList(songRepository.findAllByArtist(artist));
    }

    @Override
    public List<SongDTO> getSongsByArtistAlbumNull(Artist artist) {
        return songList(songRepository.findAllByArtistAndAlbumIsNull(artist));
    }

    @Override
    public List<SongDTO> getAllSongs() {
        return songList(songRepository.findAll());
    }

    @Override
    public List<SongDTO> search(String query) {
        return songList(songRepository.findByTitleStartingWithIgnoreCase(query));
    }

    @Override
    public Song getCurrentSong(Long songId) {
        return songRepository.findById(songId).orElse(null);
    }

    @Override
    public SongDTO getTrendingSong() {
        Song trending = songRepository.findTrending();
        return SongDTO.from(trending);
    }

}
