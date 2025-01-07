package mysite.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class ExecutionTimeAspect {

    @Around("execution(* mysite.repository..*(..))") // Repository 패키지의 모든 메서드
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Object result;
        try {
            result = joinPoint.proceed(); // 실제 메서드 실행
        } finally {
            stopWatch.stop();
            long totalTime = stopWatch.getTotalTimeMillis();
            String methodName = joinPoint.getSignature().toShortString();
            System.out.println("[Execution Time][" + methodName + "] " + totalTime + " millis");
        }

        return result; // 메서드 실행 결과 반환
    }
}

