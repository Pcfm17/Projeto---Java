[Clique aqui para ver o vídeo no youtube](https://youtu.be/dKw9Lv_I824)
Vamos começar com o arquivo controller:
Para não ficar muito repetitivo, nas primeiras linhas tendo as importações para ter acesso e o movimento entre os arquivos.

1. ControleAlimento.java :
   Tem um campo privado que guarda referencia para a view (Alimento).
   Criando um construtor dessa variável privada.
   Tem uma método público buscarInformacoesAlimento recebendo a String nomeAlimento, nisso temos o if que checa se a String é vazia (null) ou vazia após trim() (remove espaços iniciais/finais).
   No "JOptionPane.showMessageDialog(view, "...");" informa ao usário que precisa digitar algo. O return interrompe o método fazendo com que não tenha a busca caso ocorra uma falha na verificação.
   O try está sendo usado para caso de erro e para não ocorrer vazamento de conexão, pois quando try fechar a conexão também fecha e o que está no meio que seria assim try(...), nessa parte ecta fazendo a conexão com o banco de dados do pgAdmin.
   Está sendo feito um objeto DAO que fará as operações no banco de dados.
   Caso encontre no banco de dados irá aparecer no textArea da view contendo o nome do alimento e as informações e caso não encnotrar o alimento aparece no textArea o alimento não encontrado uma sujestão pois pode ser um erro de digitação ou esse alimento pode não ter no banco de dados. No cath é capturado qualquer tipo de execução que ocorra durante a conexão, execução da query ou manipulação de objetos.
   No "JOptionPane.showMessageDialog(...)" exibe um erro para o usuário e no "e.getMessage()" dá uma breve descrição do erro que está tendo.
   No "e.printStackTrace();" é imprimido o trace da exceção no console que é bem útil para debug ou erros que está em desenvolvimento.
   
2. ControleAvaliacao.java :
   Tem um campo privado que guarda referencia para a tela3 que está na view (Avaliacao).
   Criando um construtor dessa variável privada.
   Tem uma função que se chama salvarAvaliacao recebendo um variavel do tipo String, faz com que salva as resposta das avaliações no banco de dados na tabela avaliacao, tendo 3 variavei importantes sendo (email(salva pelo email), nota(Sendo um operador ternário, aninhado:quantas estrelas vc esta dando para o programa), descricao(Pega o que está escrito no textArea e salva a descricao pessoal do usuário do que ele achou do programa).
   Tendo uma verificação (if) do isEmpty() faz com que verifica se tem algum campo vazio, se estiver vazio faz com que exibe uma menssagem que fala que precisa preencher todos os campos e o return interrompe a execução do método.
   Esta parte é responsável pela conexão com o banco de dados "Conexao conexaoAvaliacao = new Conexao();", que está abrindo uma conexão entre o código e o PgAdmin, logo em seguida é cria do uma variavel "conn" que recebe a conexão que é inicializada com o null para usar o try/cath e finally.
   No try essa parte está pedindo a conexão com o banco de dados "conn = conexaoAvaliacao.getConnection();".
   Tendo o AlunoDAO junto com o conn que logo em seguida faz uma verficação se o email existe ou não, caso o email que foi acabado de ser cadastrado não esteja no banco de dados aparecerá uma menssagem falando que é necessário ir na parte do login e inserir os dados que acabou de colocar para ter o acesso.
   É criado um objeto que AvaliacaoModelcom email1, descricao e nota que esses 3 critérios é a avaliacao, logo em seguida faz com que os dados que o usuário inseriu seja salvo no banco de dados e caso de tudo certo aparecerá uma menssagem que foi salvo a resposta do usuário.
   O catch está capturando alguns erros expecíficos como a conexão ou consultas.
   O finally é executado sempre: fecha a conexão se não for nula.

3. ControleBebida.java :
   Tem um campo privado que guarda referencia para a view que está na view (Bebida).
   Criando um construtor dessa variável privada.
   Tem uma função que se chama buscarInformacoesDoBebida recebendo um variavel do tipo String tendo o nome de nomeBebida, essa função faz com que busque as informações que estão salvas no banco de dados e apareça no textArea para o usuário.
   Tem uma verificação de se o nomeBebida for null ou com espaços no começo ou no fim e se está vazio, se for null irá exibir uma menssagem pedindo que o usuário digite um nome e interrompe o método com return.
   O try está servindo como no começo que está sendo aberto, abrindo o banco de dados e ao final que está sendo fechado, fechando o banco de dados.
   É criado o DAO passando a Connection e logo depois chama método do DAO do banco de dados com a informação ou sendo null.
   Tem uma verificação de if e else, sendo o if se não for null irá exibir a bebida junto com sua imformação e o else caso não encontro a bebida irá aparecer a bebida e falando que pode ter erro de digitação ou que essa bebida não esteja no banco de dados.
   O catch está capturando qualquer exceção que ocorra no bloco try, o cath está trazendo o erro e.getMessage() tendo uma descrição curta da exceção do erro.
   O "e.printStackTrace();" é bem útil para debugar.

4. ControleCadastrarPedido.java :
   Tem um campo privado que guarda referência para a tela que está na view (CadastrarPedido).
   Criando um construtor dessa variável privada.
   Tem uma função chamada CriarPedido, que é responsável por criar novos pedidos no banco de dados. Ela começa pegando o ID e o nome do alimento digitados pelo usuário na tela, e usa o ".trim()" para remover espaços desnecessários.
   Tem uma verificação com "if" para ver se algum campo está vazio. Caso esteja vai aparece uma mensagem pedindo para preencher todos os campos e o método é interrompido com return.
   O try serve para abrir e fechar o banco de dados automaticamente. Dentro dele, o código cria um DAO passando a conexão, e faz várias verificações:
   Se o ID já existe, mostra uma mensagem de erro.
   Se o alimento não existe no cardápio, mostra a lista completa de alimentos disponíveis para o usuário escolher que está no bando de dados.
   Caso tudo esteja certo, busca o preço do alimento, cria um objeto de pedido (usando o modelo CadastrarPedidoModel) e chama o método "inserir()" do DAO para salvar no banco.
   Depois disso, aparece uma mensagem de sucesso com o ID, o nome do alimento e o preço.
   O método EditarPedido funciona de forma parecida, mas serve para atualizar um pedido já existente. Ele verifica se o ID realmente existe no banco e se o alimento antigo confere com o atual antes de permitir a alteração.
   Já o método ExcluirPedido pede confirmação antes de excluir e só apaga o pedido se o ID for encontrado.
   Existem também os métodos limparCampos() e limparCamposEdicao(), que servem para apagar os campos da tela depois de criar, editar ou excluir um pedido.
   E o método mostrarCardapioCompleto() mostra todos os alimentos disponíveis no cardápio quando o usuário tenta criar um pedido com um alimento que não existe.
   O catch serve para capturar e mostrar erros do banco de dados, e no código é usado o "JOptionPane" em vários momentos para informar o usuário sobre o que está acontecendo, seja um erro, uma advertência ou uma mensagem de sucesso.
   Basicamente neste aquivo vai ter algumas verificações igual por exemplo se o campo não estiver preenchido, na parte de conexão com o banco de dados, por isso não vou repetir algumas coisas e vou pular essas parte.
   Na função EditarPedido() vai precisar de 3 Strings ID, alimentoAntigo e o alimentoNovo para editar o alimento.
   Na String comandoSQL vai buscar no banco de dados os dados do usuário.
   Na função ExcluirPedido() vai se executado apartir do ID que o usuário informar, tendo uma confirmação de se deseja mesmo excluir o pedido.
   Na função limparCamposEdicao(), como o próprio nome já fala ele limpa o campo para deixar com mais facilidade para o usuário.
   Na função mostrarCardapioCompleto(), é executado assim que o buttom é clicado e tendo um erro no alimento essa função vai aparecer mostrando todos os alimentos e bebidas.
   Lembrando que nesse função que cria pedidos só pode ser selecionado apenas um alimento ou bebida, para ter mais alimento é necessário ir ao carrinho para adicionar mais itens no pedido.

6. ControleCadastro.java :
   Neste arquivo foi pego do exemplo que foi deixado no moodle e resto de outros arquivos foram feitos com base nele ajudando bastante em caso de dúvida.
   Tem um campo privado que guarda referência para a tela que está na view (Cadastro).
   Foi criado um construtor para essa variável privada, que recebe a tela (Cadastro) e guarda ela para o controller poder acessar os campos da interface.
   Tem uma função chamada salvarAluno(), responsável por cadastrar um novo usuário. Ela pega os valores que são as variáveis do usuário digitou na tela do email, nome, senha, gênero (usa um operador ternário para decidir entre "Masculino" ou "Feminino" conforme o radio button for selecionado).
   Logo em seguida há uma verificação com "if" para checar se algum campo obrigatório está vazio. Se algum estiver vazio, aparece uma mensagem "(JOptionPane)" pedindo para preencher todos os campos, e o método é interrompido com return.
   É criado um objeto de Conexao e declara a Connection sendo inicialmente nula. No bloco try, ele abre a conexão com o banco "(conn = conexao.getConnection())" e cria o AlunoDAO passando essa conexão. O DAO é responsável pelas operações no banco.
   Antes de inserir, há uma verificação de duplicidade: "dao.verificarEmailExistente(email)". Se o e-mail já estiver cadastrado, o sistema mostra uma mensagem de erro pedindo para usar outro email e interrompe a inserção do email que está sendo repetido. Caso o e-mail não exista, o código cria um objeto Aluno com os dados coletados e chama o "dao.inserir(aluno)" para salvar no banco e no bando de dados tem uma tabela chamada usuario que esses dados são armazenados.
   Depois da inserção bem-sucedida, aparece uma mensagem de sucesso ao usuário dizendo que o cadastro deu certo e que ele pode acessar o login, depois disso ele é guiado imediatamente para o login.
   Se ocorrer algum erro de banco durante a operação, o catch "(SQLException ex)" captura a exceção e exibe uma mensagem de erro "(JOptionPane)" com "ex.getMessage()" para indicar o problema técnico. No bloco finally, a conexão é sempre fechada (se conn não for nula), para evitar deixar conexões abertas.
   Tem um botão de login caso o usuário já tenha uma conta cadastrada.

7. ControleCarrinho.java :
   Tem um campo privado que guarda referencia para a telaCarrinho que está na view (Carrinho).
   Foi criado um construtor para essa variável privada, que recebe a tela (Carrinho) e guarda ela para o controller poder acessar os campos da interface.
   Na função AdicionarPedido() tem duas variaveis que são excenciais para funciaonar sendo idPedido, alimento, pois na parte de cadastrar e na parte do carrinho foi necessério criar idPedido para ter muitos pedidos do mesmo email.
   Tendo uma verificação de preenchimento de campo, pois se tiver vazio irá exibir uma menssagem falando que é necessário que primeiro preencha todos os campos.
   Tem o try que inicia a conexão com o banco de dados do PgAdmin, tendo a conexão com a tabelas de pedido e cadastrarPedido.
   Tem uma verificação para ver se o alimento que o usuário digitou tem no cardápio e se não estiver, chama o método mostrarCardapioCompleto() para exibir todos os alimentos disponíveis.
   A parte do preço junto tirando da tabela tendo a conexão com o DAO com o banco de dados e logo depois aparecendo a parte das taxas das bebidas alcólicas salvando pelo ID que o usuário cadastrou, caso ocorra tudo certo irá aparecer uma menssagem falando que deu tudo certo, o subtotal e as taxas por causa da bebida alcólica e com isso vai fazer atualizar a lista do carrinho pelo ID do usuário e depois irá limpar o campo de alimento para que possa inserir outro deixando o ID para que possa ser mais eficiente.
   Na função RemoverPedido() tem 2 variáveis, tendo a unica diferença que essa função irá retirar um alimento ou uma bebida da sua lista e atualizar a parte do precos pois foi retirado o produto da lista.
   Na atualizarListaCarrinho() vai atualizar as mudança que foram feitas por exemplo no retirar item essa função de atualizarListaCarrinho() vai funcinar mais no final e a mesma coisa irá acontecer com o adicionar item que vai chamar a função atualizarListaCarrinho() para deixar sempre atualizado.
   O limparCampoAlimento(), como o próprio nome fala limpa apenas o campo do alimento deixando o que o usuário digitou no ID ainda lá ajudando e melhora na eficiência.
   O mostrarCardapioCompleto(), vai mostrar uma nova janela com todos os itens que estão no cardápio, uma parte dele é bem carecido com o view por causa da criação do vizual.
   O pesquisarPedidoPorID() só vai precisar do ID do usuário pois essa função irá apenas listar o que esta no banco de dados.

8. ControleHistorico.java :
   Tem um campo privado que guarda referencia para a tela que está na view (Historico).
   Foi criado um construtor para essa variável privada, que recebe a tela (Historico) e guarda ela para o controller poder acessar os campos da interface.
   A função buscarHistorico() chamando uma String email pegando todas as informações com base naquele email, ou seja, todos os "pc"=(exemplo) que foi salvo com os alimentos vai ser mostrado em uma textArea puxando essas informações do banco de dados pelo email.
   Tem uma verificação que se não tiver nunhum alimento cadastrado com aquele email vai ser mostrado uma menssagem falando que não tem nenhum alimento cadastrado com aquele email tendo o método return que irá interromper.
   Cria um StringBuilder para construir o texto que será mostrado na tela e ele percorre cada HistoricoModel da lista e adiciona uma linha com o nome do alimento.

9. ControleLogin.java :
   Neste arquivo foi pego do exemplo que foi deixado no moodle e resto de outros arquivos foram feitos com base nele ajudando bastante em caso de dúvida.
   Tem um campo privado que guarda referencia para a telaLogin que está na view (Login).
   Tem uma função de autenticar(), como o próprio nome já fala é usado para autenticar se é o próprio usuário digitando as suas informações.
   Tem duas variáveis sendo email e senha as pricipais.
   Tem o try que logo depois tem a conexão com o banco de dados para confirmar se é mesmo o usuário, se for ele mesmo irá aparecer que o login foi realizado, mas se não irá aparecer que tem um erro e não terá acesso em quando não colocar o acesso correto.
   Tem o cath que mostra os erros de login com a menssagem informando ao usuário que teve um erro de autenticação. 

10. ControlePesquisa.java :
    Tem um campo privado que guarda referencia para a tela que está na view (Pesquisa).
    Tem um campo privado que guarda referencia para a tela que está na view (Pesquisa).
    Tem uma função de salvandoPesquisaDoAlimento(), que faz com que seja salvo a pesquisa feita no banco de dados.
    Tem 2 variáveis sendo email e alimento, pois todos os alimentos que são pesquisados com o mesmo email, no histórico irá mostrar todos os alimentos com o mesmo email.
    Tem uma verificação de preenchemento dos campos, pois caso não tenha perenchido os campos irá aparecer uma menssagem falando que precisa preenche-los.
    Tem o try que inicia a conexão e a variável que coloca no banco de dados, depois aparece uma menssagem que foi salva com sucesso.
    O cath que é para o erro que seria caso não salve a sua pesquisa.

O arquivo dao:
1. AlimentoDAO.java :
   Tem um campo privado e final que guarda referencia para a conexao que está na view (Connection).
   Tem um campo privado que guarda referencia para a conexao que está na view (Connection).
   A função buscarPorNome(), que busca pelo alimento no banco de dados e exibe a informação dele.
   O try que abre a conexão com o banco de dados e ao fechalo tambem o a conexão se fecha.

2. AlunoDAO.java :
   Tem um campo privado e final que guarda referencia para a conn que está na view (Connection).
   Tem um campo privado que guarda referencia para a conn que está na view (Connection).
   A função de inserir(), faz com que o que for cadastrado vai ser salvo no banco de dados da tabela usuario, salvando as 4 variáveis sendo o email, nome, senha, genero.
   No final dessa função fecha a conexão com o banco de dados.
   Tem a função verificarLogin() que confere se o email e senha estão corretos e se já existe esse email.
   O try que abre a conexão com o banco de dados e ao fechalo tambem o a conexão se fecha.
   O catch que é para se tiver erro irá aparecer uma menssagem de erro de login, tendo o método return para interromper.
   A função verificarEmailExistente() verifica se já existe o email que está sendo cadastrado e se caso já tiver sido cadastrado irá aparec uma menssagem que já tem esse email cadastrado, mas se não irá para o login para ter o acesso a janela principal, o finally está fechando a conexão com o banco de dados.
   A função buscarPorEmail() busca por email já existentes tendo uma variável do tipo String sql que é para o pgAdmin.
   O try para falar se foi encontrado esse email se não irá aparecer o return null, podendo ter o cadastro.

3. AvaliacaoDAO.java :
   Tem um campo privado que guarda a referência da conexão com o banco de dados (con), utilizada para executar os comandos SQL dentro da classe.
   Foi criado um construtor que recebe essa conexão e armazena na variável con, permitindo que o DAO tenha acesso ao banco de dados.
   A função inserir() é responsável por salvar no banco de dados as informações enviadas pelo usuário.
   Essas informações são: email, descrição e nota (que representa a avaliação de 1 a 5 estrelas).
   Dentro do método, o comando SQL é preparado e executado usando um PreparedStatement, e no final ele é fechado para liberar os recursos da conexão.

4. BebidaDAO.java :
   Tem um campo privado e final que guarda referência para a conexao que está na view (Connection).
   A função buscarPorNome() faz a busca da bebida no banco de dados, procurando pelo nome que o usuário digitou.
   Tem uma variável do tipo String sql que contém o comando SQL responsável por procurar o nome da bebida na tabela bebidadainformacoes.
   Dentro do try, é feita a preparação do comando e a execução da consulta.
   Caso o banco encontre o nome da bebida, ele cria um objeto do tipo BebidaModel, salvando o nome e as informações da bebida encontradas.
   Se não encontrar nada, retorna null, indicando que não há nenhuma bebida cadastrada com aquele nome.

5. CadastrarPedidoDAO.java :
   Tem um campo privado que guarda referência para a conexao que está na view (Connection).
   A função inserir() que é responsável por salvar o pedido feito pelo usuário no banco de dados, guardando o id, o alimento e o preço.
   A função verificarIdExistente() faz uma verificação no banco para saber se já existe algum pedido com o mesmo id, evitando duplicações.
   A função buscarPorId() procura um pedido específico no banco de dados usando o id e, caso encontre, retorna o pedido com suas informações completas.
   A função atualizarPedido() permite modificar o alimento e o preço de um pedido já existente no banco, usando o id como referência.
   A função excluirPedido() serve para remover um pedido do banco de dados, também usando o id.
   A função buscarPrecoAlimento() busca o preço de um alimento cadastrado no cardápio, retornando BigDecimal.ZERO caso não encontre.
   A função verificarAlimentoExiste() verifica se o alimento digitado pelo usuário realmente existe no cardápio antes de tentar usá-lo.
   A função listarCardapio() retorna todos os alimentos cadastrados no banco, em ordem alfabética, para mostrar o cardápio completo ao usuário.

6. CarrinhoDAO.java :
   Tem um campo privado que guarda referência para a conn, que é usada para fazer a conexão com o banco de dados.
   A função adicionarAlimento() é responsável por adicionar um novo alimento a um pedido já existente, atualizando também o preço total no banco de dados.
   A função buscarPedidoPorId() busca um pedido específico usando o id, retornando todas as informações do pedido se ele existir.
   A função removerAlimento() serve para retirar um alimento do pedido, atualizando o banco de dados e recalculando o preço total depois da remoção. Ela faz isso verificando o alimento dentro da lista e subtraindo seu valor do preço total.
   A função calcularTotalComTaxa() soma uma taxa de 10% ao valor total se o pedido tiver bebidas alcoólicas, como cerveja, vinho, vodka ou whisky, retornando o total com a taxa inclusa.
   A função listarAlimentosPorId() lista todos os alimentos que estão associados a um determinado pedido, separando e organizando para mostrar corretamente.
   A função verificarIdExistente() verifica se o id informado já está cadastrado no banco de dados, evitando erros de duplicidade ou pedidos inexistentes.

7. Conexao.java :
   Essa classe é o pilar principal do código, pois é ela que faz a conexão com o banco de dados.
   Tem três constantes privadas: URL, USER e PASSWORD, que guardam as informações necessárias para se conectar ao PostgreSQL, ou seja, o endereço do banco, o nome do usuário e a senha.
   A função getConnection() é responsável por criar e retornar a conexão ativa com o banco.
   Dentro dela, o código primeiro tenta carregar o driver do PostgreSQL (para garantir que o Java consiga se comunicar com o banco).
   Depois, ele usa o DriverManager para abrir a conexão utilizando as informações fornecidas.
   Caso o driver não seja encontrado, o sistema lança uma exceção avisando que o driver do PostgreSQL não foi localizado.
   Essa classe é essencial, pois sem ela nenhuma parte do sistema conseguiria salvar, buscar ou atualizar informações no banco de dados.

8. HistoricoDAO.java :
   Essa classe é responsável por buscar o histórico de alimentos pesquisados de um usuário com base no seu e-mail.
   Ela possui o método buscarPorEmail(), que recebe um email como parâmetro e retorna uma lista de objetos HistoricoModel contendo os dados encontrados.
   Dentro do método, é criada uma lista vazia que será preenchida com os resultados vindos do banco de dados.
   O código faz a conexão utilizando a classe Conexao, que é o pilar principal de todo o sistema, pois permite a comunicação com o banco.
   Depois, é preparado um comando SQL que seleciona os campos email e alimento da tabela salvandopesquisa, filtrando pelo e-mail informado.
   Cada resultado encontrado é transformado em um novo HistoricoModel, que é adicionado à lista.
   Durante o processo, também há uma verificação de erro: se ocorrer algum problema ao buscar os dados, o sistema mostra uma mensagem de erro no console.
   No final, a lista com todos os registros é retornada, permitindo que a interface mostre para o usuário todos os alimentos pesquisados por aquele email.

9. PesquisaDAO.java :
    Essa classe é responsável por gerenciar as pesquisas realizadas pelos usuários, registrando e consultando os alimentos pesquisados no banco de dados.
    Ela faz parte da camada DAO (Data Access Object), que é responsável pela comunicação direta entre o sistema e o banco.
    Logo no início, a classe recebe uma conexão com o banco de dados através de seu construtor.
    Essa conexão, proveniente da classe Conexao, que é o pilar central do sistema, garante que as informações sejam gravadas e recuperadas corretamente no PostgreSQL.
    O método inserir() tem a função de salvar uma nova pesquisa no banco.
    Ele cria um comando SQL que insere o e-mail do usuário e o alimento pesquisado na tabela salvandopesquisa.
    A partir dos dados vindos do objeto PesquisaModel, o método executa o comando e fecha a instrução para evitar vazamentos de recursos.
    Já o método buscarPorEmail() serve para procurar no banco as pesquisas feitas por um determinado usuário, filtrando pelo e-mail informado.
    Se o e-mail for encontrado, o método cria um novo objeto PesquisaModel com as informações recuperadas, o email e o alimento pesquisado, e o retorna.
    Caso contrário, retorna null, indicando que não há registros para aquele e-mail.
    Com isso, a classe PesquisaDAO permite que o sistema armazene e recupere de forma eficiente as buscas feitas pelos usuários, garantindo a persistência e a consistência dos dados de pesquisa.

O arquivo model:
1. AlimentoModel.java :
   A classe AlimentoModel representa os dados de um alimento no sistema.
   Ela possui dois atributos: nome e informações, com seus respectivos getters e setters.
   É usada para armazenar e transportar informações sobre alimentos entre as partes do sistema, mantendo o código organizado e fácil de entender.

2. Aluno.java :
   A classe Aluno representa as informações de um aluno dentro do sistema.
   Ela contém os atributos email, nome, senha e gênero, além de um segundo conjunto de dados usado para avaliações (pedidoId, descrição e nota).
   A classe também possui construtores, getters e setters para manipular os dados, e um método toString() que facilita a exibição das informações do aluno.
   É usada principalmente para armazenar e transferir dados de alunos entre as partes do sistema.

3. AvaliacaoModel.java :
   A classe AvaliacaoModel representa a avaliação feita por um usuário no sistema.
   Ela possui três atributos principais: emailAvaliacao, descrição e nota, que guardam respectivamente o e-mail do avaliador, o texto da avaliação e a nota atribuída.
   Conta com um construtor para inicializar os dados e métodos getters para acessá-los.
   É usada para enviar ou armazenar as informações de avaliação de forma organizada e padronizada.

4. BebidaModel.java :
   A classe BebidaModel representa uma bebida cadastrada no sistema.
   Ela possui dois atributos principais: nome e informações, que armazenam o nome da bebida e seus detalhes.
   A classe implementa a interface Imposto_Alcool, indicando que pode estar relacionada a cálculos de impostos para bebidas alcoólicas.
   Possui métodos getters e setters para acessar e modificar seus dados de forma controlada.

5. CadastrarPedidoModel :
   A classe CadastrarPedidoModel representa um pedido feito pelo usuário no sistema.
   Ela possui três atributos principais: id, alimento e preço, que identificam o pedido, descrevem o item solicitado e armazenam o valor total.
   Conta com construtores para criar objetos com ou sem valores iniciais e com métodos getters e setters para acessar e alterar os dados do pedido de forma segura e organizada.

6. CarrinhoModel.java :
   A classe CarrinhoModel representa os itens adicionados ao carrinho de compras de um usuário.
   Ela possui dois atributos principais: email, que identifica o cliente, e alimento, que indica o produto escolhido.
   A classe conta com construtores para criar objetos com ou sem valores iniciais, getters e setters para acessar e modificar os dados, e um método toString() que retorna o nome do alimento — facilitando a exibição das informações na interface.

7. HistoricoModel.java :
   A classe HistoricoModel representa o histórico de pesquisas ou pedidos de um usuário.
   Ela contém dois atributos: email, que identifica o cliente, e alimento, que registra o item pesquisado ou pedido.
   A classe possui um construtor para inicializar os dados e getters para acessar as informações, sendo usada principalmente para exibir ou armazenar o histórico do usuário no sistema.

8. interface Imposto_Alcool :
   Ligado a bebida para comprir o requisito.

9. PesquisaModel.java :
    A classe PesquisaModel representa uma pesquisa feita por um usuário no sistema.
   Ela possui dois atributos principais: email, que identifica o usuário que realizou a pesquisa, e alimento, que guarda o nome do item pesquisado.
   A classe conta com um construtor para inicializar os dados e métodos getters para acessar essas informações, servindo como modelo para registrar e recuperar pesquisas realizadas no banco de dados. 




   
