package in.assesment.movie_review_service.service;


import in.assesment.movie_review_service.Dto.ResponseDto;
import in.assesment.movie_review_service.Dto.ReviewDto;
import in.assesment.movie_review_service.model.Review;

public interface IReviewService {

	ResponseDto<Review> createReview( ReviewDto reviewDto);

	ResponseDto<Review> updateReview(long id, ReviewDto reviewDto);

	ResponseDto<String> updateAverageRatingAfterReviewDeletion(long id);

	ResponseDto<Review> getReviewById(long id);

}
