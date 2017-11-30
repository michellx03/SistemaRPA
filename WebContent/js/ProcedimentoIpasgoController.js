function ProcedimentosIpasgoConsultaController($scope, $http) {
	var dataSet = [];

	$http({
		method : "GET",
		url : '/AcessoRestrito/rest/procedimento_ipasgo',
	})
			.success(
					function(data) {
						for (var i = 0; i < data.length; i++) {
							dataSet
									.push([
											"" + data[i].pripId + "",
											"" + data[i].pripDescricao + "",
											"" + data[i].pripCodigo + "",
											"" + data[i].pripPorte + "",
											"" + data[i].pripAuxiliar + "",
											"" + data[i].pripValor + "",
											'<div class="btn-group"><button class="btn btn-xs green dropdown-toggle" type="button"data-toggle="dropdown" aria-expanded="false">Ações <i class="fa fa-angle-down"></i></button><ul class="dropdown-menu" role="menu"><li><a href="#!/ProcedimentoExcluir/'
											+ data[i].pripId
											+ '"><i class="fa fa-remove"></i> Excluir</a></li><li><a href="#!/ProcedimentoExcluir/'
											 ]);
						}
						$('#data_table_preencher').DataTable({
							data : dataSet,
							columns : [ {
								title : "ID"
							}, {
								title : "Descricao"
							}, {
								title : "Codigo"
							}, {
								title : "Porte"
							}, {
								title : "Auxiliar"
							}, {
								title : "Valor"
							}, {
								title : "Ação"
							}, ]
						});

					});

};

function ProcedimentoExcluirController($scope, $http, $routeParams, $location) {

	$http({
		method : "DELETE",
		url : '/AcessoRestrito/rest/procedimento_ipasgo?id=' + $routeParams.id + '',
	});

	$('#showToastSucesso').click();
	$location.path("/ProcedimentosIpasgo");

}