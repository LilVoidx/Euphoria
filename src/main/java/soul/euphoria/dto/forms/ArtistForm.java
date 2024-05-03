package soul.euphoria.dto.forms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import soul.euphoria.models.Enum.Genre;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArtistForm {

    private String stageName;
    private String bio;
    private Genre genre;
}
