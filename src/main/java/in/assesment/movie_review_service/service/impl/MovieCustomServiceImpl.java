package in.assesment.movie_review_service.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.assesment.movie_review_service.Dto.ResponseDto;
import in.assesment.movie_review_service.custom_exceptions.NotFoundException;
import in.assesment.movie_review_service.model.Movie;
import in.assesment.movie_review_service.repository.IMovieRepository;
import in.assesment.movie_review_service.service.IMovieCustomService;

@Service
public class MovieCustomServiceImpl implements IMovieCustomService {

	private static final Logger logInfo = LoggerFactory.getLogger(MovieServiceImpl.class);

	@Autowired
	private IMovieRepository movieRepository;

	@Override
	public ResponseDto<List<Movie>> getLatestMoviesByRating() {
		List<Movie> movies = movieRepository.getLatestMoviesByRating();
		if (!movies.isEmpty()) {
			return new ResponseDto<>(movies, "Data fetched successfully", 200);
		} else {
			throw new NotFoundException("Not data found");
		}
	}

	@Override
	public ResponseDto<List<Movie>> getLatestMoviesByReleasedDate() {
		List<Movie> movies = movieRepository.getLatestMoviesReleasedDate();
		if (!movies.isEmpty()) {
			return new ResponseDto<>(movies, "Data fetched successfully", 200);
		} else {
			throw new NotFoundException("Not data found");
		}
	}

	@Override
	public ResponseDto<List<Movie>> filterMovies(Long genreId, Long languageId, Date releaseDate, Double minRating) {
		// TODO Auto-generated method stub
		return null;
	}

}
