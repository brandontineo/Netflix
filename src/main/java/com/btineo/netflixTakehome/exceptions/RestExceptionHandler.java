package com.btineo.netflixTakehome.exceptions;

import org.springframework.core.annotation.Order;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.btineo.netflixTakehome.responses.ApiError;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
  
   //other exception handlers
  
   @ExceptionHandler(ResourceNotFoundException.class)
   protected ResponseEntity<Object> handleEntityNotFound(
		   ResourceNotFoundException ex) {
       ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
       apiError.setMessage(ex.getMessage());
       apiError.setSuggestion(ex.getSuggestion());
       return buildResponseEntity(apiError);
   }
   
   private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
	    return new ResponseEntity<>(apiError, apiError.getStatus());
	}
}