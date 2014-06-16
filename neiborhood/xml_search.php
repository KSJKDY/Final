<?php
    
include 'dbinfo.inc';
    
$qry = "select * from xml";
$result = mysql_query($qry);

$xmlcode = "<?xml version = \"1.0\" encoding = \"utf-8\"?>\n";

while($obj = mysql_fetch_object($result))
{
	$name = $obj->name;
	$price = $obj->price;

	$xmlcode .= "<node>\n"; //여러개가 나올 경우 xml에서 구분하기 쉽게 하기 위해서 node로 구분
	$xmlcode .= "<name>$name</name>\n";
	$xmlcode .= "<price>$price</price>\n";
	$xmlcode .= "</node>\n";
}

$dir = "/var/www/html/xml/";
$filename = $dir."searchresult.xml";

file_put_contents($filename, $xmlcode); //xmlcode의 내용을 xml 파일로 출력

?>
