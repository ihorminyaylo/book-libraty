import * as angular from 'angular'
import {HttpApi, IApi, IEntitiesAndCountPages} from "../service-api";

export interface IBooksAndCountPages extends IEntitiesAndCountPages<IBook>{}

export class IBook {
  id: number;
  name: string;
  publisher: string;
  yearPublished: string;
  createDate: string;
  removeStatus: boolean = false;
  constructor() {}
}

export interface IBooksApi extends IApi<IBook> {}

class HttpBooksApi extends HttpApi<IBook> implements IBooksApi {
    API_URL = this.BOOK_URL;
    constructor($http: angular.IHttpService) {
        super($http);
    }
}

const moduleName = 'myApp.booksApi'
export default moduleName

angular.module(moduleName, [])
  .service('booksApi', HttpBooksApi)
