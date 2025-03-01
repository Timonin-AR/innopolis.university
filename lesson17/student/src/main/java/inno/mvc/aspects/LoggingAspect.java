package inno.mvc.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class LoggingAspect {
    private Long startTime;

    @Pointcut("execution(* registrationStudent(..))")
    private void registrationPointcut() {
    }

    @Pointcut("execution(* signupCourse(..))")
    private void signupCoursePointcut() {
    }

    @Pointcut("execution(* getStudentsHasCourse(..))")
    private void getStudentsHasCoursePointcut() {
    }

    @Pointcut("registrationPointcut() || signupCoursePointcut() || getStudentsHasCoursePointcut()")
    private void allMethodsFromStudentService() {
    }


    @Before("allMethodsFromStudentService()")
    public void loginBeforeAdvice(JoinPoint joinPoint) {
        startTime = System.currentTimeMillis();
        log.debug("Начинаю выполнять метод: {}", joinPoint.getSignature());
    }

    @After("allMethodsFromStudentService()")
    public void loginAfterAdvice(JoinPoint joinPoint) {
        log.debug("Выполнился метод: {} выполнился за {} миллисекунды", joinPoint.getSignature(), System.currentTimeMillis() - startTime);
    }
}
