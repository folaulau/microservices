(function() {
	'use strict';
	'use strict';
	angular.module('restaurant')
	.factory('OrderService', OrderService);

	OrderService.$inject = [ '$resource', '$log' ];
	

	function OrderService($resource, $log, $httpParamSerializerJQLike) {
		var host = 'http://localhost:8083';
		var resourceUrl = host+'/api/orders';

		return $resource(resourceUrl, {}, {
			'query' : {
				method : 'GET',
				isArray : true
			},
			'add' : {
				method : 'POST',
				headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
				url: resourceUrl,
				transformRequest : function(data,headersGetter) {
					var str = [];
                    for (var d in data)
                        str.push(encodeURIComponent(d) + "=" + encodeURIComponent(data[d]));
                    return str.join("&");
				}
			},
			'getById' : {
				method : 'GET',
				url: resourceUrl+"/:id",
				transformResponse : function(data) {
					if (data) {
						data = angular.fromJson(data);
					}
					return data;
				}
			},
		});
	}
})();