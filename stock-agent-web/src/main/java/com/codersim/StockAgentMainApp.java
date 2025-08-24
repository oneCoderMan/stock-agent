package com.codersim;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.StopWatch;

/**
 * @Author： yijun
 * @DATE: 2025/8/22 23:03
 * @Description
 *
 */
@SpringBootApplication
public class StockAgentMainApp {
    public static void main(String[] args) {
        StopWatch stopwatch = new StopWatch("run StockAgentMainApp");
        stopwatch.start();
        SpringApplication.run(StockAgentMainApp.class, args);
        stopwatch.stop();
        System.out.println(stopwatch.prettyPrint());
        System.out.println("=========== run StockAgentMainApp success ============");
    }
}
