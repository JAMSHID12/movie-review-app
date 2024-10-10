package in.assesment.movie_review_service.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import in.assesment.movie_review_service.model.Movie;

@Repository
public interface IMovieRepository extends JpaRepository<Movie, Long> {

	Optional<Movie> findByTitle(String title);

	@Query(value = "SELECT * FROM movies ORDER BY total_rating DESC LIMIT 5", nativeQuery = true)
	List<Movie> getLatestMoviesByRating();

	@Query(value = "SELECT * FROM movies ORDER BY release_date DESC LIMIT 5", nativeQuery = true)
	List<Movie> getLatestMoviesReleasedDate();

	@Query(value = "SELECT * FROM movies WHERE LOWER(title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR LOWER(description) LIKE LOWER(CONCAT('%', :searchTerm, '%')) LIMIT 10", nativeQuery = true)
	List<Movie> searchMoviesByTitleOrDescription(@Param("searchTerm") String searchTerm);

	@Query(value = "SELECT DISTINCT m.* " +
            "FROM movies m " +
            "JOIN movies_genres mg ON m.id = mg.movie_id " +
            "JOIN movies_languages ml ON m.id = ml.movie_id " +
            "WHERE (COALESCE(:genreId, NULL) IS NULL OR mg.genres_id = :genreId) " +
            "AND (COALESCE(:languageId, NULL) IS NULL OR ml.languages_id = :languageId) " +
            "AND (COALESCE(:releaseDate, NULL) IS NULL OR m.release_date = :releaseDate) " +
            "AND (COALESCE(:minRating, NULL) IS NULL OR m.total_rating >= :minRating) " , 
    nativeQuery = true)
	Page<Movie> filterMovies(@Param("genreId") Long genreId, @Param("languageId") Long languageId,
			@Param("releaseDate") Date releaseDate, // Change to String
			@Param("minRating") Double minRating, Pageable pageable);
	

}
