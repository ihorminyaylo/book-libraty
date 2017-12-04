import * as angular from 'angular'

import booksApiModule, {IBook, IBooksAndCountPages, IBooksApi} from '../../services/books-api/books-api'

class BooksIndex {
    checkAll: boolean;
    activeDeleteSelected: boolean;
    totalItems: number;
    currentPage = 1;
    maxSize: number = 10;
    offset: number = 0;
    booksAndCountPages: IBooksAndCountPages;

    constructor (private booksApi: IBooksApi, private $uibModal: ng.ui.bootstrap.IModalService) {
        this.pageChanged(this.currentPage);
    }
    pageChanged(page) {
        this.currentPage = page;
        this.offset = (this.currentPage-1)*this.maxSize;
        this.booksApi.getByPage(this.maxSize, this.offset).then(booksAndCountPages => {this.booksAndCountPages = booksAndCountPages;
        this.totalItems = this.booksAndCountPages.totalItems});
        this.checkAll = false;
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
    delete(book): void {
        this.$uibModal.open({
            backdrop: false,
            controller: DeleteBook,
            controllerAs: 'delete',
            templateUrl: 'bulkDelete-book.html',
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
            templateUrl: 'bulk-bulkDelete-book.html',
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
    constructor(private $uibModalInstance: ng.ui.bootstrap.IModalServiceInstance,
                private booksApi: IBooksApi,
                private $uibModal: ng.ui.bootstrap.IModalService) {}
    ok(name): void {
        this.book.name = name;
        this.booksApi.create(this.book).catch(
            this.$uibModal.open({
                animation: true,
                backdrop: false,
                controller: ErrorDialog,
                templateUrl: 'error.html'
            }));
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
    constructor(private $uibModalInstance: ng.ui.bootstrap.IModalServiceInstance,
                private booksApi: IBooksApi,
                private $uibModal: ng.ui.bootstrap.IModalService,
                private book: IBook) {}

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
        this.booksApi.delete([this.book.id]);
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
        this.booksApi.delete(this.idEntities);
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