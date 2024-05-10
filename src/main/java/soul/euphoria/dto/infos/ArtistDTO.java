package soul.euphoria.dto.infos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import soul.euphoria.models.Enum.Genre;
import soul.euphoria.models.music.Album;
import soul.euphoria.models.music.Song;
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
    private List<Long> albumIds;
    private List<Long> songIds;

    public static ArtistDTO from(Artist artist) {
        return ArtistDTO.builder()
                .artistId(artist.getArtistId())
                .stageName(artist.getStageName())
                .bio(artist.getBio())
                .genre(artist.getGenre())
                .albumIds(artist.getAlbums().stream().map(Album::getAlbumId).collect(Collectors.toList()))
                .songIds(artist.getSongs().stream().map(Song::getSongId).collect(Collectors.toList()))
                .build();
    }

    public static List<ArtistDTO> artistList(List<Artist> artists) {
        return artists.stream()
                .map(ArtistDTO::from)
                .collect(Collectors.toList());
    }
}
