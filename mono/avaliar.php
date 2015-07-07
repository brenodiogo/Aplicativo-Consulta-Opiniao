<?php
    $cpf=$_GET['cpf'];
    $id=$_GET['id'];
    $pergunta1=$_GET['p1'];
    $pergunta1nota=$_GET['p1nota'];
    $pergunta2=$_GET['p2'];
    $pergunta2nota=$_GET['p2nota'];
    $pergunta3=$_GET['p3'];
    $pergunta3nota=$_GET['p3nota'];
    $pergunta4=$_GET['p4'];
    $pergunta4nota=$_GET['p4nota'];
    $pergunta5=$_GET['p5'];
    $pergunta5nota=$_GET['p5nota'];
    $conexao = pg_connect("host=localhost port=5433 dbname=bdCentral user=postgres password=breno123") or print("Nao foi possivel se conectar ao banco de dados.");
    $result = pg_query($conexao, "INSERT INTO AVALIACAO (ID, CPF, CODIGO, AVALIACAO) VALUES ($id,'$cpf',$pergunta1,$pergunta1nota)");
    $result = pg_query($conexao, "INSERT INTO AVALIACAO (ID, CPF, CODIGO, AVALIACAO) VALUES ($id,'$cpf',$pergunta2,$pergunta2nota)");
    $result = pg_query($conexao, "INSERT INTO AVALIACAO (ID, CPF, CODIGO, AVALIACAO) VALUES ($id,'$cpf',$pergunta3,$pergunta3nota)");
    $result = pg_query($conexao, "INSERT INTO AVALIACAO (ID, CPF, CODIGO, AVALIACAO) VALUES ($id,'$cpf',$pergunta4,$pergunta4nota)");
    $result = pg_query($conexao, "INSERT INTO AVALIACAO (ID, CPF, CODIGO, AVALIACAO) VALUES ($id,'$cpf',$pergunta5,$pergunta5nota)");

    if($result)
    echo 1;
 
      
    pg_close($conexao);
?>