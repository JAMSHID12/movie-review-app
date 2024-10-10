package in.assesment.movie_review_service.controller;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.assesment.movie_review_service.Dto.GenreLanguageDto;
import in.assesment.movie_review_service.Dto.ResponseDto;
import in.assesment.movie_review_service.component.MovieModelAssembler;
import in.assesment.movie_review_service.custom_exceptions.NotFoundException;
import in.assesment.movie_review_service.model.Movie;
import in.assesment.movie_review_service.model.MovieModel;
import in.assesment.movie_review_service.service.IMovieCustomService;

@RestController
@RequestMapping("/movie")
@CrossOrigin(origins = "http://localhost:3000")
public class MovieCustomRestController {
	
	private static final Logger logInfo = LoggerFactory.getLogger(MovieCustomRestController.class);

	@Autowired
	private IMovieCustomService movieCustomService;
	
	@Autowired
    private MovieModelAssembler movieModelAssembler;
	
	@Autowired
    private PagedResourcesAssembler<Movie> pagedResourcesAssembler;

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
	
	@GetMapping("/search-movie")
	public ResponseEntity<ResponseDto<List<Movie>>> searchMoviesByNameandDescription(@RequestParam("query") String query) {
		try {
			logInfo.info(query + " this is the query");
			ResponseDto<List<Movie>> response = movieCustomService.searchMoviesByTitleOrDescription(query);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (NotFoundException e) {
			return new ResponseEntity<>(new ResponseDto<>(null, e.getMessage(), 204), HttpStatus.NOT_FOUND);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(new ResponseDto<>(null, e.getMessage(), 500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	

	@GetMapping("/filter")
	public ResponseEntity<ResponseDto<Page<Movie>>> filterMovies(
	        @RequestParam(required = false) Long genreId,
	        @RequestParam(required = false) Long languageId,
	        @RequestParam(required = false) 
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date releaseDate,
	        @RequestParam(required = false) Double minRating,
	        @RequestParam(defaultValue = "0") int page, 
	        @RequestParam(defaultValue = "10") int size 
	) {
	    try {
	        Pageable pageable = PageRequest.of(page, size);
	        ResponseDto<Page<Movie>> response = movieCustomService.filterMovies(pageable, genreId, languageId, releaseDate, minRating );
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    } catch (NotFoundException e) {
	        return new ResponseEntity<>(new ResponseDto<>(null, e.getMessage(), 204), HttpStatus.NOT_FOUND);
	    } catch (RuntimeException e) {
	        return new ResponseEntity<>(new ResponseDto<>(null, e.getMessage(), 500), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	
	
	@GetMapping("/paginate")
    public PagedModel<MovieModel> getMovies(@RequestParam(value = "page", defaultValue = "0") int page,
                                        @RequestParam(value = "size", defaultValue = "10") int size) {
		if(page < 0) page = 0;
        if(size < 1) size = 10;
        Pageable pageable = PageRequest.of(page, size);
        Page<Movie> moviePage = movieCustomService.movieWithPagination(pageable);

        return pagedResourcesAssembler.toModel(moviePage, movieModelAssembler);
    }
	
	@GetMapping("/genrs-language")
	public ResponseEntity<ResponseDto<GenreLanguageDto>> getAllGenereAndLanguageList(){
		 try {
		        ResponseDto<GenreLanguageDto> response = movieCustomService.getAllGenereAndLanguageList();
		        return new ResponseEntity<>(response, HttpStatus.OK);
		    } catch (NotFoundException e) {
		        return new ResponseEntity<>(new ResponseDto<>(null, e.getMessage(), 204), HttpStatus.NOT_FOUND);
		    } catch (RuntimeException e) {
		        return new ResponseEntity<>(new ResponseDto<>(null, e.getMessage(), 500), HttpStatus.INTERNAL_SERVER_ERROR);
		    }
	}

}
