<?php
	require('config.php');

    $nome=$_GET['usuario'];
    $cpf=$_GET['cpf'];
    $senha=$_GET['senha'];
    $email=$_GET['email'];
    $conexao = pg_connect($GLOBALS['str_conex']);
    $result = pg_query($conexao, "INSERT INTO USUARIO (CPF, NOME, EMAIL, SENHA) VALUES ('$cpf','$nome','$email','$senha')");
    if($result)
    echo 1;
 
      
    pg_close($conexao);
?>