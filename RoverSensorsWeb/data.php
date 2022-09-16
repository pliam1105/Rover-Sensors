<!DOCTYPE html>
<html>
    <head>
        <title>Rover Sensor Data</title>
        <meta charset="utf-8">
        <style>
        div.canvas{
            display: grid;
            grid-gap: 30px;
            grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
            margin:60px;
            .canvas-container {
   width: 100%;
   text-align:center;
   margin:0 auto;
   align:center;
    }
}

            b { margin:auto; text-align:center; font-size: 20px; }
            canvas { margin:auto; display:inline; }
            #map { height:500px; width:100%; }
        </style>
        <meta name='viewport' content='initial-scale=1,maximum-scale=1,user-scalable=no' />
<script src='https://api.mapbox.com/mapbox.js/v3.1.1/mapbox.js'></script>
<link href='https://api.mapbox.com/mapbox.js/v3.1.1/mapbox.css' rel='stylesheet' />
        <script src="gauge.min.js"></script>
        <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
         <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Poppins">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script type="text/javascript">
        function drawGauge(){
        <?php
$mission_id=$_GET["mission_id"];
$servername = "localhost";
$username = "id19492879_rover";
$password = "Pli@m12112005";
$dbname = "id19492879_roverdb";
// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

$sql = "SELECT * FROM sensor_data where mission_id=$mission_id ORDER BY id DESC LIMIT 1";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
        $temperature = $row['temperature'];
        $humidity = $row['humidity'];
        $light = $row['light'];
        $gas = $row['gas'];
        $co2 = $row['CO2'];
        $tvoc = $row['TVOC'];
        $bmp = $row['barometric_pressure'];
        $uv = $row['UV'];
        $radiation = $row['radiation'];
    }
}?>

var opts = {
  angle: 0.15, // The span of the gauge arc
  lineWidth: 0.44, // The line thickness
  radiusScale: 1, // Relative radius
  pointer: {
    length: 0.6, // // Relative to gauge radius
    strokeWidth: 0.035, // The thickness
    color: '#000000' // Fill color
  },
  limitMax: false,     // If false, max value increases automatically if value > maxValue
  limitMin: false,     // If true, the min value of the gauge will be fixed
  colorStart: '#00B3CF',   // Colors
  colorStop: '#0B20DA',    // just experiment with them
  strokeColor: '#E0E0E0',  // to see which ones work best for you
  generateGradient: true,
  highDpiSupport: true,     // High resolution support
  
};
var target = document.getElementById('tmp'); // your canvas element
var gauge = new Gauge(target).setOptions(opts);
gauge.maxValue = 60; // set max gauge value
gauge.setMinValue(0);  // Prefer setter over gauge.minValue = 0
gauge.animationSpeed = 32; // set animation speed (32 is default value)
gauge.setTextField(document.getElementById("tmp_p"));
gauge.set(<?php echo $temperature ?>); // set actual value

var opts = {
  angle: 0.15, // The span of the gauge arc
  lineWidth: 0.44, // The line thickness
  radiusScale: 1, // Relative radius
  pointer: {
    length: 0.6, // // Relative to gauge radius
    strokeWidth: 0.035, // The thickness
    color: '#000000' // Fill color
  },
  limitMax: false,     // If false, max value increases automatically if value > maxValue
  limitMin: false,     // If true, the min value of the gauge will be fixed
  colorStart: '#00B3CF',   // Colors
  colorStop: '#0B20DA',    // just experiment with them
  strokeColor: '#E0E0E0',  // to see which ones work best for you
  generateGradient: true,
  highDpiSupport: true,     // High resolution support
  
};
var target = document.getElementById('humid'); // your canvas element
var gauge = new Gauge(target).setOptions(opts);
gauge.maxValue = 100; // set max gauge value
gauge.setMinValue(0);  // Prefer setter over gauge.minValue = 0
gauge.animationSpeed = 32; // set animation speed (32 is default value)
gauge.setTextField(document.getElementById("humid_p"));
gauge.set(<?php echo $humidity ?>); // set actual value

