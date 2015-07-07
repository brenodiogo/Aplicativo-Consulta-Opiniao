<?php
class estabelecimento {
    public $id;
    public $name;
    public $latitude;
    public $longitude;

    public $connection;
    public function __construct()     {
        $this->connection = pg_connect("host=localhost port=5433 dbname=bdCentral user=postgres password=breno123") or print("cant connect");
    }
    public function __destruct() { 
    }

    public function retornarPosicao() {
          $result = pg_query($this->connection, "SELECT latitude, longitude, nome, tipo, id FROM estabelecimento");
          if (!$result)  {
              echo "An error occured.\n";
              exit;
          }
          else    {
                    $i = 0;
                    $i2 = 1;
                   while ($row = pg_fetch_row($result))  {
                          $posicao[$i] = $row[0];
                          $posicao[$i2] = $row[1];
                          $posicao[$i2+1] = $row[2];
                          $posicao[$i2+2] = $row[3];
                          $posicao[$i2+3] = $row[4];
                          echo $posicao[$i];
                          echo 'AAA';
                          echo $posicao[$i2];
                          echo 'AAA';
                          echo $posicao[$i2+1];
                          echo 'AAA';
                          echo $posicao[$i2+2];
                          echo 'AAA';
                          echo $posicao[$i2+3];
                          echo 'AAA';
                          $i++;
                          $i++;
                          $i++;
                          $i++;
                          $i++;
                          $i2++;
                          $i2++;
                          $i2++;
                          $i2++;
                          $i2++;
                   }
          } 

    pg_close($this->connection);
    return $posicao;
    }

    public function update(){
    }
    public function delete(){
    }
}
$estabelecimentos = new estabelecimento();
$posicao = $estabelecimentos->retornarPosicao();
?>