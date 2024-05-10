package soul.euphoria.services.music.impl;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
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

import java.io.InputStream;
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

            // Get song duration from file metadata
            String songDuration = getSongDuration(songFile);

            // Create and save the song
            Song song = Song.builder()
                    .title(songForm.getTitle())
                    .releaseDate(releaseDate)
                    .duration(songDuration)
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

    //TODO: THIS METHOD IS NOT WORKING
    private String getSongDuration(MultipartFile songFile) {
        try {
            InputStream stream = songFile.getInputStream();
            Parser parser = new AutoDetectParser();
            Metadata metadata = new Metadata();
            ParseContext context = new ParseContext();
            BodyContentHandler handler = new BodyContentHandler();

            parser.parse(stream, handler, metadata, context);

            // Get the duration from the metadata
            String duration = metadata.get("Duration");
            if (duration != null) {
                // Parse the duration to minutes and seconds
                int seconds = (int) Double.parseDouble(duration);
                int minutes = seconds / 60;
                seconds %= 60;
                return String.format("%02d:%02d", minutes, seconds);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "00:00";
    }

}
