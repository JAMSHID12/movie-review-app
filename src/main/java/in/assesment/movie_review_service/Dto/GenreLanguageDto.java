package in.assesment.movie_review_service.Dto;

import java.util.List;

import in.assesment.movie_review_service.model.Genre;
import in.assesment.movie_review_service.model.Language;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenreLanguageDto {
	
	private List<Genre> genres;
	
	private List<Language> language;

}
