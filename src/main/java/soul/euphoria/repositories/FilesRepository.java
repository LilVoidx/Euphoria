package soul.euphoria.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import soul.euphoria.models.FileInfo;

public interface FilesRepository extends JpaRepository<FileInfo, Long> {
    FileInfo findByStorageFileName (String fileName);
}