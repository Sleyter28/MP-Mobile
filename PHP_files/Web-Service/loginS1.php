<?php
require("config.inc.php");

if (!empty($_POST)) {
	try{
		$sql = $conn->prepare('SELECT DB_name FROM clients_info WHERE email = :email');
		$sql->execute(array('email' => $_POST['email']));
		$resultado = $sql->fetchAll();

		foreach ($resultado as $row) {
			$response["DB_name"] = $row["DB_name"];
		}
		echo json_encode($response);
	}catch(PDOException $e){
		echo "ERROR: " . $e->getMessage();
	}
}
?>
