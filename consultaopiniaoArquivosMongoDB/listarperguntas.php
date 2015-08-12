<?php
	require('config.php');

    $tipo = $_GET['tipo'];
    $conexao = pg_connect($GLOBALS['str_conex']);
    $result = pg_query($conexao, "SELECT DESCRICAO, CODAVALIACAO FROM TIPOAVALIACAO WHERE TIPO = $tipo");
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
                   while ($row = pg_fetch_row($result))  {
                          $posicao[$i] = $row[0];
                          $posicao[$i+1] = $row[1];
                          echo $posicao[$i];
                          echo 'AAA';
                          echo $posicao[$i+1];
                          echo 'AAA';
                          $i++;
                   }
    pg_close($conexao);
}
?>