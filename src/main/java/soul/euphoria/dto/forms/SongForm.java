package soul.euphoria.dto.forms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SongForm {
    @NotBlank
    @Size(max = 30)
    private String title;

    @NotBlank
    private String releaseDate;

    @NotBlank
    private MultipartFile songFile;

    @NotBlank
    private MultipartFile imageFile;

    @NotBlank
    private String genre;

    @NotBlank
    private String Duration;
}
