package in.assesment.movie_review_service.controller_advice;

import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ErrorHandlingControllerAdvice {

//	@ExceptionHandler(ConstraintViolationException.class)
//	  @ResponseStatus(HttpStatus.BAD_REQUEST)
//	  @ResponseBody
//	  ValidationErrorResponse onConstraintValidationException(ConstraintViolationException e) {
//	    ValidationErrorResponse error = new ValidationErrorResponse();
//	    for (ConstraintViolation violation : e.getConstraintViolations()) {
//	      error.getViolations().add(new Violation(violation.getPropertyPath().toString(), violation.getMessage()));
//	    }
//	    return error;
//	  }
//	 
//	  @ExceptionHandler(MethodArgumentNotValidException.class)
//	  @ResponseStatus(HttpStatus.BAD_REQUEST)
//	  @ResponseBody
//	  ValidationErrorResponse onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
//	    ValidationErrorResponse error = new ValidationErrorResponse();
//	    for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
//	      error.getViolations().add(new Violation(fieldError.getField(), fieldError.getDefaultMessage()));
//	    }
//	    return error;
//	  }
}
