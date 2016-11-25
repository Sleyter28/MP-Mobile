<?php

	 include 'config.inc.php';
	 
	 // Check whether username or password is set from android	
     if(isset($_GET['email']))
     {
		  // Innitialize Variable
		  $result='';
	   	  $email = $_POST['email'];
         
		  
		  // Query database for row exist or not
          $sql = 'SELECT email, direction FROM clients_info WHERE  email = :email';
          $stmt = $conn->prepare($sql);
          $stmt->bindParam(':email', $email, PDO::PARAM_STR);
          $stmt->execute();
          if($stmt->rowCount()){
            $result= array();
            $result["email"] = $rowCount["email"];
            $result["direction"] = $rowCount["direction"];
          } elseif(!$stmt->rowCount()) {
			  	  $result="false";
          }
		  
		  // send result back to android
   		  echo $result;
  	}
	
?>