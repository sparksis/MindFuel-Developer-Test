angular.module('drawingApp', [ 'ngResource', 'ngRoute', 'ngMaterial' , 'toolbar' ]);

angular.module('drawingApp').config(
		function($mdThemingProvider) {
			$mdThemingProvider.theme('default').primaryPalette('orange')
					.backgroundPalette('blue-grey');
		});

angular.module('drawingApp').controller('CanvasController', function(Canvas) {
	$self = this;
	$self.push = Canvas.push;
});

angular.module('drawingApp').controller('AppController', function(Canvas) { });