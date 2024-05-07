package soul.euphoria.services.file;

import org.springframework.web.multipart.MultipartFile;
import soul.euphoria.models.FileInfo;

import javax.servlet.http.HttpServletResponse;

public interface FileStorageService {
    String saveFile(MultipartFile file);

    void writeFileToResponse(String fileName, HttpServletResponse response);

    FileInfo findByStorageName(String storageName);
}