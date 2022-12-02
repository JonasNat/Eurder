package com.switchfully.eurder.exceptions;

import com.switchfully.eurder.exceptions.item.ItemAlreadyExistsException;
import com.switchfully.eurder.exceptions.item.ItemNotFoundException;
import com.switchfully.eurder.exceptions.order.OrderNotFoundException;
import com.switchfully.eurder.exceptions.security.UnauthorizedException;
import com.switchfully.eurder.exceptions.security.WrongPasswordException;
import com.switchfully.eurder.exceptions.user.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class ControllerExceptionHandler {
    Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler({
            InvalidEmailAddressException.class,
            RequiredFieldIsEmptyException.class,
            CustomerAlreadyExistsException.class,
            UserNotFoundException.class,
            CustomerNotFoundException.class
    })
    protected void UserExceptionHandler(RuntimeException ex, HttpServletResponse response) throws IOException {
        logger.warn(ex.getMessage());
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler({
            ItemAlreadyExistsException.class,
            ItemNotFoundException.class
    })
    protected void ItemExceptionHandler(RuntimeException ex, HttpServletResponse response) throws IOException {
        logger.warn(ex.getMessage());
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler({
            WrongPasswordException.class,
            UnauthorizedException.class
    })
    protected void SecurityExceptionHandler(RuntimeException ex, HttpServletResponse response) throws IOException {
        logger.warn(ex.getMessage());
        response.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
    }

    @ExceptionHandler({
        OrderNotFoundException.class
    })
    protected void orderExceptionHandler(RuntimeException ex, HttpServletResponse response) throws IOException {
        logger.warn(ex.getMessage());
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
    }
}
