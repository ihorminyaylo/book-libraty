import * as angular from 'angular'

import booksApiModule, {IBook, IBooksApi} from '../../services/books-api/books-api'
import {ListParams, ReviewPattern} from "../../services/service-api";
import {IReview, IReviewsAndCountPages, IReviewsApi} from "../../services/reviews-api/reviews-api";
import {IAuthor, IAuthorsApi} from "../../services/authors-api/authors-api";

interface IRouteParams extends angular.route.IRouteParamsService {
    idBook: string;
}

class BooksShow {
  
  book: IBook = new IBook;
  reviewsAndCountPages: IReviewsAndCountPages;
  currentPage = 1;
  limit: number = 10;
  offset: number = 0;
  
  constructor (private booksApi: IBooksApi, private reviewsApi: IReviewsApi, private $routeParams: IRouteParams, private $uibModal: ng.ui.bootstrap.IModalService) {
      if (!isNaN(parseInt($routeParams.idBook))) {
          this.book.id = parseInt($routeParams.idBook);
      }
      this.pageChanged(this.currentPage);
  }
  pageChanged(page) {
        this.currentPage = page;
        this.offset = (this.currentPage-1)*this.limit;
        this.booksApi.getByBook(this.book.id).then(book => {this.book = book});
        return this.reviewsApi.find(new ListParams(this.limit, this.offset, new ReviewPattern(this.book.id), null))
            .then(reviewsAndCountPages => {this.reviewsAndCountPages = reviewsAndCountPages;});
  }
  dialog;
    edit(): void {
        this.dialog = this.$uibModal.open({
            backdrop: false,
            controller: EditBook,
            controllerAs: 'edit',
            templateUrl: 'edit-book.html',
            resolve: {
                book: () => this.book
            },
            scope: ''
        });

        this.dialog.result.then( function () {
        })
    }

    addReview(): void {
        this.dialog = this.$uibModal.open({
            controller: AddReview,
            controllerAs: 'addReview',
            templateUrl: 'add-review.html',
            scope: '',
            resolve: {
                book: () => this.book
            }
        });

        this.dialog.result.then( function () {
        })
    }
}

const moduleName = 'myApp.books-show' 
export default moduleName

angular.module(moduleName, [booksApiModule])
  .component('booksShow', {
    templateUrl: 'app/components/books-show/books-show.html',
    controller: BooksShow
  }).controller("BooksShow",BooksShow);


class EditBook {
    book: IBook = new IBook();
    authors: IAuthor[] = [];
    selectAuthors: IAuthor[] = [];
    click: boolean = false;


    modalOptions = {
        closeButtonText: 'CLOSE',
        actionButtonText: 'OK',
        headerText: `Edit book: ${this.book.name} ${this.book.publisher}`,
    };
    constructor(private $uibModalInstance: ng.ui.bootstrap.IModalServiceInstance,
                private booksApi: IBooksApi,
                private authorsApi: IAuthorsApi,
                private $uibModal: ng.ui.bootstrap.IModalService,
                book: IBook) {
        this.book = book;
        this.authorsApi.readAll().then(authors => this.authors = authors.data);
        this.selectAuthors = this.book.authors;
    }
    addAuthorForBook(author: IAuthor) {
        this.selectAuthors.push(author);
        let index = this.authors.indexOf(author, 0);
        if (index > -1) {
            this.authors.splice(index, 1);
        }
    }
    deleteAuthor(authorDelete: IAuthor) {
        let index = this.selectAuthors.indexOf(authorDelete, 0);
        if (index > -1) {
            this.selectAuthors.splice(index, 1);
        }
    }
    ok(name, publisher, yearPublisher) {
        this.book.name = name;
        this.book.publisher = publisher;
        this.book.yearPublished = yearPublisher;
        this.booksApi.createBook(this.book, this.selectAuthors);
        this.$uibModalInstance.close();
    }
    cancel(): void {
        this.$uibModalInstance.close();
    }
}

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
                private book: IBook) {
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
        this.booksApi.createReview(commenterName, comment, rating, this.book);
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