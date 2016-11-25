angular.module('authenticationUi',['ngResource','ngCookies']);
angular.module('authenticationUi').factory('Authenticator', function($resource){
	return $resource('/login',{
		username:'@username',
		password:'@password'
	},{
		login:{
			method:'POST',
			xsrfHeaderName: 'X-CSRF-TOKEN',
			xsrfCookieName: 'X-CSRF-TOKEN'
		}
	});
});
