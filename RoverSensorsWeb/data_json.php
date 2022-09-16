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
  $result = $dblink->query("SELECT AVG(gas),AVG(temperature),AVG(humidity),AVG(light),AVG(CO2),AVG(TVOC),AVG(UV),AVG(barometric_pressure),AVG (altitude),AVG (radiation),date_time,mission_id FROM sensor_data WHERE mission_id=$mission_id GROUP BY DATE( date_time ), HOUR( date_time ), MINUTE( date_time )");

//Initialize array variable
  $dbdata = array();

//Fetch into associative array
  while ( $row = $result->fetch_assoc())  {
	$dbdata[]=$row;
  }
  
//Print array in JSON format
 echo json_encode($dbdata);
 
?>
