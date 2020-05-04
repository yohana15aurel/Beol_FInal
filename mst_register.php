 <?php
include 'connection.php';

$name = $_POST['name'];
$email = $_POST['email'];
$password = $_POST['password'];
$birthdate = $_POST['birthdate'];
$mobilephone = $_POST['mobilephone'];

//SET QUERIES
$sql  = "INSERT INTO 1317012_mst_user (name,email,password,birthdate,mobilephone) ";
$sql .= "VALUES ('$name','$email','$password','$birth_date','$mobilephone') ";


// array for JSON response  
$result   = mysqli_query($conn,$sql) or die(mysqli_error("gagal"));


$response = array();
$response["data"] = array();

while ($row = mysqli_fetch_array($result)) {
	// temp user array
	
	$rs = array();

	
	// push single product into final response array
	array_push($response["data"], $rs);
}
// success
$response["success"] = 1;
$response["message"] = "Successfully retrieved";

echo json_encode($response,JSON_UNESCAPED_SLASHES);
?>