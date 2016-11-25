<?php

$servername = "localhost";
$username = "ReadApp";
$password = "MarketPymesApp";
$dbname = "database_per_client";

try {
    	$conn = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
    	$conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    }
catch(PDOException $e)
    {
    	die("OOPs something went wrong");
    }
 
?>
