package in.assesment.movie_review_service.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidationErrorResponse {
    private String field;
    private String message;
    private String errorType;
    private int responseCode;
}
