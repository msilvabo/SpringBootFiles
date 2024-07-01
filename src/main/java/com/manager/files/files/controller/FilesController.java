package com.manager.files.files.controller;

import com.manager.files.files.entity.FileEntity;
import com.manager.files.files.response.ResponseFile;
import com.manager.files.files.response.ResponseMessage;
import com.manager.files.files.services.FileServices;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/fileManager")
public class FilesController {

    @Autowired
    private FileServices fileServices;

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        fileServices.store(file);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Achivo subido exitosamente"));

    }@PostMapping("/picture")
    public ResponseEntity<ResponseMessage> uploadUpdateFile(@RequestParam("file") MultipartFile file) throws IOException {
        fileServices.picture(file);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Achivo subido exitosamente"));
    }

    @GetMapping("/files/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable UUID id) throws FileNotFoundException {
        FileEntity fileEntity = fileServices.getFile(id).get();
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_TYPE, fileEntity.getType())
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+ fileEntity.getName()+ "\"")
                .body(fileEntity.getData());
    }

    @GetMapping("/files")
    public ResponseEntity<List<ResponseFile>> getListFiles() {
        List<ResponseFile> files = fileServices.getAllFiles();
        return ResponseEntity.status(HttpStatus.OK).body(files);
    }
}
