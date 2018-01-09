package book.library.java.controller;

import book.library.java.exception.BusinessException;
import book.library.java.list.ListParams;
import book.library.java.model.Review;
import book.library.java.model.pattern.ReviewPattern;
import book.library.java.service.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/review")
public class ReviewController {

    private static final Logger log = LoggerFactory.getLogger(ReviewController.class);

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody Review review) throws BusinessException {
        log.info("In create(review=[{}])", review);
        return ResponseEntity.ok(reviewService.create(review));
    }

    @PostMapping(value = "/find")
    public ResponseEntity<?> read(@RequestBody ListParams<ReviewPattern> listParams) throws BusinessException {
        log.info("In read(listParams=[{}])", listParams);
        return ResponseEntity.ok(reviewService.readReviews(listParams));
    }

    @PutMapping
    public ResponseEntity update(@RequestBody Review review) throws BusinessException {
        log.info("In update(listParams=[{}])", review);
        reviewService.update(review);
        return ResponseEntity.ok(review);
    }
}
