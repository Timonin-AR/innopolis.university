<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<body>
<h2>Sign up on course</h2>
<br>
<br>
<form:form action="congratulate" modelAttribute="courseForm" method="POST">
             Course Name:
             <br>
             java: <form:checkbox path="courseName" value="java"/>
             <br>
             jdbc: <form:checkbox path="courseName" value="jdbc"/>
             <br>
             spring: <form:checkbox path="courseName" value="spring"/>
             <br>
             Student ID:       <form:input path="studentId"/>
                               <input type="submit" value="Signup">
</form:form>
</body>
</html>
