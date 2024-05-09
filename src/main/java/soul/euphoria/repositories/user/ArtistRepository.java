package soul.euphoria.repositories.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import soul.euphoria.models.user.Artist;

import java.util.Optional;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
    @Query("SELECT a FROM Artist a WHERE a.user.userId = :userId")
    Optional<Artist> findArtistByUserUserId(@Param("userId") Long userId);
}
