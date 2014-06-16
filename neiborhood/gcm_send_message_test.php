
<?php

 include 'dbinfo.inc'; 


$apiKey = "AIzaSyB7DgmYVLeoTRTmQYY_vEefjzZXkM0fEDQ";


$i =$_POST['type'];     // 타입
$ID = $_POST['ID'];     // 보는 사람 아이디
$my_reg = $_POST['reg_id'];   // 내 기기 ID
$my_id = $_POST['myID'];       // 내 ID



if($i == 1){
$message = "밥사주세요~관심있어요";
}
if($i == 0){
$message = "밥사줄께요~관심있어요";
}



$messageData = addslashes($message); 
mysql_query($conn, "UPDATE p SET yourID = '$ID' where ID = '$my_id'");  // 내ID가 있는곳에 보내는 사람의 ID를 집어넣는다
$result = mysql_query("SELECT reg_id FROM p where ID = '$ID'");  // 받는사람의  ID가 있는 곳의 등록된 기기ID를 불러옴
$conidix =0;

$arr = array();

$arr['data'] = array();

$arr['data']['msg'] = $messageData;

$arr['registration_ids'] = array();

while($row = mysql_fetch_array($result))

  {

	$arr['registration_ids'][0] =  $row['reg_id'] ;

    $conidx++;

  }

$headers = array('Content-Type:application/json ; charset=UTF-8', 'Authorization:key=AIzaSyB7DgmYVLeoTRTmQYY_vEefjzZXkM0fEDQ');





$ch = curl_init();

curl_setopt($ch, CURLOPT_URL,    'https://android.googleapis.com/gcm/send');

curl_setopt($ch, CURLOPT_HTTPHEADER,  $headers);

curl_setopt($ch, CURLOPT_POST,    true);

curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);

curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);

curl_setopt($ch, CURLOPT_POSTFIELDS,json_encode($arr));







$response = curl_exec($ch);




 echo $response;

curl_close($ch);
?>
