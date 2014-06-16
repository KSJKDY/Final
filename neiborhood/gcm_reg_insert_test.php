<?php

 include 'dbinfo.inc'; 

$response = array();

 


if (isset($_POST['reg_id']) && isset($_POST['ID'])) {

 
    
 $reg_id = $_POST['reg_id'];

    $ID = $_POST['ID'];


  mysql_query("update p set reg_id = '$reg_id' where ID = '$ID';");
   


    if ($result) {

        // successfully inserted into database

        $response["success"] = 1;

        $response["message"] = "Product successfully created.";

 

        echo json_encode($response);

    } else {

        // failed to insert row

        $response["success"] = 0;

        $response["message"] = "Oops! An error occurred.";

 

        echo json_encode($response);

    }

} else {

    // required field is missing

    $response["success"] = 0;

    $response["message"] = "Required field(s) is missing";

 

    echo json_encode($response);

}

?>