var opts = {
  angle: 0.15, // The span of the gauge arc
  lineWidth: 0.44, // The line thickness
  radiusScale: 1, // Relative radius
  pointer: {
    length: 0.6, // // Relative to gauge radius
    strokeWidth: 0.035, // The thickness
    color: '#000000' // Fill color
  },
  limitMax: false,     // If false, max value increases automatically if value > maxValue
  limitMin: false,     // If true, the min value of the gauge will be fixed
  colorStart: '#00B3CF',   // Colors
  colorStop: '#0B20DA',    // just experiment with them
  strokeColor: '#E0E0E0',  // to see which ones work best for you
  generateGradient: true,
  highDpiSupport: true,     // High resolution support
  
};
var target = document.getElementById('light'); // your canvas element
var gauge = new Gauge(target).setOptions(opts);
gauge.maxValue = 100; // set max gauge value
gauge.setMinValue(0);  // Prefer setter over gauge.minValue = 0
gauge.animationSpeed = 32; // set animation speed (32 is default value)
gauge.setTextField(document.getElementById("light_p"));
gauge.set(<?php echo $light ?>); // set actual value

var opts = {
  angle: 0.15, // The span of the gauge arc
  lineWidth: 0.44, // The line thickness
  radiusScale: 1, // Relative radius
  pointer: {
    length: 0.6, // // Relative to gauge radius
    strokeWidth: 0.035, // The thickness
    color: '#000000' // Fill color
  },
  limitMax: false,     // If false, max value increases automatically if value > maxValue
  limitMin: false,     // If true, the min value of the gauge will be fixed
  colorStart: '#00B3CF',   // Colors
  colorStop: '#0B20DA',    // just experiment with them
  strokeColor: '#E0E0E0',  // to see which ones work best for you
  generateGradient: true,
  highDpiSupport: true,     // High resolution support
  
};
var target = document.getElementById('gas'); // your canvas element
var gauge = new Gauge(target).setOptions(opts);
gauge.maxValue = 85; // set max gauge value
gauge.setMinValue(0);  // Prefer setter over gauge.minValue = 0
gauge.animationSpeed = 32; // set animation speed (32 is default value)
gauge.setTextField(document.getElementById("gas_p"));
gauge.set(<?php echo $gas ?>); // set actual value

var opts = {
  angle: 0.15, // The span of the gauge arc
  lineWidth: 0.44, // The line thickness
  radiusScale: 1, // Relative radius
  pointer: {
    length: 0.6, // // Relative to gauge radius
    strokeWidth: 0.035, // The thickness
    color: '#000000' // Fill color
  },
  limitMax: false,     // If false, max value increases automatically if value > maxValue
  limitMin: false,     // If true, the min value of the gauge will be fixed
  colorStart: '#00B3CF',   // Colors
  colorStop: '#0B20DA',    // just experiment with them
  strokeColor: '#E0E0E0',  // to see which ones work best for you
  generateGradient: true,
  highDpiSupport: true,     // High resolution support
  
};
var target = document.getElementById('co2'); // your canvas element
var gauge = new Gauge(target).setOptions(opts);
gauge.maxValue = 1500; // set max gauge value
gauge.setMinValue(0);  // Prefer setter over gauge.minValue = 0
gauge.animationSpeed = 32; // set animation speed (32 is default value)
gauge.setTextField(document.getElementById("co2_p"));
gauge.set(<?php echo $co2 ?>); // set actual value

