angular.module('drawingApp', [ 'ngResource', 'ngRoute', 'ngMaterial' ]);

angular.module('drawingApp').config(
		function($mdThemingProvider) {
			$mdThemingProvider.theme('default').primaryPalette('orange')
					.backgroundPalette('blue-grey');
		});

angular.module('drawingApp').controller('AppController', function(Canvas) {

});