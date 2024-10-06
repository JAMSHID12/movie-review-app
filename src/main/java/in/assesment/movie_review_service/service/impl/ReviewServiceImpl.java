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
	public ResponseDto<Review> createReview(final ReviewDto reviewDto) {
			final Optional<Movie> movieOptional = movieRepository.findById(reviewDto.getMovieId());
			if (movieOptional.isEmpty()) {
				throw new NotFoundException("Movie does not exist id: " + reviewDto.getMovieId());
			}
			final Optional<UserInfo> userInfoOptional = userRepository.findById(reviewDto.getUserId());
			if (userInfoOptional.isEmpty()) {
				throw new NotFoundException("User does not exist id: " + reviewDto.getUserId());
			}
			if (reviewRepository.findByUserIdAndMovieId(reviewDto.getUserId(), reviewDto.getMovieId()).isEmpty()) {
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
				updateReviewCountAndTotal(movieOptional.get(), reviewDto.getRating());

				return new ResponseDto<>(savedUser, "Review submitted successfully", 201);
			} else {
				throw new ConflictException("You Already reviewed this movie");
			}

	}

	public void updateReviewCountAndTotal(Movie movieOptional, int rating) {
		if (movieOptional.getTotalRatedCount() != 1) {
			final double totalRating = movieOptional.getTotalRating() + rating;
			final int totalRatedCount = movieOptional.getTotalRatedCount() + 1;
			final double average = (double) totalRating / totalRatedCount;
			final double updatedRating = Math.round(average * 100.0) / 100.0;
			movieOptional.setTotalRating(updatedRating);
			movieOptional.setTotalRatedCount(totalRatedCount);
			movieRepository.save(movieOptional);
		}
	}

	@Override
	public ResponseDto<Review> updateReview(long id, ReviewDto reviewDto) {
		final Optional<Review> reviewOptional = reviewRepository.findById(id);
		if (reviewOptional.isEmpty()) {
			throw new NotFoundException("Review does not exist by id: " + id);
		}
		final Optional<Movie> movieOptional = movieRepository.findById(reviewDto.getMovieId());
		if (movieOptional.isEmpty()) {
			throw new NotFoundException("Movie does not exist id: " + reviewDto.getMovieId());
		}
		final Optional<UserInfo> userInfoOptional = userRepository.findById(reviewDto.getUserId());
		if (userInfoOptional.isEmpty()) {
			throw new NotFoundException("User does not exist id: " + reviewDto.getUserId());
		}
		reviewOptional.get().setMovie(movieOptional.get());
		reviewOptional.get().setUserDetail(userInfoOptional.get());
		reviewOptional.get().setReviewText(reviewDto.getComment());
		reviewOptional.get().setRating(reviewDto.getRating());
		reviewOptional.get().setStatus(true);
		reviewOptional.get().setCreatedAt(reviewOptional.get().getCreatedAt());
		reviewOptional.get().setUpdatedAt(LocalDateTime.now());

		updateReviewCountAndTotal(movieOptional.get(), reviewDto.getRating());
		return new ResponseDto<>(reviewOptional.get(), "Review updated successfully", 201);
	}

	@Override
	public ResponseDto<String> deleteReview(long id) {
		Optional<Review> reviewOptional = reviewRepository.findById(id);

		if (reviewOptional.isPresent()) {
			userRepository.deleteById(reviewOptional.get().getId());
			return new ResponseDto<>(null, "User deleted successfully", 200);

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
			throw new NotFoundException("User not found with ID: " + id);
		}
	}

}
