/**
 * Gives angular a injectable reference to the page's Canvas.
 * 
 * This factory-service abuses the angular lifecycle a bit and incorrectly
 * modifies the dom. The result is we have a fairly clean wrapper which can be
 * used to control sketch.js
 */
angular.module('drawingApp').factory('Canvas', function() {

	$(function() {
		sk = $('#drawing').sketch();
	});

	var el = document.getElementById("drawing");
	Object.defineProperty(el, 'sketch', {
		get : function() {
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
		// not permit redoing)
		el.sketch.actions.push(redo.pop());
		redraw();
	};
	el.clear = function() {
		el.sketch.actions = [];
		redraw();
	}
	return el;
});