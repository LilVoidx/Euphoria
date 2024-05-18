package soul.euphoria.models.user;

import lombok.*;
import soul.euphoria.models.*;
import soul.euphoria.models.Enum.Role;
import soul.euphoria.models.Enum.State;
import soul.euphoria.models.music.Playlist;
import soul.euphoria.models.music.Song;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotBlank
    @Size(max = 8)
    @Column(nullable = false, unique = true)
    private String username;

    @NotBlank
    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank
    @Size(min = 8)
    @Column(nullable = false)
    private String password;

    @NotBlank
    @Column(nullable = false)
    private String firstName;

    @NotBlank
    @Column(nullable = false)
    private String lastName;

    @Pattern(regexp = "^7\\d{10}$", message = "Phone number must start with 7 and be followed by 10 digits")
    @Column
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private State state;

    private String confirmationCode;

    @Column(name = "registration_date", nullable = false)
    private LocalDate registrationDate;

    @OneToOne
    @JoinColumn(name = "profile_picture_id", referencedColumnName = "fileInfoId")
    private FileInfo profilePicture;

    @OneToMany(mappedBy = "user")
    private List<Playlist> playlists;

    @ManyToMany
    @JoinTable(
            name = "user_favorite_songs",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "userId"),
            inverseJoinColumns = @JoinColumn(name = "song_id", referencedColumnName = "songId")
    )
    private List<Song> favoriteSongs;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Artist artist;
}