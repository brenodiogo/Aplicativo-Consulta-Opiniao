<?php
	require('config.php');

    $cpf=$_GET['cpf'];
    $nome=$_GET['nome'];
    $email=$_GET['email'];
    $senha=$_GET['senha'];
    $conexao = pg_connect($GLOBALS['str_conex']);
    $result = pg_query($conexao, "INSERT INTO USUARIO (CPF, NOME, EMAIL, SENHA) VALUES ('$cpf', '$nome', '$email', '$senha')");
    echo $result;
          if (pg_num_rows($result) >= 1)  {
              echo 1;
              exit;
          }
          else    {
              echo 0;
          } 
    pg_close($conexao);

?>