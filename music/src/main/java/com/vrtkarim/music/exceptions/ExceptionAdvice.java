package com.vrtkarim.music.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(FileError.class)
    public ResponseEntity<String> handleFileError(FileError e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoMusicFileFound.class)
    public ResponseEntity<String> handleNoMusicFileFound(NoMusicFileFound e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(UploadFailed.class)
    public ResponseEntity<String> handleUploadFailed(UploadFailed e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
    }
    @ExceptionHandler(ChangesFailed.class)
    public ResponseEntity<String> handleChangesFailed(ChangesFailed e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
    }
}
