
var productOrderingManagerModule = angular.module('productOrderingApp', ['ngAnimate']);

productOrderingManagerModule.controller('productOrderingController', function ($scope,$http) {
	var urlBase="";
	$scope.seeOrdersTable=false;
	$scope.toggle=true;
	$http.defaults.headers.post["Content-Type"] = "application/json";
 
 	function findAllCustomers() {
 		$http.get(urlBase + '/customers').
        success(function (data) {
            if (data._embedded != undefined) {
                $scope.customers = data._embedded.customers;
            } else {
                $scope.customers = [];
            }
        });
 	}
 	findAllCustomers();
 	
 	$scope.search = function search() {
 		
 		var date = new Date();
 		var year = date.getFullYear();
 		var month = date.getMonth() + 1;
 		var firstDay = new Date(date.getFullYear(), date.getMonth(), 1);
 		var lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0);
 		
 		if ($scope.customerSelected == undefined || $scope.customerSelected == null){
 		   alert("Por favor seleccione un cliente");
 		} else {
 			$http.get(urlBase + '/listar_ordenes'
 				+ '?id_cliente=' +  $scope.customerSelected.customerId
 				+ '&fecha_ini=' + firstDay.getDate() + '-' + month + '-' + year
 				+ '&fecha_fin=' + lastDay.getDate() + '-' + month + '-' + year
 			).success(function (data) {
 	            if (data.orders != undefined) {
 	            	$scope.orders = data.orders;
 	            	$scope.toggle='!toggle';
 	            	$scope.seeOrdersTable=true;
 	            } else {
 	            	$scope.orders = [];
 	            	$scope.toggle='toggle';
 	            	$scope.seeOrdersTable=false;
 	            } 	            
 	        });
 		}
 	};
 	

});