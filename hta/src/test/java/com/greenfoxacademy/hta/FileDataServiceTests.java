package com.greenfoxacademy.hta;

import com.greenfoxacademy.hta.models.filemanagement.FileData;
import com.greenfoxacademy.hta.repositories.filedatarepository.IFileDataRepository;
import com.greenfoxacademy.hta.services.filemanagment.FileDataService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import javax.activation.UnsupportedDataTypeException;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class FileDataServiceTests {

    @Test
    public void whenCreatingFilePath_thenPathIsOK() {
        // Arrange
        IFileDataRepository iFileDataRepositoryMock = mock(IFileDataRepository.class);
        FileDataService fileDataService = new FileDataService(iFileDataRepositoryMock);
        Path path = Path.of("\\test\\path");
        String newFileName = "test.txt";

        // Act
        String newFilePath = fileDataService.creatingFilePath(path, newFileName);

        // Assert
        Assertions.assertEquals("\\test\\path\\test.txt", newFilePath);
    }

    @Test
    public void whenUploadFileDataToDirectory_thenSaveIsCalledAndFileNameAndFileTypeAreOK() throws IOException {
        // Arrange
        IFileDataRepository iFileDataRepositoryMock = mock(IFileDataRepository.class);
        FileDataService fileDataService = new FileDataService(iFileDataRepositoryMock);
        fileDataService.setRoot(Path.of("${java.io.tmpdir}\\hta"));
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "weightFile",
                "test.txt",
                MediaType.TEXT_PLAIN_VALUE,
                new byte[]{116, 101, 115, 116}
        );

        FileData savedFileData = new FileData();
        savedFileData.setId(1L);
        savedFileData.setFileName("test." + LocalDateTime.now() + ".txt");
        savedFileData.setFileType(MediaType.TEXT_PLAIN_VALUE);
        savedFileData.setFilePath("http://localhost:8080/api/user/download-file/1");
        when(iFileDataRepositoryMock.save(any(FileData.class))).thenReturn(savedFileData);

        // Act
        fileDataService.uploadFileDataToDirectory(mockMultipartFile);

        // Assert
        verify(iFileDataRepositoryMock, times(2)).save(Mockito.argThat(fd -> fd.getFileName().contains("test")));
        verify(iFileDataRepositoryMock, times(2)).save(Mockito.argThat(fd -> fd.getFileType().equals(mockMultipartFile.getContentType())));
    }

    @Test
    public void whenUploadFileDataToDirectory_thenExtensionIsNotAccepted() {
        // Arrange
        IFileDataRepository iFileDataRepositoryMock = mock(IFileDataRepository.class);
        FileDataService fileDataService = new FileDataService(iFileDataRepositoryMock);
        fileDataService.setRoot(Path.of("${java.io.tmpdir}\\hta"));
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "weightFile",
                "test.exe",
                MediaType.TEXT_PLAIN_VALUE,
                new byte[]{116, 101, 115, 116}
        );

        // Act && Assert
        Assertions.assertThrows(
                UnsupportedDataTypeException.class, () -> fileDataService.uploadFileDataToDirectory(
                        mockMultipartFile
                )
        );
    }

    @Test
    public void whenUploadFileDataToDirectory_thenFileNameIsTooLong() {
        // Arrange
        IFileDataRepository iFileDataRepositoryMock = mock(IFileDataRepository.class);
        FileDataService fileDataService = new FileDataService(iFileDataRepositoryMock);
        fileDataService.setRoot(Path.of("${java.io.tmpdir}\\hta"));
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "weightFile",
                "testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest" +
                        "testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest" +
                        "testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest" +
                        "testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest.txt",
                MediaType.TEXT_PLAIN_VALUE,
                new byte[]{116, 101, 115, 116}
        );

        // Act && Assert
        Assertions.assertThrows(
                IOException.class, () -> fileDataService.uploadFileDataToDirectory(
                        mockMultipartFile
                )
        );
    }

    @Test
    public void whenDownloadFileById_thenDownloadIsOK() throws IOException {
        // Arrange
        IFileDataRepository iFileDataRepositoryMock = mock(IFileDataRepository.class);
        FileDataService fileDataService = new FileDataService(iFileDataRepositoryMock);
        fileDataService.setRoot(Path.of("${java.io.tmpdir}\\hta"));
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "weightFile",
                "test.txt",
                MediaType.TEXT_PLAIN_VALUE,
                new byte[]{116, 101, 115, 116}
        );

        FileData savedFileData = new FileData();
        savedFileData.setId(1L);
        savedFileData.setFileName(fileDataService.addTimeToFileName(mockMultipartFile.getOriginalFilename(), LocalDateTime.now()));
        savedFileData.setFileType(MediaType.TEXT_PLAIN_VALUE);
        savedFileData.setFilePath("http://localhost:8080/api/user/download-file/1");
        when(iFileDataRepositoryMock.save(any(FileData.class))).thenReturn(savedFileData);
        when(fileDataService.uploadFileDataToDirectory(mockMultipartFile)).thenReturn(savedFileData);
        when(iFileDataRepositoryMock.findById(savedFileData.getId())).thenReturn(Optional.of(savedFileData));

        // Act
        byte[] bytes = fileDataService.downloadFileById(1L);

        // Assert
        Assertions.assertNotNull(bytes);
        Assertions.assertTrue(bytes.length > 0);
    }

    @Test
    public void whenAddTimeToFileName_thenTimeIsCorrectlyAppended() {
        // Arrange
        IFileDataRepository iFileDataRepositoryMock = mock(IFileDataRepository.class);
        FileDataService fileDataService = new FileDataService(iFileDataRepositoryMock);
        String fileName = "test.txt";
        LocalDateTime time = LocalDateTime.of(LocalDate.of(2023, 5, 27), LocalTime.of(12, 30));

        // Act
        String newFileName = fileDataService.addTimeToFileName(fileName, time);

        // Assert
        Assertions.assertEquals("test.2023-05-27-12-30-00.txt", newFileName);
    }

    @Test
    public void whenGetFileById_thenFindIsCalledWithRightId() throws IOException{
        // Arrange
        IFileDataRepository iFileDataRepositoryMock = mock(IFileDataRepository.class);
        FileDataService fileDataService = new FileDataService(iFileDataRepositoryMock);
        FileData fileData = FileData.builder()
                .id(1L).fileName("file.txt")
                .filePath("\\downloadPath")
                .fileType(MediaType.TEXT_PLAIN_VALUE).build();
        when(iFileDataRepositoryMock.findById(1L)).thenReturn(Optional.of(fileData));

        // Act
        fileDataService.getFileById(1L);

        // Assert
        verify(iFileDataRepositoryMock).findById(Mockito.argThat(id -> id.equals(1L)));
    }

    @Test
    public void whenGetFileById_thenFileIsNotFound() {
        // Arrange
        IFileDataRepository iFileDataRepositoryMock = mock(IFileDataRepository.class);
        FileDataService fileDataService = new FileDataService(iFileDataRepositoryMock);

        // Act && Assert
        Assertions.assertThrows(IOException.class, () -> fileDataService.getFileById(1L));
    }
}
