package soul.euphoria.services.music.impl;

import javassist.NotFoundException;
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
import soul.euphoria.services.file.FileStorageService;
import soul.euphoria.dto.forms.SongForm;
import soul.euphoria.services.music.SongService;

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
            Date releaseDate = null;
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                releaseDate = dateFormat.parse(songForm.getReleaseDate());

            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid release date format");
            }

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
    public SongDTO favorite(Long userId, Long songId) {
        Optional<User> optionalUser = usersRepository.findById(userId);
        Optional<Song> optionalSong = songRepository.findById(songId);

        if (optionalUser.isPresent() && optionalSong.isPresent()) {
            User user = optionalUser.get();
            Song song = optionalSong.get();

            if (songRepository.existsBySongIdAndFavoritesContaining(songId, user)) {
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
        Song trending = songRepository.findTopByOrderByFavoritesAsc();
        return SongDTO.from(trending);
    }

}
