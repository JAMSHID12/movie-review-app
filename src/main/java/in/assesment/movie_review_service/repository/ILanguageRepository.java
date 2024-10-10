package in.assesment.movie_review_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.assesment.movie_review_service.model.Language;

@Repository
public interface ILanguageRepository extends JpaRepository<Language, Long>{

}
