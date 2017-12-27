function MedicosConsultaController($scope, $http) {
	var dataSet = [];

	$http({
		method : "GET",
		url : '/AcessoRestrito/rest/medicos',
	})
			.success(
					function(data) {
						for (var i = 0; i < data.length; i++) {
							dataSet
									.push([
											"" + data[i].mediId + "",
											"" + data[i].mediNome + "",
											"" + data[i].mediCrm + "",
											"" + data[i].mediEspecialidade + "",
											'<div class="btn-group"><button class="btn btn-xs green dropdown-toggle" type="button"data-toggle="dropdown" aria-expanded="false">Ações <i class="fa fa-angle-down"></i></button><ul class="dropdown-menu" role="menu"><li><a href="#!/MedicosAlteracao/'
													+ data[i].mediId
													+ '"><i class="fa fa-edit"></i> Editar</a></li><li><a href="#!/MedicosExcluir/'
													+ data[i].mediId
													+ '"'
													+ '><i class="fa fa-remove"></i> Excluir</a></li></ul></div>' ]);
						}
						$('#data_table_preencher').DataTable({
							data : dataSet,
							columns : [ {
								title : "ID"
							}, {
								title : "Nome"
							},{
								title : "CRM"
							},{
								title : "Especialidade"
							},{
								title : "Ação"
							}, ]
						});

					});

};

function MedicosCadastroController($scope, $http, $location) {
	$scope.Descontos;
	$scope.Medicos;
	
	$http({
		method : "GET",
		url : '/AcessoRestrito/rest/medicos',
	}).success(function(data) {
		$scope.Medicos = data;
	});

	$scope.Cadastrar = function() {
		
		var medico = $scope.medico;
		var crm = $scope.crm;
		var especialidade = $scope.especialidade;
		var cpf = $scope.cpf;

			var Medico = {
				mediCpf : cpf,
				mediCrm : crm,
				mediEspecialidade : especialidade,
				mediNome : medico
			}

			$http({
				method : "POST",
				url : '/AcessoRestrito/rest/medicos',
				data : Medico,
			}).success(function(data) {
				$('#showToastSucesso').click();
				$location.path("/MedicosConsulta");

			});
	}

};

function MedicosExcluirController($scope, $http, $routeParams, $location) {

	$http({
		method : "DELETE",
		url : '/AcessoRestrito/rest/medicos?id=' + $routeParams.id + '',
	});

	$('#showToastSucesso').click();
	$location.path("/MedicosConsulta");

}

function MedicoAlteracaoController($scope, $http, $routeParams, $location) {
	$scope.Descontos;
	$scope.Medicos;

	$http({
		method : "GET",
		url : '/AcessoRestrito/rest/medicos/obterDados?id=' + $routeParams.id + '',
	}).success(function(data) {
		$scope.Medicos = data;
		
		$scope.medico = data.mediNome;
		$scope.crm = data.mediCrm;
		$scope.especialidade = data.mediEspecialidade;
		$scope.cpf = data.mediCpf;
		
	});

	$http({
		method : "GET",
		url : '/AcessoRestrito/rest/descontos',
	}).success(function(data) {
		$scope.Descontos = data;
		
	});

	$scope.Alterar = function() {
		var medico = $scope.medico;
		var crm = $scope.crm;
		var especialidade = $scope.especialidade;
		var cpf = $scope.cpf;

			var Medico = {
					mediId : $routeParams.id,
					mediNome : medico,
					mediCrm : crm,
					mediEspecialidade : especialidade,
					mediCpf : cpf
			}

			$http({
				method : "PUT",
				url : '/AcessoRestrito/rest/medicos',
				data : Medico,
			}).success(function(data) {
				$('#showToastSucesso').click();
				$location.path("/MedicosConsulta");

			});
	}
};