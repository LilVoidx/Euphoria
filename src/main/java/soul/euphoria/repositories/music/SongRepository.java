package soul.euphoria.repositories.music;

import org.springframework.data.jpa.repository.JpaRepository;
import soul.euphoria.models.music.Song;

public interface SongRepository extends JpaRepository<Song, Long> {
}

