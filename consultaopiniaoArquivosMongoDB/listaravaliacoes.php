<head>
<link rel="stylesheet" type="text/css" href="styles.css">
</head>
<?php
	require('config.php');
	
    $id=$_GET['id'];
    $conexao = pg_connect($GLOBALS['str_conex']);
    $result = pg_query($conexao, "select a.nome as NomeEstabelecimento, c.nome as NomeUsuarioAvaliacao, d.descricao as Pergunta, b.avaliacao from estabelecimento a, avaliacao b, usuario c, tipoavaliacao d where a.id = b.id and c.cpf = b.cpf and a.id = $id and d.codavaliacao = b.codigo");
    if (!$result)  {
              echo "An error occured.\n";
              exit;
          }
          else    {
                    $i = 0;
                    $i2 = 1;
                    if (pg_num_rows($result) == 0) {
                      echo 0;
                      exit;
                    }
                    echo "<div class=\"CSSTableGenerator\">";
                    echo "<table border=1>";
                    echo "<tr><td>Local</td><td>Nome</td><td>Pergunta</td><td>Avaliacao (de 0 a 5)</td></tr>";

                   while ($row = pg_fetch_row($result))  {
                          echo "<tr>";
                          echo "<td valign=top>".$row[0]."</td>";
                          echo "<td valign=top>".$row[1]."</td>";
                          echo "<td valign=top>".$row[2]."</td>";
                          echo "<td valign=middle>".$row[3]."</td>";
                          echo "</tr>";
                   }
                          echo "</table>";
                          echo "</div>";
  
    pg_close($conexao);
}
?>