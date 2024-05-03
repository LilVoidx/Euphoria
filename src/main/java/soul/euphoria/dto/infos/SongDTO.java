package soul.euphoria.dto.infos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import soul.euphoria.models.Enum.Genre;
import soul.euphoria.models.music.Song;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SongDTO {
    private Long songId;
    private String title;
    private UserDTO artist;
    private AlbumDTO album;
    private Date releaseDate;
    private String duration;
    private String songFileInfoUrl;
    private String songImageInfoUrl;
    private Genre genre;

    public static SongDTO from(Song song) {
        String songFileInfoUrl = null;
        if (song.getSongFileInfo() != null) {
            songFileInfoUrl = song.getSongFileInfo().getStorageFileName();
        }

        String songImageInfoUrl = null;
        if (song.getSongImageInfo() != null) {
            songImageInfoUrl = song.getSongImageInfo().getStorageFileName();
        }

        return SongDTO.builder()
                .songId(song.getSongId())
                .title(song.getTitle())
                .artist(UserDTO.from(song.getArtist()))
                .album(AlbumDTO.from(song.getAlbum()))
                .releaseDate(song.getReleaseDate())
                .duration(song.getDuration())
                .songFileInfoUrl(songFileInfoUrl)
                .songImageInfoUrl(songImageInfoUrl)
                .genre(song.getGenre())
                .build();
    }

    public static List<SongDTO> SongList(List<Song> songs) {
        return songs.stream()
                .map(SongDTO::from)
                .collect(Collectors.toList());
    }
}
