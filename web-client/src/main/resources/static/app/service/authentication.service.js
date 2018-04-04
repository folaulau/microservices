(function() {
	'use strict';
	'use strict';
	angular.module('restaurant')
	.factory('AuthenticationService', AuthenticationService);

	AuthenticationService.$inject = [ '$resource', '$log' ];
	

	function AuthenticationService($resource, $log, $httpParamSerializerJQLike) {
		var host = 'http://localhost:8080';
		var resourceUrl = host+'/api/users/';

		return $resource(resourceUrl, {}, {
			'query' : {
				method : 'GET',
				isArray : true
			},
			'login' : {
				method : 'POST',
				headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
				url: host+'/api/login',
				transformRequest : function(data,headersGetter) {
					var str = [];
                    for (var d in data)
                        str.push(encodeURIComponent(d) + "=" + encodeURIComponent(data[d]));
                    return str.join("&");
				}
			},
		});
	}
})();