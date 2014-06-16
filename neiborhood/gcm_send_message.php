<HTML>

<HEAD>

<TITLE>GuestBook</TITLE>

<meta http-equiv="content-Type" content="text/html" charset="utf-8">

</HEAD>







<BODY BGCOLOR="#006699" LINK="#99CCFF" VLINK="#99CCCC" TEXT="#FFFFFF">

<center>

<br><p>




<?php

 include 'dbinfo.inc'; 


$apiKey = "AIzaSyBhjGaYXCoom2DuWWmWyJtTHqseoG36x50";







    $message = $_POST['message'];




 echo("

      <FORM name='form' method='post' action='$PHP_SELF'>

      <TABLE border='0' cellspacing='1'>

      <TR>

      <TD width='109' bgcolor='#5485B6'><P align='center'><FONT face='굴림' size='2' color='#CDDAE4'>

        message</FONT></TD>

      <TD width='541'><P>&nbsp;<INPUT type='text' name='message' SIZE=25 MAXLENGTHTH='20'></TD>

      </TR>

	    <TR>

      <TD><P>&nbsp;</TD>

      <TD><P>&nbsp;<INPUT type='submit' name='submit' value='sendMessage'></TD>

      </TR>

	  </TABLE>

      <input type=hidden name=mode value='up'>

      </FORM>");




	  if ($mode == 'up') {

		  $messageData = addslashes($message); 

				sendNotification($apiKey,$messageData);

	  }




function sendNotification( $apiKey, $messageData )

{   




	$headers = array('Content-Type:application/json ; charset=UTF-8', 'Authorization:key=AIzaSyBhjGaYXCoom2DuWWmWyJtTHqseoG36x50');






	$result = mysql_query("SELECT reg_id FROM p");

	

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

	mysql_close($conn);




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



}




echo("

<p>                             

</BODY>

</HTML>");

?>
