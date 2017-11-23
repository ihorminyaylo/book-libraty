System.register([], function (exports_1, context_1) {
    "use strict";
    var __moduleName = context_1 && context_1.id;
    var moduleName;
    return {
        setters: [],
        execute: function () {
            moduleName = 'myApp.routes';
            exports_1("default", moduleName);
            angular.module(moduleName, [])
                .config(function ($routeProvider) {
                $routeProvider
                    .when('/', {
                    template: '<home-index></home-index>'
                })
                    .when('/books', {
                    template: '<books-index></books-index>'
                })
                    .when('/authors/:isbn', {
                    template: '<authors-index></authors-index>'
                })
                    .when('/books/:isbn', {
                    template: '<books-show></books-show>'
                });
            });
        }
    };
});
//# sourceMappingURL=routes.js.map