<?php

include 'gcm_reg_insert.php';
include 'dbinfo.inc'; 
	

$apiKey = "AIzaSyB7DgmYVLeoTRTmQYY_vEefjzZXkM0fEDQ";

$message = "자고싶다";
$sql="select reg_id from p where pnum = Dobbie Doyeob Kim";
$messageData = addslashes($message); 

$result=mysql_query($sql,$conn);
$conidx=0;
$arr   = array();

$arr['data'] = array();

$arr['data']['msg'] = $messageData;

$arr['registration_ids'] = array();

while($row = mysql_fetch_array($result))

  {

	$arr['registration_ids'][$conidx] =  $row['reg_id'] ;

    $conidx++;

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
