package com.codersim.chatclient;

import com.codersim.common.Result;
import com.codersim.exception.StockAgentAppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author： yijun
 * @DATE: 2025/8/23 15:40
 * @Description
 *
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Result<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.error(ex.getMessage(), ex);
        return Result.failed("Invalid params: " + ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(StockAgentAppException.class)
    public Result<?> handleStockExceptions(Exception ex) {
        log.error(ex.getMessage(), ex);
        return Result.failed("Agent Exception: " + ex.getMessage());
    }


    /**
     * 处理空指针异常
     */
    @ResponseBody
    @ExceptionHandler(NullPointerException.class)
    public Result<?> handleNullPointerExceptions(NullPointerException ex) {
        log.error(ex.getMessage(), ex);
        return Result.failed("空指针异常，请检查数据完整性");
    }

    /**
     * 处理所有未被特定方法捕获的异常
     * 这是一个兜底的异常处理方法
     * @ResponseBody 这个注解必须要要，不然发生错误会陷入死循环
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Result<?> handleAllExceptions(Exception ex) {
        log.error(ex.getMessage(), ex);
        // 返回通用错误信息，避免暴露敏感的系统信息
        return Result.failed("系统发生未知错误，请联系管理员");
    }
}
