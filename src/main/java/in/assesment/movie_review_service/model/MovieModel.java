package in.assesment.movie_review_service.model;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieModel extends RepresentationModel<MovieModel> {
    private Long id;
    private String title;
    private String description;


}
