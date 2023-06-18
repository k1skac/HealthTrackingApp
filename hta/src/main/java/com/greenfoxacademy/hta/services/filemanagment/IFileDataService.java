package com.greenfoxacademy.hta.services.filemanagment;

import com.greenfoxacademy.hta.models.filemanagement.FileData;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

public interface IFileDataService {
    FileData uploadFileDataToDirectory(MultipartFile file) throws IOException;
    byte[] downloadFileById(Long fileId) throws IOException;
    String addTimeToFileName(String fileName, LocalDateTime time);
    boolean checkFileNameLength(String fileName);
    FileData getFileById(Long id) throws IOException;
    FileData save(FileData uploadedFile);
}
