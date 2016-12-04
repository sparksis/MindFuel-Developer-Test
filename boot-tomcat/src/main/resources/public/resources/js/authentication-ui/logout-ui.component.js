/**
 * As we use spring csrf the logout must be from a POST
 */
angular.module('authenticationUi').component("logout", {
	template : '',
	controller : function(Authenticator, $location) {
		Authenticator.logout({},function(){window.location.reload(true);});
	}
});