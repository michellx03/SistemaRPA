function ReferenciaConsultaController($scope, $http) {
	var dataSet = [];

	$http({
		method : "GET",
		url : '/AcessoRestrito/rest/referencia',
	})
			.success(
					function(data) {
						for (var i = 0; i < data.length; i++) {
							dataSet
									.push([
											"" + data[i].refeId + "",
											"" + data[i].refeReferencia + "",
											'<div class="btn-group"><button class="btn btn-xs green dropdown-toggle" type="button"data-toggle="dropdown" aria-expanded="false">Ações <i class="fa fa-angle-down"></i></button><ul class="dropdown-menu" role="menu"><li><a href="#!/ReferenciaAlteracao/'
											+ data[i].refeId
											+ '"><i class="fa fa-edit"></i> Editar</a></li><li><a href="#!/ReferenciaVisualizar/'
											+ data[i].refeId
											+ '"'
											+ '><i class="fa fa-remove"></i> Excluir</a></li></ul></div>' ]);
						}
						$('#data_table_preencher').DataTable({
							data : dataSet,
							columns : [ {
								title : "ID"
							}, {
								title : "Referencia"
							}, {
								title : "Ação"
							}, ]
						});

					});

};

function ReferenciaCadastroController($scope, $http, $location) {

	$scope.ListaAno;

	$http({
		method : "GET",
		url : '/AcessoRestrito/rest/ano',
	}).success(function(data) {
		$scope.ListaAno = data;
	});
	
	
	$scope.Cadastrar = function() {
		var referencia = $scope.referencia;
		
		var refeAno = $("#single option:selected").val();

		if (referencia != "" && referencia != undefined) {

			var Referencia = {
					refeReferencia : referencia,
					refeStatus : 2,
					refeAno : refeAno
			}

			$http({
				method : "POST",
				url : '/AcessoRestrito/rest/referencia',
				data : Referencia,
			}).success(function(data) {
				$('#showToastSucesso').click();
				$location.path("/ReferenciaConsulta");

			});

		}

	}
};


function ReferenciaAlteracaoController($scope, $http, $routeParams, $location) {

	$scope.Referencia;
	$scope.ListaAno;
	$scope.Status;

	$http({
		method : "GET",
		url : '/AcessoRestrito/rest/referencia/obterDados?id=' + $routeParams.id + '',
	}).success(function(data) {
		$scope.Referencia = data;
		$scope.referencia = data.refeReferencia;
		
		$('#single').append(
				'<option selected="true" value="' + data.anoId
						+ '">' + data.anoAno + ' </option> ');
		
		$('#single1').append(
				'<option selected="true" value="' + data.statId
						+ '">' + data.statStatus + ' </option> ');
	});

	$http({
		method : "GET",
		url : '/AcessoRestrito/rest/ano',
	}).success(function(data) {
		$scope.ListaAno = data;
		
	});
	
	$http({
		method : "GET",
		url : '/AcessoRestrito/rest/status',
	}).success(function(data) {
		$scope.Status = data;
		
	});

	$scope.Alterar = function() {
		var referencia = $scope.referencia;
		var refeAno = $("#single option:selected").val();
		var refeStatus = $("#single1 option:selected").val();

		if (referencia != "" && referencia != undefined) {

			var Referencia = {
					refeId : $routeParams.id,
					refeReferencia : referencia,
					refeAno : refeAno,
					refeStatus: refeStatus
			}

			$http({
				method : "PUT",
				url : '/AcessoRestrito/rest/referencia',
				data : Referencia,
			}).success(function(data) {
				$('#showToastSucesso').click();
				$location.path("/ReferenciaConsulta");

			});

		}

	}
};


