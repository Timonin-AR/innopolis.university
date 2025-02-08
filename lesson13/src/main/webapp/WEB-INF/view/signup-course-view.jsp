<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<body>
<h2>Sign up on course</h2>
<br>
<br>
<form:form action="congratulateCourse" modelAttribute="courseForm" method="POST">

             Course Name: 	   <form:input path="courseName"/>
             Student ID:       <form:input path="studentId"/>
                               <input type="submit" value="Signup">
</form:form>
</body>
</html>
