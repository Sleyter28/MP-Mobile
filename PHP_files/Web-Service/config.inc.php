<?php
    $username = "ReadApp";
    $password = "MarketPymesApp";
    $servername = "localhost";
    $dbname = "Database_per_Client";
try {
    $conn = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    }
catch(PDOException $e)
    {
    die("OOPs something went wrong");
    }
?>
