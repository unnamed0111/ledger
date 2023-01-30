package com.portfolio.ledger.controller.advice;

import com.portfolio.ledger.controller.TableController;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@ControllerAdvice(basePackageClasses = {TableController.class})
@Log4j2
public class CustomAdvice {

    @ExceptionHandler(Exception.class)
    public String commonExceptionHandler(Exception e, RedirectAttributes redirectAttributes) {
        Map<String, String> errorMap = new HashMap<>();

        errorMap.put("errorType", "CommonException");
        errorMap.put("message", "서비스에 에러가 생겼습니다.");
        errorMap.put("time", "" + System.currentTimeMillis());

        redirectAttributes.addFlashAttribute("error", errorMap);

        return "redirect:/table/list";
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String noSuchElementExceptionHandler(Exception e, RedirectAttributes redirectAttributes) {
        Map<String, String> errorMap = new HashMap<>();

        errorMap.put("errorType", "NoSuchElement");
        errorMap.put("message", "해당하는 게시물이 없습니다.");
        errorMap.put("time", "" + System.currentTimeMillis());

        redirectAttributes.addFlashAttribute("errors", errorMap);

        log.info(errorMap);

        return "redirect:/table/list";
    }
}
