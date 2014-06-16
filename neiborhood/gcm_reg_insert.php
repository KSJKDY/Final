<?php

 include 'dbinfo.inc'; 

$response = array();

 

if (isset($_POST['reg_id']) && isset($_POST['myname']) ) {

 

    $reg_id = $_POST['reg_id'];

    $myname = $_POST['myname'];

echo $reg_id,$myname;

 $result = mysql_query("update p set reg_id ='$reg_id' where name ='$myname'");

 mysql_query("update test set reg_id = '$reg_id' where name = '$myname'");



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
