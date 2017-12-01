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
        .when('/authors', {
            template: '<authors-index></authors-index>'
        })
      .when('/books_show', {
        template: '<books-show></books-show>'
      })
        .when('/modal', {
            template: '<app></app>'
        })
  })
