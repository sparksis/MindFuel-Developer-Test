angular.module('drawingApp').factory('Session', function($resource) {
	return $resource("rest/session",{},{query:{isArray:false}});
});