(function() {
    'use strict';
    angular.module('restaurant', ['ui.router', 'ngResource', 'ngMessages']);
    
    angular.module('restaurant').config(function($httpProvider) {
		console.log("configuring $httpProvider....");
		$httpProvider.interceptors.push('TokenAuthInterceptor');
        delete $httpProvider.defaults.headers.common['X-Requested-With'];
	});
    
    angular.module('restaurant').run(function($rootScope, $transitions, $timeout, $log, $location, $window) {
    		$log.log("restaurant run(...)");
	    	$transitions.onSuccess({},function(transition){
	    		$log.log("transition success");
	    		$log.log("to "+transition.to().name);
	    		$log.log("from "+transition.from().name);
	    	});
    });
    
    
    
})();