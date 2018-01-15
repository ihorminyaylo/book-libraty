import * as angular from 'angular'

import authorsApiModule, {IAuthor, IAuthorsAndCountPages, IAuthorsApi} from '../../services/authors-api/authors-api'
import {ListParams, SortParams} from "../../services/service-api";

class AuthorsIndex {
    private sortParam: SortParams = new SortParams('createDate', 'ASC');
    private maxPages: number = 3;
    private checkAll: boolean = false;
    private disableBulkDelete: boolean = true;
    private currentPage: number = 1;
    private limit: number = 10;
    private offset: number = 0;
    private listParams: ListParams<IAuthor> = new ListParams(this.limit, this.offset, null, this.sortParam);
    private authorsAndCountPages: IAuthorsAndCountPages;

    constructor(private authorsApi: IAuthorsApi, private $uibModal: ng.ui.bootstrap.IModalService) {
        this.pageChanged(this.currentPage);
    }

    private sortBy(type: string): void {
        this.sortParam.parameter = type;
        if (this.sortParam.type === 'ASC') {
            this.sortParam.type = 'DESC';
        }
        else {
            this.sortParam.type = 'ASC';
        }
        this.currentPage = 1;
        this.pageChanged(this.currentPage);
    }

    private pageChanged(pageNumber: number): void {
        this.currentPage = pageNumber;
        this.listParams.offset = (this.currentPage - 1) * this.limit;
        this.authorsApi.find(this.listParams)
            .then(authorsAndCountPages => {
                this.authorsAndCountPages = authorsAndCountPages;
                this.authorsAndCountPages.list.forEach(author => author.averageRatingRound = Math.round(author.averageRating))
            });
        this.checkAll = false;
    }

    private checkAuthor(authorId: number): void {
        this.disableBulkDelete = true;
        this.authorsAndCountPages.list.forEach(author => {
            if (author.id === authorId) {
                author.removeStatus = !author.removeStatus;
                if (author.removeStatus === false) {
                    this.checkAll = false
                }
            }
            if (author.removeStatus === true) {
                this.disableBulkDelete = false
            }
        });
    }

    private checkAllAuthor(): void {
        this.checkAll = !this.checkAll;
        this.disableBulkDelete = !this.checkAll;
        this.authorsAndCountPages.list.forEach(author => author.removeStatus = this.checkAll);
    }

    private addAuthor(): void {
        this.$uibModal.open({
            backdrop: false,
            controller: AddAuthor,
            controllerAs: 'addAuthor',
            templateUrl: 'add-author.html',
            resolve: {
                authorsAndCountPages: () => this.authorsAndCountPages,
                listParams: () => this.listParams,
            }
        });
        this.checkAll = false;
        this.disableBulkDelete = true;
        this.authorsAndCountPages.list.forEach(author => author.removeStatus = false);
    };

    private editAuthor(author: IAuthor): void {
        this.$uibModal.open({
            backdrop: false,
            controller: EditAuthor,
            controllerAs: 'editAuthor',
            templateUrl: 'edit-author.html',
            resolve: {
                author: () => author,
                authorsAndCountPages: () => this.authorsAndCountPages,
                listParams: () => this.listParams,
            },
        });
        this.checkAll = false;
        this.disableBulkDelete = true;
        this.authorsAndCountPages.list.forEach(author => author.removeStatus = false);
    }

    private deleteAuthor(author: IAuthor): void {
        this.$uibModal.open({
            backdrop: false,
            controller: DeleteAuthor,
            controllerAs: 'delete',
            templateUrl: 'delete-author.html',
            resolve: {
                author: () => author,
                authorsAndCountPages: () => this.authorsAndCountPages,
                listParams: () => this.listParams,
            }
        });
        this.checkAll = false;
        this.disableBulkDelete = true;
        this.authorsAndCountPages.list.forEach(author => author.removeStatus = false);
    }

    private bulkDeleteAuthors(entitiesRemove: IAuthor[], idEntities: number[]) {
        entitiesRemove = [];
        idEntities = [];
        this.authorsAndCountPages.list.forEach(author => {
            if (author.removeStatus) {
                entitiesRemove.push(author);
                idEntities.push(author.id)
            }
        });
        this.$uibModal.open({
            backdrop: false,
            controller: BulkDelete,
            controllerAs: 'bulkDelete',
            templateUrl: 'bulk-delete-author.html',
            resolve: {
                authorsRemove: () => entitiesRemove,
                idEntities: () => idEntities,
                authorsAndCountPages: () => this.authorsAndCountPages,
                listParams: () => this.listParams,
            }
        });
        this.checkAll = false;
        this.disableBulkDelete = true;
        this.authorsAndCountPages.list.forEach(author => author.removeStatus = false);
    }
}

const moduleName = 'myApp.authors-index';
export default moduleName

angular.module(moduleName, [authorsApiModule])
    .component('authorsIndex', {
        templateUrl: 'app/components/authors-index/authors-index.html',
        controller: AuthorsIndex
    }).controller("AuthorsIndex", AuthorsIndex);


/*Add new author modal*/
class AddAuthor {
    private clickSave: boolean = false;
    private author: IAuthor = new IAuthor();
    private modalOptions = {
        closeButtonText: 'CANCEL',
        actionButtonText: 'SAVE',
        headerText: `Add new author`,
    };

