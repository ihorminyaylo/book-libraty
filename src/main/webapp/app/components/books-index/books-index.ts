import * as angular from 'angular'

import booksApiModule, {IBook, IBooksAndCountPages, IBooksApi} from '../../services/books-api/books-api'
import {IAuthor, IAuthorsApi} from "../../services/authors-api/authors-api";
import {IDatepickerPopupConfig, IDatepickerPopupConfig} from "angular-ui-bootstrap";
import {IReview, IReviewsApi} from "../../services/reviews-api/reviews-api";
import {BookPattern, ListParams} from "../../services/service-api";

interface IRouteParams extends angular.route.IRouteParamsService {
    isbn: string
    rating: number;
}

class BooksIndex {
    checkAll: boolean;
    activeDeleteSelected: boolean;
    currentPage = 1;
    limit: number = 10;
    offset: number = 0;
    authorId: number = null;
    rating: number;
    authorWithBooks: IAuthor = null;
    booksAndCountPages: IBooksAndCountPages;
    search: string;
    authors: IAuthor[] = [];
    constructor (private booksApi: IBooksApi, private authorsApi: IAuthorsApi, private $uibModal: ng.ui.bootstrap.IModalService, $routeParams: IRouteParams) {
        if (!isNaN(parseInt($routeParams.isbn))) {
            this.authorId = parseInt($routeParams.isbn);
        }
        if (!isNaN(($routeParams.rating))) {
            this.rating = $routeParams.rating;
        }
        this.pageChanged(this.currentPage);
    }
    selectWithAuthor(author) {
        this.authorWithBooks = author;
        if (author == null) {
            this.authorId = null;
        }
        else {
            this.authorId = author.id;
        }
        this.pageChanged(this.currentPage);
    }
    searchBy(filterBy) {
        console.log(filterBy);
        if (filterBy === '') {
            this.search = null;
        }
        this.search = filterBy;
        this.pageChanged(this.currentPage);
    }
    pageChanged(page) {
        this.currentPage = page;
        this.offset = (this.currentPage-1)*this.limit;
        this.checkAll = false;
        this.authorsApi.readAll().then(authors => this.authors = authors.data);
        return this.booksApi.find(new ListParams(this.limit, this.offset, new BookPattern(this.authorId, this.search, this.rating), null))
            .then(booksAndCountPages => {this.booksAndCountPages = booksAndCountPages; console.log(this.booksAndCountPages)});
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
    click: boolean = false;
    constructor(private $uibModalInstance: ng.ui.bootstrap.IModalServiceInstance,
                private booksApi: IBooksApi,
                private authorsApi: IAuthorsApi,
                private $uibModal: ng.ui.bootstrap.IModalService) {
        authorsApi.readAll().then(authors => this.authors = authors.data);
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

    //todo: validation for input value
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
    close() {
        this.$uibModalInstance.close();
    }
}