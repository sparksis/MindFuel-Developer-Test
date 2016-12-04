angular.module('authenticationUi',['ngResource','ngCookies']);
angular.module('authenticationUi').factory('Authenticator', function($resource){
	return $resource('/:action',{
		username:'@username',
		password:'@password'
	},{
		login:{
			method:'POST',
			params:{action:'login'},
			xsrfHeaderName: 'X-CSRF-TOKEN',
			xsrfCookieName: 'X-CSRF-TOKEN'
		},
		logout:{
			method:'POST',
			params:{action:'logout'},
			transformResponse: function(){ return null; },
			xsrfHeaderName: 'X-CSRF-TOKEN',
			xsrfCookieName: 'X-CSRF-TOKEN'
		}
	});
});
