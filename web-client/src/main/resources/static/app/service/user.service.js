(function() {
	'use strict';
	'use strict';
	angular.module('restaurant')
	.factory('UserService', UserService);

	UserService.$inject = [ '$resource', '$log' ];
	

	function UserService($resource, $log) {
		var resourceUrl = '/api/search/';

		return $resource(resourceUrl, {}, {
			'query' : {
				method : 'GET',
				isArray : true
			},
			'search' : {
				method : 'GET',
				url: "/api/search/:phrase",
				transformResponse : function(data) {
					if (data) {
						data = angular.fromJson(data);
					}
					return data;
				}
			}
		});
	}
})();