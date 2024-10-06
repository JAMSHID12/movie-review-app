package in.assesment.movie_review_service.service;

import in.assesment.movie_review_service.Dto.ResponseDto;
import in.assesment.movie_review_service.Dto.UserInfoDto;
import in.assesment.movie_review_service.model.UserInfo;

public interface IUserInfoService {

	ResponseDto<UserInfo> createUser(UserInfoDto userInfoDto);

	ResponseDto<UserInfo> getUserById(long id);

	ResponseDto<UserInfo> updateUser(long id, UserInfoDto userInfoDto);

	ResponseDto<String> deleteUser(long id);

	ResponseDto<UserInfo> deactivate(long id);

}
