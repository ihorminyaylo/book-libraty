import * as angular from 'angular'
import {AuthorPattern, HttpApi, IApi, IEntitiesAndCountPages, ListParams} from "../service-api";

export interface IAuthorsAndCountPages extends IEntitiesAndCountPages<IAuthor> {
}
export class IAuthor {
    id: number;
    firstName: string;
    secondName: string;
    createDate: string;
    averageRating: number;
    books: string[];
    removeStatus: boolean;
    constructor () {}
}

export interface IAuthorsApi  extends IApi<IAuthor> {
    readAll();
    find(listParams: ListParams<AuthorPattern>);
    getByBook(idBook: number);
}

class HttpAuthorsApi extends HttpApi<IAuthor> implements IAuthorsApi {
    API_URL = this.AUTHOR_URL;
    constructor($http: angular.IHttpService) {
        super($http);
    }

    public readAll() {
        return this.$http.get(this.BASE_URL + this.API_URL + '/findAll');
    }

    getByBook(idBook: number) {
        this.$http.get(this.BASE_URL + this.API_URL + `/byBook?idBook=${idBook}`)
    }
    public find(listParams: ListParams<AuthorPattern>) {
        return this.$http.post(this.BASE_URL + this.API_URL + '/find', {limit: listParams.limit, offset: listParams.offset,
            pattern: {}})
            .then(entitiesResponse => entitiesResponse.data);
    }
}

const moduleName = 'myApp.authorsApi'
export default moduleName

angular.module(moduleName, [])
    .service('authorsApi', HttpAuthorsApi)
