package soul.euphoria.services.music.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import soul.euphoria.dto.forms.AlbumForm;
import soul.euphoria.dto.infos.AlbumDTO;
import soul.euphoria.dto.infos.SongDTO;
import soul.euphoria.models.Enum.Genre;
import soul.euphoria.models.music.Album;
import soul.euphoria.models.music.Song;
import soul.euphoria.models.user.Artist;
import soul.euphoria.repositories.music.AlbumRepository;
import soul.euphoria.repositories.music.SongRepository;
import soul.euphoria.repositories.user.ArtistRepository;
import soul.euphoria.services.file.FileStorageService;
import soul.euphoria.services.music.AlbumService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AlbumServiceImpl implements AlbumService {

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Override
    public Album createAlbum(AlbumForm albumForm, MultipartFile imageFile, Long userId) {
        // Get the artist by user ID
        Optional<Artist> artistOptional = artistRepository.findArtistByUserUserId(userId);
        if (artistOptional.isPresent()) {

            Artist artist = artistOptional.get();

            // Save cover image
            String coverImageFileName = fileStorageService.saveFile(imageFile);

            // Convert releaseDate string from Form to Date for Song
            Date releaseDate;
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                releaseDate = dateFormat.parse(albumForm.getReleaseDate());

            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid release date format");
            }

            // Create Album
            Album album = Album.builder()
                    .title(albumForm.getTitle())
                    .genre(Genre.valueOf(albumForm.getGenre()))
                    .coverImageInfo(fileStorageService.findByStorageName(coverImageFileName))
                    .releaseDate(releaseDate)
                    .artist(artist)
                    .build();

            // Save the album
            album = albumRepository.save(album);
            return album;
        }
        return null;
    }


    @Override
    public AlbumDTO getAlbumDetails(Long albumId) {
        Album album = albumRepository.findById(albumId).orElse(null);
        if (album != null) {
            return AlbumDTO.from(album);
        }
        return null;
    }

    @Override
    public List<SongDTO> getAlbumSongs(Long albumId) {
        return songRepository.findAllByAlbumId(albumId);
    }

}
