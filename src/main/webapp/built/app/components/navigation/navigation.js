System.register(["angular"], function (exports_1, context_1) {
    "use strict";
    var __moduleName = context_1 && context_1.id;
    var angular, moduleName, Navigation;
    return {
        setters: [
            function (angular_1) {
                angular = angular_1;
            }
        ],
        execute: function () {
            moduleName = 'myApp.navigation';
            exports_1("default", moduleName);
            Navigation = /** @class */ (function () {
                function Navigation($location) {
                    this.$location = $location;
                }
                Navigation.prototype.isActive = function (route) {
                    var routeExp = new RegExp(route);
                    return routeExp.test(this.$location.path());
                };
                return Navigation;
            }());
            angular.module(moduleName, [])
                .component('navigation', {
                templateUrl: 'app/components/navigation/navigation.html',
                controller: Navigation
            });
        }
    };
});
//# sourceMappingURL=navigation.js.map