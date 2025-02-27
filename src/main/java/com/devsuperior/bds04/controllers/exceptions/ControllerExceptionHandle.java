package com.devsuperior.bds04.controllers.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.devsuperior.bds04.services.exceptions.DatabaseException;
import com.devsuperior.bds04.services.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class ControllerExceptionHandle {

		@ExceptionHandler(ResourceNotFoundException.class)
		public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request)
		{
			HttpStatus status = HttpStatus.NOT_FOUND;
			StandardError err = new StandardError();
			err.setTimestamp(Instant.now());
			err.setStatus(status.value());
			err.setError("CVV:Resource not found");
			err.setMessage(e.getMessage());
			err.setPath(request.getRequestURI());
			return ResponseEntity.status(status).body(err);
			}

		@ExceptionHandler(DatabaseException.class)
		public ResponseEntity<StandardError> DatabaseException(DatabaseException  e, HttpServletRequest request)
		{
			HttpStatus status = HttpStatus.BAD_REQUEST;
			StandardError err = new StandardError();
			err.setTimestamp(Instant.now());
			err.setStatus(status.value());
			err.setError("CVV:Database exception");
			err.setMessage(e.getMessage());
			err.setPath(request.getRequestURI());
			return ResponseEntity.status(status).body(err);
			}
	}