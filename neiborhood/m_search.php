<?php

include 'dbinfo.inc';            //설정파일 불러오기
$sql="select*from member order by name";
$sql_result=mysql_query($sql,$conn);

$count=mysql_num_rows($sql_result);

echo ("<table border =1 width = 700>
<tr>
	<td>회원명</td>
	<td>전화번호</td>
	<td>성별</td>
	<td>주소</td>
	<td>나이</td>
    <td>ID</td>
    <td>사줌</td>
    <td>얻어먹음</td>
	</tr>");

for($i=0;$i<$count;$i++)
{
	$result_array=mysql_fetch_array($sql_result);
echo("<tr>
	<td>$result_array[name]</td>

	<td>$result_array[phone]</td>

	<td>$result_array[sex]</td>

	<td>$result_array[address]</td>

	<td>$result_array[age]</td>
    <td>$result_array[ID]</td>
    <td>$result_array[count]</td>
    <td>$result_array[count1]</td>
	</tr>");
}

echo("</table>");

$sql="select*from location order by name";
$sql_result=mysql_query($sql,$conn);

$count=mysql_num_rows($sql_result);

echo ("<table border =1 width = 700>
<tr>
	<td>회원명</td>
	<td>경도</td>
	<td>위도</td>
    <td>타입</td>
    <td>ID</td>
	</tr>");

for($i=0;$i<$count;$i++)
{
	$result_array=mysql_fetch_array($sql_result);
echo("<tr>

    <td>$result_array[name]</td>
	<td>$result_array[lat]</td>

	<td>$result_array[log]</td>
    <td>$result_array[type]</td>
    <td>$result_array[ID]</td>
	</tr>");
}

echo("</table>");

$sql="select*from p order by name";
$sql_result=mysql_query($sql,$conn);

echo ("<table border =1 width = 700>
<tr>
	<td>회원명</td>
	<td>yourID</td>
	<td>reg_id</td>
	<td>ID</td>
	</tr>");

for($i=0;$i<$count;$i++)
{
	$result_array=mysql_fetch_array($sql_result);
echo("<tr>
	<td>$result_array[name]</td>

	<td>$result_array[yourID]</td>

	<td>$result_array[reg_id]</td>

	<td>$result_array[ID]</td>

	</tr>");
}

echo("</table>");

$sql="select*from test order by name";
$sql_result=mysql_query($sql,$conn);

echo ("<table border =1 width = 700>
<tr>
	<td>회원명</td>
	<td>status</td>
	<td>reg_id</td>
	<td>s_reg_id</td>
	<td>g_reg_id</td>
	</tr>");

for($i=0;$i<$count;$i++)
{
	$result_array=mysql_fetch_array($sql_result);
echo("<tr>
	<td>$result_array[name]</td>

	<td>$result_array[status]</td>

	<td>$result_array[reg_id]</td>

	<td>$result_array[s_reg_id]</td>

	<td>$result_array[g_reg_id]</td>
	</tr>");
}

echo("</table>");
echo ("<a href='index.php'>초기화면으로</a>");
?>


