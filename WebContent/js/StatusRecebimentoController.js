function StatusRecebimentoConsultaController($scope, $http) {
	var dataSet = [];

	$http({
		method : "GET",
		url : '/AcessoRestrito/rest/status_recebimento',
	})
			.success(
					function(data) {
						for (var i = 0; i < data.length; i++) {
							dataSet
									.push([
											"" + data[i].streId + "",
											"" + data[i].streStatus + "",
											'<div class="btn-group"><button class="btn btn-xs green dropdown-toggle" type="button"data-toggle="dropdown" aria-expanded="false">Ações <i class="fa fa-angle-down"></i></button><ul class="dropdown-menu" role="menu"><li><a href="#!/StatusRecebimentoAlteracao/'
													+ data[i].streId
													+ '"><i class="fa fa-edit"></i> Editar</a></li><li><a href="'
													+ data[i].streId
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


function StatusRecebimentoAlteracaoController($scope, $http, $routeParams, $location) {

	$scope.Status;

	$http({
		method : "GET",
		url : '/AcessoRestrito/rest/status_recebimento/obterDados?id=' + $routeParams.id + '',
	}).success(function(data) {
		$scope.Status = data;
	});

	$scope.Alterar = function() {
		var status = $scope.status;

		if (status != "" && status != undefined) {

			var Status = {
				streId : $routeParams.id,
				streStatus : status
			}

			$http({
				method : "PUT",
				url : '/AcessoRestrito/rest/status_recebimento',
				data : Status,
			}).success(function(data) {
				$('#showToastSucesso').click();
				$location.path("/StatusRecebimentoConsulta");

			});

		}

	}
};