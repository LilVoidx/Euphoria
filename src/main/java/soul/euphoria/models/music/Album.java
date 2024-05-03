package soul.euphoria.models.music;

import lombok.*;
import soul.euphoria.models.Enum.Genre;
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
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long albumId;

    @NotBlank
    @Column(nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "artist_id", referencedColumnName = "userId")
    private User artist;

    private LocalDate releaseDate;

    @OneToOne
    @JoinColumn(name = "cover_image_id", referencedColumnName = "fileInfoId")
    private FileInfo coverImageInfo;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Genre genre;


    @OneToMany(mappedBy = "album")
    private List<Song> songs;
}
