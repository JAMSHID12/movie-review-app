package in.assesment.movie_review_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import in.assesment.movie_review_service.Dto.LoginResponse;
import in.assesment.movie_review_service.Dto.LoginUserDto;
import in.assesment.movie_review_service.Dto.ResponseDto;
import in.assesment.movie_review_service.Dto.UserInfoDto;
import in.assesment.movie_review_service.custom_exceptions.ConflictException;
import in.assesment.movie_review_service.model.UserInfo;
import in.assesment.movie_review_service.service.IUserInfoService;
import in.assesment.movie_review_service.service.impl.AuthenticationService;
import in.assesment.movie_review_service.service.impl.JwtService;
import jakarta.validation.Valid;


@RequestMapping("/auth")
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class AuthenticationController {
	
	@Autowired
	private JwtService jwtService;

	@Autowired
	private AuthenticationService authenticationService;
	
	@Autowired
	private IUserInfoService userService;

	@PostMapping("/register")
	public ResponseEntity<ResponseDto<UserInfo>> saveUserInfo(@Valid @RequestBody UserInfoDto userInfoDto){
		try {
            ResponseDto<UserInfo> response = userService.createUser(userInfoDto);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch (ConflictException e) {
            return new ResponseEntity<>(new ResponseDto<>(null, e.getMessage(), 400), HttpStatus.BAD_REQUEST);
        }catch (RuntimeException e) {
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto<>(null, "An error occurred while fetching user", 500));        }
		
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
		UserInfo authenticatedUser = authenticationService.authenticate(loginUserDto);

		String jwtToken = jwtService.generateToken(authenticatedUser);

		LoginResponse loginResponse = LoginResponse.builder().email(authenticatedUser.getEmail())
				.userName(authenticatedUser.getName()).token(jwtToken).tokenType("bearer")
				.expiresIn(jwtService.getExpirationTime())
				.build();

		return ResponseEntity.ok(loginResponse);
	}
}
