function ContaDemonstrativoConsultaController($scope, $http) {
	var dataSet = [];

	$http({
		method : "GET",
		url : '/AcessoRestrito/rest/conta_demonstrativo/obterDados',
	})
			.success(
					function(data) {
						for (var i = 0; i < data.length; i++) {
							dataSet
									.push([
											"" + data[i].code_id + "",
											"" + data[i].code_paciente + "",
											"" + data[i].refe_referencia + "",
											"" + data[i].prip_descricao + "",
											"" + data[i].code_numero_guia + "",
											'<div class="btn-group"><button class="btn btn-xs green dropdown-toggle" type="button"data-toggle="dropdown" aria-expanded="false">Ações <i class="fa fa-angle-down"></i></button><ul class="dropdown-menu" role="menu"><li><a href="#!/ContaDemonstrativoAlteracao/'
													+ data[i].code_id
													+ '"><i class="fa fa-edit"></i> Editar</a></li><li><a href="#'
													+ data[i].code_id
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
								title : "Descricao"
							}, {
								title : "Guia"
							}, {
								title : "Ação"
							}, ]
						});

					});

};

function ContaDemonstrativoAlteracaoController($scope, $http, $routeParams, $location) {
	$scope.Conta;
	$scope.Referencia = {};
	$scope.CodigoProcedimento = {};
	
	$http({
		method : "GET",
		url : '/AcessoRestrito/rest/referencia',
	}).success(function(data) {
		$scope.Referencia = data;
		
	});
	
	$http({
		method : "GET",
		url : '/AcessoRestrito/rest/procedimento_ipasgo',
	}).success(function(data) {
		$scope.CodigoProcedimento = data;
		
	});

	$http({
		method : "GET",
		url : '/AcessoRestrito/rest/conta_demonstrativo/obterDadosId?id=' + $routeParams.id + '',
	}).success(function(data) {
		$scope.Conta = data;
		
		$scope.dataAtendimento = data.code_data_atendimento;
		
		var referencia = data.refe_referencia == null?"Selecione uma opcao":data.refe_referencia;
		
		$('#referencia').append(
				'<option selected="true" value="' + data.code_refe_id
						+ '">' + referencia + ' </option> ');
		
		var codigoProcedimento = data.prip_codigo == null?"Selecione uma opcao":data.prip_codigo;
		
		$('#codigoProcedimento').append(
				'<option selected="true" value="' + data.code_prip_codigo
						+ '">' + codigoProcedimento + ' </option> ');
	});

	$scope.Alterar = function() {

		var paciente = $("#paciente").val();
		var convenio = $("#convenio").val();
		var codeValor = $("#codeValor").val();
		var codeCarteira = $("#codeCarteira").val();
		var referencia = $("#referencia option:selected").val();
		var dataAtendimento = $("#dataAtendimento").val();
		var numeroGuia = $("#numeroGuia").val();
		var pripCodeCodigo = $("#codigoProcedimento option:selected").val();
		var codigo = $("#codeCodigo").val();
		
			var ContaDemonstrativo = {
				codeId : $routeParams.id,
				codeConvenio : convenio,
				codeCarteira : codeCarteira,
				codeNumeroGuia : numeroGuia,
				codePaciente : paciente,
				codeRefeId : referencia,
				codeValor : codeValor,
				codePripCodigo : pripCodeCodigo,
				codeCodigo : codigo
				
			}
			
			$http({
				method : "PUT",
				url : '/AcessoRestrito/rest/conta_demonstrativo/alterarConta?id='+ $routeParams.id + '&dataAtendimento='+dataAtendimento+'',
				data : ContaDemonstrativo,
			}).success(function(data) {
				$location.path("/ContaDemonstrativoConsulta");
				$('#showToastSucesso').click();
				

			});

		}

	
}
