package soul.euphoria.models.music;

import lombok.*;
import soul.euphoria.models.FileInfo;
 import soul.euphoria.models.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playlistId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User user;

    @NotBlank
    @Column(nullable = false)
    private String name;

    private String description;

    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;

    @OneToOne
    @JoinColumn(name = "cover_image_id", referencedColumnName = "fileInfoId")
    private FileInfo coverImageInfo;

    @ManyToMany
    @JoinTable(
            name = "playlist_songs",
            joinColumns = @JoinColumn(name = "playlist_id", referencedColumnName = "playlistId"),
            inverseJoinColumns = @JoinColumn(name = "song_id", referencedColumnName = "songId")
    )
    private List<Song> songs;
}
