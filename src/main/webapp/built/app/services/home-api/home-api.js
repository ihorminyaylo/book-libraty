System.register(["angular"], function (exports_1, context_1) {
    "use strict";
    var __moduleName = context_1 && context_1.id;
    var angular, HttpHomeApi, moduleName;
    return {
        setters: [
            function (angular_1) {
                angular = angular_1;
            }
        ],
        execute: function () {
            HttpHomeApi = /** @class */ (function () {
                function HttpHomeApi($http) {
                    this.$http = $http;
                }
                return HttpHomeApi;
            }());
            moduleName = 'myApp.homeApi';
            exports_1("default", moduleName);
            angular.module(moduleName, [])
                .service('homeApi', HttpHomeApi);
        }
    };
});
//# sourceMappingURL=home-api.js.map