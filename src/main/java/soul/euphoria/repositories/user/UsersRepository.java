package soul.euphoria.repositories.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import soul.euphoria.models.user.Artist;
import soul.euphoria.models.user.User;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    Optional<User> findByConfirmationCode(String confirmationCode);

    /*@Query("SELECT u FROM Artist u WHERE u.user_id = :userId")
    Optional<Artist> findArtistByUserId(Long userId);*/

}