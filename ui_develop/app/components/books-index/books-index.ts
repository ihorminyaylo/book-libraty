import * as angular from 'angular'

import booksApiModule, {IBook, IBooksAndCountPages, IBooksApi} from '../../services/books-api/books-api'
import {IAuthor, IAuthorsApi} from "../../services/authors-api/authors-api";
import {IDatepickerPopupConfig, IDatepickerPopupConfig} from "angular-ui-bootstrap";
import {IReview, IReviewsApi} from "../../services/reviews-api/reviews-api";

interface IRouteParams extends angular.route.IRouteParamsService {
    isbn: string
    rating: number;
}

class BooksIndex {
    checkAll: boolean;
    activeDeleteSelected: boolean;
    currentPage = 1;
    maxSize: number = 10;
    offset: number = 0;
    byAuthorId: number;
    rating: number;
    authorWithBooks: IAuthor;
    booksAndCountPages: IBooksAndCountPages;
    filter: String;
    authors: IAuthor[] = [];
    showSelectedAuthors: boolean = true;
    constructor (private booksApi: IBooksApi, private authorsApi: IAuthorsApi, private $uibModal: ng.ui.bootstrap.IModalService, $routeParams: IRouteParams) {
        if (!isNaN(parseInt($routeParams.isbn))) {
            this.byAuthorId = parseInt($routeParams.isbn);
            this.showSelectedAuthors = false;
            this.authorsApi.getById(this.byAuthorId).then(authors => this.authorWithBooks = authors.data.list[0]);
        }
        if (!isNaN(($routeParams.rating))) {
            this.rating = $routeParams.rating;
        }
        authorsApi.getAll().then(authorsAndCountPages => {this.authors = authorsAndCountPages});
        this.pageChanged(this.currentPage);
    }
    selectWithAuthor(author) {
        this.authorWithBooks = author;
        this.byAuthorId = author.id;
        this.pageChanged(this.currentPage);
    }
    search(filterBy) {
        this.filter = filterBy;
        this.pageChanged(this.currentPage);
    }
    pageChanged(page) {
        this.currentPage = page;
        this.offset = (this.currentPage-1)*this.maxSize;
        this.checkAll = false;
        if (this.byAuthorId != null) {
            return this.booksApi.getByPageByAuthor(this.maxSize, this.offset, this.byAuthorId, this.filter).then(booksAndCountPages => this.booksAndCountPages = booksAndCountPages);
        }
        if (this.rating != null) {
            return this.booksApi.getByRating(this.maxSize, this.offset, this.rating, this.filter).then(booksAndCountPages => this.booksAndCountPages = booksAndCountPages);
        }
        else {
            this.booksApi.getBookByPage(this.maxSize, this.offset, this.filter).then(booksAndCountPages => this.booksAndCountPages = booksAndCountPages);
        }
    }
    check(bookId) {
        this.activeDeleteSelected = false;
        this.booksAndCountPages.list.forEach(book =>{
            if (book.id === bookId) {
                book.removeStatus = !book.removeStatus;
                if (book.removeStatus === false) {this.checkAll = false}
            }
            if (book.removeStatus === true) {this.activeDeleteSelected = true}
        });
    }
    checkAllBook() {
        this.checkAll = !this.checkAll;
        this.activeDeleteSelected = this.checkAll;
        this.booksAndCountPages.list.forEach(book => book.removeStatus = this.checkAll);
    }
    add(): void {
        this.$uibModal.open({
            backdrop: false,
            controller: AddBook,
            controllerAs: 'add',
            templateUrl: 'add-book.html',
        }).result.then(function () {
        });
    }
    edit(book): void {
        this.$uibModal.open({
            backdrop: false,
            controller: EditBook,
            controllerAs: 'edit',
            templateUrl: 'edit-book.html',
            resolve: {
                book: () => book
            }
        }).result.then(function () {
        });
    }
    addReview(book): void {
        this.$uibModal.open({
            backdrop: false,
            controller: AddReview,
            controllerAs: 'addReview',
            templateUrl: 'add-review.html',
            resolve: {
                book: () => book
            }
        }).result.then(function () {
        });
    }
    delete(book): void {
        this.$uibModal.open({
            backdrop: false,
            controller: DeleteBook,
            controllerAs: 'delete',
            templateUrl: 'delete-book.html',
            resolve: {
                book: () => book
            }
        }).result.then(function () {
        });
    }
    bulkDeleteBooks(booksRemove: IBook[], idEntities: number[]) {
        booksRemove = []
        idEntities = [];
        this.booksAndCountPages.list.forEach(book => {if (book.removeStatus) {booksRemove.push(book); idEntities.push(book.id)}});
        this.$uibModal.open({
            backdrop: false,
            controller: BulkDelete,
            controllerAs: 'bulkDelete',
            templateUrl: 'bulk-delete-book.html',
            resolve: {
                idEntities: () => idEntities,
                booksRemove: () => booksRemove,
            }
        }).result.then(function () {
        });
    }
}

