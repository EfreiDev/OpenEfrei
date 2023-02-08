package dev.efrei.openefrei.controllers.v1;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.efrei.openefrei.utils.Response;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@RestController
public class ErrorHandlerController implements ErrorController {

	@RequestMapping("/v1/error")
	public String handleError(HttpServletRequest request) {
	    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
	    
	    if (status != null) {
	        Integer statusCode = Integer.valueOf(status.toString());
	        HttpStatus error = HttpStatus.resolve(statusCode);
	        if(error != null) {
	        	return Response.get(statusCode, error.getReasonPhrase());
	        }
	    }
	    return Response.get(12004, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
	}

}
