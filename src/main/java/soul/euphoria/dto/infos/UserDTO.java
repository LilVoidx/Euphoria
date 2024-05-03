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
    private String fullName;
    private String phoneNumber;
    private LocalDate registrationDate;
    private String profilePictureUrl;
    private List<PlaylistDTO> playlists;
    private List<SongDTO> favoriteSongs;
    private String stageName;
    private String bio;
    private Genre genre;
    private List<AlbumDTO> albums;
    private List<SongDTO> songs;

    public static UserDTO from(User user) {
        String profilePictureUrl = null;
        if (user.getProfilePicture() != null) {
            profilePictureUrl = user.getProfilePicture().getStorageFileName();
        }

        return UserDTO.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .fullName(user.getFirstName()+ " " + user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .registrationDate(user.getRegistrationDate())
                .profilePictureUrl(profilePictureUrl)
                .playlists(user.getPlaylists().stream().map(PlaylistDTO::from).collect(Collectors.toList()))
                .favoriteSongs(user.getFavoriteSongs().stream().map(SongDTO::from).collect(Collectors.toList()))
                .stageName(user.getStageName())
                .bio(user.getBio())
                .genre(user.getGenre())
                .albums(user.getAlbums().stream().map(AlbumDTO::from).collect(Collectors.toList()))
                .songs(user.getSongs().stream().map(SongDTO::from).collect(Collectors.toList()))
                .build();
    }

    public static List<UserDTO> userList(List<User> users) {
        return users.stream()
                .map(UserDTO::from)
                .collect(Collectors.toList());
    }
}
