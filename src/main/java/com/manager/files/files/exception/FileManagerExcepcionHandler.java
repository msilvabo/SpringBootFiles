package com.manager.files.files.exception;

import com.manager.files.files.response.ResponseMessage;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.FileNotFoundException;

@ControllerAdvice
public class FileManagerExcepcionHandler extends ResponseEntityExceptionHandler {

//    @Order(Ordered.HIGHEST_PRECEDENCE)
//    @ExceptionHandler(MaxUploadSizeExceededException.class)
//    public ResponseEntity<ResponseMessage> handleMaxSizeException(MaxUploadSizeExceededException exc){
//        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage("Tamaño de archivo excede limite"));
//    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<ResponseMessage> handleFileNotFoundException(FileNotFoundException exc){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("Archivo no encontrado"));
    }

}
