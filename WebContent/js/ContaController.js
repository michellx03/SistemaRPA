function ContaLerController($scope, $location, $http) {
	$scope.Referencia = {};
	
	$http({
		method : "GET",
		url : '/AcessoRestrito/rest/referencia',
	}).success(function(data) {
		$scope.Referencia = data;
		
	});
	
	$scope.ler = function() {
		
		var referencia = $("#referencia option:selected").val();
	
		$http(
			{
				method : "POST",
				url : '/AcessoRestrito/rest/conta/lerArquivo?referencia='+ referencia +'',
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
	
	$scope.lerProcedimentosIpasgo = function() {
		
		$http(
			{
				method : "POST",
				url : '/AcessoRestrito/rest/procedimento_ipasgo/lerProcedimentosIpasgo',
			}).success(function(data) {
				$location.path("/LerDemonstrativos");
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