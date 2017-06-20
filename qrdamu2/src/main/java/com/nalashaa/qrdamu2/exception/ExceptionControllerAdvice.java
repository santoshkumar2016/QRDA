
package com.nalashaa.qrdamu2.exception;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.nalashaa.qrdamu2.util.GenericResponseDataBlock;

@ControllerAdvice
public class ExceptionControllerAdvice {

    private static final Logger logger = LogManager.getLogger(ExceptionControllerAdvice.class.getName());

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericResponseDataBlock> exceptionHandler(Exception ex) {
        GenericResponseDataBlock error = new GenericResponseDataBlock();
        error.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setMessage(ex.getMessage());
        return new ResponseEntity<GenericResponseDataBlock>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(QrdaException.class)
    public ResponseEntity<String> exceptionHandler(QrdaException ex) {
        logger.error(ex.getMessage());
        return new ResponseEntity<String>(ex.getErrorMsg(), HttpStatus.OK);
    }
}