<?php
$servername = "localhost";
$username = "u351871370_buty";
$password = "NezuGevaXy";
$dbname = "u351871370_buty";
$now = date('Y-m-d H:i:s');

$temperature = $_GET['temperature'];
$humidity = $_GET['humidity'];
$light = $_GET['light'];
$gas=$_GET['gas'];
$co2=$_GET['co2'];
$tvoc=$_GET['tvoc'];
$uv=$_GET['uv'];
$barometric_pressure=$_GET['barometric_pressure'];
$latitude=$_GET['latitude'];
$longitude=$_GET['longitude'];
$altitude=$_GET ['altitude'];
$radiation=$_GET ['radiation'];

$conn = mysqli_connect("localhost","u351871370_buty","NezuGevaXy");
if (!$conn)
{
    die('Could not connect: ' . mysqli_connect_error());
}
$con_result = mysqli_select_db($conn,"u351871370_buty");
if(!$con_result)
{
	die('Could not connect to specific database: ' . mysqli_error($conn));	
}
    $sql = "SELECT*FROM mission_table";
	$result = mysqli_query($conn,$sql);
	if (!$result) {
		die('Invalid query: ' . mysqli_error($conn));
	}
	if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
        $start_time=$row["start_time"];
        $finish_time=$row["finish_time"];
        $mission_id=$row["mission_id"];
        $start=new DateTime($start_time);
        $finish=new DateTime($finish_time);
        $now=new DateTime();
        if($now>$start && $now<$finish){
            $htemperature = $temperature;
	        $hhumidity = $humidity;

	        $sql = "INSERT INTO sensor_data(mission_id,date_time, temperature, humidity, light, gas, CO2, TVOC, UV, barometric_pressure, latitude, longitude, altitude, radiation) VALUES ($mission_id,now(),$temperature,$humidity,$light,$gas,$co2,$tvoc,$uv,$barometric_pressure,$latitude,$longitude, $altitude, $radiation)";
	        $result = mysqli_query($conn,$sql);
	    if (!$result) {
		    die('Invalid query: ' . mysqli_error($conn));
	    }
	    die("<h1>Sent data with mission!</h1>");
        }
    }
            $htemperature = $temperature;
	        $hhumidity = $humidity;

	        $sql = "INSERT INTO sensor_data(date_time, temperature, humidity, light, gas, CO2, TVOC, UV, barometric_pressure, latitude, longitude, altitude, radiation) VALUES (now(),$temperature,$humidity,$light,$gas,$co2,$tvoc,$uv,$barometric_pressure,$latitude,$longitude, $altitude, $radiation)";
	        $result = mysqli_query($conn,$sql);
	        if (!$result) {
	    	die('Invalid query: ' . mysqli_error($conn));
	        }
}
	echo "<h1>THE DATA HAS BEEN SENT!!</h1>";
	mysqli_close($conn);
?>