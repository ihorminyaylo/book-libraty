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
    constructor (private booksApi: IBooksApi, private authorsApi: IAuthorsApi) {
        this.booksApi.countBooksOfEachRating().then(reviews => {reviews.data.forEach(reviews => {this.reviewDetails.push(new ReviewDetail(Math.round(reviews[0]), reviews[1]));});});
        this.booksApi.findTop().then(books => {this.books = books; this.books.forEach(book => book.averageRatingRound = Math.round(book.averageRating))});
        this.authorsApi.findTop().then(authors => {this.authors = authors; this.authors.forEach(author => author.averageRatingRound = Math.round(author.averageRating))});
    }
}

const moduleName = 'myApp.reviews-index';
export default moduleName

angular.module(moduleName, [reviewsApiModule])
    .component('reviewsIndex', {
        templateUrl: 'app/components/reviews-index/reviews-index.html',
        controller: ReviewsIndex
    }).controller("ReviewsIndex", ReviewsIndex);