System.register(["angular", "../../services/books-api/books-api"], function (exports_1, context_1) {
    "use strict";
    var __moduleName = context_1 && context_1.id;
    var angular, books_api_1, BooksShow, moduleName;
    return {
        setters: [
            function (angular_1) {
                angular = angular_1;
            },
            function (books_api_1_1) {
                books_api_1 = books_api_1_1;
            }
        ],
        execute: function () {
            BooksShow = /** @class */ (function () {
                function BooksShow(booksApi, $routeParams) {
                    var _this = this;
                    booksApi.getByIsbn($routeParams.isbn)
                        .then(function (book) { return _this.book = book; });
                }
                return BooksShow;
            }());
            moduleName = 'myApp.books-show';
            exports_1("default", moduleName);
            angular.module(moduleName, [books_api_1["default"]])
                .component('booksShow', {
                templateUrl: 'app/components/books-show/books-show.html',
                controller: BooksShow
            });
        }
    };
});
//# sourceMappingURL=books-show.js.map