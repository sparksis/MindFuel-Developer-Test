angular.module('adminUi').component('adminUi', {
	templateUrl : '/resources/js/admin-ui/admin-ui.template.html',
	controllerAs : 'admin',
	controller : function(User,$mdToast) {
		$self = this;
		$self.users = User.query();
		$self.newUser = undefined;
		$self.createUser = function() {
			User.create(
				$self.newUser,
				function(){
					$mdToast.show($mdToast.simple().textContent('Success!'))
				},function(){
					$mdToast.show($mdToast.simple().textContent('Shitbrok'))
				});
		};

	}
});