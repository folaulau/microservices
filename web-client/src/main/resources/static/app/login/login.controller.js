(function() {
	'use strict';

	angular.module('restaurant').controller('LoginController', LoginController);

	LoginController.$inject = [ '$window','$scope', '$http', '$log', '$state', 'AuthenticationService', 'TokenStorage'];

	function LoginController($window, $scope, $http, $log, $state, AuthenticationService, TokenStorage) {
		$log.log("login controller");
		var login = this;
		login.data = {};
		
		init();
		
		function init(){
			checkToken();
			prefill();
			removeToken();
		}
		
		function prefill(){
			login.data.username = "folau@gmail.com";
			login.data.password = "test12";
		}
		
		login.submit = function(){
			var encodedUsernamePassword = window.btoa(login.data.username+":"+login.data.password);
			$http.defaults.headers.common['Authorization'] = 'Basic '+encodedUsernamePassword;
			
			AuthenticationService.login(login.data,
				function(result){
					$log.log("login success");
					$log.log(result);
					
					let authToken = result['jwt_token'];
					$log.log("authToken");
					$log.log(authToken);
					if(authToken!=null && authToken.length>10){
						TokenStorage.store(authToken);
					}
					
					
					window.location = "/order/";
					
					//checkToken();
				},
				function(error){
					$log.log("error");
					$log.log(error);
				}
			);
		}
		
		function checkToken(){
			let authToken = TokenStorage.retrieve();
			$log.log("authToken: "+authToken);
			$log.log("isString: "+angular.isString(authToken));
			if(angular.isString(authToken) && authToken!=="undefined"){
				$log.log("Decoded JWT");
				var decoded = jwt_decode(authToken);
				
				$log.log(decoded);
				$log.log("expiration time: "+new Date(decoded.exp));
			}
		}
		
		function removeToken(){
			let authToken = TokenStorage.clear();
			$log.log("removing token: "+authToken);
		}
	}
})();
