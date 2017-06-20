function qrdaFactory($http) {
	var qrdaFactory = {};

	qrdaFactory.getProviders = function() {
		return $http.get('qrda/getProviders').then(
				function(response) {
					return response.data;
				});
	};
	
	qrdaFactory.getMeasures = function() {
		return $http.get('qrda/getMeasures').then(
				function(response) {
					return response.data;
				});
	};
	
	qrdaFactory.createMU2files = function(data) {
 		return $http({
 			method : 'POST',
 			url : 'qrda/createMU2files' ,
 			data : data
 		}).then(function(response) {
 			return response.data;
 		});
 	};
	
	
	return qrdaFactory;
}
angular.module('qrda').factory('qrdaFactory', qrdaFactory);