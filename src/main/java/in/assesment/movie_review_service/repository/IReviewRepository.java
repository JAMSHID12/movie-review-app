package in.assesment.movie_review_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import in.assesment.movie_review_service.model.Review;

@Repository
public interface IReviewRepository extends JpaRepository<Review, Long>{
	
	@Query(value = "select * from reviews where user_id = :userId and movie_id = :movieId", nativeQuery = true)
	List<Review> findByUserIdAndMovieId(@Param("userId") Long userId, @Param("movieId") Long movieId); 
}
