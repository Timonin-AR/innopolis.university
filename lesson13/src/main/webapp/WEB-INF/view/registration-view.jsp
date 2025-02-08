<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<body>
<h2>Registration</h2>
<br>
<br>
<form:form action="congratulate" modelAttribute="student">
       FIO: <form:input path="fio"/>
       <form:errors path="fio"/>
       <br>
       <br>
       Email: <form:input path="email"/>
       <form:errors path="email"/>
       <br>
       <br>
       <input type="submit" value="register">
</form:form>
</body>
</html>
