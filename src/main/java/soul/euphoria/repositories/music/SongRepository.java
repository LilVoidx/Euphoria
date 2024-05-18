package soul.euphoria.repositories.music;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import soul.euphoria.dto.infos.SongDTO;
import soul.euphoria.models.music.Song;
import soul.euphoria.models.user.Artist;
import soul.euphoria.models.user.User;

import java.util.List;

public interface SongRepository extends JpaRepository<Song, Long> {
    List<Song> findAllByArtist(Artist artist);

    List<Song> findAllByArtistAndAlbumIsNull(Artist artist);

    @Query("SELECT s FROM Song s WHERE s.songId IN :ids")
    List<Song> getSongsByIds(List<Long> ids);

    @Query("SELECT s FROM Song s WHERE s.album.albumId = :albumId")
    List<SongDTO> findAllByAlbumId(Long albumId);

    List<Song> findByTitleStartingWithIgnoreCase(String title);

    boolean existsBySongIdAndFavoritesContaining(Long songId, User user);

    @Query("SELECT s FROM Song s JOIN s.favorites u WHERE u.username = :username")
    List<Song> findAllFavoritesByUsername(String username);

    @Query(value = "SELECT s.* FROM Song s LEFT JOIN user_favorite_songs ufs ON s.song_id = ufs.song_id GROUP BY s.song_id ORDER BY COUNT(ufs.user_id) DESC LIMIT 1", nativeQuery = true)
    Song findTrending();
}


