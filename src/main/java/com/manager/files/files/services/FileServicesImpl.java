package com.manager.files.files.services;

import com.manager.files.files.entity.FileEntity;
import com.manager.files.files.repository.FileRepository;
import com.manager.files.files.response.ResponseFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FileServicesImpl implements FileServices {

    @Autowired
    private FileRepository fileRepository;

    @Override
    public FileEntity store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileEntity fileEntity = FileEntity.builder()
                .name(fileName)
                .type(file.getContentType())
                .data(file.getBytes())
                .url("direccion")
                .build();
        return fileRepository.save(fileEntity);
    }

    @Override
    public FileEntity picture(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        byte[] bytes = file.getBytes();
        String type = file.getContentType();
        String carpeta = "src/main/resources/images";
        File folder = new File(carpeta);
        if (!folder.exists()) {
            boolean a = folder.mkdir();
            System.out.println("folder no existe");
        }else{
            System.out.println("folder Existe");
        }
        Path path = Paths.get(carpeta + "/" + fileName);
        Files.write(path,bytes);

        System.out.println("filename " + fileName);
        FileEntity fileEntity = FileEntity.builder()
                .name(fileName)
                .type(file.getContentType())
                .data(file.getBytes())
                .url(carpeta + "/" + fileName)
                .build();

        return fileRepository.save(fileEntity);
    }

    @Override
    public Optional<FileEntity> getFile(UUID id) throws FileNotFoundException {
        Optional<FileEntity> file = fileRepository.findById(id);
        if (file.isPresent()) {
            return file;
        }
        throw new FileNotFoundException();
    }

    @Override
    public List<ResponseFile> getAllFiles() {
        List<ResponseFile> files = fileRepository.findAll().stream().map(dbFile ->{
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("api/fileManager/files/")
                    .path(dbFile.getId().toString())
                    .toUriString();
            return ResponseFile.builder()
                    .name(dbFile.getName())
                    .url(fileDownloadUri)
                    .type(dbFile.getType())
                    .size(dbFile.getData().length)
                    .build();
        }).collect(Collectors.toList());
        return files;
    }
}