    constructor(private $uibModalInstance: ng.ui.bootstrap.IModalServiceInstance,
                private authorsApi: IAuthorsApi,
                private authorsAndCountPages: IAuthorsAndCountPages,
                private listParams: ListParams<IAuthor>) {
    }

    private reloadDates(): void {
        this.authorsApi.find(this.listParams)
            .then(authorsAndCountPages => {
                this.authorsAndCountPages.list = authorsAndCountPages.list;
                this.authorsAndCountPages.totalItems = authorsAndCountPages.totalItems;
                this.authorsAndCountPages.list.forEach(author => author.averageRatingRound = Math.round(author.averageRating))
            });
    }

    private save(firstName, secondName): void {
        this.author.firstName = firstName;
        this.author.secondName = secondName;
        this.authorsApi.create(this.author).then(response => this.reloadDates());
        this.$uibModalInstance.close(this.authorsAndCountPages);
    }

    private cancel(): void {
        this.$uibModalInstance.close();
    }
}

class EditAuthor {
    private firstName: string;
    private secondName: string;
    private modalOptions = {
        closeButtonText: 'CANCEL',
        actionButtonText: 'SAVE',
        headerText: `Edit author: ${this.author.firstName} ${this.author.secondName}`,
    };

    constructor(private $uibModalInstance: ng.ui.bootstrap.IModalServiceInstance,
                private authorsApi: IAuthorsApi,
                private author: IAuthor,
                private authorsAndCountPages: IAuthorsAndCountPages,
                private listParams: ListParams<IAuthor>) {
        this.firstName = author.firstName;
        this.secondName = author.secondName;
    }

    private reloadDates(): void {
        this.authorsApi.find(this.listParams)
            .then(authorsAndCountPages => {
                this.authorsAndCountPages.list = authorsAndCountPages.list;
                this.authorsAndCountPages.totalItems = authorsAndCountPages.totalItems;
                this.authorsAndCountPages.list.forEach(author => author.averageRatingRound = Math.round(author.averageRating))
            });
    }

    private save(firstName, secondName): void {
        this.author.firstName = firstName;
        this.author.secondName = secondName;
        this.author.createDate = null;
        this.authorsApi.update(this.author).then(response => this.reloadDates());
        this.$uibModalInstance.close();
    }

    private cancel(): void {
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
    notRemove: IAuthor[] = [];

    constructor(private $uibModalInstance: ng.ui.bootstrap.IModalServiceInstance,
                private authorsApi: IAuthorsApi,
                private author: IAuthor,
                private $uibModal: ng.ui.bootstrap.IModalService,
                private authorsAndCountPages: IAuthorsAndCountPages,
                private listParams: ListParams<IAuthor>) {
        this.notRemove.push(author);
    }

    private reloadDates(): void {
        this.authorsApi.find(this.listParams)
            .then(authorsAndCountPages => {
                this.authorsAndCountPages.list = authorsAndCountPages.list;
                this.authorsAndCountPages.totalItems = authorsAndCountPages.totalItems;
                this.authorsAndCountPages.list.forEach(author => author.averageRatingRound = Math.round(author.averageRating))
            });
    }

    ok() {
        this.authorsApi.delete(this.author.id).then(response => {
            console.log(response.data);
            if (response.data !== '') {
                if (this.author.id === response.data) {
                    let index = this.notRemove.indexOf(this.author, 0);
                    if (index > -1) {
                        this.notRemove.splice(index, 1);
                    }
                }
            }
            if (this.notRemove.length > 0) {
                this.$uibModal.open({
                    backdrop: false,
                    controller: ErrorDialog,
                    controllerAs: 'errorDialog',
                    templateUrl: 'error.html',
                    resolve: {
                        authorsNotRemove: () => this.notRemove
                    }
                });
            }
            this.reloadDates();
        });
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
    notRemoves: IAuthor[] = [];
    removes: IAuthor[] = [];

    constructor(private $uibModalInstance: ng.ui.bootstrap.IModalServiceInstance,
                private authorsApi: IAuthorsApi,
                private authorsRemove: IAuthor[],
                private idEntities: number[],
                private $uibModal: ng.ui.bootstrap.IModalService,
                private authorsAndCountPages: IAuthorsAndCountPages,
                private listParams: ListParams<IAuthor>) {
        //this.notRemoves = authorsRemove;
    }

    private reloadDates(): void {
        this.authorsApi.find(this.listParams)
            .then(authorsAndCountPages => {
                this.authorsAndCountPages.list = authorsAndCountPages.list;
                this.authorsAndCountPages.totalItems = authorsAndCountPages.totalItems;
                this.authorsAndCountPages.list.forEach(author => author.averageRatingRound = Math.round(author.averageRating))
            });
    }

    ok() {
        this.authorsApi.bulkDelete(this.idEntities).then(response => {
            if (response.data.length !== 0) {
                this.authorsRemove.forEach(author => {
                    response.data.forEach(id => {
                        if (author.id === id) {
                            this.removes.push(author);
                        }
                    })
                });
                this.authorsRemove.forEach(author => {
                    if (this.removes.indexOf(author) < 0) {
                        this.notRemoves.push(author)
                    }
                })
            }
            if (this.notRemoves.length > 0) {
                this.$uibModal.open({
                    backdrop: false,
                    controller: ErrorDialog,
                    controllerAs: 'errorDialog',
                    templateUrl: 'error.html',
                    resolve: {
                        authorsNotRemove: () => this.notRemoves
                    }
                });
            }
            this.reloadDates();
        });
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