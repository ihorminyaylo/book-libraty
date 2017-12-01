import {IAuthorsAndCountPages, IAuthorsApi} from "../../../services/authors-api/authors-api";

const moduleName = 'myApp.app';
export default moduleName

const app = angular.module(moduleName, ['ui.bootstrap']);
/**
 * Controller

 */
class Controller {
    currentPage = 1;
    authorsAndCountPages: IAuthorsAndCountPages;
    perPage: number = 5;
    authorsApi: IAuthorsApi;
    constructor (authorsApi: IAuthorsApi, private $uibModal: ng.ui.bootstrap.IModalService) {
        this.authorsApi = authorsApi;
        this.openPage(this.currentPage);
    }

    openPage(page) {
        this.currentPage = page;
        this.authorsApi.getByPage(this.currentPage, this.perPage).then(authorsAndCountPages => this.authorsAndCountPages = authorsAndCountPages);
    }
    openModal(): void {
        this.$uibModal.open({
            backdrop: false,
            controller: ModalController,
            controllerAs: 'modal',
            templateUrl: 'modal.html'
        });
    }
}

app.controller('Controller', Controller).component('app', {
    templateUrl: 'app/components/authors-index/modal/modal.html',
    controller: Controller
});

/**
 * Modal controller
 */
class ModalController {
    constructor(private $uibModalInstance: ng.ui.bootstrap.IModalServiceInstance, authorsApi: IAuthorsApi) { }

    ok(firstName, lastName): void {
        console.log(firstName + lastName);
        this.$uibModalInstance.close();
    }

    cancel(): void {
        this.$uibModalInstance.dismiss();
    }
}