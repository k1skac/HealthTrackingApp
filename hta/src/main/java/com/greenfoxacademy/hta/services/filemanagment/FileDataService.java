package com.greenfoxacademy.hta.services.filemanagment;

import com.greenfoxacademy.hta.models.filemanagement.FileData;
import com.greenfoxacademy.hta.repositories.filedatarepository.IFileDataRepository;
import lombok.Setter;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.activation.UnsupportedDataTypeException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Setter
public class FileDataService implements IFileDataService {
    private final IFileDataRepository iFileDataRepository;
    @Value("${hta.root}")
    private Path root;
    @Autowired
    public FileDataService(IFileDataRepository iFileDataRepository) {
        this.iFileDataRepository = iFileDataRepository;
    }

    private void createRootDirectory() throws IOException {
        if (!Files.exists(root)) {
            Files.createDirectories(root);
        }
    }

    public String creatingFilePath(Path root, String newFileName) {
        return root + "\\" + newFileName;
    }

    public FileData uploadFileDataToDirectory(MultipartFile file) throws IOException {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());

        if (!isSupportedExtension(extension)) {
            throw new UnsupportedDataTypeException();
        }

        LocalDateTime now = LocalDateTime.now();
        String newFileName = addTimeToFileName(file.getOriginalFilename(), now);
        String filePath = creatingFilePath(root, newFileName);
        String downloadPath = "http://localhost:8080/api/user/download-file/";
        createRootDirectory();

        if (!checkFileNameLength(filePath)) {
            throw new IOException("File name is too long!");
        }

        FileData fileData = iFileDataRepository.save(FileData.builder()
                .fileName(newFileName)
                .fileType(file.getContentType())
                .filePath(downloadPath).build()
        );
        fileData.setFilePath(downloadPath.concat(fileData.getId().toString()));
        iFileDataRepository.save(fileData);

        file.transferTo(new File(filePath));
        return fileData;
    }

    public byte[] downloadFileById(Long id) throws IOException {
        FileData fileData = getFileById(id);
        String filePath = root + "\\" + fileData.getFileName();
        return Files.readAllBytes(new File(filePath).toPath());
    }

    public String addTimeToFileName(String fileName, LocalDateTime time) {
        String type = fileName.substring(fileName.lastIndexOf("."));
        String name = fileName.substring(0, fileName.lastIndexOf("."));
        return name.concat("." + time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss"))).concat(type);
    }

    public boolean checkFileNameLength(String fileName) {
        return fileName.length() <= 255;
    }

    @Override
    public FileData save(FileData uploadedFile) {
        return iFileDataRepository.save(uploadedFile);
    }

    @Override
    public FileData getFileById(Long id) throws IOException {
        Optional<FileData> fileData = iFileDataRepository.findById(id);
        if (fileData.isPresent()) {
            return fileData.get();
        }
        throw new FileNotFoundException("File doesn't exist!");
    }

    private boolean isSupportedExtension(String extension) {
        List<String> extensions = new ArrayList<>(List.of("jpeg", "png", "imp", "jpg", "doc", "txt", "csv", "docx"));
        return extension != null && extensions.contains(extension);
    }
}
