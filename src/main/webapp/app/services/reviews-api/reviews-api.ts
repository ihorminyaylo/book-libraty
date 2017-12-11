import {HttpApi, IApi, IEntitiesAndCountPages} from "../service-api";

export interface IReviewsAndCountPages extends IEntitiesAndCountPages<IReview>{}

export class IReview {
    id: number;
    commenterName: string;
    comment: string;
    createDate: string;
    rating: string;
    bookId: number;
    constructor() {}
}

export interface IReviewsApi extends IApi<IReview> {
    readAll();
}

class HttpReviewsApi extends HttpApi<IReview> implements IReviewsApi {
    API_URL = this.REVIEW_URL;
    constructor($http: angular.IHttpService) {
        super($http);
    }
    public readAll() {
        return this.$http.get(this.BASE_URL + this.API_URL + '/review_detail');
    }
}

const moduleName = 'myApp.reviewsApi'
export default moduleName

angular.module(moduleName, [])
    .service('reviewsApi', HttpReviewsApi)