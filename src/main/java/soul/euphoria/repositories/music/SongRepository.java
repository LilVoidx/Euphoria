package soul.euphoria.repositories.music;

import org.springframework.data.jpa.repository.JpaRepository;
import soul.euphoria.models.music.Song;
import soul.euphoria.models.user.Artist;

import java.util.List;

public interface SongRepository extends JpaRepository<Song, Long> {
    List<Song> findAllByArtist(Artist artist);
}

