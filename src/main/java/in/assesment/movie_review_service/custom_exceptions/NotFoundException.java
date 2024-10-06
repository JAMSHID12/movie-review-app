package in.assesment.movie_review_service.custom_exceptions;

@SuppressWarnings("serial")
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
