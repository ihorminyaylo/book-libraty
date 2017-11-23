System.register(["angular"], function (exports_1, context_1) {
    "use strict";
    var __moduleName = context_1 && context_1.id;
    var angular, HttpBooksApi, moduleName;
    return {
        setters: [
            function (angular_1) {
                angular = angular_1;
            }
        ],
        execute: function () {
            HttpBooksApi = /** @class */ (function () {
                /*http://localhost:9090/api/author*/
                /*'http://bookmonkey-api.angularjs.de/books'*/
                function HttpBooksApi($http) {
                    this.$http = $http;
                    this.baseUrl = 'http://localhost:9090/api/book';
                }
                HttpBooksApi.prototype.all = function () {
                    return this.$http.get(this.baseUrl + '/find')
                        .then(function (booksResponse) { return booksResponse.data; });
                };
                HttpBooksApi.prototype.getByIsbn = function (isbn) {
                    return this.$http.get(this.baseUrl + "/" + isbn)
                        .then(function (bookResponse) { return bookResponse.data; });
                };
                return HttpBooksApi;
            }());
            moduleName = 'myApp.booksApi';
            exports_1("default", moduleName);
            angular.module(moduleName, [])
                .service('booksApi', HttpBooksApi);
        }
    };
});
//# sourceMappingURL=books-api.js.map