const moduleName = 'myApp.routes'
export default moduleName

angular.module(moduleName, [])
  .config(($routeProvider: angular.route.IRouteProvider) => {
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
      })
  })
