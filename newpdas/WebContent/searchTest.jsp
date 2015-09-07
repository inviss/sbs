<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>query test</title>
</head>
<body>

<form method="get" action="getData.jsp">
        <table>
        <tr>
                <td valign="top">scn </td>
                <td>
                        : <input type="text" name="scn" size="50" value="program"/><br>
                        &nbsp;&nbsp;ex) annot <br>
                        &nbsp;&nbsp;ex) corner <br>
                        &nbsp;&nbsp;ex) program <br>
                        &nbsp;&nbsp;ex) picture <br>
                        &nbsp;&nbsp;ex) tape <br>
                </td>
        </tr>
        <tr>
                <td valign="top">query</td>
                <td>
                        : <input type="text" name="query" size="50" value="text_idx='프랑스' allword"/><br>
                        &nbsp;&nbsp;ex) text_idx='ì¸í°ë·' allword<br>
                        &nbsp;&nbsp;ex) title='ì¸í°ë·'<br>
                        &nbsp;&nbsp;ex) text_idx='ì¸í°ë·' allword order by regdate asc
                </td>
        </tr>
        <tr>
                <td valign="top">pageNum</td>
                <td>
                        : <input type="text" name="pageNum" size="50" value="1"/><br>
                        &nbsp;&nbsp;ex) 1
                </td>
        </tr>
        <tr>
                <td valign="top">pageSize</td>
                <td>
                        : <input type="text" name="pageSize" size="50" value="10"/><br>
                        &nbsp;&nbsp;ex) 10
                </td>
        </tr>
        <tr>
                <td colspan="2"><br></td>
        </tr>
        <tr>
                <td valign="top">root tag</td>
                <td>
                        : <input type="text" name="root" size="50" value="root"/><br>
                        &nbsp;&nbsp;ex) root
                </td>
        </tr>
        <tr>
                <td valign="top">wrapper tag</td>
                <td>
                        : <input type="text" name="wrapper" size="50" value="sample"/><br>
                        &nbsp;&nbsp;ex) sample<br>
                        &nbsp;&nbsp;ex) sbs
                </td>
        </tr>
        <tr>
                <td colspan="2" align="right"><input type="submit" value="Search"/></td>
        </tr>
        </table>
</form>

</body>
</html>