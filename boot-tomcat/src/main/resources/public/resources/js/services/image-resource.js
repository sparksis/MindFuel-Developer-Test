angular.module('drawingApp').factory('Image', function($resource) {
	return $resource('/rest/images/:filename', {filename:'@filename'}, {
		save : {
			method : 'PUT',
		}
	});
});