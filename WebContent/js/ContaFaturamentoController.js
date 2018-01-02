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
											"" + data[i].medi_nome + "",
											"" + data[i].stpa_status + "",
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
								title : "Medico"
							}, {
								title : "Pago?"
							}, {
								title : "Ação"
							}, ]
						});

					});

};

function ContaFaturamentoAlteracaoController($scope, $http, $routeParams, $location) {
	$scope.Conta;
	$scope.Referencia = {};
	$scope.Medicos = {};
	$scope.StatusPagamento = {};
	$scope.TipoAtendimento = {};
	
	$scope.descricao = false;
	
	$http({
		method : "GET",
		url : '/AcessoRestrito/rest/tipo_atendimento',
	}).success(function(data) {
		$scope.TipoAtendimento = data;
	});
	
	$http({
		method : "GET",
		url : '/AcessoRestrito/rest/referencia',
	}).success(function(data) {
		$scope.Referencia = data;
	});
	
	$http({
		method : "GET",
		url : '/AcessoRestrito/rest/medicos',
	}).success(function(data) {
		$scope.Medicos = data;
	});
	
	$http({
		method : "GET",
		url : '/AcessoRestrito/rest/status_pagamento',
	}).success(function(data) {
		$scope.StatusPagamento = data;
	});
	
	$http({
		method : "GET",
		url : '/AcessoRestrito/rest/conta_faturamento/obterDadosId?id=' + $routeParams.id + '',
	}).success(function(data) {
		$scope.Conta = data;
		
		$scope.dataAtendimento = data.contaDataAtendimento;
		
		var referencia = data.refe_referencia == null?"Selecione uma opcao":data.refe_referencia;
		var medico = data.medi_nome == null?"Selecione uma opcao":data.medi_nome;
		var status = data.stpa_status == null?"Selecione uma opcao":data.stpa_status;
		var tipoAtendimento = data.tiat_tipo == null?"Selecione uma opcao":data.tiat_tipo;
		
		$('#status').append(
				'<option selected="true" value="' + data.cofa_status_pagamento
						+ '">' + status+ ' </option> ');
		
		$('#atendimento').append(
				'<option selected="true" value="' + data.cofa_tipo_atendimento
						+ '">' + tipoAtendimento+ ' </option> ');
		
		$('#medico').append(
				'<option selected="true" value="' + data.cofa_medico
						+ '">' + medico + ' </option> ');
		
		$('#referencia').append(
				'<option selected="true" value="' + data.cofa_refe_id
						+ '">' + referencia + ' </option> ');
	});

	$scope.Alterar = function() {
		
		var paciente = $("#paciente").val();
		var numeroGuia = $("#numeroGuia").val();
		var tipoAtendimento = $("#atendimento option:selected").val();
		var referencia = $("#referencia option:selected").val();
		var medico = $("#medico option:selected").val();
		var matricula = $("#matricula").val();
		var valor = $("#valor").val();
		var dataAtendimento = $("#dataAtendimento").val();	
		var status = $("#status option:selected").val();
		var descricaoAtendimento = $("#descricaoAtendimento").val();

			var ContaFaturamento = {
				cofaId : $routeParams.id,
				cofaPaciente : paciente,
				cofaNumeroGuia : numeroGuia,
				cofaTipoAtendimento : tipoAtendimento,
				cofaRefeId : referencia,
				cofaMedico : medico,
				cofaMatriculla : matricula,
				cofaValor : valor,
				cofaStatusPagamento : status,
				cofaDescricaoAtendimento : descricaoAtendimento
				
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