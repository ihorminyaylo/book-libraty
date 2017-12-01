import * as angular from 'angular'

import homeApiModule from '../../services/home-api/home-api'



class HomeIndex {

    constructor (
    ) {}
}

const moduleName = 'myApp.home-index';
export default moduleName

angular.module(moduleName, [homeApiModule])
    .component('homeIndex', {
        templateUrl: 'app/components/home-index/home-index.html',
        controller: HomeIndex
    }).controller("HomeIndex", HomeIndex)