const moduleName = 'myApp.books-index';
export default moduleName

angular.module(moduleName, [booksApiModule])
  .component('booksIndex', {
    templateUrl: 'app/components/books-index/books-index.html',
    controller: BooksIndex
  }).controller("BooksIndex", BooksIndex);



/*Add new author modal*/
class AddBook {
    book: IBook = new IBook();
    authors: IAuthor[] = [];
    selectAuthors: IAuthor[] = [];
    constructor(private $uibModalInstance: ng.ui.bootstrap.IModalServiceInstance,
                private booksApi: IBooksApi,
                private authorsApi: IAuthorsApi,
                private $uibModal: ng.ui.bootstrap.IModalService) {
        authorsApi.getAll().then(authorsAndCountPages => {this.authors = authorsAndCountPages.list; console.log(this.authors)});
    }
    addAuthorForBook(author: IAuthor) {
        this.selectAuthors.push(author);
        let index = this.authors.indexOf(author, 0);
        if (index > -1) {
            this.authors.splice(index, 1);
        }
    }
    ok(name, publisher, yearPublisher): void {
        this.book.name = name;
        this.book.publisher = publisher;
        this.book.yearPublished = yearPublisher;
        this.booksApi.createBook(this.book, this.selectAuthors);
        this.$uibModalInstance.close(true);
    }
    cancel(): void {
        this.$uibModalInstance.close();
    }
}

class AddReview {
    rate = 0;
    max = 5;
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
        console.log(commenterName + comment + rating);
       /* this.review.commenterName = commenterName;
        this.review.comment = comment;
        this.review.rating = rating;
        this.review.bookId = this.book.id;*/
        this.booksApi.createReview(commenterName, comment, rating, this.book);
        this.$uibModalInstance.close(true);
    }
    cancel(): void {
        this.$uibModalInstance.close();
    }
}


class EditBook {
    modalOptions = {
        closeButtonText: 'CLOSE',
        actionButtonText: 'OK',
        headerText: `Edit book: ${this.book.name} ${this.book.publisher}`,
    };
    selectAuthors: IAuthor[] = [];]
    constructor(private $uibModalInstance: ng.ui.bootstrap.IModalServiceInstance,
                private booksApi: IBooksApi,
                //private authorApi: IAuthorsApi,
                private $uibModal: ng.ui.bootstrap.IModalService,
                private book: IBook) {
        this.booksApi.getByBook(book.id).then(authors => {this.selectAuthors = authors; console.log(this.selectAuthors)});
    }

    ok(name, publisher): void {
        this.booksApi.update(this.book).catch(
            this.$uibModal.open({
                backdrop: false,
                controller: ErrorDialog,
                controllerAs: 'errorDialog',
                templateUrl: 'error.html'
            }));
        this.$uibModalInstance.close();
    }
    cancel(): void {
        this.$uibModalInstance.close();
    }
}

class DeleteBook {
    modalOptions = {
        closeButtonText: 'CLOSE',
        actionButtonText: 'OK',
        headerText: 'Warning!',
        bodyText: `Are you sure to delete book: ${this.book.name}?`
    };
    constructor(private $uibModalInstance: ng.ui.bootstrap.IModalServiceInstance,
                private booksApi: IBooksApi,
                private book: IBook) {
    }
    ok() {
        this.booksApi.delete(this.book.id);
        this.$uibModalInstance.close();
    }
    close() {
        this.$uibModalInstance.close();
    }
}

class BulkDelete {
    modalOptions = {
        closeButtonText: 'CLOSE',
        actionButtonText: 'OK',
        headerText: 'Warning!',
        bodyText: `Are you sure to delete selected books?`
    };
    constructor(private $uibModalInstance: ng.ui.bootstrap.IModalServiceInstance,
                private booksApi: IBooksApi,
                private booksRemove: IBook[],
                private idEntities: number[]) {
    }
    ok() {
        this.booksApi.bulkDelete(this.idEntities);
        this.$uibModalInstance.close();
    }
    close() {
        this.$uibModalInstance.close();
    }
}

class ErrorDialog {
    constructor(private $uibModalInstance: ng.ui.bootstrap.IModalServiceInstance) {
    }
    close(): void {
        this.$uibModalInstance.close();
    }
}