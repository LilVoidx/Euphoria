package soul.euphoria.dto.infos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import soul.euphoria.models.music.Album;
import soul.euphoria.models.Enum.Genre;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlbumDTO {
    private Long albumId;
    private String title;
    private UserDTO artist;
    private LocalDate releaseDate;
    private String coverImageInfoUrl;
    private Genre genre;
    private List<SongDTO> songs;

    public static AlbumDTO from(Album album) {
        String coverImageInfoUrl = null;
        if (album.getCoverImageInfo() != null) {
            coverImageInfoUrl = album.getCoverImageInfo().getStorageFileName();
        }

        return AlbumDTO.builder()
                .albumId(album.getAlbumId())
                .title(album.getTitle())
                .artist(UserDTO.from(album.getArtist()))
                .releaseDate(album.getReleaseDate())
                .coverImageInfoUrl(coverImageInfoUrl)
                .genre(album.getGenre())
                .songs(album.getSongs().stream().map(SongDTO::from).collect(Collectors.toList()))
                .build();
    }

    public static List<AlbumDTO> AlbumList (List<Album> albums) {
        return albums.stream()
                .map(AlbumDTO::from)
                .collect(Collectors.toList());
    }
}
