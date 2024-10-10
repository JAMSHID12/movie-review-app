package in.assesment.movie_review_service.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import in.assesment.movie_review_service.Dto.GenreLanguageDto;
import in.assesment.movie_review_service.Dto.ResponseDto;
import in.assesment.movie_review_service.custom_exceptions.NotFoundException;
import in.assesment.movie_review_service.model.Genre;
import in.assesment.movie_review_service.model.Language;
import in.assesment.movie_review_service.model.Movie;
import in.assesment.movie_review_service.repository.IGenresRepostiroy;
import in.assesment.movie_review_service.repository.ILanguageRepository;
import in.assesment.movie_review_service.repository.IMovieRepository;
import in.assesment.movie_review_service.service.IMovieCustomService;

@Service
public class MovieCustomServiceImpl implements IMovieCustomService {

	private static final Logger logInfo = LoggerFactory.getLogger(MovieCustomServiceImpl.class);

	@Autowired
	private IMovieRepository movieRepository;
	
	@Autowired
	private IGenresRepostiroy genrerepository;
	
	@Autowired
	private ILanguageRepository languageRepository;

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
	public ResponseDto<Page<Movie>> filterMovies(Pageable pageable, Long genreId, Long languageId, Date releaseDate, Double minRating) {
		Page<Movie> movies = movieRepository.filterMovies(genreId, languageId, releaseDate, minRating, pageable);
		System.out.println(pageable.getPageNumber());
		System.out.println(pageable.getOffset());
		if (!movies.isEmpty()) {
			return new ResponseDto<>(movies, "Data fetched successfully", 200);
		} else {
			throw new NotFoundException("Not data found");
		}
	}

	@Override
	public Page<Movie> movieWithPagination(Pageable pageable) {
		return movieRepository.findAll(pageable);
	}

	@Override
	public ResponseDto<List<Movie>> searchMoviesByTitleOrDescription(String serchTerm) {
		logInfo.info(serchTerm);
		List<Movie> movies = movieRepository.searchMoviesByTitleOrDescription(serchTerm);
		if (!movies.isEmpty()) {
			return new ResponseDto<>(movies, "Data fetched successfully", 200);
		} else {
			throw new NotFoundException("Not data found");
		}
	}

	@Override
	public ResponseDto<GenreLanguageDto> getAllGenereAndLanguageList() {
		List<Genre> genre = genrerepository.findAll();
		List<Language> language = languageRepository.findAll();
		GenreLanguageDto result = GenreLanguageDto.builder().genres(genre).language(language).build();
		if (null != result) {
			return new ResponseDto<>(result, "Data fetched successfully", 200);
		} else {
			throw new NotFoundException("Not data found");
		}
	}

}
