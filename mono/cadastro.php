<?php
    $nome=$_GET['usuario'];
    $cpf=$_GET['cpf'];
    $senha=$_GET['senha'];
    $email=$_GET['email'];
    $conexao = pg_connect("host=localhost port=5433 dbname=bdCentral user=postgres password=breno123") or print("Nao foi possivel se conectar ao banco de dados.");
    $result = pg_query($conexao, "INSERT INTO USUARIO (CPF, NOME, EMAIL, SENHA) VALUES ('$cpf','$nome','$email','$senha')");
    if($result)
    echo 1;
 
      
    pg_close($conexao);
?>