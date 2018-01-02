import * as angular from 'angular'

import authorsApiModule, {IAuthor, IAuthorsAndCountPages, IAuthorsApi} from '../../services/authors-api/authors-api'
import {AuthorPattern, ListParams, SortParams} from "../../services/service-api";

class AuthorsIndex {
    sortType     = 'name'; // set the default sort type
    sortReverse: string;
    sortParam: SortParams;
    maxPages: number = 3;
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
    authorsAndCountPages: IAuthorsAndCountPages;
    constructor (private authorsApi: IAuthorsApi, private $uibModal: ng.ui.bootstrap.IModalService) {
        this.pageChanged(this.currentPage);
    }
    pageChanged(page) {
        this.currentPage = page;
        this.offset = (this.currentPage-1)*this.limit;
        /*this.authorsApi.getByPage(this.limit, this.offset).then(authorsAndCountPages => {this.authorsAndCountPages = authorsAndCountPages;
        this.totalItems = this.authorsAndCountPages.totalItems});*/

        this.authorsApi.find(new ListParams(this.limit, this.offset, null, this.sortParam))
            .then(authorsAndCountPages => {this.authorsAndCountPages = authorsAndCountPages; this.authorsAndCountPages.list.forEach(author => author.averageRatingRound = Math.round(author.averageRating))});
        this.checkAll = false;
    }
    check(authorId) {
        this.activeDeleteSelected = true;
        this.authorsAndCountPages.list.forEach(author =>{
            if (author.id === authorId) {
                author.removeStatus = !author.removeStatus;
                if (author.removeStatus === false) {this.checkAll = false}
            }
            if (author.removeStatus === true) {this.activeDeleteSelected = false}
        });
    }
    checkAllAuthor() {
        this.checkAll = !this.checkAll;
        this.activeDeleteSelected = !this.checkAll;
        this.authorsAndCountPages.list.forEach(author => author.removeStatus = this.checkAll);
    }
    add(): void {
        this.$uibModal.open({
            backdrop: false,
            controller: AddAuthor,
            controllerAs: 'addAuthor',
            templateUrl: 'add-author.html',
            resolve: {
                authorsApi: () => this.authorsApi,
                authorsAndCountPages: () => this.authorsAndCountPages,
                currentPage: () => this.currentPage
            }
        });
        this.checkAll = false;
    };
    edit(author): void {
        this.$uibModal.open({
            backdrop: false,
            controller: EditAuthor,
            controllerAs: 'editAuthor',
            templateUrl: 'edit-author.html',
            resolve: {
                author: () => author,
                authorsApi: () => this.authorsApi,
                authorsAndCountPages: () => this.authorsAndCountPages,
                currentPage: () => this.currentPage
            },
        });
        this.checkAll = false;
    }
    delete(author): void {
        this.$uibModal.open({
            backdrop: false,
            controller: DeleteAuthor,
            controllerAs: 'delete',
            templateUrl: 'delete-author.html',
            resolve: {
                author: () => author,
                authorsAndCountPages: () => this.authorsAndCountPages,
                currentPage: () => this.currentPage
            }
        });
        this.checkAll = false;
    }
    dialog;
    bulkDeleteAuthors(entitiesRemove: IAuthor[], idEntities: number[]) {
        entitiesRemove = [];
        idEntities = [];
        this.authorsAndCountPages.list.forEach(author => {if (author.removeStatus) {entitiesRemove.push(author); idEntities.push(author.id)}});
        this.dialog = this.$uibModal.open({
            backdrop: false,
            controller: BulkDelete,
            controllerAs: 'bulkDelete',
            templateUrl: 'bulk-delete-author.html',
            resolve: {
                authorsRemove: () => entitiesRemove,
                idEntities: () => idEntities,
                authorsAndCountPages: () => this.authorsAndCountPages,
                currentPage: () => this.currentPage
            }
        });
        this.checkAll = false;
    }
}

const moduleName = 'myApp.authors-index';
export default moduleName

angular.module(moduleName, [authorsApiModule])
    .component('authorsIndex', {
        templateUrl: 'app/components/authors-index/authors-index.html',
        controller: AuthorsIndex
    }).controller("AuthorsIndex",AuthorsIndex);


/*Add new author modal*/
class AddAuthor {
    click: boolean = false;
    author: IAuthor = new IAuthor();

    constructor(private $uibModalInstance: ng.ui.bootstrap.IModalServiceInstance,
                private authorsApi: IAuthorsApi,
                private $uibModal: ng.ui.bootstrap.IModalService,
                private $scope: ng.IScope,
                private authorsAndCountPages: IAuthorsAndCountPages,
                private currentPage: number) {}

    offset = 0;
    limit = 10;
    search: string;
    pageChanged() {
        this.offset = (this.currentPage-1)*this.limit;
        this.authorsApi.find(new ListParams(this.limit, this.offset, null, null))
            .then(authorsAndCountPages => {this.authorsAndCountPages.list = authorsAndCountPages.list; this.authorsAndCountPages.totalItems = authorsAndCountPages.totalItems; this.authorsAndCountPages.list.forEach(author => author.averageRatingRound = Math.round(author.averageRating))});
    }

