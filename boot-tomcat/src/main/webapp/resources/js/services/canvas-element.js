/**
 * Gives angular a injectable reference to the page's Canvas
 */
angular.module('drawingApp').factory('Canvas', function() {
	return document.getElementById("drawing");
});