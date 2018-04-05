angular.module('restaurant').factory('TokenStorage', function($log) {
		$log.log("setting up TokenStorage...");
		var storageKey = 'auth_token';
		return {
			store : function(token) {
				$log.log("Storing token: "+token);
				return localStorage.setItem(storageKey, token);
			},
			retrieve : function() {
				$log.log("Retrieving token");
				return localStorage.getItem(storageKey);
			},
			clear : function() {
				$log.log("Clearing token");
				return localStorage.removeItem(storageKey);
			}
		};
	}).factory('TokenAuthInterceptor', function($q, $log, TokenStorage) {
		$log.log("setting up TokenAuthInterceptor...");
		return {
			request : function(config) {
				var authToken = TokenStorage.retrieve();
				if (authToken) {
					config.headers['X-AUTH-TOKEN'] = authToken;
				}
				return config;
			},
			responseError : function(error) {
				if (error.status === 401 || error.status === 403) {
					TokenStorage.clear();
					$log.log("token cleared!");
				}
				return $q.reject(error);
			}
		};
	});