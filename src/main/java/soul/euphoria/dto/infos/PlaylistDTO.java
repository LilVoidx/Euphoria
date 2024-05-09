package soul.euphoria.dto.infos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import soul.euphoria.models.user.Playlist;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlaylistDTO {
    private Long playlistId;
    private String name;
    private String description;
    private LocalDate creationDate;
    private String coverImageInfoUrl;
    private List<SongDTO> songs;

    public static PlaylistDTO from(Playlist playlist) {
        String coverImageInfoUrl = null;
        if (playlist.getCoverImageInfo() != null) {
            coverImageInfoUrl = playlist.getCoverImageInfo().getStorageFileName();
        }

        return PlaylistDTO.builder()
                .playlistId(playlist.getPlaylistId())
                .name(playlist.getName())
                .description(playlist.getDescription())
                .creationDate(playlist.getCreationDate())
                .coverImageInfoUrl(coverImageInfoUrl)
                .songs(SongDTO.songList(playlist.getSongs()))
                .build();
    }

    public static List<PlaylistDTO> playlistList(List<Playlist> playlists) {
        return playlists.stream()
                .map(PlaylistDTO::from)
                .collect(Collectors.toList());
    }
}
