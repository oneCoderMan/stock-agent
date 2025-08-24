package com.codersim.util;

import com.github.rholder.retry.RetryListener;
import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import com.github.rholder.retry.WaitStrategies;
import com.google.common.base.Predicates;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * @Author： yijun
 * @DATE: 2023/9/16 20:37
 * @Description 重试工具类
 * <dependency>
 *       <groupId>com.github.rholder</groupId>
 *       <artifactId>guava-retrying</artifactId>
 *       <version>2.0.0</version>
 *     </dependency>
 */
public class RetryUtil {
    /**
     * 初始化重试实例
     * @param sleepTimeInMs  重试间隔时间，单位：毫秒
     * @param retryTimes 失败重试次数
     */
    public static <T> Retryer<T> getGuavaRetryer(Long sleepTimeInMs,
                                                 Integer retryTimes,
                                                 boolean exponential){
        if (exponential) {
            Retryer<T> retryer = RetryerBuilder.<T>newBuilder()
                    .retryIfException()    //抛出runtime异常、checked异常时都会重试，但是抛出error不会重试。
                    .retryIfResult(Predicates.equalTo(null))    //返回null也需要重试
                    .withWaitStrategy(WaitStrategies.exponentialWait(1, TimeUnit.HOURS))    //重调策略
                    .withStopStrategy(StopStrategies.stopAfterAttempt(retryTimes))        //尝试次数
                    .build();
            return retryer;
        }
        Retryer<T> retryer = RetryerBuilder.<T>newBuilder()
                .retryIfException()    //抛出runtime异常、checked异常时都会重试，但是抛出error不会重试。
                .retryIfResult(Predicates.equalTo(null))    //返回null也需要重试
                .withWaitStrategy(WaitStrategies.fixedWait(sleepTimeInMs, TimeUnit.MILLISECONDS))    //重调策略
                .withStopStrategy(StopStrategies.stopAfterAttempt(retryTimes))        //尝试次数
                .build();
        return retryer;
    }

    /**
     * 初始化重试实例
     * @param sleepTimeInMs  重试间隔时间，单位：毫秒
     * @param retryTimes 失败重试次数
     * @param retryListener 重试监听实例
     */
    public static <T> Retryer<T> getGuavaRetryer(Long sleepTimeInMs,
                                                 Integer retryTimes,
                                                 boolean exponential,
                                                 RetryListener retryListener){
        if (exponential) {
            Retryer<T> retryer = RetryerBuilder.<T>newBuilder()
                    .retryIfException()    //抛出runtime异常、checked异常时都会重试，但是抛出error不会重试。
                    .retryIfResult(Predicates.equalTo(null))    //返回null也需要重试
                    .withWaitStrategy(WaitStrategies.exponentialWait(1, TimeUnit.HOURS))
                    .withStopStrategy(StopStrategies.stopAfterAttempt(retryTimes))   //尝试次数
                    .withRetryListener(retryListener)
                    .build();
            return retryer;
        }
        Retryer<T> retryer = RetryerBuilder.<T>newBuilder()
                .retryIfException()    //抛出runtime异常、checked异常时都会重试，但是抛出error不会重试。
                 .retryIfResult(Predicates.equalTo(null))    //返回null也需要重试
                .withWaitStrategy(WaitStrategies.fixedWait(sleepTimeInMs, TimeUnit.MILLISECONDS))    //重调策略
                .withStopStrategy(StopStrategies.stopAfterAttempt(retryTimes))   //尝试次数
                .withRetryListener(retryListener)
                .build();
        return retryer;
    }


    /**
     *
     * @param callable    执行的操作
     * @param retryTimes  最大重试次数（>1）
     * @param sleepTimeInMillSecond 运行失败后休眠对应时间再重试(ms)
     * @return
     * @param <T>
     */
    public static <T> T executeWithRetry(Callable<T> callable,
                                         int retryTimes,
                                         long sleepTimeInMillSecond,
                                         boolean exponential)
            throws Exception {
        Retryer<T> retryer = getGuavaRetryer(sleepTimeInMillSecond, retryTimes, exponential);
        T result = retryer.call(callable);
        return result;
    }

    /**
     *
     * @param callable    执行的操作
     * @param retryTimes  最大重试次数（>1）
     * @param exponential 休眠时间是否指数递增
     * @param sleepTimeInMillSecond 运行失败后休眠对应时间再重试(ms)
     * @return
     * @param <T>
     * src/test/java/com/java/study/javastudy/utilTest/RetryUtilTest.java
     */
    public static <T> T executeWithRetry(Callable<T> callable,
                                         int retryTimes,
                                         long sleepTimeInMillSecond,
                                         boolean exponential,
                                         RetryListener retryListener)
            throws Exception {
        Retryer<T> retryer = getGuavaRetryer(sleepTimeInMillSecond, retryTimes,
                exponential, retryListener);
        T result = retryer.call(callable);
        return result;
    }
}
