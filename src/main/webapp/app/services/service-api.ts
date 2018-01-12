import {IAuthor, IAuthorsApi} from "./authors-api/authors-api";
import {IReview} from "./reviews-api/reviews-api";

export class AuthorPattern {
}

export class BookPattern {
    authorId: number = null;
    search: string = null;
    rating: number = null;
    constructor(authorId: number, search: string, rating: number) {
        this.authorId = authorId;
        this.search = search;
        this.rating = rating;
    }
}

export class ReviewPattern {
    bookId: number;
    constructor(bookId: number) {
        this.bookId = bookId;
    }
}

export interface IEntitiesAndCountPages<T> {
    list: T[];
    totalItems: number;
}

export interface IApi<T> {
    create(entity: T);
    createReview(commenterName, comment, rating, book);
    getAll();
    getByPage(maxSize, offset): angular.IPromise<IEntitiesAndCountPages<T>>;
    getById(id: number);
    update(entity: T);
    delete(idEntity: number);
    bulkDelete(idEntities: number[]);
}

export class ListParams<P> {
    limit: number = null;
    offset: number = null;
    pattern: P = null;
    sortParams: SortParams = null;
    constructor(limit: number, offset: number, pattern: P, sortParams: SortParams) {
        this.limit = limit;
        this.offset = offset;
        this.pattern = pattern;
        this.sortParams = sortParams;
    }
}
export class SortParams {
    parameter: string;
    type: string;
    constructor(parameter: string, type: string) {
        this.parameter = parameter;
        this.type = type;
    }
}

export class HttpApi<T> implements IApi<T> {
    BASE_URL: string = '' + location.pathname;
    API_URL: string;
    AUTHOR_URL: string = '/api/author';
    BOOK_URL: string = '/api/book';
    REVIEW_URL: string = '/api/review';
    controllerType: string;
    $http: angular.IHttpService;
    constructor($http: angular.IHttpService) {
        this.$http = $http;
    }
    public create(entity: T) {
        return this.$http.post(this.BASE_URL + this.API_URL, entity);
    }

    public getAll() {
        return this.$http.post<IEntitiesAndCountPages<T>>(this.BASE_URL + this.API_URL + '/find', {})
            .then(entitiesResponse => entitiesResponse.data);
    }
    public getByPage(limit, offset) {
        return this.$http.post<IEntitiesAndCountPages<T>>(this.BASE_URL + this.API_URL + '/find',
            {limit: limit, offset: offset})
            .then(entitiesResponse => entitiesResponse.data);
    }
    public getById(id: number) {
        return this.$http.get<IEntitiesAndCountPages<T>>(this.BASE_URL + this.API_URL + `/byId?idAuthor=${id}`);
    }
    public update(entity: T) {
        return this.$http.put(this.BASE_URL + this.API_URL, entity);
    }

    public delete(idEntity: number) {
        return this.$http.delete(this.BASE_URL + this.API_URL + '/' + idEntity);
    }
    public bulkDelete(idEntities: number[]) {
        return this.$http.put(this.BASE_URL + this.API_URL + '/delete', idEntities );
    }
    public createReview(commenterName, comment, rating, book) {
        return this.$http.post(this.BASE_URL + this.REVIEW_URL, {commenterName, comment, rating, book});
    }
}

const moduleName = 'myApp.httpApi'
export default moduleName

angular.module(moduleName, [])
    .service('httpApi', HttpApi)