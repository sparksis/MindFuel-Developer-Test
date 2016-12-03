angular.module('adminUi').component('adminUi', {
	templateUrl : '/resources/js/admin-ui/admin-ui.template.html',
	controllerAs : 'admin',
	controller : function(User) {
		$self = this;
		$self.users = User.query();
		$self.newUser = undefined;
	}
});