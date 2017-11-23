import * as angular from 'angular'

export interface IAuthorsAndCountPages {
    authorDtoList: IAuthor[];
    pageCount: string;
}
export interface IAuthor {
    id: string;
    firstName: string;
    secondName: string;
    createDate: string;
    pageCount: string;
}

export interface IAuthorsApi {
    all(page, perPage): angular.IPromise<IAuthorsAndCountPages>
    getByIsbn(isbn: string): angular.IPromise<IAuthorsAndCountPages>
}

class HttpAuthorsApi implements IAuthorsApi {

    private baseUrl: string = 'http://localhost:9090/api/author'

    constructor(
        private $http: angular.IHttpService
    ) {}

    public all(page, perPage) {
        return this.$http.get<IAuthorsAndCountPages>(this.baseUrl + `/find?page=${page}&pageSize=${perPage}`)
            .then(authorsResponse => authorsResponse.data)
    }

    public getByIsbn(isbn: string) {
        return this.$http.get<IAuthorsAndCountPages>(`${this.baseUrl}/${isbn}`)
            .then(authorResponse => authorResponse.data)
    }
}

const moduleName = 'myApp.authorsApi'
export default moduleName

angular.module(moduleName, [])
    .service('authorsApi', HttpAuthorsApi)
