<!DOCTYPE html>
<html>
  <head>
    <title>Interface com o Gestor</title>
    <meta name="viewport"
        content="width=device-width, initial-scale=1.0, user-scalable=no">
    <meta charset="UTF-8">
    <link rel="stylesheet" href="http://cdn.leafletjs.com/leaflet-0.7.3/leaflet.css" />
    <!--[if lte IE 8]>
        <link rel="stylesheet" href="http://code.leafletjs.com/leaflet-0.3.1/leaflet.ie.css" />
    <![endif]-->

    <!--<script src="http://code.leafletjs.com/leaflet-0.3.1/leaflet.js"></script>-->
  </head>
<body>

<?php
header('Content-Type: text/html; charset=utf-8');

require('config.php');

class estabelecimentos {
    public $id;
    public $name;
    public $latitude;
    public $longitude;
    public $connection;
	
    public function __construct()     {
        $this->connection = pg_connect($GLOBALS['str_conex']);
    }
	
    public function __destruct() { 
    }

    public function retornarPosicao() {
          $result = pg_query($this->connection, "select a.latitude, a.longitude, a.tipo, AVG(avaliacao) AS Media, a.id from estabelecimento a, avaliacao b where a.id = b.id group by a.latitude, a.longitude, a.tipo, a.id");
          if (!$result)  {
              echo "An error occured.\n";
              exit;
          }
          else    {
                    $i = 0;
                    $i2 = 1;
                   while ($row = pg_fetch_row($result))  {
                          $posicao[$i] = $row[0];
                          $posicao[$i+1] = $row[1];
                          $posicao[$i+2] = $row[2];
                          $posicao[$i+3] = $row[3];
                          $posicao[$i+4] = $row[4];
                          $i++;
                          $i++;
                          $i++;
                          $i++;
                          $i++;
                   }
          } 

    pg_close($this->connection);
    return $posicao;
    }
    public function retornar() {
          $result = pg_query($this->connection, "select a.latitude, a.longitude, a.tipo from estabelecimento a");
          if (!$result)  {
              echo "An error occured.\n";
              exit;
          }
          else    {
                    $i = 0;
                   while ($row = pg_fetch_row($result))  {
                          $posicao[$i] = $row[0];
                          $posicao[$i+1] = $row[1];
                          $posicao[$i+2] = $row[2];
                          $i++;
                          $i++;
                          $i++;
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
$meusEstabelecimentos = new estabelecimentos();
$estabelecimento = $meusEstabelecimentos->retornar();
$meusEstabelecimentos = new estabelecimentos();
$posicao = $meusEstabelecimentos->retornarPosicao();
?>
  <p id="demo"></p>
  <p id="demo2"></p>
  <p id="demo3"></p>
 <div id="map" style="width: 950px; height: 550px"></div>
<script src="http://cdn.leafletjs.com/leaflet-0.7.3/leaflet.js"></script>
<script>

        var map = L.map('map').setView(new L.LatLng(-15.809762,-47.887945), 11);
        L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
              attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
          }).addTo(map);


        //ICONE DA ESCOLA
        var escola = L.icon({
            iconUrl: 'iconescol.png',
            shadowUrl: 'iconescol.png',

            iconSize:     [50, 64], // size of the icon
            shadowSize:   [0, 0], // size of the shadow
            iconAnchor:   [22, 94], // point of the icon which will correspond to marker's location
            shadowAnchor: [4, 62],  // the same for the shadow
            popupAnchor:  [-3, -76] // point from which the popup should open relative to the iconAnchor
            });

        //ICONE DO HOSPITAL
        var hospital = L.icon({
            iconUrl: 'iconhosp.png',
            shadowUrl: 'iconhosp.png',

            iconSize:     [50, 64], // size of the icon
            shadowSize:   [0, 0], // size of the shadow
            iconAnchor:   [22, 94], // point of the icon which will correspond to marker's location
            shadowAnchor: [4, 62],  // the same for the shadow
            popupAnchor:  [-3, -76] // point from which the popup should open relative to the iconAnchor
            });

        //ICONE CIRCULO VERDE
         var greencircle = L.icon({
            iconUrl: 'greencircle.png',
            shadowUrl: 'greencircle.png',

            iconSize:     [64, 64], // size of the icon
            shadowSize:   [0, 0], // size of the shadow
            iconAnchor:   [22, 94], // point of the icon which will correspond to marker's location
            shadowAnchor: [4, 62],  // the same for the shadow
            popupAnchor:  [-3, -76] // point from which the popup should open relative to the iconAnchor
            });

         //ICONE CIRCULO VERMELHO
         var redcircle = L.icon({
            iconUrl: 'redcircle.png',
            shadowUrl: 'redcircle.png',

            iconSize:     [64, 64], // size of the icon
            shadowSize:   [0, 0], // size of the shadow
            iconAnchor:   [22, 94], // point of the icon which will correspond to marker's location
            shadowAnchor: [4, 62],  // the same for the shadow
            popupAnchor:  [-3, -76] // point from which the popup should open relative to the iconAnchor
            });
        

        <?php $contadorLocais = count($estabelecimento) ?> 
        <?php $contadorPosicao = count($posicao) ?>
        
        var i3 = <?php echo $contadorLocais ?>;
        var i2 = <?php echo $contadorPosicao ?>;
        
        var js_locais = <?php echo json_encode($estabelecimento); ?>;
        var js_array = <?php echo json_encode($posicao); ?>;

        
        for (var i = 0; i < i3; i++) {
          if(js_locais[i+2] == 1){
              var popup = "Desculpe. <br>Não há notas cadastradas para essa escola.";
              L.marker([js_locais[i],js_locais[i+1]], {icon:escola}).bindPopup(popup).addTo(map);
            } else if (js_locais[i+2] == 2){
              var popup = "Desculpe. <br>Não há notas cadastradas para esse hospital.";
              L.marker([js_locais[i],js_locais[i+1]], {icon:hospital}).bindPopup(popup).addTo(map);  
          }
          i++;
          i++;
    }       

        for (var i = 0; i < i2; i++) {
          if(js_array[i+2] == 1){

            if(js_array[i+3] > 2.5){
              var popup = "Perfeito. <br>A nota média para essa escola foi de " + js_array[i+3] + ".<br><a href= 'listaravaliacoes.php?id=" + js_array[i+4] + "' target='_blank'>Visualizar os dados cadastrados </a>";
              L.marker([js_array[i],js_array[i+1]], {icon:escola}).bindPopup(popup).addTo(map);  
              L.marker([js_array[i],js_array[i+1]], {icon:greencircle, opacity:0.6}).bindPopup(popup).addTo(map);
            } else if(js_array[i+3] <= 2.5) {
              var popup = "Atenção!<br>A nota média para essa escola foi de " + js_array[i+3] + ".<br><a href= 'listaravaliacoes.php?id=" + js_array[i+4] + "' target='_blank'>Visualizar os dados cadastrados </a>";
              L.marker([js_array[i],js_array[i+1]], {icon:escola}).bindPopup(popup).addTo(map);  
              L.marker([js_array[i],js_array[i+1]], {icon:redcircle, opacity:0.6}).bindPopup(popup).addTo(map);
            } else {
              L.marker([js_array[i],js_array[i+1]], {icon:escola}).addTo(map); 
            }
            
          }
          else if (js_array[i+2] == 2){

            if(js_array[i+3] > 2.5){
              var popup = "Perfeito. <br>A nota média para esse hospital foi de " + js_array[i+3] + ".<br><a href= 'listaravaliacoes.php?id=" + js_array[i+4] + "' target='_blank'>Visualizar os dados cadastrados </a>";
              L.marker([js_array[i],js_array[i+1]], {icon:hospital}).bindPopup(popup).addTo(map);  
              L.marker([js_array[i],js_array[i+1]], {icon:greencircle, opacity:0.6}).bindPopup(popup).addTo(map);
            } else if(js_array[i+3] <= 2.5) {
              var popup = "Atenção!<br>A nota média para esse hospital foi de " + js_array[i+3] + ".<br><a href= 'listaravaliacoes.php?id=" + js_array[i+4] + "' target='_blank'>Visualizar os dados cadastrados </a>";
              L.marker([js_array[i],js_array[i+1]], {icon:hospital}).bindPopup(popup).addTo(map);  
              L.marker([js_array[i],js_array[i+1]], {icon:redcircle, opacity:0.6}).bindPopup(popup).addTo(map);
            } else {
              L.marker([js_array[i],js_array[i+1]], {icon:hospital}).addTo(map);
            }
          }
          i++;
          i++;
          i++;
          i++;
    }

        </script>
</body>
</html>