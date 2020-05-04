<?php
include 'connection.php';
$id_konsul = $_POST['id_konsul'];


$mysql_qry = "SELECT * FROM 1317012_mst_read_konsul WHERE id_konsul = ".$id_konsul." ";

// error jika query gagal
$response["success"] = 0;
$response["message"] = "Error Input";

$result   = mysqli_query($conn,$mysql_qry) or die(json_encode($response,JSON_UNESCAPED_SLASHES));

$response = array();
$response["data"] = array();
        
while ($row = mysqli_fetch_array($result)) {
// temp user array
            
    $payload = array();
	$payload["id_konsul"]  = $row["id_konsul"];
    $payload["judulKonsul"]  = $row["judulKonsul"];
    $payload["isiKonsul"] = $row["isiKonsul"];
            
// push single product into final response array
    array_push($response["data"], $payload);
}
// success
$response["success"] = 1;
$response["message"] = "Successfully retrieved";
    
echo json_encode($response,JSON_UNESCAPED_SLASHES);
?>