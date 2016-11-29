angular.module('toolbar').component('toolbar', {
	templateUrl : 'resources/js/toolbar/toolbar.template.html',
	controllerAs : 'toolbar',
	controller : function(Canvas,$location) {
		$self=this;
		$self.colors = [ 'White', 'Silver', 'Gray', 'Black', 'Red',
						'Maroon', 'Yellow', 'Olive', 'Lime', 'Green', 'Aqua',
						'Teal', 'Blue', 'Navy', 'Fuchsia', 'Purple' ];
		$self.undo=Canvas.undo;
		$self.redo=Canvas.redo;
		$self.clear=Canvas.clear;
	}
});