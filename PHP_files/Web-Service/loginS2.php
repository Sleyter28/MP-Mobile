<?php 
if (!empty($_POST)) { 
    $username = "ReadApp"; 
    $password = "MarketPymesApp"; 
    $servername = "localhost";
    $dbname = $_POST['DB_name'];
    try {
        $conn = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
        $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
        $sql = $conn->prepare('SELECT cp_id_user_919819828828, cp_user_19829928822 FROM `lp_login_1901992891` WHERE `cp_password_1992398983` = :password && cp_user_19829928822 = :email');
        $sql->execute(array('password' => $_POST['password'], 'email' => $_POST['email']));
        $resultado = $sql->fetchAll();

        foreach ($resultado as $row) {
            $response["cp_id_user_919819828828"] = $row["cp_id_user_919819828828"];
            $response["cp_user_19829928822"] = $row["cp_user_19829928822"];
        }
        echo json_encode($response);
    }
    catch(PDOException $e) {
        echo "ERROR: " . $e->getMessage();
        die(json_encode($response));
    }
}
?>