var opts = {
  angle: 0.15, // The span of the gauge arc
  lineWidth: 0.44, // The line thickness
  radiusScale: 1, // Relative radius
  pointer: {
    length: 0.6, // // Relative to gauge radius
    strokeWidth: 0.035, // The thickness
    color: '#000000' // Fill color
  },
  limitMax: false,     // If false, max value increases automatically if value > maxValue
  limitMin: false,     // If true, the min value of the gauge will be fixed
  colorStart: '#00B3CF',   // Colors
  colorStop: '#0B20DA',    // just experiment with them
  strokeColor: '#E0E0E0',  // to see which ones work best for you
  generateGradient: true,
  highDpiSupport: true,     // High resolution support
  
};
var target = document.getElementById('tvoc'); // your canvas element
var gauge = new Gauge(target).setOptions(opts);
gauge.maxValue = 150; // set max gauge value
gauge.setMinValue(0);  // Prefer setter over gauge.minValue = 0
gauge.animationSpeed = 32; // set animation speed (32 is default value)
gauge.setTextField(document.getElementById("tvoc_p"));
gauge.set(<?php echo $tvoc ?>); // set actual value

var opts = {
  angle: 0.15, // The span of the gauge arc
  lineWidth: 0.44, // The line thickness
  radiusScale: 1, // Relative radius
  pointer: {
    length: 0.6, // // Relative to gauge radius
    strokeWidth: 0.035, // The thickness
    color: '#000000' // Fill color
  },
  limitMax: false,     // If false, max value increases automatically if value > maxValue
  limitMin: false,     // If true, the min value of the gauge will be fixed
  colorStart: '#00B3CF',   // Colors
  colorStop: '#0B20DA',    // just experiment with them
  strokeColor: '#E0E0E0',  // to see which ones work best for you
  generateGradient: true,
  highDpiSupport: true,     // High resolution support
  
};
var target = document.getElementById('bmp'); // your canvas element
var gauge = new Gauge(target).setOptions(opts);
gauge.maxValue = 150000; // set max gauge value
gauge.setMinValue(0);  // Prefer setter over gauge.minValue = 0
gauge.animationSpeed = 32; // set animation speed (32 is default value)
gauge.setTextField(document.getElementById("bmp_p"));
gauge.set(<?php echo $bmp ?>); // set actual value

var opts = {
  angle: 0.15, // The span of the gauge arc
  lineWidth: 0.44, // The line thickness
  radiusScale: 1, // Relative radius
  pointer: {
    length: 0.6, // // Relative to gauge radius
    strokeWidth: 0.035, // The thickness
    color: '#000000' // Fill color
  },
  limitMax: false,     // If false, max value increases automatically if value > maxValue
  limitMin: false,     // If true, the min value of the gauge will be fixed
  colorStart: '#00B3CF',   // Colors
  colorStop: '#0B20DA',    // just experiment with them
  strokeColor: '#E0E0E0',  // to see which ones work best for you
  generateGradient: true,
  highDpiSupport: true,     // High resolution support
  
};
var target = document.getElementById('uv'); // your canvas element
var gauge = new Gauge(target).setOptions(opts);
gauge.maxValue = 20; // set max gauge value
gauge.setMinValue(0);  // Prefer setter over gauge.minValue = 0
gauge.animationSpeed = 32; // set animation speed (32 is default value)
gauge.setTextField(document.getElementById("uv_p"));
gauge.set(<?php echo $uv ?>); // set actual value

var opts = {
  angle: 0.15, // The span of the gauge arc
  lineWidth: 0.44, // The line thickness
  radiusScale: 1, // Relative radius
  pointer: {
    length: 0.6, // // Relative to gauge radius
    strokeWidth: 0.035, // The thickness
    color: '#000000' // Fill color
  },
  limitMax: false,     // If false, max value increases automatically if value > maxValue
  limitMin: false,     // If true, the min value of the gauge will be fixed
  colorStart: '#00B3CF',   // Colors
  colorStop: '#0B20DA',    // just experiment with them
  strokeColor: '#E0E0E0',  // to see which ones work best for you
  generateGradient: true,
  highDpiSupport: true,     // High resolution support
  
};
var target = document.getElementById('rad'); // your canvas element
var gauge = new Gauge(target).setOptions(opts);
gauge.maxValue = 500; // set max gauge value
gauge.setMinValue(0);  // Prefer setter over gauge.minValue = 0
gauge.animationSpeed = 32; // set animation speed (32 is default value)
gauge.setTextField(document.getElementById("rad_p"));
gauge.set(<?php echo $radiation ?>); // set actual value

}
    $(document).ready(function(){
    drawGauge();
    setInterval(drawGauge,5000);
});
</script>
    </head>
    <body>
        <header>
            <div class="header" style="background-color: #808080; width: 100%; height: 80px;">
                <img src="control_logo.png" alt="logo" style="float: left; width: 80px; height: 80px;" />
                <h2 style="position: relative; top:18px; left: 10px;">Rover Control Panel</h2>
            </div>
        </header>
    <br/>
    <br/>
    <p style="text-align:center; margin:0 auto;"><?php
