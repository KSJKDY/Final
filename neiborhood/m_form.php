<form name=f1 method="post" action=m_insert.php>

<table>
    <tr>
        <td>회원이름 : </td>
        <td><input type=text name="name"></td>
    </tr>

    <tr>
        <td>전화번호 : </td>
        <td><input type=text name="phone"></td>
    </tr>

    <tr>
        <td>남녀성별 : </td>
        <td><input type=radio name=sex value=male>남자
              <input type=radio name=sex value=female>여자</td>
    </tr>

    <tr>
        <td>주소 : </td>
        <td><select name=address1 size=1>
                <option value="서울">서울</option>
                <option value="경기">경기</option>
                <option value="강원">강원</option>
                <option value="충북">충북</option>
                <option value="충남">충남</option>
                <option value="경북">경북</option>
                <option value="경남">경남</option>
                <option value="전남">전남</option>
                <option value="전북">전북</option>
                <option value="제주">제주</option>
            </select>
            <input type=text size=50 name=address2></td>
    </tr>

    <tr>
        <td>나이 : </td>
        <td><input type=text name=age></td>
    </tr>

    <tr>
        <td colspan=2>
            <input type=submit value=입력>
            <input type=reset value=취소>
        </td>
    </tr>

</table>
</form>
