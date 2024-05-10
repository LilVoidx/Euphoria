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
    private Long artistId;
    private String artistName;
    private String albumTitle;
    private Long albumId;
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
            System.out.println(songImageInfoUrl);
        }

        Long albumId = null;
        String albumTitle = null;
        if (song.getAlbum() != null) {
            albumId = song.getAlbum().getAlbumId();
            albumTitle = song.getAlbum().getTitle();
        }

        return SongDTO.builder()
                .songId(song.getSongId())
                .title(song.getTitle())
                .artistId(song.getArtist().getArtistId())
                .artistName(song.getArtist().getStageName())
                .albumId(albumId)
                .albumTitle(albumTitle)
                .releaseDate(song.getReleaseDate())
                .duration(song.getDuration())
                .songFileInfoUrl(songFileInfoUrl)
                .songImageInfoUrl(songImageInfoUrl)
                .genre(song.getGenre())
                .build();
    }


    public static List<SongDTO> songList(List<Song> songs) {
        return songs.stream()
                .map(SongDTO::from)
                .collect(Collectors.toList());
    }
}
