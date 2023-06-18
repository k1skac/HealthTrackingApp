package com.greenfoxacademy.hta.repositories.filedatarepository;

import com.greenfoxacademy.hta.models.filemanagement.FileData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFileDataRepository extends JpaRepository<FileData, Long> {
}
