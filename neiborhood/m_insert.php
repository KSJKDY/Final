<?php

$host="localhost";
$user="root";
$password="1234qwer";

$conn=mysqli_connect($host, $user, $password, "r");   //mysql 연결 설정

// Check connection
if (mysqli_connect_errno())
  {
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  }
  


$name = $_POST['name'];    // 추가할 이름 
$ID = $_POST['ID'];  // 사용자 facebook ID

/*
if($sex==male)                   //성별 결정
    $sex="남자";
else if($sex==female)
    $sex="여자";
*/
//$address=$address1." ".$address2;    //주소 완성


/* insert 구문 */
mysqli_query($conn,"insert into member values('$name', '', '', '','','','$ID',0,0)"); 

mysqli_query($conn, "insert into location values('$name','','','','$ID')");

mysqli_query($conn, "insert into p values('$name','','','$ID')");

mysqli_query($conn, "insert into test values('$name','','','$ID')");



mysqli_close($conn);                //db 연결 종료. ram 에서 삭제.

echo "등록 완료. 축하드립니다. <br>";
echo ("<a href='index.php'>초기화면으로</a>");

?>

