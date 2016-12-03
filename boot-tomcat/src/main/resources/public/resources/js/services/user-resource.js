angular.module('drawingApp').factory('User',function($resource){
	return $resource('/rest/users/:username',{username:'@username'},{
		create:{method:'post',url:'/rest/users'},
		save:{method:'put'}
	});
});