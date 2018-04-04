(function() {
    'use strict';

    angular
        .module('restaurant')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider','$urlRouterProvider'];

    function stateConfig($stateProvider, $urlRouterProvider) {
    	$stateProvider
    	.state('public', {
            abstract: true,
            views: {
		    	
            }
        })
    	
    	$urlRouterProvider.otherwise('/');
    }
})();