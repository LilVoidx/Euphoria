package soul.euphoria.services.file.impl;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import soul.euphoria.models.FileInfo;
import soul.euphoria.repositories.FilesRepository;
import soul.euphoria.services.file.FileStorageService;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class FileStorageServiceImpl implements FileStorageService {

    @Value("${storage.path}")
    private String storagePath;

    @Autowired
    private FilesRepository filesRepository;

    @Override
    public String saveFile(MultipartFile file) {

        String storageName = UUID.randomUUID().toString() + "." +
                FilenameUtils.getExtension(file.getOriginalFilename());

        FileInfo fileInfo = FileInfo.builder()
                .originalFileName(file.getOriginalFilename())
                .type(file.getContentType())
                .size(file.getSize())
                .storageFileName(storageName)
                .url(storagePath + "/" + storageName)
                .build();

        try {
            String fullPath = Paths.get("src/main/resources", storagePath).toString();
            Files.createDirectories(Paths.get(fullPath)); //This will Create directories if they don't exist
            Files.copy(file.getInputStream(), Paths.get(fullPath, storageName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        filesRepository.save(fileInfo);
        return fileInfo.getStorageFileName();
    }


    @Override
    public void writeFileToResponse(String fileName, HttpServletResponse response) {
        FileInfo fileInfo = filesRepository.findByStorageFileName(fileName);
        response.setContentType(fileInfo.getType());

        try {
            IOUtils.copy(new FileInputStream("src/main/resources" + "/" + fileInfo.getUrl()), response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public FileInfo findByStorageName(String storageName) {
        return filesRepository.findByStorageFileName(storageName);
    }
}