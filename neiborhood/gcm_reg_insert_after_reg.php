<?php

 include 'dbinfo.inc'; 

$response = array();

 

   $reg_id = $_POST['reg_id'];

    $name = $_POST['yname'];
 

mysql_query("update p set g_reg_id = '$reg_id' where name = '$name'");
mysql_query("update test set g_reg_id = '$reg_id' where name = '$name'");
mysql_query("update p set s_reg_id = (select reg_id from test where name = '$name') where reg_id = '$reg_id';");

include 'gcm_send_message_after_reg_earn.php';

//mysql_query("insert into p (send)=(select (reg_id) from p where name='$yname') where reg_id ='$reg_id'");

/*
if (isset($_POST['reg_id']) && isset($_POST['yname']) ) {

 

 

//$result=mysql_query("insert into p (send)=(select (reg_id) from p where name='$yname') where reg_id ='$reg_id'");


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
*/

?>
