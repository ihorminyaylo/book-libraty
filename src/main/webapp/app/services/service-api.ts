import {IAuthor, IAuthorsApi} from "./authors-api/authors-api";


export interface IEntitiesAndCountPages<T> {
    list: T[];
    totalItems: number;
}

export interface IApi<T> {
    create(entity: T);
    getByPage(page, perPage): angular.IPromise<IEntitiesAndCountPages<T>>;
    update(entity: T);
    delete(idEntities: number[]);
}

export class HttpApi<T> implements IApi<T> {
    BASE_URL: string = 'http://localhost:9090';
    API_URL: string;
    AUTHOR_URL: string = '/api/author';
    BOOK_URL: string = '/api/book';
    controllerType: string;
    $http: angular.IHttpService;
    constructor($http: angular.IHttpService) {
        this.$http = $http;
    }
    public create(entity: T) {
        return this.$http.post(this.BASE_URL + this.API_URL, entity);
    }
    public getByPage(limit, offset) {
        console.log(this.BASE_URL + this.API_URL + '/find');
        return this.$http.post<IEntitiesAndCountPages<T>>(this.BASE_URL + this.API_URL + '/find',
            {limit: limit, offset: offset})
            .then(entitiesResponse => entitiesResponse.data);
    }
    public update(entity: T) {
        return this.$http.put(this.BASE_URL + this.API_URL + '/event', entity);
    }
    public delete(idEntities: number[]) {
        return this.$http.put(this.BASE_URL + this.API_URL + '/delete', idEntities );
    }
}

const moduleName = 'myApp.httpApi'
export default moduleName

angular.module(moduleName, [])
    .service('httpApi', HttpApi)