angular.module('drawingApp').config(function($locationProvider, $routeProvider) {
	$locationProvider.html5Mode(true);
	$routeProvider.when('/',{
		template : '<canvas-ui></canvas-ui>'
	}).when('/login',{
		template : '<authentication-ui></authentication-ui>'
	}).when('/logout',{
		template : '<logout></logout>'
	}).when('/admin',{
		template : '<admin-ui></admin-ui>'
	}).otherwise({
		template : 'notfound'
	});
});