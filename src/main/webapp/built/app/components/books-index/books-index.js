System.register(["angular", "../../services/books-api/books-api"], function (exports_1, context_1) {
    "use strict";
    var __moduleName = context_1 && context_1.id;
    var angular, books_api_1, BooksIndex, moduleName;
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
            BooksIndex = /** @class */ (function () {
                function BooksIndex(booksApi) {
                    var _this = this;
                    booksApi.all()
                        .then(function (books) { return _this.books = books; });
                }
                return BooksIndex;
            }());
            moduleName = 'myApp.books-index';
            exports_1("default", moduleName);
            angular.module(moduleName, [books_api_1["default"]])
                .component('booksIndex', {
                templateUrl: 'app/components/books-index/books-index.html',
                controller: BooksIndex
            });
        }
    };
});
//# sourceMappingURL=books-index.js.map