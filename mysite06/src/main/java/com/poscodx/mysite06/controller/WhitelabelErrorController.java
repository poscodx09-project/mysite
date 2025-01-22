package com.poscodx.mysite06.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/error")
public class WhitelabelErrorController implements ErrorController {
    // from global exceptionhandler
    @RequestMapping("/404")
    public String error_404(){
        return "errors/404";
    }
    @RequestMapping("/500")
    public String error_500(){
        return "errors/exception";
    }

    // from white label
    @ResponseBody
    @RequestMapping("")
    public String handleError(HttpServletRequest request){
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if(status != null){

            int code = Integer.parseInt(status.toString());
            if(code == HttpStatus.NOT_FOUND.value()) {
                return "errors/404";
            } else if (code == HttpStatus.BAD_REQUEST.value()) {
                return "errors/400";
            } else if (code == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "errors/500";
            }

        }
        return "errors/500";
    }

}
