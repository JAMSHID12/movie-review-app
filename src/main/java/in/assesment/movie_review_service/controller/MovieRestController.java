package in.assesment.movie_review_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.assesment.movie_review_service.Dto.MovieDto;
import in.assesment.movie_review_service.Dto.ResponseDto;
import in.assesment.movie_review_service.custom_exceptions.ConflictException;
import in.assesment.movie_review_service.custom_exceptions.NotFoundException;
import in.assesment.movie_review_service.model.Movie;
import in.assesment.movie_review_service.model.UserInfo;
import in.assesment.movie_review_service.service.IMovieService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/movie")
public class MovieRestController {

	@Autowired
	private IMovieService movieService;

	@PostMapping
	public ResponseEntity<ResponseDto<Movie>> registerMovie(@AuthenticationPrincipal final UserInfo userInfo, @Valid @RequestBody MovieDto movieDto) {
		try {
			ResponseDto<Movie> response = movieService.createMovie(userInfo, movieDto);
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} catch (ConflictException e) {
			return new ResponseEntity<>(new ResponseDto<>(null, e.getMessage(), 400), HttpStatus.BAD_REQUEST);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(new ResponseDto<>(null, e.getMessage(), 500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("{id}")
	public ResponseEntity<ResponseDto<Movie>> getMovieDetailsById(@PathVariable(required = true) long id) {
		try {
			ResponseDto<Movie> response = movieService.getMovieDetailsById(id);
			return ResponseEntity.ok(response);
		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDto<>(null, e.getMessage(), 404));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseDto<>(null, "An error occurred while fetching movie", 500));
		}
	}

	@PutMapping("{id}")
	public ResponseEntity<ResponseDto<Movie>> updateMovie(@AuthenticationPrincipal final UserInfo userInfo, @PathVariable(required = true) long id,
			@RequestBody MovieDto movieDto) {
		try {
			ResponseDto<Movie> response = movieService.updateMovie(userInfo, id, movieDto);
			return ResponseEntity.ok(response);
		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDto<>(null, e.getMessage(), 404));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseDto<>(null, "An error occurred while updating movie", 500));
		}
	}

	@DeleteMapping("{id}")
	public ResponseEntity<ResponseDto<String>> deleteMovie(@AuthenticationPrincipal final UserInfo userInfo, @PathVariable("id") long id) {
		try {
			ResponseDto<String> response = movieService.deleteMoive(id);
			return ResponseEntity.ok(response);
		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDto<>(null, e.getMessage(), 404));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseDto<>(null, "An error occurred while deleting movie", 500));
		}
	}
	
	@PatchMapping("{id}")
	public ResponseEntity<ResponseDto<Movie>> patchMovie(@AuthenticationPrincipal final UserInfo userInfo, @PathVariable("id") long id, @RequestBody MovieDto movieDto) {
	    try {
	        ResponseDto<Movie> response = movieService.deactivate(id, movieDto);
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
