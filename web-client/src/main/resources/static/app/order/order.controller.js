(function() {
	'use strict';

	angular.module('restaurant').controller('OrderController', OrderController);

	OrderController.$inject = [ '$window','$scope', '$http', '$log', '$state', 'OrderService'];

	function OrderController($window, $scope, $http, $log, $state, OrderService) {
		$log.log("order controller");
		var order = this;
		order.data = {};
		
		var id = 1;
		
		init();
		
		function init(){
			getOrderById(id)
		}
		
		function getOrderById(id){
			OrderService.getById({id:id},
				function(result){
					$log.log("get order by id success");
					$log.log(result);
				},
				function(error){
					$log.log("get order by id error");
					$log.log(error);
				}
			);
		}
		
		
	}
})();
