<?php
include 'connection.php';


$mysql_qry = "SELECT * FROM 1317012_mst_layanan_bengkelMobil";

// error jika query gagal
$response["success"] = 0;
$response["message"] = "Error Input";

$result   = mysqli_query($conn,$mysql_qry) or die(json_encode($response,JSON_UNESCAPED_SLASHES));

$response = array();
$response["data"] = array();
        
while ($row = mysqli_fetch_array($result)) {
// temp user array
            
    $payload = array();
	$payload["id_layanan"]  = $row["id_layanan"];
    $payload["jenis_layanan"]  = $row["jenis_layanan"];
    $payload["deskripsi_layanan"] = $row["deskripsi_layanan"];
    $payload["harga"] = $row["harga"];
            
// push single product into final response array
    array_push($response["data"], $payload);
}
// success
$response["success"] = 1;
$response["message"] = "Successfully retrieved";
    
echo json_encode($response,JSON_UNESCAPED_SLASHES);
?>