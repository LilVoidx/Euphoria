package soul.euphoria.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class FileInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileInfoId;

    @NotBlank
    @Column(name = "original_file_name", nullable = false)
    private String originalFileName;

    @NotBlank
    @Column(name = "storage_file_name", nullable = false)
    private String storageFileName;

    @Column(nullable = false)
    private Long size;

    @NotBlank
    @Column(nullable = false)
    private String type;

    @NotBlank
    @Column(nullable = false)
    private String url;
}
