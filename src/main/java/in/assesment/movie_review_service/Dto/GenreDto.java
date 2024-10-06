package in.assesment.movie_review_service.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenreDto {
	@NotNull(message = "ID is mandatory")
	private Long id;
	@NotBlank(message = "Genre name is mandatory")
    private String name;
}
