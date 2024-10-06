package in.assesment.movie_review_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.assesment.movie_review_service.Dto.ResponseDto;
import in.assesment.movie_review_service.Dto.ReviewDto;
import in.assesment.movie_review_service.custom_exceptions.ConflictException;
import in.assesment.movie_review_service.custom_exceptions.NotFoundException;
import in.assesment.movie_review_service.model.Review;
import in.assesment.movie_review_service.service.IReviewService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/review")
public class ReviewRestController {

	
	@Autowired
	private IReviewService reviewService;
	
	@PostMapping
	public ResponseEntity<ResponseDto<Review>> saveReview(@Valid @RequestBody ReviewDto reviewDto){
		try {
            ResponseDto<Review> response = reviewService.createReview(reviewDto);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch (NotFoundException e) {
            return new ResponseEntity<>(new ResponseDto<>(null, e.getMessage(), 400), HttpStatus.BAD_REQUEST);
        }catch (ConflictException e) {
            return new ResponseEntity<>(new ResponseDto<>(null, e.getMessage(), 304), HttpStatus.ALREADY_REPORTED);
        }
		catch (RuntimeException e) {
            return new ResponseEntity<>(new ResponseDto<>(null, e.getMessage(), 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
		
	}
	
	@GetMapping("{id}")
	public ResponseEntity<ResponseDto<Review>> getUserInfoById(@PathVariable(required = true) long id){
		try {
			ResponseDto<Review> response = reviewService.getReviewById(id);
	        return ResponseEntity.ok(response);
	    } catch (NotFoundException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                             .body(new ResponseDto<>(null, e.getMessage(), 404));
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body(new ResponseDto<>(null, "An error occurred while fetching review", 500));
	    }
	}
	
	@PutMapping("{id}")
	public ResponseEntity<ResponseDto<Review>> updateReview(@PathVariable("id") long id, @RequestBody ReviewDto reviewDto) {
	    try {
	        ResponseDto<Review> response = reviewService.updateReview(id, reviewDto);
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
	public ResponseEntity<ResponseDto<String>> deleteReview(@PathVariable("id") long id) {
	    try {
	        ResponseDto<String> response = reviewService.deleteReview(id);
	        return ResponseEntity.ok(response);
	    } catch (NotFoundException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                             .body(new ResponseDto<>(null, e.getMessage(), 404));
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body(new ResponseDto<>(null, "An error occurred while deleting review", 500));
	    }
	}
	
}
