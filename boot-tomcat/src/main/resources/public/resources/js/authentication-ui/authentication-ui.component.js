angular.module('authenticationUi').component('authenticationUi', {
	templateUrl : '/resources/js/authentication-ui/authentication-ui.template.html',
	controllerAs: 'auth',
	controller : function(Authenticator,$cookies) {
		var $self = this;
		$self.csrfToken = $cookies.get("X-CSRF-TOKEN");
		$self.credentials={
				username:undefined,
				password:undefined,
		};
		
		//TODO implement get Authenticator to play nicely with spring 
	}
});