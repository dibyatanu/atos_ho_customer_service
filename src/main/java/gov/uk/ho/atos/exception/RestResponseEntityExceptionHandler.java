package gov.uk.ho.atos.exception;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler  {

	 @ExceptionHandler({CustomerServiceException.class})
	 void handleBadRequests(final HttpServletResponse response) throws IOException  {
	     response.sendError(HttpStatus.BAD_REQUEST.value());
	 }

}
