package in.assesment.movie_review_service.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import in.assesment.movie_review_service.Dto.ResponseDto;
import in.assesment.movie_review_service.model.Movie;

public interface IMovieCustomService {

	ResponseDto<List<Movie>> getLatestMoviesByRating();

	ResponseDto<List<Movie>> getLatestMoviesByReleasedDate();

	ResponseDto<List<Movie>> filterMovies(Long genreId, Long languageId, Date releaseDate, Double minRating);

	Page<Movie> movieWithPagination(Pageable pageable);

}
