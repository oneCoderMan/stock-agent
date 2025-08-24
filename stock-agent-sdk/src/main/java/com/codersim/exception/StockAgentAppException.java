package com.codersim.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author： yijun
 * @DATE: 2025/8/23 15:37
 * @Description
 *
 */
public class StockAgentAppException extends RuntimeException {
    private final static Logger logger = LoggerFactory.getLogger(StockAgentAppException.class);

    public StockAgentAppException(String msg) {
        super(msg);
        logger.error("stock agent app exception: {}", msg);
    }


}
