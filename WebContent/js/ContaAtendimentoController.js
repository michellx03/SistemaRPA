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
											"" + data[i].streStatus + "",
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


function ContaAtendimentoAlteracaoController($scope, $http, $routeParams, $location) {
	$scope.Conta;
	$scope.Status = {};
	$scope.Referencia = {};
	
	$http({
		method : "GET",
		url : '/AcessoRestrito/rest/status_recebimento',
	}).success(function(data) {
		$scope.Status = data;
		
	});
	
	$http({
		method : "GET",
		url : '/AcessoRestrito/rest/referencia',
	}).success(function(data) {
		$scope.Referencia = data;
		
	});

	$http({
		method : "GET",
		url : '/AcessoRestrito/rest/conta/obterDadosId?id=' + $routeParams.id + '',
	}).success(function(data) {
		$scope.Conta = data;
		
		$scope.dataAtendimento = data.contaDataAtendimento;
		
		var status = data.streStatus == null?"Selecione uma opcao":data.streStatus;
		var referencia = data.refeReferencia == null?"Selecione uma opcao":data.refeReferencia;
		
		$('#status').append(
				'<option selected="true" value="' + data.contaStreId
						+ '">' + status + ' </option> ');
		
		$('#referencia').append(
				'<option selected="true" value="' + data.contaRefeId
						+ '">' + referencia + ' </option> ');
	});

	$scope.Alterar = function() {
		
		var paciente = $("#paciente").val();
		var convenio = $("#convenio").val();
		var tipoAtendimento = $("#atendimento").val();
		var medico = $("#medico").val();
		var referencia = $("#referencia option:selected").val();
		var dataAtendimento = $("#dataAtendimento").val();
		var status = $("#status option:selected").val();
		var numeroGuia = $("#numeroGuia").val();
		

			var Conta = {
				contaId : $routeParams.id,
				contaConvenio : convenio,
				contaMedico : medico,
				contaNumeroGuia : numeroGuia,
				contaPaciente : paciente,
				contaRefeId : referencia,
				contaStreId : status,
				contaTipoAtendimento : tipoAtendimento
				
			}
			
			$http({
				method : "PUT",
				url : '/AcessoRestrito/rest/conta/alterarConta?id='+ $routeParams.id + '&dataAtendimento='+dataAtendimento+'',
				data : Conta,
			}).success(function(data) {
				$('#showToastSucesso').click();
				$location.path("/ContaAtendimentoConsulta");

			});

		}

	
}
