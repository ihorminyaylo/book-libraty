import * as angular from 'angular'

export interface IHomeApi {
}

class HttpHomeApi implements IHomeApi {
    constructor(
        private $http: angular.IHttpService
    ) {}
}

const moduleName = 'myApp.homeApi'
export default moduleName

angular.module(moduleName, [])
    .service('homeApi', HttpHomeApi)

