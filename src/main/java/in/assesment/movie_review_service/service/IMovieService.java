package in.assesment.movie_review_service.service;

import in.assesment.movie_review_service.Dto.MovieDto;
import in.assesment.movie_review_service.Dto.ResponseDto;
import in.assesment.movie_review_service.model.Movie;

public interface IMovieService {

	ResponseDto<Movie> createMovie(MovieDto movieDto);

	ResponseDto<Movie> getMovieDetailsById(long id);

	ResponseDto<Movie> updateMovie(long id, MovieDto movieDto);

	ResponseDto<String> deleteMoive(long id);

	ResponseDto<Movie> deactivate(long id, MovieDto movieDto);

}
