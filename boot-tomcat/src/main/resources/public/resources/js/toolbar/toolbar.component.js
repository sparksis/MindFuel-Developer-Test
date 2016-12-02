angular.module('toolbar').component('toolbar', {
	templateUrl : 'resources/js/toolbar/toolbar.template.html',
	controllerAs : 'toolbar',
	controller : function(Canvas,$location,$mdDialog,Image) {
		$self=this;
		$self.colors = [ 'White', 'Silver', 'Gray', 'Black', 'Red',
						'Maroon', 'Yellow', 'Olive', 'Lime', 'Green', 'Aqua',
						'Teal', 'Blue', 'Navy', 'Fuchsia', 'Purple' ];
		$self.undo=Canvas.undo;
		$self.redo=Canvas.redo;
		$self.clear=Canvas.clear;

		//Color palette visibility
		$self.palette=false;
		$self.togglePalette=function(){
			$self.palette=!$self.palette;			
		};
		
		$self.showSaveDialog = function(ev) {
			    // Appending dialog to document.body to cover sidenav in docs app
			    var confirm = $mdDialog.prompt()
			      .title('Save')
			      .textContent('Filename')
			      .placeholder('eg. image.png')
			      .targetEvent(ev)
			      .ok('Save')
			      .cancel('Cancel');
			    $mdDialog.show(confirm)
			    .then(function(result) { 
			    	Image.save({filename:result,data:Canvas.sketch.actions});
			    });
		};
		
		$self.showLoadDialog = function(ev) {
		    // Appending dialog to document.body to cover sidenav in docs app
		    var confirm = $mdDialog.prompt()
		      .title('Load')
		      .textContent('Filename')
		      .placeholder('eg. image.png')
		      .targetEvent(ev)
		      .ok('Load')
		      .cancel('Cancel');
		    $mdDialog.show(confirm)
		    .then(function(result) { 
		    	Image.get({filename:result},function(image){
		    		Canvas.sketch.actions=image.data;
		    		Canvas.redraw();
		    	});
		    });
		};
	}
});