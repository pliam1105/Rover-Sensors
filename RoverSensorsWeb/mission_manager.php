<?php
$start=$_GET["mission_start"];
$end=$_GET["mission_end"];
$planet_id=$_GET["planet_id"];
$servername = "localhost";
$username = "id19492879_rover";
$password = "Pli@m12112005";
$dbname = "id19492879_roverdb";
$start=date_create($start);
$end=date_create($end);
date_modify($start,"-3 hours");
date_modify($end,"-3 hours");
$start=$start->format('Y-m-d H:i:s');
$end=$end->format('Y-m-d H:i:s');
$conn = mysqli_connect($servername,$username,$password);
if (!$conn)
{
    die('Could not connect: ' . mysqli_connect_error());
}
$con_result = mysqli_select_db($conn,$dbname);
if(!$con_result)
{
	die('Could not connect to specific database: ' . mysqli_error($conn));	
}

	$sql = "INSERT INTO mission_table(insert_time, start_time, finish_time, planet_id) VALUES (now(),'$start','$end',$planet_id)";
	$result = mysqli_query($conn,$sql);
	if (!$result) {
		die('Invalid query: ' . mysqli_error($conn));
	}
	echo "<h1>Mission Created!</h1>";
	mysqli_close($conn);
?>