(function() {
	'use strict';

	angular.module('restaurant').controller('LoginController', LoginController);

	LoginController.$inject = [ '$window','$scope', '$http', '$log', '$state', 'AuthenticationService'];

	function LoginController($window, $scope, $http, $log, $state, AuthenticationService) {
		$log.log("login controller");
		var login = this;
		login.data = {};
		
		init();
		
		function init(){
			prefill();
		}
		
		function prefill(){
			login.data.username = "folau@gmail.com";
			login.data.password = "test12";
		}
		
		login.submit = function(){
			AuthenticationService.login(login.data,
				function(result){
					$log.log("success");
					$log.log(result);
				},
				function(error){
					$log.log("error");
					$log.log(error);
				}
			);
		}
	}
})();
