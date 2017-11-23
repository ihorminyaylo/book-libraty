import * as angular from 'angular'

export interface IBook {
  name: string;
  publisher: string;
  yearPublished: string;
  createDate: string;
}

export interface IBooksApi {
  all(): angular.IPromise<IBook[]>
  getByIsbn(isbn: string): angular.IPromise<IBook>
}

class HttpBooksApi implements IBooksApi {

  private baseUrl: string = 'http://localhost:9090/api/book'
/*http://localhost:9090/api/author*/
/*'http://bookmonkey-api.angularjs.de/books'*/
  constructor(
    private $http: angular.IHttpService
  ) {}

  public all() {
    return this.$http.get<IBook[]>(this.baseUrl + '/find')
      .then(booksResponse => booksResponse.data)
  }

  public getByIsbn(isbn: string) {
    return this.$http.get<IBook>(`${this.baseUrl}/${isbn}`)
      .then(bookResponse => bookResponse.data)
  }
}

const moduleName = 'myApp.booksApi'
export default moduleName

angular.module(moduleName, [])
  .service('booksApi', HttpBooksApi)
