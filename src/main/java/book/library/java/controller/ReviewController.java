package book.library.java.controller;

import book.library.java.dto.ReadParamsDto;
import book.library.java.exception.BusinessException;
import book.library.java.exception.DaoException;
import book.library.java.model.Review;
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
    public ResponseEntity create(@RequestBody Review review) throws DaoException, BusinessException {
        reviewService.create(review);
        return ResponseEntity.ok(review);
    }

    @PostMapping(value = "/find")
    public ResponseEntity<?> read(@RequestBody ReadParamsDto readParamsDto) throws BusinessException {
        return ResponseEntity.ok(reviewService.read(readParamsDto));
    }

    @PutMapping(value = "/event")
    public ResponseEntity update(@RequestBody Review review) throws DaoException, BusinessException {
        reviewService.update(review);
        return ResponseEntity.ok(review);
    }

    @PutMapping(value = "/delete")
    public ResponseEntity delete(@RequestBody List<Integer> listIdReviews) throws DaoException, BusinessException {
        reviewService.delete(listIdReviews);
        return ResponseEntity.ok(listIdReviews);
    }
}
