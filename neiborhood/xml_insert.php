<?php
    
include 'dbinfo.inc';

$name = $_REQUEST[name];
$price = $_REQUEST[price];

$qry = "insert into xml(name, price) values('$name', '$price');";
$result = mysql_query($qry);

$xmlcode = "<?xml version = \"1.0\" encoding = \"utf-8\" ?>\n";
$xmlcode .= "<result>$result</result>\n";

$dir = "/var/www/html/xml/";  // xml파일을 저장할 경로
$filename = $dir."insertresult.xml";

file_put_contents($filename, $xmlcode); //xmlcode의 내용을 xml 파일로 출력

?>

