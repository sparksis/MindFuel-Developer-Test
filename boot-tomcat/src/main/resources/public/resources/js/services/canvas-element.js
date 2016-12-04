/**
 * Gives angular a injectable reference to the page's Canvas.
 * 
 * This factory-service abuses the angular lifecycle a bit and incorrectly
 * modifies the dom. The result is we have a fairly clean wrapper which can be
 * used to control sketch.js
 */
angular.module('drawingApp').factory('Canvas', function() {

	var Canvas = {
		init : function() {
			$('#drawing').sketch();
		},
		redraw:function() {
			Canvas.sketch.redraw.call(Canvas.sketch)
		},
		undo:function() {
			Canvas.$redo.push(Canvas.sketch.actions.pop());
			Canvas.redraw();
		},
		redo:function() {
			// TODO validate redo (check to see if the stack has changed, if so
			// do not permit redoing)
			Canvas.sketch.actions.push(Canvas.$redo.pop());
			Canvas.redraw();
		},
		clear :function() {
			Canvas.sketch.actions = [];
			Canvas.redraw();
		},
		$redo : [],
	}
	
	Object.defineProperty(Canvas,'el',{
		get:function(){
			return document.getElementById("drawing");
		}
	});
	
	// add a readonly property to the canvas so
	// we can access the sketch without jquery
	Object.defineProperty(Canvas, 'sketch', {
		get : function() {
			return $('#drawing').data('sketch');
		}
	});

	return Canvas;
});