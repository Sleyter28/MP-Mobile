<?php
require("config.inc.php");

if (!empty($_POST)) {
	try{
		$sql = $conn->prepare('SELECT email, direction FROM clients_info WHERE email = :email');
		$sql->execute(array('email' => $_POST['email']));
		$resultado = $sql->fetchAll();

		foreach ($resultado as $row) {
			$response["email"] = $row["email"];
			$response["direction"] = $row["direction"];
		}
		echo json_encode($response);
	}catch(PDOException $e){
		echo "ERROR: " . $e->getMessage();
	}
}
?>