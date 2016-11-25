/**
 * Gives angular a injectable reference to the page's Canvas
 */
angular.module('drawingApp').factory('Canvas', function() {
	var el = document.getElementById("drawing");
	Object.defineProperty(el,'sketch',{
		get: function(){
			return $('#drawing').data('sketch');
		}
	});
	var redo = [];
	var redraw = function() {
		el.sketch.redraw.call(el.sketch)
	};
	el.undo = function() {
		redo.push(el.sketch.actions.pop());
		redraw();
	};
	el.redo = function() {
		// TODO validate redo (check to see if the stack has changed, if so do
		// not go permit redoing)
		el.sketch.actions.push(redo.pop());
		redraw();
	};
	el.clear = function(){
		el.sketch.actions = [];
		el.sketch.redraw();
	}
	return el;
});