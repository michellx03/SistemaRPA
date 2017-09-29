function StatusConsultaController($scope, $http) {
	var dataSet = [];

	$http({
		method : "GET",
		url : '/AcessoRestrito/rest/status',
	})
			.success(
					function(data) {
						for (var i = 0; i < data.length; i++) {
							dataSet
									.push([
											"" + data[i].statId + "",
											"" + data[i].statStatus + "",
											'<div class="btn-group"><button class="btn btn-xs green dropdown-toggle" type="button"data-toggle="dropdown" aria-expanded="false">Ações <i class="fa fa-angle-down"></i></button><ul class="dropdown-menu" role="menu"><li><a href="#!/StatusAlteracao/'
													+ data[i].statId
													+ '"><i class="fa fa-edit"></i> Editar</a></li><li><a href="#!/AnoVisualizar/'
													+ data[i].statId
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


function StatusAlteracaoController($scope, $http, $routeParams, $location) {

	$scope.Status;

	$http({
		method : "GET",
		url : '/AcessoRestrito/rest/status/obterDados?id=' + $routeParams.id + '',
	}).success(function(data) {
		$scope.Status = data;
	});

	$scope.Alterar = function() {
		var status = $scope.status;

		if (status != "" && status != undefined) {

			var Status = {
				statId : $routeParams.id,
				statStatus : status
			}

			$http({
				method : "PUT",
				url : '/AcessoRestrito/rest/status',
				data : Status,
			}).success(function(data) {
				$('#showToastSucesso').click();
				$location.path("/StatusConsulta");

			});

		}

	}
};