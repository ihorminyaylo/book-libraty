import * as angular from 'angular'

import authorsApiModule, {IAuthor, IAuthorsAndCountPages, IAuthorsApi} from '../../services/authors-api/authors-api'
import {ListParams} from "../../services/service-api";

class AuthorsIndex {
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
        console.log(this.limit + 'offset' + this.offset);

        this.authorsApi.find(new ListParams(this.limit, this.offset, null, null))
            .then(authorsAndCountPages => {this.authorsAndCountPages = authorsAndCountPages; console.log(this.authorsAndCountPages)});
        this.checkAll = false;
    }
    check(authorId) {
        this.activeDeleteSelected = false;
        this.authorsAndCountPages.list.forEach(author =>{
            if (author.id === authorId) {
                author.removeStatus = !author.removeStatus;
                if (author.removeStatus === false) {this.checkAll = false}
            }
            if (author.removeStatus === true) {this.activeDeleteSelected = true}
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
            controllerAs: 'add',
            templateUrl: 'add-author.html',
        }).result.then(function () {
        });
    }
    edit(author): void {
        this.$uibModal.open({
            backdrop: false,
            controller: EditAuthor,
            controllerAs: 'edit',
            templateUrl: 'edit-author.html',
            resolve: {
                author: () => author
            }
        }).result.then(function () {
        });
    }
    delete(author): void {
        this.$uibModal.open({
            backdrop: false,
            controller: DeleteAuthor,
            controllerAs: 'delete',
            templateUrl: 'delete-author.html',
            resolve: {
                author: () => author
            }
        }).result.then(function () {
        });
    }
    bulkDeleteAuthors(entitiesRemove: IAuthor[], idEntities: number[]) {
        entitiesRemove = [];
        idEntities = [];
        this.authorsAndCountPages.list.forEach(author => {if (author.removeStatus) {entitiesRemove.push(author); idEntities.push(author.id)}});
        this.$uibModal.open({
            backdrop: false,
            controller: BulkDelete,
            controllerAs: 'bulkDelete',
            templateUrl: 'bulk-delete-author.html',
            resolve: {
                authorsRemove: () => entitiesRemove,
                idEntities: () => idEntities
            }
        }).result.then(function () {
        });
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
    author: IAuthor = new IAuthor();
    constructor(private $uibModalInstance: ng.ui.bootstrap.IModalServiceInstance,
                private authorsApi: IAuthorsApi,
                private $uibModal: ng.ui.bootstrap.IModalService) {}
    ok(firstName, secondName): void {
        this.author.firstName = firstName;
        this.author.secondName = secondName;
        this.authorsApi.create(this.author).catch(
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

class EditAuthor {
    modalOptions = {
        closeButtonText: 'CLOSE',
        actionButtonText: 'OK',
        headerText: `Edit author: ${this.author.firstName} ${this.author.secondName}`,
    };
    constructor(private $uibModalInstance: ng.ui.bootstrap.IModalServiceInstance,
                private authorsApi: IAuthorsApi,
                private $uibModal: ng.ui.bootstrap.IModalService,
                private author: IAuthor) {}

    //todo: validation for input value
    ok(firstName, secondName): void {
        this.author.firstName = firstName;
        this.author.secondName = secondName;
        this.authorsApi.update(this.author).catch(
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

class DeleteAuthor {
    modalOptions = {
        closeButtonText: 'CLOSE',
        actionButtonText: 'OK',
        headerText: 'Warning!',
        bodyText: `Are you sure to delete author: ${this.author.firstName} ${this.author.secondName}?`
    };
    constructor(private $uibModalInstance: ng.ui.bootstrap.IModalServiceInstance,
                private authorsApi: IAuthorsApi,
                private author: IAuthor) {
    }
    ok() {
        this.authorsApi.bulkDelete([this.author.id]);
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
    constructor(private $uibModalInstance: ng.ui.bootstrap.IModalServiceInstance,
                private authorsApi: IAuthorsApi,
                private authorsRemove: IAuthor[],
                private idEntities: number[]) {
    }
    ok() {
        this.authorsApi.bulkDelete(this.idEntities);
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