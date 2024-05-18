package soul.euphoria.dto.forms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import soul.euphoria.models.Enum.Genre;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArtistForm {

    @NotBlank
    private String stageName;

    @NotBlank
    private String bio;

    @NotBlank
    private Genre genre;
}
