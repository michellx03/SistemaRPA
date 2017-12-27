function ConvenioConsultaController($scope, $http) {
	var dataSet = [];

	$http({
		method : "GET",
		url : '/AcessoRestrito/rest/convenio',
	})
			.success(
					function(data) {
						for (var i = 0; i < data.length; i++) {
							dataSet
									.push([
											"" + data[i].convId + "",
											"" + data[i].convConvenio + "",
											'<div class="btn-group"><button class="btn btn-xs green dropdown-toggle" type="button"data-toggle="dropdown" aria-expanded="false">Ações <i class="fa fa-angle-down"></i></button><ul class="dropdown-menu" role="menu"><li><a href="#!/ConvenioAlteracao/'
													+ data[i].convId
													+ '"><i class="fa fa-edit"></i> Editar</a></li><li><a href="#'
													+ data[i].convId
													]);
						}
						$('#data_table_preencher').DataTable({
							data : dataSet,
							columns : [ {
								title : "ID"
							}, {
								title : "Convenio"
							}, {
								title : "Ação"
							}, ]
						});

					});

};


function ConvenioCadastroController($scope, $http, $location) {

	$scope.Cadastrar = function() {
		var convenio = $scope.convenio;

		if (convenio != "" && convenio != undefined) {

			var Convenio = {
				convConvenio : convenio
			}

			$http({
				method : "POST",
				url : '/AcessoRestrito/rest/convenio',
				data : Convenio,
			}).success(function(data) {
				$('#showToastSucesso').click();
				$location.path("/ConvenioConsulta");

			});

		}

	}

};

function ConvenioAlteracaoController($scope, $http, $routeParams, $location) {

	$scope.Convenio;

	$http({
		method : "GET",
		url : '/AcessoRestrito/rest/convenio/obterDados?id=' + $routeParams.id + '',
	}).success(function(data) {
		$scope.Convenio = data;
	});

	$scope.Alterar = function() {
		var convenio = $scope.convenio;

		if (convenio != "" && convenio != undefined) {

			var Convenio = {
				convId : $routeParams.id,
				convConvenio : convenio
			}

			$http({
				method : "PUT",
				url : '/AcessoRestrito/rest/convenio',
				data : Convenio,
			}).success(function(data) {
				$('#showToastSucesso').click();
				$location.path("/ConvenioConsulta");

			});

		}

	}
};