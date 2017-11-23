System.register(["angular", "angular-route", "./routes", "./components/navigation/navigation", "./components/books-index/books-index", "./components/books-show/books-show", "./components/authors-index/authors-index", "./components/home-index/home-index"], function (exports_1, context_1) {
    "use strict";
    var __moduleName = context_1 && context_1.id;
    var angular, routes_1, navigation_1, books_index_1, books_show_1, authors_index_1, home_index_1;
    return {
        setters: [
            function (angular_1) {
                angular = angular_1;
            },
            function (_1) {
            },
            function (routes_1_1) {
                routes_1 = routes_1_1;
            },
            function (navigation_1_1) {
                navigation_1 = navigation_1_1;
            },
            function (books_index_1_1) {
                books_index_1 = books_index_1_1;
            },
            function (books_show_1_1) {
                books_show_1 = books_show_1_1;
            },
            function (authors_index_1_1) {
                authors_index_1 = authors_index_1_1;
            },
            function (home_index_1_1) {
                home_index_1 = home_index_1_1;
            }
        ],
        execute: function () {
            angular.module('myApp', [
                'ngRoute',
                routes_1["default"],
                navigation_1["default"],
                home_index_1["default"],
                authors_index_1["default"],
                books_index_1["default"],
                books_show_1["default"],
            ]);
        }
    };
});
//# sourceMappingURL=app.js.map