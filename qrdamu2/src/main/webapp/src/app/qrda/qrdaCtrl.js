function QrdaCtrl($state, $timeout, $http,$window,$location,
		toastr, qrdaFactory, $element, $anchorScroll, $mdDialog, $scope) {
	var that = this ;
	that.progessDisplay = false;
	that.showGrid = false;
	that.searchTerm;
	var table;
	that.clearSearchTerm = function() {
		that.searchTerm = '';
     };
     that.searchTermProvider;
 	that.clearSearchTermProvider = function() {
 		that.searchTermProvider = '';
      }; 
      
     // The md-select directive eats keydown events for some quick select
     // logic. Since we have a search input here, we don't need that logic.
     $element.find('input').on('keydown', function(ev) {
         ev.stopPropagation();
     });
	qrdaFactory.getProviders().then(function(data){
		that.providerList = data;
	});
	
	qrdaFactory.getMeasures().then(function(data){
		that.measureList = data;
	});
	that.formSubmit = function(){
		that.showGrid = false;
		if(that.form.$invalid){
    		that.form.$setSubmitted();
    		return;
    	}
		that.progessDisplay = true;
		var startDate = that.startDate.toLocaleDateString();
		var endDate = that.endDate.toLocaleDateString();
		var formattedStartDate = formateDate(startDate);
		var formattedEndDate = formateDate(endDate);
		var qrdaJson = {};
		qrdaJson.providerList = that.provider;
		qrdaJson.measureList = that.measures;
		qrdaJson.startDate = formattedStartDate;
		qrdaJson.endDate = formattedEndDate;
		
		qrdaFactory.createMU2files(qrdaJson).then(function(data){
			toastr.success("MU2 Files generated Successfully");
			that.progessDisplay = false;
			that.showGrid = true;
			table = $('#qrdaGrid').DataTable( {
				destroy: true,
		        data: data.cat1PatientDetails,
		        columns: [
		                  { data: "provider" },
		                  { data: "cqm" },
		                  { data: "cid" },
		                  { data: "lastName" },
		                  { data: "firstName" },
		                  { data: "gender" },
		                  { data: "birthDate" }
		              ]
		    } );
			$timeout(function(){
				$location.hash('qrdaGrid');
				$anchorScroll();
			}, 1000);

		}, function(data){
			toastr.error("Records for selected inputs doesn't exist");
			that.progessDisplay = false;
		});
	}
	that.showUploader = function(ev) {
	    $mdDialog.show({
	      controller: UploadController,
	      templateUrl: 'src/app/qrda/uploader.html',
	      parent: angular.element(document.body),
	      targetEvent: ev,
	      clickOutsideToClose:true
	    })
	    .then(function(answer) {
	      $scope.status = 'You said the information was "' + answer + '".';
	    }, function() {
	      $scope.status = 'You cancelled the dialog.';
	    });
	  };
	  
	  function UploadController($scope, $mdDialog) {
		  $scope.hide = function() {
			  $mdDialog.hide();
		  };

		  $scope.cancel = function() {
			  $mdDialog.cancel();
		  };

		  $scope.answer = function(answer) {
			  $mdDialog.hide(answer);
		  };
	  }
	
	var formateDate = function(date){
		var dateArray = date.split('/');
		var actualDate = dateArray[2]+'-'+dateArray[0]+'-'+dateArray[1];
		return actualDate;
	}
	
}

angular.module('qrda').controller('qrdaCtrl', QrdaCtrl);
