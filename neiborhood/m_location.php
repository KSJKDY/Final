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

$locationLat = $_POST['locationLat'];       // 추가할 경도
$locationLog = $_POST['locationLog'];    // 추가할 위도
$name = $_POST['name'];

echo $locationLat;
echo $locationLog;


/* insert 구문 */

mysqli_query($conn,"UPDATE location SET lat = '$locationLat', log = '$locationLog' where name = '$name'");

mysqli_close($conn);                //db 연결 종료. ram 에서 삭제.

echo "등록 완료. 축하드립니다. <br>";
echo ("<a href='index.php'>초기화면으로</a>");

?>
