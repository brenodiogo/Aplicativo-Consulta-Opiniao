<?php
    $usuario=$_GET['usuario'];
    $senha=$_GET['senha'];
    $conexao = pg_connect("host=localhost port=5433 dbname=bdCentral user=postgres password=breno123") or print("Nao foi possivel se conectar ao banco de dados.");
    $result = pg_query($conexao, "SELECT nome, email FROM usuario WHERE cpf = '$usuario' AND senha = '$senha'");
          if (pg_num_rows($result) >= 1)  {
              echo 1;
              exit;
          }
          else    {
              echo 0;
          } 
    pg_close($conexao);
?>