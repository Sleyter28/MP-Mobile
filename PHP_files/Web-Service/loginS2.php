<?php
if (!empty($_POST)) {
    $username = "ReadApp";
    $password = "MarketPymesApp";
    $servername = "localhost";
    $dbname = $_POST['DB_name'];
    try {
        $conn = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
        $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
        $sql = $conn->prepare('SELECT lg.cp_id_user_919819828828, lg.cp_user_19829928822, cp.cp_id_company_919819828828 FROM lp_login_1901992891 lg, lp_companies_i092002992 cp WHERE cp_password_1992398983 = :password && cp_user_19829928822 = :email && lg.cp_id_user_919819828828 = cp.cp_id_user_919819828828');
        $sql->execute(array('password' => $_POST['password'], 'email' => $_POST['email']));
        $resultado = $sql->fetchAll();

        foreach ($resultado as $row) {
            $response["DB_name"] = $_POST['DB_name'];
            $response["id_user"] = $row["cp_id_user_919819828828"];
            $response["user"] = $row["cp_user_19829928822"];
            $response["id_company"] = $row["cp_id_company_919819828828"];
        }
        echo json_encode($response);
    }
    catch(PDOException $e) {
        echo "ERROR: " . $e->getMessage();
        die(json_encode($response));
    }
}
?>
