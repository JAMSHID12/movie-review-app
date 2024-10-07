package in.assesment.movie_review_service.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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

	@Query("SELECT m FROM Movie m WHERE LOWER(m.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR LOWER(m.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
	List<Movie> searchMoviesByTitleOrDescription(String searchTerm);

	@Query(value = "SELECT m.* FROM movies m " + "JOIN movie_genre mg ON m.id = mg.movie_id "
			+ "JOIN genres g ON mg.genre_id = g.id " + "JOIN movie_language ml ON m.id = ml.movie_id "
			+ "JOIN languages l ON ml.language_id = l.id " + "WHERE (:genreId IS NULL OR g.id = :genreId) "
			+ "AND (:languageId IS NULL OR l.id = :languageId) "
			+ "AND (:releaseDate IS NULL OR m.release_date = :releaseDate) "
			+ "AND (:minRating IS NULL OR m.rating >= :minRating) "
			+ "ORDER BY m.release_date DESC", nativeQuery = true)
	List<Movie> filterMovies(@Param("genreId") Long genreId, @Param("languageId") Long languageId,
			@Param("releaseDate") Date releaseDate, @Param("minRating") Double minRating);

}
