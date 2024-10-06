package in.assesment.movie_review_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.assesment.movie_review_service.model.Movie;

@Repository
public interface IMovieRepository extends JpaRepository<Movie, Long>{

	Optional<Movie> findByTitle(String title);

}
