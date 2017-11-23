System.register(["angular", "../../services/authors-api/authors-api"], function (exports_1, context_1) {
    "use strict";
    var __moduleName = context_1 && context_1.id;
    var angular, authors_api_1, AuthorsIndex, moduleName;
    return {
        setters: [
            function (angular_1) {
                angular = angular_1;
            },
            function (authors_api_1_1) {
                authors_api_1 = authors_api_1_1;
            }
        ],
        execute: function () {
            AuthorsIndex = /** @class */ (function () {
                function AuthorsIndex(authorsApi, $routeParams) {
                    var _this = this;
                    this.currentPage = 1;
                    this.perPage = 3;
                    this.currentPage = parseInt($routeParams.isbn);
                    this.authorsApi = authorsApi;
                    if (parseInt($routeParams.isbn) <= 0 || $routeParams.isbn == null) {
                        $routeParams.isbn = '1';
                    }
                    this.$routeParams = $routeParams;
                    this.nextPage = parseInt(this.$routeParams.isbn) + 1;
                    authorsApi.all($routeParams.isbn, this.perPage)
                        .then(function (authorsAndCountPages) {
                        _this.authorsAndCountPages = authorsAndCountPages;
                        //todo: add catch for exception from server
                        if (parseInt($routeParams.isbn) <= parseInt(_this.authorsAndCountPages.pageCount) && parseInt($routeParams.isbn) > 0) {
                            _this.$routeParams = $routeParams;
                        }
                        else {
                            $routeParams.isbn = '1';
                            _this.$routeParams.isbn = $routeParams.isbn;
                        }
                    })["catch"](function (error) { alert(error); });
                }
                AuthorsIndex.prototype.openPage = function (page) {
                    //this.currentPage = 2;
                    //this.authorsApi.all(this.currentPage, 2).then(authorsAndCountPages => this.authorsAndCountPages = authorsAndCountPages);
                };
                AuthorsIndex.prototype.openModal = function () {
                    console.log('adsdas');
                    /*this.dialog.open(HomeIndex, {
                        width: '250px',
                        data: { name: 'dasdas', animal: 'dasdasdasasd' }
                    });*/
                };
                return AuthorsIndex;
            }());
            moduleName = 'myApp.authors-index';
            exports_1("default", moduleName);
            angular.module(moduleName, [authors_api_1["default"]])
                .component('authorsIndex', {
                templateUrl: 'app/components/authors-index/authors-index.html',
                controller: AuthorsIndex
            }).controller("AuthorsIndex", AuthorsIndex);
        }
    };
});
//# sourceMappingURL=authors-index.js.map