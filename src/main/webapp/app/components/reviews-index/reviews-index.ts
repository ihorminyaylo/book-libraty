import reviewsApiModule, {IReviewsApi} from "../../services/reviews-api/reviews-api";

class ReviewDetail {
    rating: number;
    count: number;
    constructor(rating, count) {
        this.rating = rating;
        this.count = count;
    }
}

class ReviewsIndex {
    reviewDetails: ReviewDetail[] = [];
    constructor (private reviewsApi: IReviewsApi) {
        this.reviewsApi.readAll().then(reviews => {reviews.data.forEach(reviews => this.reviewDetails.push(new ReviewDetail(reviews[0], reviews[1])));});
    }
}

const moduleName = 'myApp.reviews-index';
export default moduleName

angular.module(moduleName, [reviewsApiModule])
    .component('reviewsIndex', {
        templateUrl: 'app/components/reviews-index/reviews-index.html',
        controller: ReviewsIndex
    }).controller("ReviewsIndex", ReviewsIndex);