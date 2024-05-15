package soul.euphoria.services.music.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import soul.euphoria.models.Enum.Genre;
import soul.euphoria.models.music.Song;
import soul.euphoria.models.user.Artist;
import soul.euphoria.repositories.music.SongRepository;
import soul.euphoria.repositories.user.ArtistRepository;
import soul.euphoria.services.file.FileStorageService;
import soul.euphoria.dto.forms.SongForm;
import soul.euphoria.services.music.SongService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SongServiceImpl implements SongService {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private ArtistRepository artistRepository;

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
    public Song findById(Long songId) {
        return songRepository.findById(songId).orElse(null);
    }

    @Override
    public List<Song> getSongsByArtist(Artist artist) {
        return songRepository.findAllByArtist(artist);
    }

    @Override
    public List<Song> getAllSongs() {
        return songRepository.findAll();
    }

}
