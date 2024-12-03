package com.oasys.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
	  public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
	        ErrorResponse errorResponse = new ErrorResponse(500, "Something Went Wrong:- {0}." );
	        return new ResponseEntity<>(errorResponse, HttpStatus.OK);
	    }
	  
	  public static class ErrorResponse {
	        private int statusCode;
	        private String message;

	        public ErrorResponse(int statusCode, String message) {
	            this.statusCode = statusCode;
	            this.message = message;
	        }

	        public int getStatusCode() {
	            return statusCode;
	        }

	        public void setStatusCode(int statusCode) {
	            this.statusCode = statusCode;
	        }

	        public String getMessage() {
	            return message;
	        }

	        public void setMessage(String message) {
	            this.message = message;
	        }
	    }
}
