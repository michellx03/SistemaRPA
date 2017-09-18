


function RelatorioResultadoController($scope, $http, $routeParams, $location,$cookieStore) {
	
	
	var data = new Date();
	var responsavelEmissao = "Relatório emitido por ?1 no dia ?2 às ?3";
	responsavelEmissao = responsavelEmissao.replace("?1",$cookieStore.get('user'));
	responsavelEmissao = responsavelEmissao.replace("?2",""+data.getDate()+"/"+(data.getMonth()+1)+"/"+data.getFullYear()+"")
	responsavelEmissao = responsavelEmissao.replace("?3",""+data.getHours()+":"+data.getMinutes()+"");
	

	$scope.frase = responsavelEmissao;
	$scope.parametroAno = $routeParams.ano;
	$scope.AnoResultado = {};

	if($routeParams.ano == "undefined"){
		$scope.ano_filtro = false;
		
		
	}else{
		$scope.ano_filtro = true;
		
	}
	$http({
		method : "GET",
		url : '/AcessoRestrito/rest/ano/obterDadosRelatorio?ano=' + $routeParams.ano + '',
	}).success(function(data) {
		$scope.AnoResultado  = data;
	});
	
	

};



function AnoRelatorioFiltroController($scope, $routeParams, $location) {

	
	
	
	$scope.Filtrar = function(){
		
		
		
		$location.path("/RelatorioResultado/"+$scope.ano+"");
	}
	

};


function AnoVisualizarController($scope, $http, $routeParams, $location) {

	$scope.Ano;

	$http({
		method : "GET",
		url : '/AcessoRestrito/rest/ano/obterDados?id=' + $routeParams.id + '',
	}).success(function(data) {
		$scope.Ano = data;
	});

};

function AnoListagemController($scope, $http, $location) {

	$scope.ListaAno = {}

	$http({
		method : "GET",
		url : '/AcessoRestrito/rest/ano',
	}).success(function(data) {
		$scope.ListaAno = data;
	});

};

function AnoAlteracaoController($scope, $http, $routeParams, $location) {

	$scope.Ano;

	$http({
		method : "GET",
		url : '/AcessoRestrito/rest/ano/obterDados?id=' + $routeParams.id + '',
	}).success(function(data) {
		$scope.Ano = data;
	});

	$scope.Alterar = function() {
		var ano = $scope.ano;

		if (ano != "" && ano != undefined) {

			var Ano = {
				anoId : $routeParams.id,
				anoAno : ano
			}

			$http({
				method : "PUT",
				url : '/AcessoRestrito/rest/ano',
				data : Ano,
			}).success(function(data) {
				$('#showToastSucesso').click();
				$location.path("/AnoConsulta");

			});

		}

	}
};

function AnoExcluirController($scope, $http, $routeParams, $location) {

	$http({
		method : "DELETE",
		url : '/AcessoRestrito/rest/ano?id=' + $routeParams.id + '',
	});

	$('#showToastSucesso').click();
	$location.path("/AnoConsulta");

}

function AnoCadastroController($scope, $http, $location) {

	$scope.Cadastrar = function() {
		var ano = $scope.ano;

		if (ano != "" && ano != undefined) {

			var Ano = {
				anoAno : ano
			}

			$http({
				method : "POST",
				url : '/AcessoRestrito/rest/ano',
				data : Ano,
			}).success(function(data) {
				$('#showToastSucesso').click();
				$location.path("/AnoConsulta");

			});

		}

	}

};

function AnoConsultaController($scope, $http) {
	var dataSet = [];

	$http({
		method : "GET",
		url : '/AcessoRestrito/rest/ano',
	})
			.success(
					function(data) {
						for (var i = 0; i < data.length; i++) {
							dataSet
									.push([
											"" + data[i].anoId + "",
											"" + data[i].anoAno + "",
											'<div class="btn-group"><button class="btn btn-xs green dropdown-toggle" type="button"data-toggle="dropdown" aria-expanded="false">Ações <i class="fa fa-angle-down"></i></button><ul class="dropdown-menu" role="menu"><li><a href="#!/AnoAlteracao/'
													+ data[i].anoId
													+ '"><i class="fa fa-edit"></i> Editar</a></li><li><a href="#!/AnoVisualizar/'
													+ data[i].anoId
													+ '"><i class="fa fa-search"></i> Visualizar</a></li><li><a  href="#!/AnoExcluir/'
													+ data[i].anoId
													+ '"'
													+ '><i class="fa fa-remove"></i> Excluir</a></li></ul></div>' ]);
						}
						$('#data_table_preencher').DataTable({
							data : dataSet,
							columns : [ {
								title : "ID"
							}, {
								title : "Ano"
							}, {
								title : "Ação"
							}, ]
						});

					});

};

