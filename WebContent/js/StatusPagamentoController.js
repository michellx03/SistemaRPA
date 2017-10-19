function StatusPagamentoConsultaController($scope, $http) {
	var dataSet = [];

	$http({
		method : "GET",
		url : '/AcessoRestrito/rest/status_pagamento',
	})
			.success(
					function(data) {
						for (var i = 0; i < data.length; i++) {
							dataSet
									.push([
											"" + data[i].stpaId + "",
											"" + data[i].stpaStatus + "",
											'<div class="btn-group"><button class="btn btn-xs green dropdown-toggle" type="button"data-toggle="dropdown" aria-expanded="false">Ações <i class="fa fa-angle-down"></i></button><ul class="dropdown-menu" role="menu"><li><a href="#!/StatusPagamentoAlteracao/'
													+ data[i].stpaId
													+ '"><i class="fa fa-edit"></i> Editar</a></li><li><a href="'
													+ data[i].stpaId
													+ '"'
													 ]);
						}
						$('#data_table_preencher').DataTable({
							data : dataSet,
							columns : [ {
								title : "ID"
							}, {
								title : "Status"
							}, {
								title : "Ação"
							}, ]
						});

					});

};


function StatusPagamentoAlteracaoController($scope, $http, $routeParams, $location) {

	$scope.Status;

	$http({
		method : "GET",
		url : '/AcessoRestrito/rest/status_pagamento/obterDados?id=' + $routeParams.id + '',
	}).success(function(data) {
		$scope.Status = data;
	});

	$scope.Alterar = function() {
		var status = $scope.status;

		if (status != "" && status != undefined) {

			var Status = {
				stpaId : $routeParams.id,
				stpaStatus : status
			}

			$http({
				method : "PUT",
				url : '/AcessoRestrito/rest/status_pagamento',
				data : Status,
			}).success(function(data) {
				$('#showToastSucesso').click();
				$location.path("/StatusPagamentoConsulta");

			});

		}

	}
};