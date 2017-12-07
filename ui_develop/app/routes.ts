const moduleName = 'myApp.routes'
export default moduleName

angular.module(moduleName, [])
  .config(($routeProvider: angular.route.IRouteProvider) => {
    $routeProvider
      .when('/', {
        template: '<reviews-index></reviews-index>'
      })
      .when('/books', {
            template: '<books-index></books-index>'
      })
      .when('/books/author/:isbn', {
        template: '<books-index></books-index>'
      })
      .when('/books/rating/:rating', {
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
