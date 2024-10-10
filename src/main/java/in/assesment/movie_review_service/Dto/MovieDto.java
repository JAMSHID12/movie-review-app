package in.assesment.movie_review_service.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDto {

    private long id;
    @NotBlank(message = "Title is mandatory")
    private String title;
    private String description;
    @NotNull(message = "Release date is mandatory")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date releaseDate;
    @NotNull(message = "Duration is mandatory")
    private int duration;
    private String movieImageUrl;
    private String ottPlatformUrl;

    @NotEmpty(message = "At least one genre is required")
    private Set<GenreDto> genres;  
    @NotEmpty(message = "At least one language is required")
    private Set<LanguageDto> languages; 
    
//    @NotNull(message = "Review is mandatory")
//    private ReviewDto reviewDto;
}
