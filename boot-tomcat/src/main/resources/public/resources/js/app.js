angular.module('drawingApp', [ 'ngResource', 'ngRoute', 'ngMaterial' , 'canvasUi','adminUi', 'authenticationUi' ]);

angular.module('drawingApp').config(
		function($mdThemingProvider) {
			$mdThemingProvider.theme('default').primaryPalette('orange')
					.backgroundPalette('blue-grey');
		});

angular.module('drawingApp').controller('AppController', function(Session,$location) {
	var $self = this;
	$self.s=Session.query(null,function(){
		if(!$self.s.authenticated){
			$location.path("auth");
		}
	});
});