<?php
    
$host="localhost";
$user="root";
$password="1234qwer";

$conn=mysqli_connect($host, $user, $password, "r");        //mysql 연결 설정

// Check connection
if (mysqli_connect_errno())
  {
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  }

$result = mysqli_query($conn, "SELECT name, age, count1 from member");   // 조건 질의
$return_array = array();            

while($r = mysqli_fetch_assoc($result))
  {
      $return_array['test'][] = $r;
  }

echo json_encode($return_array);  // json형식으로 웹에 출력

mysqli_free_result($result);
mysqli_close($conn);


?>
