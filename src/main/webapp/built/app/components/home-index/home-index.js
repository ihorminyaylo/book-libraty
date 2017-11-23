System.register(["angular", "../../services/home-api/home-api"], function (exports_1, context_1) {
    "use strict";
    var __moduleName = context_1 && context_1.id;
    var angular, home_api_1, HomeIndex, moduleName;
    return {
        setters: [
            function (angular_1) {
                angular = angular_1;
            },
            function (home_api_1_1) {
                home_api_1 = home_api_1_1;
            }
        ],
        execute: function () {
            HomeIndex = /** @class */ (function () {
                function HomeIndex() {
                }
                return HomeIndex;
            }());
            moduleName = 'myApp.home-index';
            exports_1("default", moduleName);
            angular.module(moduleName, [home_api_1["default"]])
                .component('homeIndex', {
                templateUrl: 'app/components/home-index/home-index.html',
                controller: HomeIndex
            }).controller("HomeIndex", HomeIndex);
        }
    };
});
//# sourceMappingURL=home-index.js.map