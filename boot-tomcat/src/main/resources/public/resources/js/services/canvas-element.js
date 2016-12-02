/**
 * Gives angular a injectable reference to the page's Canvas.
 * 
 * This factory-service abuses the angular lifecycle a bit and incorrectly
 * modifies the dom. The result is we have a fairly clean wrapper which can be
 * used to control sketch.js
 */
angular.module('drawingApp').factory('Canvas', function() {

	//Enable sketch on the canvas
	$(function() {
		$('#drawing').sketch();
	});
	var el = document.getElementById("drawing");
	var redo = [];

	//add a readonly property to the canvas so 
	// we can access the sketch without jquery
	Object.defineProperty(el, 'sketch', {
		get : function() {
			return $('#drawing').data('sketch');
		}
	});

	//Add convenience functions so we can control the canvas
	el.redraw = function() {
		el.sketch.redraw.call(el.sketch)
	};
	el.undo = function() {
		redo.push(el.sketch.actions.pop());
		el.redraw();
	};
	el.redo = function() {
		// TODO validate redo (check to see if the stack has changed, if so do
		// not permit redoing)
		el.sketch.actions.push(redo.pop());
		el.redraw();
	};
	el.clear = function() {
		el.sketch.actions = [];
		el.redraw();
	}
	return el;
});