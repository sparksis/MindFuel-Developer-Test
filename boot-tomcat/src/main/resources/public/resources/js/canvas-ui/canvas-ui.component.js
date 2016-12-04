angular.module('canvasUi').component('canvasUi', {
	templateUrl : '/resources/js/canvas-ui/canvas-ui.template.html',
	controllerAs: 'CanvasController',
	controller : function(Canvas) {
		Canvas.init();
		Canvas.sketch.size=2;
	}
});