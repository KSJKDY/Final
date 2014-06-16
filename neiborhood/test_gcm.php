<?php


 include 'dbinfo.inc'; 

$reg_id = $_POST['reg_id'];
$name = $_POST['yname'];

mysql_query("update test set g_reg_id = '$reg_id' where name = '$name'");


?>
