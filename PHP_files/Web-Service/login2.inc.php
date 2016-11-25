<?php

	 include 'config.inc.php';
	 

    $sql = 'SELECT email, direction FROM clients_info';
    $stmt = $conn->prepare($sql);
    $stmt->execute();
    if($stmt->rowCount())
    {
			$result="true";	
      }  
    elseif(!$stmt->rowCount())
    {
			$result="false";
      }
		  
		// send result back to android
   	echo $result;
	
?>