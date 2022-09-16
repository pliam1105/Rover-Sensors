<?php
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
  $result = $dblink->query("SELECT * FROM V_Planet_Mission_Name");

//Initialize array variable
  $dbdata = array();

//Fetch into associative array
  while ( $row = $result->fetch_assoc())  {
	$dbdata[]=$row;
  }
  foreach($dbdata as &$value){
        $start=date_create($value['start_time']);
        date_modify($start,"+3 hours");
        $value['start_time']=$start->format('d/m/Y');
  }
  unset($value);
//Print array in JSON format
 echo json_encode($dbdata);
 
?>