    ok(firstName, secondName): void {
        this.author.firstName = firstName;
        this.author.secondName = secondName;
        this.offset = (8-1)*10;
        this.authorsApi.create(this.author).then(response => this.pageChanged());
        this.$uibModalInstance.close(this.authorsAndCountPages);
    }
    cancel(): void {
        this.$uibModalInstance.close();
    }
}

class EditAuthor {
    modalOptions = {
        closeButtonText: 'CLOSE',
        actionButtonText: 'OK',
        headerText: `Edit author: ${this.author.firstName} ${this.author.secondName}`,
    };
    firstName: string;
    secondName: string;
    constructor(private $uibModalInstance: ng.ui.bootstrap.IModalServiceInstance,
                private authorsApi: IAuthorsApi,
                private $uibModal: ng.ui.bootstrap.IModalService,
                private author: IAuthor,
                private authorsAndCountPages: IAuthorsAndCountPages,
                private currentPage: number) {
        this.firstName = author.firstName;
        this.secondName = author.secondName;
    }
    offset = 0;
    limit = 10;
    search: string;
    pageChanged() {
        this.offset = (this.currentPage-1)*this.limit;
        this.authorsApi.find(new ListParams(this.limit, this.offset, null, null))
            .then(authorsAndCountPages => {this.authorsAndCountPages.list = authorsAndCountPages.list; this.authorsAndCountPages.totalItems = authorsAndCountPages.totalItems; this.authorsAndCountPages.list.forEach(author => author.averageRatingRound = Math.round(author.averageRating))});
    }

    ok(firstName, secondName): void {
        this.author.firstName = firstName;
        this.author.secondName = secondName;
        this.authorsApi.update(this.author).then(response => this.pageChanged());
        this.$uibModalInstance.close(this.author);
    }
    cancel(): void {
        this.$uibModalInstance.close();
    }
}

class DeleteAuthor {
    modalOptions = {
        closeButtonText: 'CLOSE',
        actionButtonText: 'OK',
        headerText: 'Warning!',
        bodyText: `Are you sure to delete author: ${this.author.firstName} ${this.author.secondName}?`
    };
    authors: IAuthor[] = [];
    constructor(private $uibModalInstance: ng.ui.bootstrap.IModalServiceInstance,
                private authorsApi: IAuthorsApi,
                private author: IAuthor,
                private $uibModal: ng.ui.bootstrap.IModalService,
                private authorsAndCountPages: IAuthorsAndCountPages,
                private currentPage: number) {
    }
    offset = 0;
    limit = 10;
    search: string;
    pageChanged() {
        this.offset = (this.currentPage-1)*this.limit;
        this.authorsApi.find(new ListParams(this.limit, this.offset, null, null))
            .then(authorsAndCountPages => {this.authorsAndCountPages.list = authorsAndCountPages.list; this.authorsAndCountPages.totalItems = authorsAndCountPages.totalItems; this.authorsAndCountPages.list.forEach(author => author.averageRatingRound = Math.round(author.averageRating))});
    }
    ok() {
        this.authorsApi.delete(this.author.id).then(response => {if (response.data !== '') {this.authors.push(response.data); this.$uibModal.open({
            backdrop: false,
            controller: ErrorDialog,
            controllerAs: 'errorDialog',
            templateUrl: 'error.html',
            resolve: {
                authorsNotRemove: ()=> this.authors
            }
        })}
        this.pageChanged()});
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
        bodyText: `Are you sure to delete selected authors?`
    };
    authors: IAuthor[];
    constructor(private $uibModalInstance: ng.ui.bootstrap.IModalServiceInstance,
                private authorsApi: IAuthorsApi,
                private authorsRemove: IAuthor[],
                private idEntities: number[],
                private $uibModal: ng.ui.bootstrap.IModalService,
                private authorsAndCountPages: IAuthorsAndCountPages,
                private currentPage: number) {
    }
    offset = 0;
    limit = 10;
    search: string;
    pageChanged() {
        this.offset = (this.currentPage-1)*this.limit;
        this.authorsApi.find(new ListParams(this.limit, this.offset, null, null))
            .then(authorsAndCountPages => {this.authorsAndCountPages.list = authorsAndCountPages.list; this.authorsAndCountPages.totalItems = authorsAndCountPages.totalItems; this.authorsAndCountPages.list.forEach(author => author.averageRatingRound = Math.round(author.averageRating))});
    }
    ok() {
        this.authorsApi.bulkDelete(this.idEntities).then(response => {if (response.data.length !== 0) {this.authors = response.data; this.$uibModal.open({
            backdrop: false,
            controller: ErrorDialog,
            controllerAs: 'errorDialog',
            templateUrl: 'error.html',
            resolve: {
                authorsNotRemove: ()=> this.authors
            }
        })};
        this.pageChanged();});
        this.$uibModalInstance.close();
    }
    close() {
        this.$uibModalInstance.close();
    }
}

class ErrorDialog {
    constructor(private $uibModalInstance: ng.ui.bootstrap.IModalServiceInstance,
                private authorsNotRemove: IAuthor[]) {
    }
    close(): void {
        this.$uibModalInstance.close();
    }
}