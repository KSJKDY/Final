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

  $ID = $_POST['ID'];
  $type = $_POST['type'];


  mysqli_query($conn, "update location set type = '$type' where ID = '$ID'"); // db에 사용자의 타입 업데이트

  
  if($type == 0) {
      mysqli_query($conn, "update member set count = count + 1 where ID = '$ID'");
      
  }
  else if($type == 1) {
      mysqli_query($conn, "update member set count1 = count1 + 1 where ID = '$ID'");
  }
  

  mysqli_close($conn);                //db 연결 종료. ram 에서 삭제.


?>

