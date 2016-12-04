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
		$self.clear=function(){
			if(Canvas.sketch.actions.length>0){
				$mdDialog.show(
					$mdDialog.confirm()
						.title('Clear Canvas?')
						.textContent('Would you like to clear the Canvas?')
						.ok('Clear Canvas')
						.cancel('Continue Drawing')
				).then(function(){
					Canvas.clear();
				});
			}else{
				Canvas.clear();
			}
		}
		
		$self.filename=undefined;

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
			      .initialValue($self.filename)
			      .targetEvent(ev)
			      .ok('Save')
			      .cancel('Cancel');
			    $mdDialog.show(confirm)
			    .then(function(result) { 
			    	$self.filename=result;
			    	Image.save({filename:result,data:Canvas.sketch.actions});
			    });
		};
		
		$self.showLoadDialog = function(ev) {
		    $mdDialog.show({
		    	controllerAs:'load',
		    	controller: DialogController,
		        templateUrl: '/resources/js/toolbar/load-dialog.template.html',
		        parent: angular.element(document.body),
		        targetEvent: ev,
		        clickOutsideToClose:true
		      }).then(function(result) {
			    	$self.filename=result;
			    	Image.get({filename:result},function(image){
			    		Canvas.sketch.actions=image.data;
			    		Canvas.redraw();
			    	});
			    });
		    };

		
		    function DialogController($scope, $mdDialog) {
		    	var $self=this;
		    	$self.showAfter=0;
		    	$self.imagesPage;
		    	$self.nextPage=function(){
		    		if($self.showAfter+10<$self.images.length){
		    			$self.showAfter+=10;
		    			$self.imagesPage=$self.images.slice($self.showAfter,$self.showAfter+10)
		    			$self.image=$self.imagesPage[0];
		    		}
		    	}
		    	$self.prevPage=function(){
		    		if($self.showAfter-10>=0){
		    			$self.showAfter-=10;
		    			$self.imagesPage=$self.images.slice($self.showAfter,$self.showAfter+10)
		    			$self.image=$self.imagesPage[0];
		    		}
		    	}
		    	
		    	$self.images = Image.query({},
		    		function(d) {
	    				$self.disabled=d.length==0;
						$self.imagesPage=d.slice(0,10);
						$self.image = d[0];
					});
		    	$self.image=undefined;
		    	$self.disabled=true;
		    	
		    	$self.cancel = function() {
		          $mdDialog.cancel();
		        };

		        $self.ok = function() {
		          $mdDialog.hide($self.image);
		        };
		    }
	}
});