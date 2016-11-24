angular.module('toolbar').component('toolbar', {
	templateUrl : 'resources/js/toolbar/toolbar.template.html',
	controllerAs : 'toolbar',
	controller : function(Canvas) {
				this.colors = [ 'White', 'Silver', 'Gray', 'Black', 'Red',
						'Maroon', 'Yellow', 'Olive', 'Lime', 'Green', 'Aqua',
						'Teal', 'Blue', 'Navy', 'Fuchsia', 'Purple' ];
	}
});