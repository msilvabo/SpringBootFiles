package com.manager.files.files.services;

import com.manager.files.files.entity.FileEntity;
import com.manager.files.files.response.ResponseFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FileServices {
    //almacen archivos
    FileEntity store(MultipartFile file) throws IOException;

    //descargar archivos
    Optional<FileEntity> getFile(UUID id) throws FileNotFoundException;

    //lista archivos
    List<ResponseFile> getAllFiles();
}
