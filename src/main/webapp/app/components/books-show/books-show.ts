import * as angular from 'angular'

import booksApiModule, {IBook, IBooksApi} from '../../services/books-api/books-api'
import {ListParams, ReviewPattern} from "../../services/service-api";
import {IReviewsAndCountPages, IReviewsApi} from "../../services/reviews-api/reviews-api";
import {IAuthor, IAuthorsApi} from "../../services/authors-api/authors-api";

interface IRouteParams extends angular.route.IRouteParamsService {
    idBook: string;
}

class BooksShow {
    maxPages: number = 3;
    book: IBook = new IBook;
    reviewsAndCountPages: IReviewsAndCountPages;
    currentPage = 1;
    limit: number = 10;
    offset: number = 0;
    authors: IAuthor[] = [];
    click: boolean = false;
    listParam;

    constructor(private booksApi: IBooksApi, private reviewsApi: IReviewsApi, private authorsApi: IAuthorsApi, private $routeParams: IRouteParams, private $uibModal: ng.ui.bootstrap.IModalService) {
        if (!isNaN(parseInt($routeParams.idBook))) {
            this.book.id = parseInt($routeParams.idBook);
        }
        this.pageChanged(this.currentPage);
    }

    pageChanged(page) {
        this.currentPage = page;
        this.offset = (this.currentPage - 1) * this.limit;
        this.booksApi.getByBook(this.book.id).then(book => {
            this.book = book
        });
        this.authorsApi.readAll().then(authors => this.authors = authors.data);
        this.listParam = new ListParams(this.limit, this.offset, new ReviewPattern(this.book.id), null);
        return this.reviewsApi.find(this.listParam)
            .then(reviewsAndCountPages => {
                this.reviewsAndCountPages = reviewsAndCountPages;
            });
    }

    saveChange() {
        this.booksApi.updateBook(this.book).then(response => { this.dialog = this.$uibModal.open({
            controller: MessageDialog,
            controllerAs: 'message',
            templateUrl: 'message.html',
            resolve: {
                data: response.data
            }
        }); this.pageChanged(this.currentPage)});
    }
    cancelChange() {
        this.pageChanged(this.currentPage);
    }

    addAuthorForBook(author: IAuthor) {
        this.book.authors.push(author);
        let index = this.authors.indexOf(author, 0);
        if (index > -1) {
            this.authors.splice(index, 1);
        }
    }

    deleteAuthor(authorDelete: IAuthor) {
        let index = this.book.authors.indexOf(authorDelete, 0);
        if (index > -1) {
            this.book.authors.splice(index, 1);
        }
    }

    dialog;

    addReview(): void {
        this.dialog = this.$uibModal.open({
            controller: AddReview,
            controllerAs: 'addReview',
            templateUrl: 'add-review.html',
            scope: '',
            resolve: {
                book: () => this.book,
                reviewsAndCountPages: () => this.reviewsAndCountPages,
                listParam: () => this.listParam
            }
        });

        this.dialog.result.then(function () {
        })
    }
}

const moduleName = 'myApp.books-show'
export default moduleName

angular.module(moduleName, [booksApiModule])
    .component('booksShow', {
        templateUrl: 'app/components/books-show/books-show.html',
        controller: BooksShow
    }).controller("BooksShow", BooksShow);

class AddReview {
    rate = 1;
    max = 5;
    click: boolean = false;
    min = 1;
    defaultRating = 1;

    //review: IReview = new IReview();

    isReadonly = false;

    hoveringOver(value) {
        overStar = value;
        percent = 100 * (value / this.max);
    };

    ratingStates = [
        {stateOn: 'glyphicon-ok-sign', stateOff: 'glyphicon-ok-circle'},
        {stateOn: 'glyphicon-star', stateOff: 'glyphicon-star-empty'},
        {stateOn: 'glyphicon-heart', stateOff: 'glyphicon-ban-circle'},
        {stateOn: 'glyphicon-heart'},
        {stateOff: 'glyphicon-off'}
    ];

    constructor(private $uibModalInstance: ng.ui.bootstrap.IModalServiceInstance,
                private booksApi: IBooksApi,
                private $uibModal: ng.ui.bootstrap.IModalService,
                private book: IBook,
                private reviewsAndCountPages: IReviewsAndCountPages,
                private reviewsApi: IReviewsApi,
                private listParam) {
    }

    ok(commenterName, comment, rating): void {
        if (isNaN(rating)) {
            this.$uibModal.open({
                animation: true,
                backdrop: false,
                controller: ErrorDialog,
                controllerAs: 'errorDialog',
                templateUrl: 'error.html',
            });
            return;
        }
        this.booksApi.createReview(commenterName, comment, rating, this.book).then(response => {console.log('start');this.reviewsApi.find(this.listParam)
            .then(reviewsAndCountPages => {
                this.reviewsAndCountPages.list = reviewsAndCountPages.list; this.reviewsAndCountPages.totalItems = reviewsAndCountPages.totalItems
            })});
        this.$uibModalInstance.close(true);
    }

    cancel(): void {
        this.$uibModalInstance.close();
    }
}

class ErrorDialog {
    constructor(private $uibModalInstance: ng.ui.bootstrap.IModalServiceInstance) {
    }

    close() {
        this.$uibModalInstance.close();
    }
}

class MessageDialog {
    messageTitle;
    message;
    constructor(private $uibModalInstance: ng.ui.bootstrap.IModalServiceInstance, data) {
        if (Number(data)) {
            this.messageTitle = 'Success!';
            this.message = 'Your changes were successful.';
        }
        else {
            this.messageTitle = 'Warning!';
            this.message = data;
        }
    }

    close() {
        this.$uibModalInstance.close();
    }
}