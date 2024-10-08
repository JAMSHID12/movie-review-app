package in.assesment.movie_review_service.component;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import in.assesment.movie_review_service.controller.MovieCustomRestController;
import in.assesment.movie_review_service.model.Movie;
import in.assesment.movie_review_service.model.MovieModel;

@Component
public class MovieModelAssembler extends RepresentationModelAssemblerSupport<Movie, MovieModel> {

    public MovieModelAssembler() {
        super(MovieCustomRestController.class, MovieModel.class);
    }

    @Override
    public MovieModel toModel(Movie movie) {
        return new MovieModel(movie.getId(), movie.getTitle(), movie.getDescription());
    }
}
