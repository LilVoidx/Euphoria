package soul.euphoria.dto.infos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import soul.euphoria.models.Enum.Genre;
import soul.euphoria.models.user.Artist;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArtistDTO {
    private Long artistId;
    private String stageName;
    private String bio;
    private Genre genre;
    private List<AlbumDTO> albums;
    private List<SongDTO> songs;

    public static ArtistDTO from(Artist artist) {
        return ArtistDTO.builder()
                .artistId(artist.getArtistId())
                .stageName(artist.getStageName())
                .bio(artist.getBio())
                .genre(artist.getGenre())
                .albums(AlbumDTO.albumList(artist.getAlbums()))
                .songs(SongDTO.songList(artist.getSongs()))
                .build();
    }

    public static List<ArtistDTO> artistList(List<Artist> artists) {
        return artists.stream()
                .map(ArtistDTO::from)
                .collect(Collectors.toList());
    }
}
