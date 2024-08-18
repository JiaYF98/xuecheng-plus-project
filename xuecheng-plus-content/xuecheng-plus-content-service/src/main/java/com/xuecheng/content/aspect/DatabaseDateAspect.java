package com.xuecheng.content.aspect;

import com.xuecheng.base.annotation.DBAutoFill;
import com.xuecheng.base.enums.DBOperationType;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Aspect
@Component
public class DatabaseDateAspect {
    private final List<DBOperationType> dateOperationType = Arrays.asList(DBOperationType.INSERT, DBOperationType.UPDATE);

    @Pointcut("execution(* com.xuecheng.content.mapper.*.*(..))")
    public void pointCut() {
    }

    @Before("pointCut()")
    public void before(JoinPoint joinPoint) throws NoSuchFieldException, IllegalAccessException {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        DBAutoFill dbAutoFillAnnotation = method.getAnnotation(DBAutoFill.class);
        if (dbAutoFillAnnotation == null) {
            return;
        }

        DBOperationType dbOperationType = dbAutoFillAnnotation.value();

        if (dateOperationType.stream().noneMatch(dateOperationType::contains)) {
            return;
        }

        Object entity = joinPoint.getArgs()[0];
        Class<?> entityType = method.getParameterTypes()[0];
        if (DBOperationType.INSERT.equals(dbOperationType)) {
            Field createDateField = entityType.getDeclaredField("createDate");
            Field changeDateField = entityType.getDeclaredField("changeDate");

            LocalDateTime now = LocalDateTime.now();
            createDateField.setAccessible(true);
            createDateField.set(entity, now);
            changeDateField.setAccessible(true);
            changeDateField.set(entity, now);
        } else if (DBOperationType.UPDATE.equals(dbOperationType)){
            Field changeDateField = entityType.getDeclaredField("changeDate");

            changeDateField.setAccessible(true);
            changeDateField.set(entity, LocalDateTime.now());
        }
    }
}
