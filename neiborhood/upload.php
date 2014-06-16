<?php

include 'dbinfo.inc'; 

$target_path = "/var/www/html/upload/";


$tmp_img = explode(".", $_FILES['uploadedfile']['name']); //확장자와 파일이름으로 나눈다.

$img_name = $tmp_img[0] . "_" . date('Y-m-d_H_i_s') . "." . $tmp_img[1]; //파일이름에 현제 날짜와시간을 붙인다.


$name=$_POST[''];


$target_path = $target_path . basename($img_name); //업로드할 전체 경로명을 완성한다.

echo($target_path);

if (move_uploaded_file($_FILES['uploadedfile']['tmp_name'], $target_path)) {

echo ("{\"result\":\"success\",\"msg\":\"The file " . $img_name . " has been uploaded \"} ");

} else {

echo ("{\"result\":\"failed\",\"msg\":\"The file " . $img_name . " has not been uploaded \"} ");

//echo ("There was an error uploading the file, please try again!" . $target_path);

}

$sql="insert into im values('$name, $target_path')";

mysql_query($sql, $conn);        //sql 질의 수행.

?>
