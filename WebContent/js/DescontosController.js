function DescontosConsultaController($scope, $http) {
	var dataSet = [];

	$http({
		method : "GET",
		url : '/AcessoRestrito/rest/descontos',
	})
			.success(
					function(data) {
						for (var i = 0; i < data.length; i++) {
							dataSet
									.push([
											"" + data[i].descId + "",
											"" + data[i].descConvenio + "",
											"" + data[i].descValor + "",
											'<div class="btn-group"><button class="btn btn-xs green dropdown-toggle" type="button"data-toggle="dropdown" aria-expanded="false">Ações <i class="fa fa-angle-down"></i></button><ul class="dropdown-menu" role="menu"><li><a href="#!/DescontosAlteracao/'
													+ data[i].descId
													+ '"><i class="fa fa-edit"></i> Editar</a></li><li><a href="#!/DescontosExcluir/'
													+ data[i].descId
													+ '"'
													+ '><i class="fa fa-remove"></i> Excluir</a></li></ul></div>' ]);
						}
						$('#data_table_preencher').DataTable({
							data : dataSet,
							columns : [ {
								title : "ID"
							}, {
								title : "Descricao"
							},{
								title : "Valor"
							},{
								title : "Ação"
							}, ]
						});

					});

};


function descontosCadastroController($scope, $http, $location) {

	$scope.Cadastrar = function() {
		var convenio = $scope.convenio;
		var valor = $scope.valor;

		if (convenio != "" && convenio != undefined && valor != "" && valor != undefined) {

			var Desconto = {
				descConvenio : convenio,
				descValor : valor
			}

			$http({
				method : "POST",
				url : '/AcessoRestrito/rest/descontos',
				data : Desconto,
			}).success(function(data) {
				$('#showToastSucesso').click();
				$location.path("/DescontosConsulta");

			});

		}

	}

};

function DescontosExcluirController($scope, $http, $routeParams, $location) {

	$http({
		method : "DELETE",
		url : '/AcessoRestrito/rest/descontos?id=' + $routeParams.id + '',
	});

	$('#showToastSucesso').click();
	$location.path("/DescontosConsulta");

}

function DescontosAlteracaoController($scope, $http, $routeParams, $location) {

	$scope.Desconto;

	$http({
		method : "GET",
		url : '/AcessoRestrito/rest/descontos/obterDados?id=' + $routeParams.id + '',
	}).success(function(data) {
		$scope.Desconto = data;
	});

	$scope.Alterar = function() {
		var convenio = $scope.convenio;
		var valor = $scope.valor;

			var Desconto = {
				descId : $routeParams.id,
				descConvenio : convenio,
				descValor : valor
			}

			$http({
				method : "PUT",
				url : '/AcessoRestrito/rest/descontos',
				data : Desconto,
			}).success(function(data) {
				$('#showToastSucesso').click();
				$location.path("/DescontosConsulta");

			});

	}
};