angular.module('drawingApp').factory('User',function($resource){
	return $resource('/rest/users/:username',{username:'@username'},{
		create:{method:'post'},
		save:{method:'put'}
	});
});