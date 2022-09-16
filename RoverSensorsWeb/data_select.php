<html>
    <head>
        <title>Select Mission!</title>
        <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Poppins">
        <style>
            body,h1,h2,h3,h4,h5 {font-family: "Poppins", sans-serif}
            body {font-size:16px;}
            .w3-half img{margin-bottom:-6px;margin-top:16px;opacity:0.8;cursor:pointer}
            .w3-half img:hover{opacity:1}
        </style>
    </head>
    <body style="margin:0 auto;text-align:center;">
        <form action="data.php" method="get">
            Mission id: <input name="mission_id" type="number">
            <input type="submit" value="Go!" style="background-color:blue;color:white;">
        </form>
        <h3>Missions:</h3>
        <br/>
        <p style="outline:5px dotted black;">
        <?php
$servername = "localhost";
$username = "id19492879_rover";
$password = "Pli@m12112005";
$dbname = "id19492879_roverdb";
$planets = array();
$x=1;

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
$sql = "SELECT * FROM planet_table";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
       $planets[$x] = $row['Name'];
       $x+=1;
    }
}
$sql = "SELECT * FROM mission_table";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
        $start=date_create($row['start_time']);
        $end=date_create($row['finish_time']);
        date_modify($start,"+3 hours");
        date_modify($end,"+3 hours");
        $start=$start->format('Y-m-d H:i:s');
        $end=$end->format('Y-m-d H:i:s');
       echo "Mission id: ".$row['mission_id'].", Start time: ".$start.", End time: ".$end.", Planet: ".$planets[$row['planet_id']];
       echo "<br/>";
    }
}
$conn->close();
?>
    </p>
    </body>
</html>