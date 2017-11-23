System.register(["angular"], function (exports_1, context_1) {
    "use strict";
    var __moduleName = context_1 && context_1.id;
    var angular, HttpAuthorsApi, moduleName;
    return {
        setters: [
            function (angular_1) {
                angular = angular_1;
            }
        ],
        execute: function () {
            HttpAuthorsApi = /** @class */ (function () {
                function HttpAuthorsApi($http) {
                    this.$http = $http;
                    this.baseUrl = 'http://localhost:9090/api/author';
                }
                HttpAuthorsApi.prototype.all = function (page, perPage) {
                    return this.$http.get(this.baseUrl + ("/find?page=" + page + "&pageSize=" + perPage))
                        .then(function (authorsResponse) { return authorsResponse.data; });
                };
                HttpAuthorsApi.prototype.getByIsbn = function (isbn) {
                    return this.$http.get(this.baseUrl + "/" + isbn)
                        .then(function (authorResponse) { return authorResponse.data; });
                };
                return HttpAuthorsApi;
            }());
            moduleName = 'myApp.authorsApi';
            exports_1("default", moduleName);
            angular.module(moduleName, [])
                .service('authorsApi', HttpAuthorsApi);
        }
    };
});
//# sourceMappingURL=authors-api.js.map