package in.assesment.movie_review_service.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.assesment.movie_review_service.Dto.ResponseDto;
import in.assesment.movie_review_service.Dto.UserInfoDto;
import in.assesment.movie_review_service.custom_exceptions.ConflictException;
import in.assesment.movie_review_service.custom_exceptions.NotFoundException;
import in.assesment.movie_review_service.model.UserInfo;
import in.assesment.movie_review_service.repository.IUserInfoRepository;
import in.assesment.movie_review_service.service.IUserInfoService;

@Service
public class UserInfoServiceImpl implements IUserInfoService {

	private static final Logger logInfo = LoggerFactory.getLogger(UserInfoServiceImpl.class);

	@Autowired
	private IUserInfoRepository userRepository;

	@Override
	public ResponseDto<UserInfo> createUser(UserInfoDto userInfoDto) {

		try {
			if (userRepository.findByEmail(userInfoDto.getEmail()).isPresent()) {
				throw new ConflictException("Email already exists");
			}
			final UserInfo userInfo = UserInfo.builder().name(userInfoDto.getUsername()).email(userInfoDto.getEmail())
					.password(userInfoDto.getPassword()).isActive(true).roles("ROLE_USER")
					.createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).build();
			final UserInfo savedUser = userRepository.save(userInfo);
			return new ResponseDto<>(savedUser, "User created successfully", 201);
		} catch (ConflictException e) {
			logInfo.error("Conflict occurred: {}", e.getMessage());
			throw e;
		} catch (Exception e) {
			logInfo.error("Error creating user: {}", e.getMessage(), e);
			return new ResponseDto<>(null, "An error occurred while creating user", 500);
		}
	}

	@Override
	public ResponseDto<UserInfo> getUserById(long id) {
		Optional<UserInfo> userOptional = userRepository.findById(id);

		if (userOptional.isPresent()) {
			return new ResponseDto<>(userOptional.get(), "User fetched successfully", 200);
		} else {
			throw new NotFoundException("User not found with ID: " + id);
		}
	}

	@Override
	public ResponseDto<UserInfo> updateUser(long id, UserInfoDto userInfoDto) {
		Optional<UserInfo> userOptional = userRepository.findById(id);

		if (userOptional.isPresent()) {
			UserInfo existingUser = UserInfo.builder().id(id).name(userInfoDto.getUsername()).email(userInfoDto.getEmail())
					.password(userInfoDto.getPassword()).createdAt(userOptional.get().getCreatedAt())
					.roles(userOptional.get().getRoles()).isActive(userOptional.get().isActive())
					.updatedAt(LocalDateTime.now()).build();
			UserInfo updatedUser = userRepository.save(existingUser);
			return new ResponseDto<>(updatedUser, "User updated successfully", 200);
		} else {
			throw new NotFoundException("User not found with ID: " + id);
		}
	}

	@Override
	public ResponseDto<String> deleteUser(long id) {
		Optional<UserInfo> userOptional = userRepository.findById(id);

		if (userOptional.isPresent()) {
			userRepository.delete(userOptional.get());
			return new ResponseDto<>(null, "User deleted successfully", 200);
		} else {
			throw new NotFoundException("User not found with ID: " + id);
		}
	}

	@Override
	public ResponseDto<UserInfo> deactivate(long id) {
		Optional<UserInfo> userOptional = userRepository.findById(id);

		if (userOptional.isPresent()) {
			UserInfo existingUser = userOptional.get();
			existingUser.setActive(false);
			existingUser.setUpdatedAt(LocalDateTime.now());

			UserInfo updatedUser = userRepository.save(existingUser);
			return new ResponseDto<>(updatedUser, "User de-activated successfully", 200);
		} else {
			throw new NotFoundException("User not found with ID: " + id);
		}
	}

}
