import * as angular from 'angular'

import booksApiModule, {IBook, IBooksAndCountPages, IBooksApi} from '../../services/books-api/books-api'
import {IAuthor, IAuthorsApi} from "../../services/authors-api/authors-api";
import {IReview, IReviewsApi} from "../../services/reviews-api/reviews-api";
import {BookPattern, ListParams, SortParams} from "../../services/service-api";
import IRoute = angular.route.IRoute;
import IRouteProvider = angular.route.IRouteProvider;
import IRouteService = angular.route.IRouteService;

interface IRouteParams extends angular.route.IRouteParamsService {
    isbn: string;
    rating: number;
}

class BooksIndex {
    maxPages: number = 3;
    sortType     = 'name';
    sortParam: SortParams;
    sortReverse: string;
    sortParams(type) {
        this.sortType = type;
        if (this.sortReverse === 'ASC') {
            this.sortReverse = 'DESC';
        }
        else {
            this.sortReverse = 'ASC';
        }
        this.sortParam = new SortParams(this.sortType, this.sortReverse);
        this.currentPage = 1;
        this.pageChanged(this.currentPage);
    }
    checkAll: boolean;
    activeDeleteSelected: boolean = true;
    currentPage = 1;
    limit: number = 10;
    offset: number = 0;
    authorId: number = null;
    rating: number;
    authorWithBooks: IAuthor = null;
    booksAndCountPages: IBooksAndCountPages;
    search: string;
    router: IRouteService;
    authors: IAuthor[] = [];
    constructor (private booksApi: IBooksApi, private authorsApi: IAuthorsApi, private $uibModal: ng.ui.bootstrap.IModalService, private $routeParams: IRouteParams) {
        if (!isNaN(parseInt($routeParams.isbn))) {
            this.authorId = parseInt($routeParams.isbn);
            this.authorsApi.getById(this.authorId).then(author => {this.authorWithBooks = author.data});
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
    clearFilters(filter) {
        this.searchBy('');
        this.selectWithAuthor(null);
    }
    searchBy(filterBy) {
        if (filterBy === '') {
            this.search = null;
        }
        this.search = filterBy;
        this.pageChanged(this.currentPage);
    }
    pageChanged(page) {
        this.currentPage = page;
        this.offset = (this.currentPage-1)*this.limit;
        this.authorsApi.readAll().then(authors => this.authors = authors.data);
        this.booksApi.find(new ListParams(this.limit, this.offset, new BookPattern(this.authorId, this.search, this.rating), this.sortParam))
            .then(booksAndCountPages => {console.log(booksAndCountPages); this.booksAndCountPages = booksAndCountPages; this.booksAndCountPages.list.forEach(book => book.averageRatingRound = Math.round(book.averageRating))});
        this.checkAll = false;
    }
    check(bookId) {
        this.activeDeleteSelected = true;
        this.booksAndCountPages.list.forEach(book =>{
            if (book.id === bookId) {
                book.removeStatus = !book.removeStatus;
                if (book.removeStatus === false) {this.checkAll = false}
            }
            if (book.removeStatus === true) {this.activeDeleteSelected = false}
        });
    }
    checkAllBook() {
        this.checkAll = !this.checkAll;
        this.activeDeleteSelected = !this.checkAll;
        this.booksAndCountPages.list.forEach(book => book.removeStatus = this.checkAll);
    }
    dialog;

    add(): void {
        this.dialog = this.$uibModal.open({
            controller: AddBook,
            controllerAs: 'add',
            templateUrl: 'add-book.html',
            resolve: {
                selectAuthor: () => this.authorWithBooks,
                booksAndCountPages: () => this.booksAndCountPages,
                authorId: () => this.authorId,
                authors: () => this.authors,
                currentPage: () => this.currentPage,
            }});
        this.checkAll = false;
    }

    addReview(book): void {
        this.dialog = this.$uibModal.open({
            controller: AddReview,
            controllerAs: 'addReview',
            templateUrl: 'add-review.html',
            scope: '',
            resolve: {
                book: () => book,
                booksAndCountPages: () => this.booksAndCountPages,
                authorId: () => this.authorId,
                authors: () => this.authors,
                currentPage: () => this.currentPage,
            }
        });
        this.checkAll = false;
    }

    deleteBook(book): void {
        this.dialog = this.$uibModal.open({
            controller: DeleteBook,
            controllerAs: 'deleteBook',
            templateUrl: 'delete-book.html',
            resolve: {
                book: () => book,
                booksAndCountPages: () => this.booksAndCountPages,
                authorId: () => this.authorId,
                authors: () => this.authors,
                currentPage: () => this.currentPage,
            }});
        this.checkAll = false;
    }

    bulkDeleteBooks(booksRemove: IBook[], idEntities: number[]) {
        booksRemove = [];
        idEntities = [];
        this.booksAndCountPages.list.forEach(book => {if (book.removeStatus) {booksRemove.push(book); idEntities.push(book.id)}});
        this.dialog = this.$uibModal.open({
            controller: BulkDelete,
            controllerAs: 'bulkDelete',
            templateUrl: 'bulk-delete-book.html',
            scope: '',
            resolve: {
                idEntities: () => idEntities,
                booksRemove: () => booksRemove,
                booksAndCountPages: () => this.booksAndCountPages,
                authorId: () => this.authorId,
                authors: () => this.authors,
                currentPage: () => this.currentPage
            }
        });
        this.checkAll = false;
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
    selectAuthors: IAuthor[] = [];
    click: boolean = false;
    constructor(private $uibModalInstance: ng.ui.bootstrap.IModalServiceInstance,
                private booksApi: IBooksApi,
                private authorsApi: IAuthorsApi,
                private selectAuthor: IAuthor,
                private booksAndCountPages: IBooksAndCountPages,
                private authorId: number,
                private authors: IAuthor[],
                private currentPage: number) {
        authorsApi.readAll().then(authors => this.authors = authors.data);
        if (selectAuthor !== null) {
            this.addAuthorForBook(this.selectAuthor);
        }
    }
    offset = 0;
    limit = 10;
    checkAll;
    search: string;
    pageChanged() {
        this.offset = (this.currentPage-1)*this.limit;
        this.authorsApi.readAll().then(authors => this.authors = authors.data);
        this.booksApi.find(new ListParams(this.limit, this.offset, new BookPattern(this.authorId, this.search, null), null))
            .then(booksAndCountPages => {this.booksAndCountPages.list = booksAndCountPages.list; this.booksAndCountPages.totalItems = booksAndCountPages.totalItems;  this.booksAndCountPages.list.forEach(book => book.averageRatingRound = Math.round(book.averageRating)});
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
        console.log(yearPublisher);
        this.book.yearPublished = yearPublisher;
        console.log(this.book)
        this.booksApi.createBook(this.book, this.selectAuthors).then(response => this.pageChanged());
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
                private book: IBook,
                private authorsApi: IAuthorsApi,
                private booksAndCountPages: IBooksAndCountPages,
                private authorId: number,
                private authors: IAuthor[],
                private currentPage: number) {
    }
    offset = 0;
    limit = 10;
    checkAll;
    search: string;
    pageChanged() {
        this.offset = (this.currentPage-1)*this.limit;
        this.authorsApi.readAll().then(authors => this.authors = authors.data);
        this.booksApi.find(new ListParams(this.limit, this.offset, new BookPattern(this.authorId, this.search, null), null))
            .then(booksAndCountPages => {console.log(booksAndCountPages);this.booksAndCountPages.list = booksAndCountPages.list; this.booksAndCountPages.totalItems = booksAndCountPages.totalItems; this.booksAndCountPages.list.forEach(book => book.averageRatingRound = Math.round(book.averageRating)});
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
        this.booksApi.createReview(commenterName, comment, rating, this.book).then(response => this.pageChanged());;
        this.$uibModalInstance.close(true);
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
                private book: IBook,
                private authorsApi: IAuthorsApi,
                private booksAndCountPages: IBooksAndCountPages,
                private authorId: number,
                private authors: IAuthor[],
                private currentPage: number) {
    }


    offset = 0;
    limit = 10;
    checkAll;
    search: string;
    pageChanged() {
        this.offset = (this.currentPage-1)*this.limit;
        this.authorsApi.readAll().then(authors => this.authors = authors.data);
        this.booksApi.find(new ListParams(this.limit, this.offset, new BookPattern(this.authorId, this.search, null), null))
            .then(booksAndCountPages => {this.booksAndCountPages.list = booksAndCountPages.list; this.booksAndCountPages.totalItems = booksAndCountPages.totalItems; this.booksAndCountPages.list.forEach(book => book.averageRatingRound = Math.round(book.averageRating)});
    }

    ok() {
        this.booksApi.delete(this.book.id).then(response => this.pageChanged());
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
                private idEntities: number[],
                private authorsApi: IAuthorsApi,
                private booksAndCountPages: IBooksAndCountPages,
                private authorId: number,
                private authors: IAuthor[],
                private currentPage: number) {
    }

    offset = 0;
    limit = 10;
    checkAll;
    search: string;
    pageChanged() {
        this.offset = (this.currentPage-1)*this.limit;
        this.authorsApi.readAll().then(authors => this.authors = authors.data);
        this.booksApi.find(new ListParams(this.limit, this.offset, new BookPattern(this.authorId, this.search, null), null))
            .then(booksAndCountPages => {this.booksAndCountPages.list = booksAndCountPages.list; this.booksAndCountPages.totalItems = booksAndCountPages.totalItems; this.booksAndCountPages.list.forEach(book => book.averageRatingRound = Math.round(book.averageRating)});
    }


    ok() {
        this.booksApi.bulkDelete(this.idEntities).then(response => this.pageChanged());
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