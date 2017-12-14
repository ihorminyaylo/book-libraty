import * as angular from 'angular'
import {BookPattern, HttpApi, IApi, IEntitiesAndCountPages, ListParams} from "../service-api";
import {IAuthor} from "../authors-api/authors-api";
import {IReview} from "../reviews-api/reviews-api";

export interface IBooksAndCountPages extends IEntitiesAndCountPages<IBook>{}

export class IBook {
  id: number;
  name: string;
  publisher: string;
  yearPublished: string;
  createDate: string;
  removeStatus: boolean = false;
  authors: IAuthor[];
  constructor() {}
}

export interface IBooksApi extends IApi<IBook> {
    createBook(book: IBook, authors: IAuthor[]);

    find(listParams: ListParams<BookPattern>);
    findTop(count: number);
    getAverageRating();
    getBookByPage(limit, offset, filterBy);
    getByBook(idBook: number);
    getByPageByAuthor(limit, offset, author, filterBy);
    getByRating(limit, offset, rating, filterBy);
}

class HttpBooksApi extends HttpApi<IBook> implements IBooksApi {
    patternString: string = '';
    API_URL = this.BOOK_URL;
    constructor($http: angular.IHttpService) {
        super($http);
    }
    public createBook(book: IBook, authors: IAuthor[]) {
        return this.$http.post(this.BASE_URL + this.API_URL, {book, authors});
    }

    public find(listParams: ListParams<BookPattern>) {
        return this.$http.post(this.BASE_URL + this.API_URL + '/find', {limit: listParams.limit, offset: listParams.offset,
            pattern: {authorId: listParams.pattern.authorId,rating: listParams.pattern.rating, search: listParams.pattern.search}, sortParams: listParams.sortParams})
            .then(entitiesResponse => entitiesResponse.data);
    }

    public findTop(count: number) {
        return this.$http.get(this.BASE_URL + this.API_URL + `/findTop?count=${count}`).then(entitiesResponse => entitiesResponse.data);
    }

    public getAverageRating() {
        return this.$http.get(this.BASE_URL + this.API_URL + '/average_rating').then(entitiesResponse => entitiesResponse.data);
    }

    getByBook(idBook: number) {
        return this.$http.get(this.BASE_URL + this.AUTHOR_URL + `/byBook?idBook=${idBook}`).then(entitiesResponse => entitiesResponse.data);
    }
    public getByPageByAuthor(limit, offset, authorId, filterBy) {
        return this.$http.post<IEntitiesAndCountPages<IBook>>(this.BASE_URL + this.API_URL + '/find',
            {limit: limit, offset: offset, filterBy: filterBy,  'pattern': `byAuthor=${authorId}`})
            .then(entitiesResponse => entitiesResponse.data);
    }
    public getByRating(limit, offset, rating, filterBy) {
        return this.$http.post<IEntitiesAndCountPages<IBook>>(this.BASE_URL + this.API_URL + '/find',
            {limit: limit, offset: offset, filterBy: filterBy, 'pattern': `byRating=${rating}`})
            .then(entitiesResponse => entitiesResponse.data);
    }
    public getBookByPage(limit, offset, filterBy) {
        return this.$http.post<IEntitiesAndCountPages<IBook>>(this.BASE_URL + this.API_URL + '/find',
            {limit: limit, offset: offset, filterBy: filterBy})
            .then(entitiesResponse => entitiesResponse.data);
    }
}

const moduleName = 'myApp.booksApi'
export default moduleName

angular.module(moduleName, [])
  .service('booksApi', HttpBooksApi)
