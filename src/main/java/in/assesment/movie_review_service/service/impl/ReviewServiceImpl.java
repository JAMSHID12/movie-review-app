package in.assesment.movie_review_service.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.assesment.movie_review_service.Dto.ResponseDto;
import in.assesment.movie_review_service.Dto.ReviewDto;
import in.assesment.movie_review_service.custom_exceptions.ConflictException;
import in.assesment.movie_review_service.custom_exceptions.NotFoundException;
import in.assesment.movie_review_service.model.Movie;
import in.assesment.movie_review_service.model.Review;
import in.assesment.movie_review_service.model.UserInfo;
import in.assesment.movie_review_service.repository.IMovieRepository;
import in.assesment.movie_review_service.repository.IReviewRepository;
import in.assesment.movie_review_service.repository.IUserInfoRepository;
import in.assesment.movie_review_service.service.IReviewService;

@Service
public class ReviewServiceImpl implements IReviewService {

	private static final Logger logInfo = LoggerFactory.getLogger(MovieServiceImpl.class);

	@Autowired
	private IReviewRepository reviewRepository;

	@Autowired
	private IMovieRepository movieRepository;

	@Autowired
	private IUserInfoRepository userRepository;

	@Override
	public ResponseDto<Review> createReview(final UserInfo userInfo, final ReviewDto reviewDto) {
		final Optional<Movie> movieOptional = movieRepository.findById(reviewDto.getMovieId());
		if (movieOptional.isEmpty()) {
			throw new NotFoundException("Movie does not exist id: " + reviewDto.getMovieId());
		}
		final Optional<UserInfo> userInfoOptional = userRepository.findById(userInfo.getId());
		if (userInfoOptional.isEmpty()) {
			throw new NotFoundException("User does not exist id: " + userInfo.getId());
		}
		if (reviewRepository.findByUserIdAndMovieId( userInfo.getId(), reviewDto.getMovieId()).isEmpty()) {
			Review reivewObj = new Review();
			reivewObj.setMovie(movieOptional.get());
			reivewObj.setUserDetail(userInfoOptional.get());
			reivewObj.setReviewText(reviewDto.getComment());
			reivewObj.setRating(reviewDto.getRating());
			reivewObj.setStatus(true);
			reivewObj.setCreatedAt(LocalDateTime.now());
			reivewObj.setUpdatedAt(LocalDateTime.now());
			final Review savedUser = reviewRepository.save(reivewObj);

			// update rating of movie
			updateReviewCountAndTotal(movieOptional.get(), reviewDto.getRating(), 0, "INSERT");

			return new ResponseDto<>(savedUser, "Review submitted successfully", 201);
		} else {
			throw new ConflictException("You Already reviewed this movie");
		}

	}

	public void updateReviewCountAndTotal(Movie movie, int newRating, double oldRating, String method) {
	    double oldTotalRating = movie.getTotalRating();
	    int oldTotalRatedCount = movie.getTotalRatedCount();
	    double newAverage;

	    switch (method) {
	        case "INSERT":
	        	oldTotalRatedCount++;
	            oldTotalRating += newRating;
	            final double average = (double) oldTotalRating / 2;
	            newAverage = Math.round(average * 100.0) / 100.0;
	            break;

	        case "UPDATE":
	            double sumOfRatings = oldTotalRating * oldTotalRatedCount;
	            double newSumOfRatings = sumOfRatings - oldRating;
	        	double finalNewAverage = newSumOfRatings / (oldTotalRatedCount - 1);
	        	newAverage = Math.round(finalNewAverage * 100.0) / 100.0;
	        	newAverage += newRating; 
	            newAverage = newAverage / 2; 
	            newAverage = Math.round(newAverage * 100.0) / 100.0;
	            break;

	        case "DELETE":
	        	double sumOfRatingss = oldTotalRatedCount * oldTotalRating;
	        	double newSumOfRating = sumOfRatingss - oldRating;
	        	double finalNewAverag = newSumOfRating / (oldTotalRatedCount - 1);
	        	newAverage = Math.round(finalNewAverag * 100.0) / 100.0;
	        	oldTotalRatedCount--;
	            break;

	        default:
	            throw new IllegalArgumentException("Unexpected value: " + method);
	    }

	    movie.setTotalRating(newAverage);
	    movie.setTotalRatedCount(oldTotalRatedCount);
	    movieRepository.save(movie);
	}


	@Override
	public ResponseDto<Review> updateReview(long id, ReviewDto reviewDto, final UserInfo userInfo) {
		final Optional<Review> reviewOptional = reviewRepository.findById(id);
		if (reviewOptional.isEmpty()) {
			throw new NotFoundException("Review does not exist by id: " + id);
		}
		final Optional<Movie> movieOptional = movieRepository.findById(reviewDto.getMovieId());
		if (movieOptional.isEmpty()) {
			throw new NotFoundException("Movie does not exist id: " + reviewDto.getMovieId());
		}
		final Optional<UserInfo> userInfoOptional = userRepository.findById(userInfo.getId());
		if (userInfoOptional.isEmpty()) {
			throw new NotFoundException("User does not exist id: " + userInfo.getId());
		}
		reviewOptional.get().setMovie(movieOptional.get());
		reviewOptional.get().setUserDetail(userInfoOptional.get());
		reviewOptional.get().setReviewText(reviewDto.getComment());
		reviewOptional.get().setRating(reviewDto.getRating());
		reviewOptional.get().setStatus(true);
		reviewOptional.get().setCreatedAt(reviewOptional.get().getCreatedAt());
		reviewOptional.get().setUpdatedAt(LocalDateTime.now());

		updateReviewCountAndTotal(movieOptional.get(), reviewDto.getRating(), 0, "UPDATE");
		return new ResponseDto<>(reviewOptional.get(), "Review updated successfully", 201);
	}

	@Override
	public ResponseDto<String> updateAverageRatingAfterReviewDeletion(long id) {
		Optional<Review> reviewOptional = reviewRepository.findById(id);
		if (reviewOptional.isPresent()) {
			updateReviewCountAndTotal(reviewOptional.get().getMovie(), 0, reviewOptional.get().getRating(), "DELETE");
			userRepository.deleteById(reviewOptional.get().getId());
			return new ResponseDto<>(null, "Review deleted successfully", 200);

		} else {
			throw new NotFoundException("Review not found with ID: " + id);
		}
	}

	@Override
	public ResponseDto<Review> getReviewById(long id) {
		Optional<Review> reviewOptional = reviewRepository.findById(id);

		if (reviewOptional.isPresent()) {
			return new ResponseDto<>(reviewOptional.get(), "Review fetched successfully", 200);
		} else {
			throw new NotFoundException("Review not found with ID: " + id);
		}
	}

}
