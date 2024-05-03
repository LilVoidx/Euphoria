package soul.euphoria.repositories.user;

import org.springframework.data.jpa.repository.JpaRepository;
import soul.euphoria.models.user.Artist;


public interface ArtistRepository  extends JpaRepository<Artist, Long> {

}
