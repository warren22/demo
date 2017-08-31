package com.blf.monitor;


import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 
 * @author 万明
 *
 */
@Aspect
@Component
public class StorageMonitor {
    private static final Logger LOGGER = LoggerFactory.getLogger(StorageMonitor.class);

    @Before("execution(* StorageController.uploadDocument *)")
    public void uploadDocumentMonitor()
        throws Throwable {
        LOGGER.debug("In uploadDocumentMonitor");
        /*StopWatch watch = new StopWatch();

        watch.start();
        Object uploadResult = proceedingJoinPoint.proceed();
        watch.stop();

        LOGGER.info("Uploadexec time : {}", watch.toString());
        return (String) uploadResult;*/
    }

}
