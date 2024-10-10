package in.assesment.movie_review_service.service;


import in.assesment.movie_review_service.Dto.ResponseDto;
import in.assesment.movie_review_service.Dto.ReviewDto;
import in.assesment.movie_review_service.model.Review;
import in.assesment.movie_review_service.model.UserInfo;

public interface IReviewService {

	ResponseDto<Review> createReview( UserInfo userInfo, ReviewDto reviewDto);

	ResponseDto<Review> updateReview(long id, ReviewDto reviewDto, final UserInfo userInfo);

	ResponseDto<String> updateAverageRatingAfterReviewDeletion(long id);

	ResponseDto<Review> getReviewById(long id);

}
