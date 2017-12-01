import * as angular from 'angular'
import {HttpApi, IApi, IEntitiesAndCountPages} from "../service-api";

export interface IAuthorsAndCountPages extends IEntitiesAndCountPages<IAuthor> {}
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

export interface IAuthorsApi  extends IApi<IAuthor> {}

class HttpAuthorsApi extends HttpApi<IAuthor> implements IAuthorsApi {
    API_URL = this.AUTHOR_URL;
    constructor($http: angular.IHttpService) {
        super($http);
    }
}

const moduleName = 'myApp.authorsApi'
export default moduleName

angular.module(moduleName, [])
    .service('authorsApi', HttpAuthorsApi)
