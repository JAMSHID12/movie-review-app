package in.assesment.movie_review_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import in.assesment.movie_review_service.model.UserInfo;

@Repository
public interface IUserInfoRepository extends JpaRepository<UserInfo, Long>{

	Optional<UserInfo> findByEmail(String email);
}
