<?php
$mission_id=$_GET["mission_id"];
$servername = "localhost";
$username = "id19492879_rover";
$password = "Pli@m12112005";
$dbname = "id19492879_roverdb";
$temperature=array();
$humidity=array();;
$light=array();
$datetime=array();
$gas=array();
$co2=array();
$tvoc=array();
$bmp=array();
$uv=array();
$radiation=array();

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
            array_push($radiation,1);
        }else{
            array_push($radiation,$row['radiation']);
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
$humidityA=array_filter($humidity);
$humidityAV=array_sum($humidityA)/count($humidityA);
$humidityAV=round($humidityAV,2);
$lightA=array_filter($light);
$lightAV=array_sum($lightA)/count($lightA);
$lightAV=round($lightAV,2);
$gasA=array_filter($gas);
$gasAV=array_sum($gasA)/count($gasA);
$gasAV=round($gasAV,2);
$co2A=array_filter($co2);
$co2AV=array_sum($co2A)/count($co2A);
$co2AV=round($co2AV,2);
$tvocA=array_filter($tvoc);
$tvocAV=array_sum($tvocA)/count($tvocA);
$tvocAV=round($tvocAV,2);
$uvA=array_filter($uv);
$uvAV=array_sum($uvA)/count($uvA);
$uvAV=round($uvAV,2);
$bmpA=array_filter($bmp);
$bmpAV=array_sum($bmpA)/count($bmpA);
$bmpAV=round($bmpAV,2);
$radA=array_filter($radiation);
$radAV=array_sum($radA)/count($radA);
$radAV=round($radAV,2);
if($temperature_min<=$temperatureAV&&$temperature_max>=$temperatureAV&&$humidity_min<=$humidityAV&&$humidity_max>=$humidityAV&&$light_min<=$lightAV&&$light_max>=$lightAV&&$gas_min<=$gasAV&&$gas_max>=$gasAV&&$co2_min<=$co2AV&&$co2_max>=$co2AV&&$tvoc_min<=$tvocAV&&$tvoc_max>=$tvocAV&&$uv_min<=$uvAV&&$uv_max>=$uvAV&&$bmp_min<=$bmpAV&&$bmp_max>=$bmpAV&&$rad_min<=$radAV&&$rad_max>=$radAV){
    echo "1";
}else{
    echo "0";
}
$conn->close();
?>