(function() {
    'use strict';

    angular
        .module('springboot')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
    	$stateProvider.state('form', {
            parent: 'public',
            url: '/form',
            views: {
                'main@': {
                    templateUrl: '/form/form.html'
                }
            }
        });
    }
})();
