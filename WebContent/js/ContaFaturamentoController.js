function ContaFaturamentoConsultaController($scope, $http) {
	var dataSet = [];

	$http({
		method : "GET",
		url : '/AcessoRestrito/rest/conta_faturamento/obterDados',
	})
			.success(
					function(data) {
						for (var i = 0; i < data.length; i++) {
							dataSet
									.push([
											"" + data[i].cofa_id + "",
											"" + data[i].cofa_paciente + "",
											"" + data[i].refe_referencia + "",
											"" + data[i].cofa_data_atendimento+ "",
											"" + data[i].cofa_numero_guia + "",
											"" + data[i].cofa_tipo_atendimento + "",
											'<div class="btn-group"><button class="btn btn-xs green dropdown-toggle" type="button"data-toggle="dropdown" aria-expanded="false">Ações <i class="fa fa-angle-down"></i></button><ul class="dropdown-menu" role="menu"><li><a href="#!/ContaFaturmanetoAlteracao/'
													+ data[i].cofa_id
													+ '"><i class="fa fa-edit"></i> Editar</a></li><li><a href="#'
													+ data[i].cofa_id
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
								title : "Data Atendimento"
							}, {
								title : "Guia"
							}, {
								title : "Tipo atendimento"
							}, {
								title : "Ação"
							}, ]
						});

					});

};

function ContaFaturamentoAlteracaoController($scope, $http, $routeParams, $location) {
	$scope.Conta;
	$scope.Referencia = {};
	
	$http({
		method : "GET",
		url : '/AcessoRestrito/rest/referencia',
	}).success(function(data) {
		$scope.Referencia = data;
		
	});

	$http({
		method : "GET",
		url : '/AcessoRestrito/rest/conta_faturamento/obterDadosId?id=' + $routeParams.id + '',
	}).success(function(data) {
		$scope.Conta = data;
		
		$scope.dataAtendimento = data.contaDataAtendimento;
		
		var referencia = data.refe_referencia == null?"Selecione uma opcao":data.refe_referencia;
		
		$('#referencia').append(
				'<option selected="true" value="' + data.cofa_refe_id
						+ '">' + referencia + ' </option> ');
	});

	$scope.Alterar = function() {
		
		var paciente = $("#paciente").val();
		var numeroGuia = $("#numeroGuia").val();
		var tipoAtendimento = $("#atendimento").val();
		var referencia = $("#referencia option:selected").val();
		var medico = $("#medico").val();
		var matricula = $("#matricula").val();
		var valor = $("#valor").val();
		var dataAtendimento = $("#dataAtendimento").val();		

			var ContaFaturamento = {
				cofaId : $routeParams.id,
				cofaPaciente : paciente,
				cofaNumeroGuia : numeroGuia,
				cofaTipoAtendimento : tipoAtendimento,
				cofaRefeId : referencia,
				cofaMedico : medico,
				cofaMatriculla : matricula,
				cofaValor : valor
				
			}
			
			$http({
				method : "PUT",
				url : '/AcessoRestrito/rest/conta_faturamento/alterarConta?id='+ $routeParams.id + '&dataAtendimento='+dataAtendimento+'',
				data : ContaFaturamento,
			}).success(function(data) {
				$('#showToastSucesso').click();
				$location.path("/ContaFaturamentoConsulta");

			});

		}

	
}