package in.assesment.movie_review_service.service.impl;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.assesment.movie_review_service.Dto.GenreDto;
import in.assesment.movie_review_service.Dto.LanguageDto;
import in.assesment.movie_review_service.Dto.MovieDto;
import in.assesment.movie_review_service.Dto.ResponseDto;
import in.assesment.movie_review_service.Dto.ReviewDto;
import in.assesment.movie_review_service.custom_exceptions.ConflictException;
import in.assesment.movie_review_service.custom_exceptions.NotFoundException;
import in.assesment.movie_review_service.model.Genre;
import in.assesment.movie_review_service.model.Language;
import in.assesment.movie_review_service.model.Movie;
import in.assesment.movie_review_service.model.UserInfo;
import in.assesment.movie_review_service.repository.IMovieRepository;
import in.assesment.movie_review_service.repository.IUserInfoRepository;
import in.assesment.movie_review_service.service.IMovieService;

@Service
public class MovieServiceImpl implements IMovieService {

	private static final Logger logInfo = LoggerFactory.getLogger(MovieServiceImpl.class);

	@Autowired
	private IMovieRepository movieRepository;

	@Autowired
	private IUserInfoRepository userRepository;

//	@Autowired
//	private IReviewService reviewService;

	@Override
	public ResponseDto<Movie> createMovie(MovieDto movieDto) {
		try {
			if (movieRepository.findByTitle(movieDto.getTitle()).isPresent()) {
				throw new ConflictException("This movie already exists");
			}
			Optional<UserInfo> userObj = userRepository.findById(movieDto.getUserId());
			if (userObj.isEmpty()) {
				throw new NotFoundException("User not found with ID: " + movieDto.getUserId());
			}
//			//final ReviewDto reviewDtoObj = movieDto.getReviewDto();
//			double updatedRating = 0;
//			if(null != reviewDtoObj && 0 != reviewDtoObj.getRating()) {
//				final double average = (double) reviewDtoObj.getRating() / 1;
//				updatedRating = Math.round(average * 100.0)/100.0;
//			}
			
			Set<Genre> genres = new HashSet<>();
			for (GenreDto genre : movieDto.getGenres()) {
				Genre genreObj = new Genre();
				genreObj.setId(genre.getId());
				genreObj.setName(genre.getName());
				genres.add(genreObj);
			}
			Set<Language> languages = new HashSet<>();
			for (LanguageDto language : movieDto.getLanguages()) {
				Language languageObj = new Language();
				languageObj.setId(language.getId());
				languageObj.setName(language.getName());
				languages.add(languageObj);
			}
			
			final Movie movieObj = Movie.builder().title(movieDto.getTitle()).description(movieDto.getDescription()).duration(movieDto.getDuration())
					.releaseDate(movieDto.getReleaseDate()).movieImageUrl(movieDto.getMovieImageUrl()).userDetail(userObj.get()).status(true)
					.totalRatedCount(0).totalRating(0).createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).genres(genres)
					.languages(languages)
					.build();
			
			Movie saveMovie = movieRepository.save(movieObj);
			
//			// add review table if review exist in dto
//			if(null != reviewDtoObj) {
//				reviewDtoObj.setUserId(movieDto.getUserId());
//				reviewDtoObj.setMovieId(saveMovie.getId());
//				reviewService.createReview(reviewDtoObj);
//			}
			
			return new ResponseDto<>(saveMovie, "Movie created successfully", 201);
		} catch (ConflictException e) {
			logInfo.error("Conflict occurred: {}", e.getMessage());
			throw e;
		} catch (Exception e) {
			logInfo.error("Error creating user: {}", e.getMessage(), e);
			return new ResponseDto<>(null, "An error occurred while creating user", 500);
		}
	}
	
	

	@Override
	public ResponseDto<Movie> getMovieDetailsById(long id) {
		Optional<Movie> movieOptional = movieRepository.findById(id);
		if (movieOptional.isPresent()) {
			return new ResponseDto<>(movieOptional.get(), "User fetched successfully", 200);
		} else {
			throw new NotFoundException("User not found with ID: " + id);
		}
	}

	@Override
	public ResponseDto<Movie> updateMovie(long id, MovieDto movieDto) {
		Optional<Movie> movieResult = movieRepository.findById(id);
		if (movieResult.isEmpty()) {
			throw new NotFoundException("Movie not found with ID: " + movieDto.getUserId());
		}
		Set<Genre> genres = new HashSet<>();
		for (GenreDto genre : movieDto.getGenres()) {
			Genre genreObj = new Genre();
			genreObj.setId(genre.getId());
			genreObj.setName(genre.getName());
			genres.add(genreObj);
		}
		Set<Language> languages = new HashSet<>();
		for (LanguageDto language : movieDto.getLanguages()) {
			Language languageObj = new Language();
			languageObj.setId(language.getId());
			languageObj.setName(language.getName());
			languages.add(languageObj);
		}
		Movie movie = Movie.builder()
				
				.id(id).title(movieDto.getTitle()).description(movieDto.getDescription()).duration(movieDto.getDuration())
				.releaseDate(movieDto.getReleaseDate()).movieImageUrl(movieDto.getMovieImageUrl()).userDetail(movieResult.get().getUserDetail()).status(true)
				.totalRatedCount(movieResult.get().getTotalRatedCount()).totalRating(movieResult.get().getTotalRating()).createdAt(movieResult.get().getCreatedAt()).updatedAt(LocalDateTime.now()).genres(genres)
				.languages(languages)
				.build();
		
		Movie saveMovie = movieRepository.save(movie);
		return new ResponseDto<>(saveMovie, "Movie created successfully", 201);

	}

	@Override
	public ResponseDto<String> deleteMoive(long id) {
		Optional<Movie> movieOptional = movieRepository.findById(id);

		if (movieOptional.isPresent()) {
			userRepository.deleteById(id);
			return new ResponseDto<>(null, "Movie deleted successfully", 200);
		} else {
			throw new NotFoundException("Movie not found with ID: " + id);
		}
	}

	

	@Override
	public ResponseDto<Movie> deactivate(long id, MovieDto movieDto) {
		Optional<Movie> movieOptional = movieRepository.findById(id);

		if (movieOptional.isPresent()) {
			Movie existingUser = movieOptional.get();

			existingUser.setStatus(false);
			existingUser.setUpdatedAt(LocalDateTime.now());

			Movie updatedMoive = movieRepository.save(existingUser);
			return new ResponseDto<>(updatedMoive, "User de-activated successfully", 200);
		} else {
			throw new NotFoundException("User not found with ID: " + id);
		}
	}

}
