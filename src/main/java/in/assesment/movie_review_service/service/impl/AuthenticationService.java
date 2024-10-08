package in.assesment.movie_review_service.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import in.assesment.movie_review_service.Dto.LoginUserDto;
import in.assesment.movie_review_service.model.UserInfo;
import in.assesment.movie_review_service.repository.IUserInfoRepository;
import in.assesment.movie_review_service.service.IAuthenticationService;

@Service
public class AuthenticationService implements IAuthenticationService{

	@Autowired
	private IUserInfoRepository userRepository;

	@Autowired
	private AuthenticationManager authenticationManager;

	public UserInfo authenticate(LoginUserDto input) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(input.getEmail(), input.getPassword()));

		return userRepository.findByEmail(input.getEmail()).orElseThrow();
	}
}