$mission_id=$_GET["mission_id"];
$servername = "localhost";
$username = "id19492879_rover";
$password = "Pli@m12112005";
$dbname = "id19492879_roverdb";
$temperature=array();
$humidity=array();
$light=array();
$datetime=array();
$gas=array();
$co2=array();
$tvoc=array();
$bmp=array();
$uv=array();
$rad=array();

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

$sql = "SELECT * FROM sensor_data WHERE mission_id=$mission_id";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
       if($row["mission_id"]==$mission_id){
           if($row['temperature']==0||is_nan($row['temperature'])){
            array_push($temperature,1);
        }else{
            array_push($temperature,$row['temperature']);
        }
        if($row['humidity']==0||is_nan($row['humidity'])){
            array_push($humidity,1);
        }else{
            array_push($humidity,$row['humidity']);
        }
        if($row['light']==0||is_nan($row['light'])){
            array_push($light,1);
        }else{
            array_push($light,$row['light']);
        }
        array_push($datetime,$row['date_time']);
        if($row['gas']==0||is_nan($row['gas'])){
            array_push($gas,1);
        }else{
            array_push($gas,$row['gas']);
        }
        if($row['CO2']==0||is_nan($row['CO2'])){
            array_push($co2,1);
        }else{
            array_push($co2,$row['CO2']);
        }
        if($row['TVOC']==0||is_nan($row['TVOC'])){
            array_push($tvoc,1);
        }else{
            array_push($tvoc,$row['TVOC']);
        }
        if($row['UV']==0||is_nan($row['UV'])){
            array_push($uv,1);
        }else{
            array_push($uv,$row['UV']);
        }
        if($row['radiation']==0||is_nan($row['radiation'])){
            array_push($rad,1);
        }else{
            array_push($rad,$row['radiation']);
        }
        if($mission_id>=8){
        if($row['barometric_pressure']==0||is_nan($row['barometric_pressure'])){
            array_push($bmp,1);
        }else{
            array_push($bmp,$row['barometric_pressure']);
        }
        }else{
            array_push($bmp,51000);
        }
       }
    }
}
$sql = "SELECT * FROM habitable_limits";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
      if($row['Type']=="Temperature"){
          $temperature_min=$row['Min'];
          $temperature_max=$row['Max'];
      }else if($row['Type']=="Humidity"){
          $humidity_min=$row['Min'];
          $humidity_max=$row['Max'];
      }else if($row['Type']=="Light"){
          $light_min=$row['Min'];
          $light_max=$row['Max'];
      }else if($row['Type']=="Gas"){
          $gas_min=$row['Min'];
          $gas_max=$row['Max'];
      }else if($row['Type']=="CO2"){
          $co2_min=$row['Min'];
          $co2_max=$row['Max'];
      }else if($row['Type']=="TVOC"){
          $tvoc_min=$row['Min'];
          $tvoc_max=$row['Max'];
      }else if($row['Type']=="UV"){
          $uv_min=$row['Min'];
          $uv_max=$row['Max'];
      }else if($row['Type']=="Barometric_pressure"){
          $bmp_min=$row['Min'];
          $bmp_max=$row['Max'];
      }else if($row['Type']=="Radiation"){
          $rad_min=$row['Min'];
          $rad_max=$row['Max'];
      }
    }
}
$sql = "SELECT * FROM mission_table WHERE mission_id=$mission_id";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
        $planet_id=$row['planet_id'];
    }
}
$sql = "SELECT * FROM planet_table WHERE planet_id=$planet_id";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
        $planet_name=$row['Name'];
        $planet_type=$row['Type'];
        $planet_mass=$row['Mass (ME)'];
        $planet_radius=$row['Radius (RE)'];
        $planet_flux=$row['Flux (SE)'];
        $planet_teq=$row['Teq (K)'];
        $planet_period=$row['Period (days)'];
        $planet_distance=$row['Distance(ly)'];
        $planet_esi=$row['ESI'];
    }
}
$temperatureA=array_filter($temperature);
$temperatureAV=array_sum($temperatureA)/count($temperatureA);
$temperatureAV=round($temperatureAV,2);
echo "<div style='border-radius: 25px; background-color: blue; color:white; text-align:center; margin:0 auto; padding: 10px; width:350px;'><p style='text-align:center; margin:0 auto;'>";
echo "Average Temperature: $temperatureAV"."<br/>";
$humidityA=array_filter($humidity);
$humidityAV=array_sum($humidityA)/count($humidityA);
$humidityAV=round($humidityAV,2);
echo "Average Humidity: $humidityAV"."<br/>";
$lightA=array_filter($light);
$lightAV=array_sum($lightA)/count($lightA);
$lightAV=round($lightAV,2);
echo "Average Light: $lightAV"."<br/>";
$gasA=array_filter($gas);
$gasAV=array_sum($gasA)/count($gasA);
$gasAV=round($gasAV,2);
echo "Average Gas: $gasAV"."<br/>";
$co2A=array_filter($co2);
$co2AV=array_sum($co2A)/count($co2A);
$co2AV=round($co2AV,2);
echo "Average CO2: $co2AV"."<br/>";
$tvocA=array_filter($tvoc);
$tvocAV=array_sum($tvocA)/count($tvocA);
$tvocAV=round($tvocAV,2);
echo "Average TVOC: $tvocAV"."<br/>";
$uvA=array_filter($uv);
$uvAV=array_sum($uvA)/count($uvA);
$uvAV=round($uvAV,2);
echo "Average UV: $uvAV"."<br/>";
$bmpA=array_filter($bmp);
$bmpAV=array_sum($bmpA)/count($bmpA);
$bmpAV=round($bmpAV,2);
echo "Average Barometric Pressure: $bmpAV"."<br/>";
$radA=array_filter($rad);
$radAV=array_sum($radA)/count($radA);
$radAV=round($radAV,2);
echo "Average Radiation: $radAV"."</p></div>";
echo "<br/><p style='outline:5px dashed black; text-align: center; margin: auto;'><br/>Planet name: $planet_name, type: $planet_type, mass(ME): $planet_mass, radius(RE): $planet_radius, flux(SE): $planet_flux, Teq(K): $planet_teq, period(days): $planet_period, distance(ly): $planet_distance, ESI: $planet_esi<br/><br/></p>";
if($temperature_min<=$temperatureAV&&$temperature_max>=$temperatureAV&&$humidity_min<=$humidityAV&&$humidity_max>=$humidityAV&&$light_min<=$lightAV&&$light_max>=$lightAV&&$gas_min<=$gasAV&&$gas_max>=$gasAV&&$co2_min<=$co2AV&&$co2_max>=$co2AV&&$tvoc_min<=$tvocAV&&$tvoc_max>=$tvocAV&&$uv_min<=$uvAV&&$uv_max>=$uvAV&&$bmp_min<=$bmpAV&&$bmp_max>=$bmpAV&&$rad_min<=$radAV&&$rad_max>=$radAV){
    echo "<br/>"."<div style='width:200px; height:70px; border-radius:70px; background-color: green; color: white; text-align:center; margin:auto;'><br/>Planet habitable!<br/></div>"."<br/>";
}else{
    echo "<br/>"."<div style='width:200px; height:70px; border-radius:70px; background-color: red; color: white; text-align:center; margin:auto;'><br/>Planet inhabitable!<br/></div>"."<br/>";
}
$conn->close();
?></p><br/>
<div class="canvas">
    </canvas>
    <div class="canvas-container">
    <canvas id="tmp" style="width: 350; height: 350; margin: auto;">
    <br/>
    </canvas><br/><b>Temperature(°C):</b><br/><b id="tmp_p"></b>
    <br/><br/></div>
    <div class="canvas-container">
    <canvas id="humid" style="width: 350; height: 350; margin: auto;"></canvas>
    <br/><b>Humidity(%):</b><br/><b id="humid_p"></b>
    <br/><br/></div>
    <div class="canvas-container">
    <canvas id="light" style="width: 350; height: 350; margin: auto;"></canvas>
    <br/>
    <b>Light(%):</b><br/><b id="light_p"></b></canvas>
    <br/><br/></div>
    <div class="canvas-container">
    <canvas id="gas" style="width: 350; height: 350; margin: auto;"></canvas>
    <br/>
    </canvas><b>Gas(%):</b><br/><b id="gas_p"></b>
    <br/><br/></div>
    <div class="canvas-container">
    <canvas id="co2" style="width: 350; height: 350; margin: auto;"></canvas>
    <br/>
    </canvas><b>CO2(ppm):</b><br/><b id="co2_p"></b>
    <br/><br/></div>
    <div class="canvas-container">
    <canvas id="tvoc" style="width: 350; height: 350; margin: auto;"></canvas>
    <br/>
    </canvas><b>TVOC(ppb):</b><br/><b id="tvoc_p"></b>
    <br/><br/></div>
    <div class="canvas-container">
    <canvas id="bmp" style="width: 350; height: 350; margin: auto;"></canvas>
    <br/>
    </canvas><b>Barometric Pressure (Pa):</b><br/><b id="bmp_p"></b>
    <br/><br/></div>
    <div class="canvas-container">
    <canvas id="uv" style="width: 350; height: 350; margin: auto;"></canvas><br/><b>UV (UV Index):</b><br/><b id="uv_p"></b>
    <br/><br/></div>
    <div class="canvas-container">
    <canvas id="rad" style="width: 350; height: 350; margin: auto;"></canvas><br/><b>Radiation (CPM):</b><br/><b id="rad_p"></b>
    <br/><br/></div></div>
    <br/>
    <div id='map'></div>
    <br/>
    <br/>
    <script>
        L.mapbox.accessToken = 'pk.eyJ1IjoicGxpYW1wYXMiLCJhIjoiY2psbWRiczM5MTNneTNwbmprN2FoaG5jNSJ9.wNg62r6BQDH0TCEGwdPJJw';
var map = L.mapbox.map('map', 'mapbox.satellite')
    .setView([0, 0], 3);

// Add a new line to the map with no points.
var polyline = L.polyline([]).addTo(map);

<?php
$mission_id=$_GET['mission_id'];
// Initialize variable for database credentials
$servername = "localhost";
$username = "id19492879_rover";
$password = "Pli@m12112005";
$dbname = "id19492879_roverdb";

//Create database connection
  $dblink = new mysqli($servername, $username, $password, $dbname);

//Check connection was successful
  if ($dblink->connect_errno) {
     printf("Failed to connect to database");
     exit();
  }

//Fetch rows from sensor data
  $result = $dblink->query("SELECT latitude,longitude FROM sensor_data WHERE mission_id=$mission_id");

//Fetch into associative array
  while ( $row = $result->fetch_assoc())  {
      $latitude = $row['latitude'];
      $longitude = $row['longitude'];
	echo "polyline.addLatLng(L.latLng($latitude,$longitude));";
  }
 
?>
// zoom the map to the polyline
map.fitBounds(polyline.getBounds());
    </script>
  </body>
</html>