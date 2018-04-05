(function() {
	'use strict';
	 angular.module('springboot').service('FormService', [ '$http', function($http) {
			
	    this.getUser = function getUser(userId) {
	        return $http({
	            method : 'GET',
	            url : 'api/users/' + userId
	        });
	    },
	    this.addUser = function addUser(params, user) {
	        return $http.post(
	        		'api/users?'+params,
	        		user
	        );
	    }
	 }]);
})();