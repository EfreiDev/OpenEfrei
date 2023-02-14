package dev.efrei.openefrei.controllers.v1;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.efrei.openefrei.utils.Response;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@RestController
public class ErrorHandlerController implements ErrorController {

	@RequestMapping("/v1/error")
	public ResponseEntity<String> handleError(HttpServletRequest request) {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

		if (status != null) {
			Integer statusCode = Integer.valueOf(status.toString());
			HttpStatus error = HttpStatus.resolve(statusCode);
			if (error != null) {
				return ResponseEntity.status(statusCode).body(Response.get(statusCode, error.getReasonPhrase()));
			}
		}
		return ResponseEntity.status(400).body(Response.get(400, HttpStatus.BAD_REQUEST.getReasonPhrase()));
	}

}
