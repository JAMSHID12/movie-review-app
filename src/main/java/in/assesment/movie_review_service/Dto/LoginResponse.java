package in.assesment.movie_review_service.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {
	
	private String email;
	private String userName;
    private String token;
    private String tokenType;
    private long expiresIn;

    public String getToken() {
        return token;
    }
}
