# Aplicativo-Consulta-Opiniao
Aplicativo para Android

Modo de instalação:

A pasta ConsultaOpiniao possui o projeto para Android.

A pasta mono possui os arquivos da interface Web.

Para as duas interfaces funcionarem corretamente, o banco de dados central deve ser configurado.

O script do banco de dados é o aquivo scriptBancoDadosCentral dentro da pasta mono.

Para que todo o projeto funcione, a seguinte ordem deve ser seguida:

1- Instalação de servidor local PostreSQL.

2- Criação de um novo banco de dados no PostgreSQL.

3- Execução do script scriptBancoDadosCentral, que está dentro da pasta mono.

4- Instalação de servidor local XAMPP.

5- Copie os arquivos da pasta mono para pasta htdocs do servidor XAMPP.

6- Em cada arquivo PHP copiado, subsitua a linha:
$conexao = pg_connect("host=localhost port=5433 dbname=bdCentral user=postgres password=breno123") or print("Nao foi possivel se conectar ao banco de dados.");
por:
$conexao = pg_connect("host=localhost port=SUA_PORTA_POSTGRESQL dbname=NOME_DO_SEU_BANCO user=SEU_USUARIO_DE_BANCO password=SUA_SENHA_DE_BANCO") or print("Nao foi possivel se conectar ao banco de dados.");

Esses dados da conexão com o banco de dados central devem ser idênticos aos dados da criação do banco, feita no passo 2.

7- Abra o arquivo interfacegestor.php e verifique que o mapa está funcionando corretamente:

http://localhost/interfacegestor.php

8- Baixe a pasta ConsultaOpiniao. Ela contem o projeto Android.

9- No Eclipse + ADT Plugin ou no Android Studio, faça a importação do projeto que está na pasta ConsultaOpinião.

10- Após importar o projeto, vá na classe "VariaveisFinais.java" e altere todos os endereços:
"http://www.plataformarumor.com.br/mono/listarestabelecimentos.php"
por:
"http://IP_DA_SUA_MAQUINA/listarestabelecimentos.php"

11- Depois de tudo isso feito, o aplicativo já estará funcionando perfeitamente no emulador do seu computador.

Vale ressaltar que é necessário abrir a porta do modem para que o aplicativo funcione em um dispositivo móvel, acessando sua máquina pela internet.

Nesse caso, o modem deve ter um port foward para a porta 80 da sua máquina (ou para a porta que você definir no XAMPP) e o endereço na classe "VariaveisFinais.java" deve ficar:
"http://IP_DA_SUA_INTERNET:PORTAENCAMINHADA_MODEM/listarestabelecimentos.php"

Mais detalhes desse port foward em:

http://www.howtogeek.com/66214/how-to-forward-ports-on-your-router/