package soul.euphoria.dto.infos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import soul.euphoria.models.Enum.Genre;
import soul.euphoria.models.user.Artist;
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

    // Properties specific to Artist
    private Long artistId;
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

        UserDTO.UserDTOBuilder builder = UserDTO.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .fullName(user.getFirstName() + " " + user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .registrationDate(user.getRegistrationDate())
                .profilePictureUrl(profilePictureUrl)
                .playlists(user.getPlaylists().stream().map(PlaylistDTO::from).collect(Collectors.toList()))
                .favoriteSongs(user.getFavoriteSongs().stream().map(SongDTO::from).collect(Collectors.toList()));

        Artist artist = user.getArtist();
        if (artist != null) {
            builder
                    .artistId(artist.getArtistId())
                    .stageName(artist.getStageName())
                    .bio(artist.getBio())
                    .genre(artist.getGenre())
                    .albums(artist.getAlbums().stream().map(AlbumDTO::from).collect(Collectors.toList()))
                    .songs(artist.getSongs().stream().map(SongDTO::from).collect(Collectors.toList()));
        }

        return builder.build();
    }

    public static List<UserDTO> userList(List<User> users) {
        return users.stream()
                .map(UserDTO::from)
                .collect(Collectors.toList());
    }
}
