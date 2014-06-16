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

$ID = $_POST['ID'];  // 사용자 facebook ID
$age = $_POST['age']; // 나이
$phone = $_POST['phone']; //연락처
$address = $_POST['address'];  // 주소
$sex = $_POST['sex'];   // 성별

mysqli_query($conn,"UPDATE member SET age = '$age', sex = '$sex', phone = '$phone', address = '$address' where ID = '$ID'"); //프로필 업데이트

mysqli_close($conn); 

?>

