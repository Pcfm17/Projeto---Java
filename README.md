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















   
