package in.assesment.movie_review_service.Dto;


import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class UserInfoDto {
	
    private long id;
    
    @NotBlank(message = "Username is mandatory")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;
    
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;  
    
//    @NotBlank(message = "Password is mandatory")
//    @Size(min = 8, max = 20, message = "password must be min 8 charector")
    private String password;
    
    private boolean isActive;
    
}