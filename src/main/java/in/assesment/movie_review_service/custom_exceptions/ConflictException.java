package in.assesment.movie_review_service.custom_exceptions;

@SuppressWarnings("serial")
public class ConflictException extends RuntimeException{

	public ConflictException(String message) {
        super(message);
    }
}
