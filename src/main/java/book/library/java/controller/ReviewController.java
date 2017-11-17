package book.library.java.controller;

import book.library.java.dto.ReviewDto;
import book.library.java.exception.DaoException;
import book.library.java.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/review")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public void create(@RequestBody ReviewDto reviewDto) throws DaoException {
        reviewService.create(reviewDto);
    }

    @GetMapping(value = "/find")
    public ResponseEntity<List<ReviewDto>> read() {
        return ResponseEntity.ok(reviewService.read());
    }

    @PutMapping(value = "/event")
    public void update(@RequestBody ReviewDto reviewDto) throws DaoException {
        reviewService.update(reviewDto);
    }

    @PutMapping(value = "/delete")
    public void delete(@RequestBody Integer idReview) throws DaoException {
        reviewService.delete(idReview);
    }
}
