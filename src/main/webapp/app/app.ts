import * as angular from 'angular'
import 'angular-route'
import '../node_modules/angular-ui-bootstrap/dist/ui-bootstrap'
import '../node_modules/angular-ui-bootstrap/dist/ui-bootstrap-tpls'
import '../bower_components/angular-bootstrap-form-validation/form-validation'

import routes from './routes'

import navigation from './components/navigation/navigation'
import booksIndex from './components/books-index/books-index'
import booksShow from './components/books-show/books-show'
import authorsIndex from './components/authors-index/authors-index'
import homeIndex from './components/home-index/home-index'
import app from './components/authors-index/modal/modal'

angular.module('myApp', [
  'ngRoute',
    app,
  routes,
  navigation,
  homeIndex,
  'ui.bootstrap',
  'ui.bootstrap.tpls',
  'ui.bootstrap.validation',
  authorsIndex,
  booksIndex,
  booksShow,
])
