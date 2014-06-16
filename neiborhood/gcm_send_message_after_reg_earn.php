<?php

//include 'gcm_reg_insert_after_reg.php';
include 'dbinfo.inc'; 

$apiKey = "AIzaSyB7DgmYVLeoTRTmQYY_vEefjzZXkM0fEDQ";

$message = "hello";


$result = mysql_query("select s_reg_id from p where reg_id = '$reg_id'");


                                              
$messageData = addslashes($message);

$conidx=0;
$arr = array();

$arr['data'] = array();

$arr['data']['msg'] = $messageData;

$arr['registration_ids'] = array();


while($row = mysql_fetch_array($result))
{
$arr['registration_ids'][0] =  $row['s_reg_id'];


}


sendNotification($apiKey,$messageData, $arr);


function sendNotification( $apiKey, $messageData, $arr )

{   

$headers = array('Content-Type:application/json ; charset=UTF-8','Authorization:key=AIzaSyB7DgmYVLeoTRTmQYY_vEefjzZXkM0fEDQ');




$ch = curl_init();


curl_setopt($ch, CURLOPT_URL,'https://android.googleapis.com/gcm/send');

curl_setopt($ch, CURLOPT_HTTPHEADER,  $headers);

curl_setopt($ch, CURLOPT_POST,    true);

curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);

curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);

curl_setopt($ch, CURLOPT_POSTFIELDS,json_encode($arr));






$response = curl_exec($ch);




 echo $response;

curl_close($ch);



}





?>
