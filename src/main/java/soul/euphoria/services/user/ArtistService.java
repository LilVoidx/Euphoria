package soul.euphoria.services.user;

import soul.euphoria.dto.forms.ArtistForm;
import soul.euphoria.models.user.Artist;
import soul.euphoria.models.user.User;

public interface ArtistService {

    void registerAsArtist(User user, ArtistForm artistForm);

    void updateArtist(String username, ArtistForm artistForm);

    ArtistForm convertArtistToForm(Artist artist);
}
