package in.assesment.movie_review_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.assesment.movie_review_service.Dto.ResponseDto;
import in.assesment.movie_review_service.Dto.UserInfoDto;
import in.assesment.movie_review_service.custom_exceptions.ConflictException;
import in.assesment.movie_review_service.custom_exceptions.NotFoundException;
import in.assesment.movie_review_service.model.UserInfo;
import in.assesment.movie_review_service.service.IUserInfoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/users")
public class UserInfoRestController {
	
	@Autowired
	private IUserInfoService userService;
	
	@GetMapping("/csrf")
	public CsrfToken csrf(CsrfToken token) {
	    return token;
	}
	
	@PostMapping
	public ResponseEntity<ResponseDto<UserInfo>> saveUserInfo(@Valid @RequestBody UserInfoDto userInfoDto){
		try {
            ResponseDto<UserInfo> response = userService.createUser(userInfoDto);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch (ConflictException e) {
            return new ResponseEntity<>(new ResponseDto<>(null, e.getMessage(), 400), HttpStatus.BAD_REQUEST);
        }catch (RuntimeException e) {
            return new ResponseEntity<>(new ResponseDto<>(null, e.getMessage(), 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
		
	}
	
	@GetMapping("{id}")
	public ResponseEntity<ResponseDto<UserInfo>> getUserInfoById(@PathVariable(required = true) long id){
		try {
	        ResponseDto<UserInfo> response = userService.getUserById(id);
	        return ResponseEntity.ok(response);
	    } catch (NotFoundException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                             .body(new ResponseDto<>(null, e.getMessage(), 404));
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body(new ResponseDto<>(null, "An error occurred while fetching user", 500));
	    }
	}
	
	@PutMapping("{id}")
	public ResponseEntity<ResponseDto<UserInfo>> updateUser(@Valid @PathVariable(required = true) long id, @Valid @RequestBody UserInfoDto userInfoDto) {
	    try {
	        ResponseDto<UserInfo> response = userService.updateUser(id, userInfoDto);
	        return ResponseEntity.ok(response);
	    } catch (NotFoundException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                             .body(new ResponseDto<>(null, e.getMessage(), 404));
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body(new ResponseDto<>(null, "An error occurred while updating user", 500));
	    }
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<ResponseDto<String>> deleteUser(@PathVariable("id") long id) {
	    try {
	        ResponseDto<String> response = userService.deleteUser(id);
	        return ResponseEntity.ok(response);
	    } catch (NotFoundException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                             .body(new ResponseDto<>(null, e.getMessage(), 404));
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body(new ResponseDto<>(null, "An error occurred while deleting user", 500));
	    }
	}
	
	@PatchMapping("{id}")
	public ResponseEntity<ResponseDto<UserInfo>> patchUser(@PathVariable("id") long id) {
	    try {
	        ResponseDto<UserInfo> response = userService.deactivate(id);
	        return ResponseEntity.ok(response);
	    } catch (NotFoundException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                             .body(new ResponseDto<>(null, e.getMessage(), 404));
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body(new ResponseDto<>(null, "De activation failed", 500));
	    }
	}


}
