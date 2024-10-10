package in.assesment.movie_review_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.assesment.movie_review_service.model.Genre;

@Repository
public interface IGenresRepostiroy extends JpaRepository<Genre, Long>{

}
