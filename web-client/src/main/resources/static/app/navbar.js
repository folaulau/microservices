(function() {
	'use strict';

	angular.module('restaurant').controller('NavbarController', NavbarController);

	NavbarController.$inject = [ '$window','$scope', '$filter', '$http', '$log', '$state', 'TokenStorage'];

	function NavbarController($window, $scope, $filter, $http, $log, $state, TokenStorage) {
		$log.log("navbar controller");
		var navbar = this;
		navbar.user = {};
		navbar.userLoggedIn = false;
		
		init();
		
		function init(){
			if(isUserLoggedIn()){
				getUserFromLocal();
				navbar.userLoggedIn = true;
			}else{
				$log.log("log out user.");
				//removeToken();
			}
		}
		
		function isUserLoggedIn(){
			let authToken = TokenStorage.retrieve();
			$log.log("authToken : "+authToken);
			if(angular.isString(authToken) && authToken!=="undefined"){
				$log.log("Decoded JWT");
				var payload = jwt_decode(authToken);
				$log.log(payload);
				$log.log("expiration time: "+$filter('date')(payload.exp,"MM/dd/yyyy h:mma"));
				return true;
			}else{
				return false;
			}
		}
		
		function getUserFromLocal(){
			let authToken = TokenStorage.retrieve();
			$log.log("authToken: "+authToken);
			var decoded = jwt_decode(authToken);
			navbar.user = decoded;
		}
		
		navbar.logout = function(){
			removeToken();
			window.location = "/";
		}
		
		function removeToken(){
			let authToken = TokenStorage.clear();
		}
		
	}
})();