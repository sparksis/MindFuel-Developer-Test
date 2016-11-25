/**
 * Gives angular a injectable reference to the page's Canvas
 */
angular.module('drawingApp').factory('Canvas', function() {
	var sketch = $('#drawing').data('sketch');
	var redo = [];
	var redraw = function() {
		sketch.redraw.call(sketch)
	};
	el = document.getElementById("drawing");
	el.undo = function() {
		redo.push(sketch.actions.pop());
		redraw();
	};
	el.redo = function() {
		// TODO validate redo (check to see if the stack has changed, if so do
		// not go permit redoing)
		sketch.actions.push(redo.pop());
		redraw();
	};
	return el;
});