angular.module('qrda',
 [
    'ui.router',
    'oc.lazyLoad',
    'ngAria',
    'ngAnimate',
    'ngMessages',
    'ngMaterial',
    'toastr',
    'angularFileUpload'
   ]).config(function(toastrConfig,$ocLazyLoadProvider,$stateProvider,$urlRouterProvider){
    
	   angular.extend(toastrConfig, {
           allowHtml: false,
           closeButton: true,
           closeHtml: '<button>&times;</button>',
           extendedTimeOut: 1000,
           iconClasses: {
               error: 'toast-error',
               info: 'toast-info',
               success: 'toast-success',
               warning: 'toast-warning'
           },
           messageClass: 'toast-message',
           onHidden: null,
           onShown: null,
           onTap: null,
           progressBar: false,
           tapToDismiss: true,
           templates: {
               toast: 'directives/toast/toast.html',
               progressbar: 'directives/progressbar/progressbar.html'
           },
           preventDuplicates: false,
           preventOpenDuplicates: true,
           timeOut: 5000,
           titleClass: 'toast-title',
           toastClass: 'toast'
       });
	   
        $urlRouterProvider.otherwise('/qrda');
    
        $stateProvider.state('qrda',{
            url: '/qrda',
            templateUrl: 'src/app/qrda/qrda.html',
            controller:'qrdaCtrl',
            controllerAs:'qrdaCtrl',
            resolve:{
                dependancies:function($ocLazyLoad){
                    return $ocLazyLoad.load([
                        'src/app/qrda/qrdaCtrl.js',
                        'src/app/qrda/attachmentCtrl.js',
                        'src/app/qrda/qrdaFactory.js'
                    ]);
                }
            }
        });
 });

angular.module('qrda')
.run( function($rootScope, $state){
     $rootScope.$on('$stateChangeError', function (event, toState, toParams, fromState, fromParams, error,$http) {
          event.preventDefault();
          //return $state.go('error');
        });
});

