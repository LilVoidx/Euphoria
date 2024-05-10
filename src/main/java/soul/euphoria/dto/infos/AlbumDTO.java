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
    private Long artistId;
    private String artistName;
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
                .artistId(album.getArtist().getArtistId())
                .artistName(album.getArtist().getStageName())
                .releaseDate(album.getReleaseDate())
                .coverImageInfoUrl(coverImageInfoUrl)
                .genre(album.getGenre())
                .songs(SongDTO.songList(album.getSongs()))
                .build();
    }

    public static List<AlbumDTO> albumList (List<Album> albums) {
        return albums.stream()
                .map(AlbumDTO::from)
                .collect(Collectors.toList());
    }
}
