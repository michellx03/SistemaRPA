function ContaLerController($scope, $location, $http) {
	
	$scope.ler = function() {
	
		$http(
			{
				method : "POST",
				url : '/AcessoRestrito/rest/conta/lerArquivo',
			}).success(function(data) {
				$location.path("/Dashboard");
				$('#showToastSucesso').click();
			});
	};	
}