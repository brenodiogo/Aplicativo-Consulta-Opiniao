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
class country {
    public $id;
    public $name;
    public $latitude;
    public $longitude;

    public $connection;
    public function __construct()     {
        $this->connection = pg_connect("host=localhost port=5433 dbname=gisdb user=postgres password=breno123") or print("cant connect");
    }
    public function __destruct() { 
    }

    public function retornarPosicao() {
          $result = pg_query($this->connection, "SELECT latitude, longitude FROM paisdois");
          //$result = pg_query($this->connection, "SELECT id, name, ST_AsText(boundary), name_alias FROM country");
          if (!$result)  {
              echo "An error occured.\n";
              exit;
          }
          else    {
          //echo "<table border=1>";
          //echo "<tr><td>ID</td><td>Name</td>
                    //<td>Boundary</td></tr>";
                    $i = 0;
                    $i2 = 1;
                   while ($row = pg_fetch_row($result))  {
                          //ec/ho "<tr>";
                          //echo "<td valign=top>".$row[0]."</td>";
                          //echo "<td valign=top>".$row[1]."</td>";
                          //echo "<td valign=top>".$row[2]."</td>";
                          //$i = 2;
                          //echo "$i2";
                          //retorna na posição 0 o GEOJSON e na posição 1 o valor do atributo
                          //$boundaryworking = array($i => $row[2], $i2 => $row[1]);
                          $posicao[$i] = $row[0];
                          $posicao[$i2] = $row[1];
                          //$boundaryworking["$i"] = $row[2];
                          //$boundaryworking["$i2"] = $row[1];
                          $i++;
                          $i++;
                          $i2++;
                          $i2++;

                           //$boundaryworking = array(0 => $row[1], 1 => $row[2]);
                          //echo "</tr>";
                   }
                          //echo $boundaryworking[0];
                          //echo $posicao[0];
                          //echo $boundaryworking[1];
                          //echo $boundaryworking[0];


          //echo "</table>";
          } 

    pg_close($this->connection);
    return $posicao;
    }

    public function update(){
    }
    public function delete(){
    }
}
$myCountry = new country();
$posicao = $myCountry->retornarPosicao();
//$myCountry = new country();
//$atributo = $myCountry->retornarAtributos();
//echo $boundaryworking;
//$myCountry->create("2","United States");
?>
 <div id="map" style="width: 800px; height: 700px"></div>
<script src="http://cdn.leafletjs.com/leaflet-0.7.3/leaflet.js"></script>
<script>

        var map = L.map('map').setView(new L.LatLng(-15.809762,-47.887945), 11);
        /*L.tileLayer('https://{s}.tiles.mapbox.com/v3/{id}/{z}/{x}/{y}.png', {
            maxZoom: 18,
            attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, ' +
                '<a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' +
                'Imagery © <a href="http://mapbox.com">Mapbox</a>',
            id: 'examples.map-i875mjb7'
        }).addTo(map);*/

        L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
              attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
          }).addTo(map);
        //"<? = $boundaryworking?>"


        //ICONE DA ESCOLA
        var greenIcon = L.icon({
            iconUrl: 'iconescol.png',
            shadowUrl: 'iconescol.png',

            iconSize:     [50, 64], // size of the icon
            shadowSize:   [0, 0], // size of the shadow
            iconAnchor:   [22, 94], // point of the icon which will correspond to marker's location
            shadowAnchor: [4, 62],  // the same for the shadow
            popupAnchor:  [-3, -76] // point from which the popup should open relative to the iconAnchor
            });

        var hospital = L.icon({
            iconUrl: 'iconhosp.png',
            shadowUrl: 'iconhosp.png',

            iconSize:     [50, 64], // size of the icon
            shadowSize:   [0, 0], // size of the shadow
            iconAnchor:   [22, 94], // point of the icon which will correspond to marker's location
            shadowAnchor: [4, 62],  // the same for the shadow
            popupAnchor:  [-3, -76] // point from which the popup should open relative to the iconAnchor
            });

         var greencircle = L.icon({
            iconUrl: 'greencircle.png',
            shadowUrl: 'greencircle.png',

            iconSize:     [64, 64], // size of the icon
            shadowSize:   [0, 0], // size of the shadow
            iconAnchor:   [22, 94], // point of the icon which will correspond to marker's location
            shadowAnchor: [4, 62],  // the same for the shadow
            popupAnchor:  [-3, -76] // point from which the popup should open relative to the iconAnchor
            });

         var redcircle = L.icon({
            iconUrl: 'redcircle.png',
            shadowUrl: 'redcircle.png',

            iconSize:     [64, 64], // size of the icon
            shadowSize:   [0, 0], // size of the shadow
            iconAnchor:   [22, 94], // point of the icon which will correspond to marker's location
            shadowAnchor: [4, 62],  // the same for the shadow
            popupAnchor:  [-3, -76] // point from which the popup should open relative to the iconAnchor
            });

        var popup = "ATENÇÃO!<br>A nota média para essa escola foi de 1.5.<br><a href= 'http://teste.com' >Visualizar os dados cadastrados </a>";
        L.marker([<?php echo $posicao[0]; ?>,<?php echo $posicao[1]; ?>], {icon:greenIcon}).bindPopup(popup).addTo(map);

        L.marker([<?php echo $posicao[2]; ?>,<?php echo $posicao[3]; ?>], {icon:greenIcon}).bindPopup(popup).addTo(map);

        var hh = "Perfeito.<br>A nota média para esse hospital foi de 4.4.<br><a href= 'teste' >Visualizar os dados cadastrados </a>";

        L.marker([<?php echo $posicao[4]; ?>,<?php echo $posicao[5]; ?>], {icon:hospital}).bindPopup(hh).addTo(map);

        L.marker([-15.815295,-47.910132], {icon:redcircle, opacity:0.6}).bindPopup(popup).addTo(map);

        L.marker([-15.809762,-47.887945], {icon:greencircle, opacity:0.4}).bindPopup(popup).addTo(map);

        L.marker([-15.831728,-47.924079], {icon:greencircle, opacity:0.4}).bindPopup(hh).addTo(map);



        </script>
</body>
</html>