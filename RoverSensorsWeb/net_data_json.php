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
  $result = $dblink->query("SELECT AVG(gas),AVG(temperature),AVG(humidity),AVG(light),AVG(CO2),AVG(TVOC),AVG(UV),AVG(barometric_pressure),AVG(altitude),AVG(radiation),date_time,mission_id FROM sensor_data WHERE mission_id=$mission_id GROUP BY DATE( date_time ), HOUR( date_time )");

//Initialize array variable
  $dbdata = array();

//Fetch into associative array
  while ( $row = $result->fetch_assoc())  {
	$dbdata[]=$row;
  }
  foreach($dbdata as &$value){
  $date=date_create($value['date_time']);
  date_modify($date,"+3 hours");
  $value['date_time']=$date->format('d/m/Y-H:i:s');
  $value['temperature']=round($value['AVG(temperature)']);
  unset($value['AVG(temperature)']);
  $value['humidity']=round($value['AVG(humidity)']);
  unset($value['AVG(humidity)']);
  $value['light']=round($value['AVG(light)']);
  unset($value['AVG(light)']);
  $value['gas']=round($value['AVG(gas)']);
  unset($value['AVG(gas)']);
  $value['CO2']=round($value['AVG(CO2)']/10);
  unset($value['AVG(CO2)']);
  $value['TVOC']=round($value['AVG(TVOC)']);
  unset($value['AVG(TVOC)']);
  $value['UV']=round($value['AVG(UV)']);
  unset($value['AVG(UV)']);
  $value['altitude']=round($value['AVG(altitude)']);
  unset($value['AVG(altitude)']);
  $value['radiation']=round($value['AVG(radiation)']);
  unset($value['AVG(radiation)']);
  $value['barometric_pressure']=round($value['AVG(barometric_pressure)']/1000);
  unset($value['AVG(barometric_pressure)']);
  }
  unset($value);
  
//Print array in JSON format
 echo json_encode($dbdata);
 
?>