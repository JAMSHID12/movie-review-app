package in.assesment.movie_review_service.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.assesment.movie_review_service.Dto.ResponseDto;
import in.assesment.movie_review_service.custom_exceptions.NotFoundException;
import in.assesment.movie_review_service.model.Movie;
import in.assesment.movie_review_service.service.IMovieCustomService;

@RestController
@RequestMapping("/api/movie")
@CrossOrigin(origins = "http://localhost:3000")
public class MovieCustomRestController {
	
	@Autowired
	private IMovieCustomService movieCustomService;

	@GetMapping("/latest-movie-by-rating")
	public ResponseEntity<ResponseDto<List<Movie>>> getLatestMoviesBasedonRating() {
		try {
			ResponseDto<List<Movie>> response = movieCustomService.getLatestMoviesByRating();
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (NotFoundException e) {
			return new ResponseEntity<>(new ResponseDto<>(null, e.getMessage(), 204), HttpStatus.NOT_FOUND);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(new ResponseDto<>(null, e.getMessage(), 500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/latest-movie-by-date")
	public ResponseEntity<ResponseDto<List<Movie>>> getLatestMoviesBasedonReleasedDate() {
		try {
			ResponseDto<List<Movie>> response = movieCustomService.getLatestMoviesByReleasedDate();
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (NotFoundException e) {
			return new ResponseEntity<>(new ResponseDto<>(null, e.getMessage(), 204), HttpStatus.NOT_FOUND);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(new ResponseDto<>(null, e.getMessage(), 500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/filter") 
    public ResponseEntity<ResponseDto<List<Movie>>> filterMovies(
            @RequestParam(required = false) Long genreId,
            @RequestParam(required = false) Long languageId,
            @RequestParam(required = false) Date releaseDate,
            @RequestParam(required = false) Double minRating) {
		try {
			ResponseDto<List<Movie>> response = movieCustomService.filterMovies(genreId, languageId, releaseDate, minRating);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (NotFoundException e) {
			return new ResponseEntity<>(new ResponseDto<>(null, e.getMessage(), 204), HttpStatus.NOT_FOUND);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(new ResponseDto<>(null, e.getMessage(), 500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }

}
