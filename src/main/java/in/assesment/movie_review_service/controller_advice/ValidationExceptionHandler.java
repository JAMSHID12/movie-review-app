package in.assesment.movie_review_service.controller_advice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import in.assesment.movie_review_service.Dto.ValidationErrorResponse;

@RestControllerAdvice
public class ValidationExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
		List<ValidationErrorResponse> errors = new ArrayList<>();

		ex.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			String errorType = "ValidationError"; 
			int responseCode = HttpStatus.BAD_REQUEST.value();

			ValidationErrorResponse errorResponse = new ValidationErrorResponse(fieldName, errorMessage, errorType,
					responseCode);
			errors.add(errorResponse);
		});
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}
}
