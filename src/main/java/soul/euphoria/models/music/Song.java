package soul.euphoria.models.music;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import soul.euphoria.models.Enum.Genre;
import soul.euphoria.models.FileInfo;
import soul.euphoria.models.user.Artist;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long songId;

    @NotBlank
    @Size(max = 8)
    private String title;

    @ManyToOne
    @JoinColumn(name = "artist_id", referencedColumnName = "artistId")
    private Artist artist;

    @ManyToOne
    @JoinColumn(name = "album_id", referencedColumnName = "albumId")
    private Album album;

    private Date releaseDate;

    private String duration;

    @OneToOne
    @JoinColumn(name = "song_file_id", referencedColumnName = "fileInfoId")
    private FileInfo songFileInfo;

    @OneToOne
    @JoinColumn(name = "image_info_id", referencedColumnName = "fileInfoId")
    private FileInfo songImageInfo;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Genre genre;
}
