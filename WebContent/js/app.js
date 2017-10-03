angular
		.module(
				'exampleApp',
				[ 'ngRoute', 'ngCookies', 'exampleApp.services', 'oc.lazyLoad' ])
		.config(
				[
						'$routeProvider',
						'$locationProvider',
						'$httpProvider',

						function($routeProvider, $locationProvider,
								$httpProvider) {

							$routeProvider.when('/UsuarioConsulta', {
								templateUrl : 'partials/Usuario/Consulta.html',
								controller : UsuarioConsultaController,

							});
							$routeProvider.when('/ReferenciaConsulta', {
								templateUrl : 'partials/Referencia/Consulta.html',
								controller : ReferenciaConsultaController,

							});
							$routeProvider.when('/StatusConsulta', {
								templateUrl : 'partials/Status/Consulta.html',
								controller : StatusConsultaController,

							});
							$routeProvider.when('/ContaLer', {
								templateUrl : 'partials/Conta/Ler.html',
								controller : ContaLerController,

							});
							$routeProvider
									.when(
											'/UsuarioAlteracao/:id',
											{
												templateUrl : 'partials/Usuario/Alteracao.html',
												controller : UsuarioAlteracaoController,

											});
							$routeProvider
							.when(
									'/ReferenciaAlteracao/:id',
									{
										templateUrl : 'partials/Referencia/Alteracao.html',
										controller : ReferenciaAlteracaoController,

									});
							$routeProvider.when('/UsuarioCadastro', {
								templateUrl : 'partials/Usuario/Cadastro.html',
								controller : UsuarioCadastroController,

							});
							$routeProvider.when('/ReferenciaCadastro', {
								templateUrl : 'partials/Referencia/Cadastro.html',
								controller : ReferenciaCadastroController,

							});
							$routeProvider
									.when(
											'/ControleAcessoConsulta',
											{
												templateUrl : 'partials/ControleAcesso/Consulta.html',
												controller : AcessoConsultaController,

											});
							$routeProvider.when('/AnoConsulta', {
								templateUrl : 'partials/Ano/Consulta.html',
								controller : AnoConsultaController,

							});
							$routeProvider.when('/AnoCadastro', {
								templateUrl : 'partials/Ano/Cadastro.html',
								controller : AnoCadastroController,

							});
							$routeProvider.when('/AnoVisualizar/:id', {
								templateUrl : 'partials/Ano/Visualizar.html',
								controller : AnoVisualizarController,

							});
							$routeProvider.when('/AnoListagem', {
								templateUrl : 'partials/Ano/Listagem.html',
								controller : AnoListagemController,

							});
							$routeProvider.when('/AnoAlteracao/:id', {
								templateUrl : 'partials/Ano/Alteracao.html',
								controller : AnoAlteracaoController,

							});
							$routeProvider.when('/StatusAlteracao/:id', {
								templateUrl : 'partials/Status/Alteracao.html',
								controller : StatusAlteracaoController,

							});
							$routeProvider.when('/AnoExcluir/:id', {
								template : '<div></div>',
								controller : AnoExcluirController,

							});
							
							$routeProvider.when('/AnoRelatorioFiltro', {
								templateUrl : 'partials/Ano/RelatorioFiltro.html',
								controller : AnoRelatorioFiltroController,

							});
							$routeProvider.when('/RelatorioResultado/:ano', {
								templateUrl : 'partials/Ano/RelatorioResultado.html',
								abstract: true,
								controller : RelatorioResultadoController,

							});
							

							$routeProvider
									.when(
											'/ControleAcessoAlteracao/:id',
											{
												templateUrl : 'partials/ControleAcesso/Alteracao.html',
												controller : ControleAcessoAlteracaoController,

											});

							$routeProvider.when('/login', {
								templateUrl : 'partials/login/formLogin.html',
								controller : LoginController
							});

							$routeProvider.when('/Dashboard', {
								template : '<div></div> ' + '</div> </div>',
								controller : DashboardController
							});

							$routeProvider.when('/DashboardIndex', {
								template : '<div></div>',
								controller : DashboardIndexController
							});

							$routeProvider.otherwise({
								template : '<div></div>',
								controller : IndexController
							});

							$locationProvider.hashPrefix('!');

							$httpProvider.interceptors
									.push(function($q, $rootScope, $location) {
										return {
											'responseError' : function(
													rejection) {
												var status = rejection.status;
												var config = rejection.config;
												var method = config.method;
												var url = config.url;

												if (status == 401) {

													window.location.href = '/AcessoRestrito/login.html#!/login'

												} else {
													$rootScope.error = method
															+ " on "
															+ url
															+ " failed with status "
															+ status;

												}

												return $q.reject(rejection);
											}
										};
									});

							$httpProvider.interceptors
									.push(function($q, $rootScope, $location) {
										return {
											'request' : function(config) {

												var isRestCall = config.url
														.indexOf('/AcessoRestrito/rest') == 0;
												if (isRestCall
														&& angular
																.isDefined($rootScope.accessToken)) {
													var accessToken = $rootScope.accessToken;
													if (exampleAppConfig.useAccessTokenHeader) {
														config.headers['X-Access-Token'] = accessToken;
													} else {

														config.url = config.url
																+ "?token="
																+ accessToken;
													}
												}

												return config
														|| $q.when(config);
											}
										};
									});

						} ]

		).run(function($rootScope, $location, $cookieStore, UserService) {
			$rootScope.$on('$viewContentLoaded', function() {
				delete $rootScope.error;
			});

			$rootScope.hasRole = function(role) {

				if ($rootScope.user === undefined) {
					return false;
				}

				if ($rootScope.user.roles[role] === undefined) {
					return false;
				}

				return $rootScope.user.roles[role];
			};

			$rootScope.logout = function() {
				delete $rootScope.user;
				delete $rootScope.accessToken;
				$cookieStore.remove('accessToken');
				$cookieStore.remove('user');
				window.location.href = '/AcessoRestrito/login.html#!/login'

			};

			var originalPath = $location.path();

			var accessToken = $cookieStore.get('accessToken');

			if (accessToken !== undefined) {

				$rootScope.accessToken = accessToken;
				UserService.get(function(user) {
					$rootScope.user = user;
					$location.path(originalPath);

				});

			} else {
				window.location.href = '/AcessoRestrito/login.html#!/login'

			}

			$rootScope.initialized = true;
		}).run(
				function($rootScope, $cookieStore, $http) {
					$rootScope.$on('$routeChangeSuccess', function(e, current,
							pre) {

						if ($cookieStore.get('user') != undefined) {
							var url = window.location.hash;
							url = url.replace('#!', '');
							$http({
								method : "POST",
								url : '/AcessoRestrito/rest/auditoria?user='
										+ $cookieStore.get('user') + '&url='
										+ url + ''
							});
						}
						// console.log(current.originalPath); // Do not use
						// $$route here it is private
					});
				});

function DashboardIndexController($scope, $cookieStore) {
	window.location.href = '/AcessoRestrito/index.html#!/Dashboard'
}

function DashboardController($scope) {

}

function IndexController($scope, $cookieStore) {

}