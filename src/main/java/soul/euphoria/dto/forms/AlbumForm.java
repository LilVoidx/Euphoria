package soul.euphoria.dto.forms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlbumForm {
    @NotBlank
    private String title;

    @NotBlank
    private MultipartFile coverImage;

    @NotBlank
    private String genre;

    @NotBlank
    private String releaseDate;

}
