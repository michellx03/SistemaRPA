function ContaAtendimentoConsultaController($scope, $http) {
	var dataSet = [];

	$http({
		method : "GET",
		url : '/AcessoRestrito/rest/conta/obterDados',
	})
			.success(
					function(data) {
						for (var i = 0; i < data.length; i++) {
							dataSet
									.push([
											"" + data[i].contaId + "",
											"" + data[i].contaPaciente + "",
											"" + data[i].refeReferencia + "",
											"" + data[i].contaConvenio + "",
											"" + data[i].contaNumeroGuia + "",
											"" + data[i].stpaStatus + "",
											'<div class="btn-group"><button class="btn btn-xs green dropdown-toggle" type="button"data-toggle="dropdown" aria-expanded="false">Ações <i class="fa fa-angle-down"></i></button><ul class="dropdown-menu" role="menu"><li><a href="#!/ContaAlteracao/'
													+ data[i].contaId
													+ '"><i class="fa fa-edit"></i> Editar</a></li><li><a href="#'
													+ data[i].contaId
													]);
						}
						$('#data_table_preencher').DataTable({
							data : dataSet,
							columns : [ {
								title : "ID"
							}, {
								title : "Paciente"
							}, {
								title : "Referencia"
							}, {
								title : "Convenio"
							}, {
								title : "Guia"
							}, {
								title : "Status"
							}, {
								title : "Ação"
							}, ]
						});

					});

};


function ContaAtendimentoAlteracaoController($scope, $http) {
	
}
