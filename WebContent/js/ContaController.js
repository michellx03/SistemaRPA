function ContaLerController($scope, $location, $http) {
	
	$scope.ler = function() {
	
		$http(
			{
				method : "POST",
				url : '/AcessoRestrito/rest/conta/lerArquivo',
			}).success(function(data) {
				$location.path("/ContaLer");
				$('#showToastSucesso').click();
			});
	};	
	
	
	$scope.lerFaturamento = function() {
		
		$http(
			{
				method : "POST",
				url : '/AcessoRestrito/rest/conta_faturamento/lerArquivoFaturamento',
			}).success(function(data) {
				$location.path("/ContaLer");
				$('#showToastSucesso').click();
			});
	};	
	
	$scope.lerDemonstrativo = function() {
	
		$http(
			{
				method : "POST",
				url : '/AcessoRestrito/rest/conta_demonstrativo/lerArquivoDemonstrativo',
			}).success(function(data) {
				$location.path("/ContaLer");
				$('#showToastSucesso').click();
			});
	};	
}