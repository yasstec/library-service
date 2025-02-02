package com.test.library_service.api.exception;

import com.test.service.library_service.api.dto.ApiErrorDto;
import com.test.service.library_service.api.dto.ServerErrorDto;
import com.test.library_service.domain.exception.FunctionalException;
import com.test.library_service.domain.exception.ObjectNotFoundException;
import com.test.library_service.domain.exception.TechnicalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler({ObjectNotFoundException.class})
    public ResponseEntity<ApiErrorDto> handleObjectNotFoundException(ObjectNotFoundException objectNotFoundException, WebRequest ignoredRequest) {
        ApiErrorDto apiErrorDto = new ApiErrorDto();
        apiErrorDto.key(objectNotFoundException.getKey());
        apiErrorDto.message(objectNotFoundException.getInternalMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiErrorDto);
    }

    @ExceptionHandler({FunctionalException.class})
    public ResponseEntity<ApiErrorDto> handleFunctionalException(FunctionalException functionalException, WebRequest ignoredRequest) {
        ApiErrorDto apiErrorDto = new ApiErrorDto();
        log.warn("Functional exception has occurred.", functionalException);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(apiErrorDto);
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ServerErrorDto> handleTechnicalException(RuntimeException exception, WebRequest request) {
        return this.handleTechnicalException(new TechnicalException(exception), request);
    }

    @ExceptionHandler({TechnicalException.class})
    public ResponseEntity<ServerErrorDto> handleTechnicalException(TechnicalException technicalException, WebRequest ignoredRequest) {
        ServerErrorDto serverErrorDto = new ServerErrorDto();
        log.error("Technical error has occurred.", technicalException);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON).body(serverErrorDto);
    }
}
