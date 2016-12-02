angular.module('drawingApp').config(function($locationProvider, $routeProvider) {
	$locationProvider.html5Mode(true);
	$routeProvider.when('/',{
		template : '<canvas-ui></canvas-ui>'
	}).when('/auth',{
		template : '<authentication-ui></authentication-ui>'
	}).when('/admin',{
		template : '<admin-ui></admin-ui>'
	}).otherwise({
		template : 'notfound'
	});
});