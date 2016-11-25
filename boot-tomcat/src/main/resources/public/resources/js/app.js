angular.module('drawingApp', [ 'ngResource', 'ngRoute', 'ngMaterial' , 'canvasUi', 'authenticationUi' ]);

angular.module('drawingApp').config(
		function($mdThemingProvider) {
			$mdThemingProvider.theme('default').primaryPalette('orange')
					.backgroundPalette('blue-grey');
		});

angular.module('drawingApp').controller('AppController', function(Session) {
	var $self = this;
	$self.s=Session.query();
});