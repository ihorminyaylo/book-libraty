import * as angular from 'angular'

import authorsApiModule, {IAuthorsAndCountPages, IAuthorsApi} from '../../services/authors-api/authors-api'


interface IRouteParams extends angular.route.IRouteParamsService {
    isbn: string
}

class AuthorsIndex {

    authorsAndCountPages: IAuthorsAndCountPages;
    currentPage: number = 1;
    perPage: number = 3;
    authorsApi: IAuthorsApi;
    $routeParams: IRouteParams;
    nextPage: number;
    constructor (
        authorsApi: IAuthorsApi,
        $routeParams: IRouteParams
    ) {
        this.currentPage = parseInt($routeParams.isbn);
        this.authorsApi = authorsApi;
        if (parseInt($routeParams.isbn) <= 0 || $routeParams.isbn == null) {
            $routeParams.isbn = '1';
        }
        this.$routeParams = $routeParams;
        this.nextPage = parseInt(this.$routeParams.isbn) + 1;
        authorsApi.all($routeParams.isbn, this.perPage)
            .then( authorsAndCountPages => {this.authorsAndCountPages = authorsAndCountPages;
            //todo: add catch for exception from server
            if (parseInt($routeParams.isbn) <= parseInt(this.authorsAndCountPages.pageCount) && parseInt($routeParams.isbn) > 0) {
                this.$routeParams = $routeParams;
            }
            else {
                $routeParams.isbn = '1';
                this.$routeParams.isbn = $routeParams.isbn;
            }}).catch(error => {alert(error);});
    }
    openPage(page) {
        //this.currentPage = 2;
        //this.authorsApi.all(this.currentPage, 2).then(authorsAndCountPages => this.authorsAndCountPages = authorsAndCountPages);
    }
    openModal(): void {
        console.log('adsdas');
        /*this.dialog.open(HomeIndex, {
            width: '250px',
            data: { name: 'dasdas', animal: 'dasdasdasasd' }
        });*/
    }
}

const moduleName = 'myApp.authors-index';
export default moduleName

angular.module(moduleName, [authorsApiModule])
    .component('authorsIndex', {
        templateUrl: 'app/components/authors-index/authors-index.html',
        controller: AuthorsIndex
    }).controller("AuthorsIndex", AuthorsIndex)
