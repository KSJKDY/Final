<?php

$host="localhost";
$user="root";
$password="1234qwer";

$conn=mysql_connect($host, $user, $password);        //mysql 연결 설정
mysql_select_db("r", $conn);                        //DB에 접속


/*
연결 확인
if($conn) echo "db연결";
else echo "db연결 실패";
*/

?>
