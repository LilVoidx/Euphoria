package soul.euphoria.dto.infos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import soul.euphoria.models.Enum.Genre;
import soul.euphoria.models.user.User;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long userId;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String fullName;
    private String phoneNumber;
    private LocalDate registrationDate;
    private String profilePictureUrl;
    private String role;
    private List<PlaylistDTO> playlists;
    private List<SongDTO> favoriteSongs;

    // Properties specific to Artist
    private Long artistId;
    private String stageName;
    private String bio;
    private Genre genre;
    private List<AlbumDTO> albums;
    private List<SongDTO> songs;

    public static UserDTO from(User user) {

        String profilePictureUrl = user.getProfilePicture() != null ? user.getProfilePicture().getStorageFileName() : null;

        return UserDTO.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .fullName(user.getFirstName() + " " + user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .registrationDate(user.getRegistrationDate())
                .profilePictureUrl(profilePictureUrl)
                .role(String.valueOf(user.getRole()))
                .playlists(PlaylistDTO.playlistList(user.getPlaylists()))
                .favoriteSongs(SongDTO.songList(user.getFavoriteSongs()))
                .build();
    }

    public static List<UserDTO> userList(List<User> users) {
        return users.stream()
                .map(UserDTO::from)
                .collect(Collectors.toList());
    }
}
