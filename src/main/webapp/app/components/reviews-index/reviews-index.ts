import reviewsApiModule, {IReviewsApi} from "../../services/reviews-api/reviews-api";
import {IBook, IBooksAndCountPages, IBooksApi} from "../../services/books-api/books-api";
import {BookPattern, ListParams} from "../../services/service-api";
import {IAuthor, IAuthorsApi} from "../../services/authors-api/authors-api";

class ReviewDetail {
    rating: number;
    count: number = 0;
    constructor(rating, count) {
        this.rating = rating;
        this.count = count;
    }
}

class ReviewsIndex {
    reviewDetails: ReviewDetail[] = [];
    books: IBook[];
    authors: IAuthor[];
    count: number = 5;
    constructor (private reviewsApi: IReviewsApi, private booksApi: IBooksApi, private authorsApi: IAuthorsApi) {
        this.reviewsApi.readAll().then(reviews => {reviews.data.forEach(reviews => {this.reviewDetails.push(new ReviewDetail(Math.round(reviews[0]), reviews[1])); console.log(this.reviewDetails)});});
        this.booksApi.findTop(this.count).then(books => this.books = books);
        this.authorsApi.findTop(this.count).then(authors => this.authors = authors);
    }
}

const moduleName = 'myApp.reviews-index';
export default moduleName

angular.module(moduleName, [reviewsApiModule])
    .component('reviewsIndex', {
        templateUrl: 'app/components/reviews-index/reviews-index.html',
        controller: ReviewsIndex
    }).controller("ReviewsIndex", ReviewsIndex);