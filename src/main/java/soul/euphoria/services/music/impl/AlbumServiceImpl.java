package soul.euphoria.services.music.impl;

import javassist.NotFoundException;
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
import soul.euphoria.services.converters.StringToDateConverter;
import soul.euphoria.services.file.FileStorageService;
import soul.euphoria.services.music.AlbumService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static soul.euphoria.dto.infos.AlbumDTO.albumList;

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

    @Autowired
    private StringToDateConverter stringToDateConverter;

    @Override
    public Album createAlbum(AlbumForm albumForm, MultipartFile imageFile, Long userId) {
        // Get the artist by user ID
        Optional<Artist> artistOptional = artistRepository.findArtistByUserUserId(userId);
        if (artistOptional.isPresent()) {

            Artist artist = artistOptional.get();

            // Save cover image
            String coverImageFileName = fileStorageService.saveFile(imageFile);

            // Convert releaseDate string from Form to Date for album
            Date releaseDate = stringToDateConverter.convert(albumForm.getReleaseDate());


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
    public void addSongToAlbum(Long songId, Long albumId) {
        // Retrieve the song and album entities
        Optional<Song> songOptional = songRepository.findById(songId);
        Optional<Album> albumOptional = albumRepository.findById(albumId);

        if (songOptional.isPresent() && albumOptional.isPresent()) {
            Song song = songOptional.get();
            Album album = albumOptional.get();

            // Set the album for the song
            song.setAlbum(album);

            // Save the updated song
            songRepository.save(song);
        } else {
            throw new IllegalArgumentException("Song or Album not found");
        }
    }

    @Override
    public void removeSongFromAlbum(Long songId, Long albumId) {
        // Retrieve the song and album entities
        Optional<Song> songOptional = songRepository.findById(songId);
        Optional<Album> albumOptional = albumRepository.findById(albumId);

        if (songOptional.isPresent() && albumOptional.isPresent()) {
            Song song = songOptional.get();
            Album album = albumOptional.get();

            // Check if the song is currently associated with the album
            if (song.getAlbum() != null && song.getAlbum().getAlbumId().equals(albumId)) {
                // Remove the association
                song.setAlbum(null);

                // Save the updated song
                songRepository.save(song);
            } else {
                throw new IllegalArgumentException("The song is not associated with the specified album");
            }
        } else {
            throw new IllegalArgumentException("Song or Album not found");
        }
    }


    @Override
    public List<AlbumDTO> findAllAlbumsByArtist(Long artistId) {
        return albumList(albumRepository.findByArtistArtistId(artistId));
    }

    @Override
    public void deleteAlbum(Long albumId) throws NotFoundException {
        Optional<Album> optionalAlbum = albumRepository.findById(albumId);
        if(optionalAlbum.isPresent()){
            Album album = optionalAlbum.get();
            albumRepository.delete(album);
        } else {
            throw new NotFoundException("Album not found with ID: " +albumId);
        }
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
