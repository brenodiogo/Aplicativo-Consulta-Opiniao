<?php
	require('config.php');

    $usuario=$_GET['usuario'];
    $senha=$_GET['senha'];
    $conexao = pg_connect($GLOBALS['str_conex']);
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