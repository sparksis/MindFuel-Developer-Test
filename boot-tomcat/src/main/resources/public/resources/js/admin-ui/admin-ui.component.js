angular.module('adminUi').component('adminUi', {
	templateUrl : '/resources/js/admin-ui/admin-ui.template.html',
	controllerAs : 'admin',
	controller : function(User,$mdToast) {
		$self = this;
		$self.users = User.query();
		$self.newUser = {active:true};
		
		$self.saveUser = function(user){
			user.$save(function(){
				$mdToast.show($mdToast.simple().textContent('Success!').position('top'));
				user.$get();
			},function(resp){
				$mdToast.show($mdToast.simple().textContent('Unable to save user at this time').position('top'))
			});
		};
		
		$self.createUser = function() {
			$self.newUser.rolesWanted=["ROLE_USER"];
			var fail = function(failMessage){
				$mdToast.show($mdToast.simple()
						.textContent(failMessage)
						.highlightClass('md-warn')
						.position('top')
						.parent(document.getElementById('toast-anchor'))
						);
			}
			
			User.create(
				$self.newUser,
				function(){
					$mdToast.show($mdToast.simple().textContent('Success!').position('top'))
					$self.users.push(User.get({username:$self.newUser.username}));
					$self.newUser = {active:true};
				},function(resp){
					if(resp.status==409){
						fail('User already exists');
					}else if(resp.status==400){
						fail('User was not accepted by the server. Check that your user is valid.');
					} else{
						fail('Failed to save user!');
					}
				}
			);
		};

	}
});