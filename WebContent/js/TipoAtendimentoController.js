function TipoAtendimentoConsultaController($scope, $http) {
	var dataSet = [];

	$http({
		method : "GET",
		url : '/AcessoRestrito/rest/tipo_atendimento',
	})
			.success(
					function(data) {
						for (var i = 0; i < data.length; i++) {
							dataSet
									.push([
											"" + data[i].tiatId + "",
											"" + data[i].tiatTipo + "",
											'<div class="btn-group"><button class="btn btn-xs green dropdown-toggle" type="button"data-toggle="dropdown" aria-expanded="false">Ações <i class="fa fa-angle-down"></i></button><ul class="dropdown-menu" role="menu"><li><a href="#!/TipoAtendimentoAlteracao/'
													+ data[i].tiatId
													+ '"><i class="fa fa-edit"></i> Editar</a></li><li><a href="#'
													+ data[i].tiatId
													]);
						}
						$('#data_table_preencher').DataTable({
							data : dataSet,
							columns : [ {
								title : "ID"
							}, {
								title : "Tipo Atendimento"
							}, {
								title : "Ação"
							}, ]
						});

					});

};

function TipoAtendimentoAlteracaoController($scope, $http, $routeParams, $location) {

	$scope.TipoAtendimento;

	$http({
		method : "GET",
		url : '/AcessoRestrito/rest/tipo_atendimento/obterDados?id=' + $routeParams.id + '',
	}).success(function(data) {
		$scope.TipoAtendimento = data;
	});

	$scope.Alterar = function() {
		var tipoAtendimento = $scope.tipoAtendimento;

		if (tipoAtendimento != "" && tipoAtendimento != undefined) {

			var TipoAtendimento = {
				tiatId : $routeParams.id,
				tiatTipo : tipoAtendimento
			}

			$http({
				method : "PUT",
				url : '/AcessoRestrito/rest/tipo_atendimento',
				data : TipoAtendimento,
			}).success(function(data) {
				$('#showToastSucesso').click();
				$location.path("/TipoAtendimentoConsulta");

			});

		}

	}
};