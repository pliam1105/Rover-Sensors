<html>
    <head>
        <title>Create new mission</title>
        <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Poppins">
        <style>
            body,h1,h2,h3,h4,h5 {font-family: "Poppins", sans-serif}
            body {font-size:16px;}
            .w3-half img{margin-bottom:-6px;margin-top:16px;opacity:0.8;cursor:pointer}
            .w3-half img:hover{opacity:1}
        </style>
    </head>
    <body style="background-color:#005000;margin:0 auto; text-align:center;">
        <b><h2>Create Mission!</h2></b>
        <form action="mission_manager.php" method="get">
            Mission start: <input type="datetime-local" name="mission_start"><br>
            Mission end: <input type="datetime-local" name="mission_end"><br>
            Planet ID: <input type="number" name="planet_id"><br>
            <input type="submit" value="Add!" style="background-color:blue;color:white;">
        </form>
        <br/>
        <article style="outline:5px dashed white;">
            <?php
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
$sql = "SELECT * FROM planet_table";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
        $planet_id=$row['planet_id'];
        $planet_name=$row['Name'];
        $planet_type=$row['Type'];
        $planet_mass=$row['Mass (ME)'];
        $planet_radius=$row['Radius (RE)'];
        $planet_flux=$row['Flux (SE)'];
        $planet_teq=$row['Teq (K)'];
        $planet_period=$row['Period (days)'];
        $planet_distance=$row['Distance(ly)'];
        $planet_esi=$row['ESI'];
        echo "Planet ID: $planet_id, Planet name: $planet_name, type: $planet_type, mass(ME): $planet_mass, radius(RE): $planet_radius, flux(SE): $planet_flux, Teq(K): $planet_teq, period(days): $planet_period, distance(ly): $planet_distance, ESI: $planet_esi<br/>";
        if($planet_id<13){
            echo "<hr>";
        }
    }
}
            ?>
            
        </article>
        <br/>
        <br/>
    </body>
</html>