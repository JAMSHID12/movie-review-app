package in.assesment.movie_review_service.service;

import in.assesment.movie_review_service.Dto.MovieDto;
import in.assesment.movie_review_service.Dto.ResponseDto;
import in.assesment.movie_review_service.model.Movie;
import in.assesment.movie_review_service.model.UserInfo;

public interface IMovieService {

	ResponseDto<Movie> createMovie(UserInfo userInfo, MovieDto movieDto);

	ResponseDto<Movie> getMovieDetailsById(long id);

	ResponseDto<Movie> updateMovie(UserInfo userInfo, long id, MovieDto movieDto);

	ResponseDto<String> deleteMoive(long id);

	ResponseDto<Movie> deactivate(long id, MovieDto movieDto);

}
