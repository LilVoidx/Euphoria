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
    @Size(max = 8)
    private String title;

    private String albumTitle;

    private MultipartFile songFile;

    private MultipartFile imageFile;

    private String genre;
}
