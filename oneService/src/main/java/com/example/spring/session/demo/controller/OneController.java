package com.example.spring.session.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/springSessionTest")
@RestController
public class OneController {
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public Map<String, Object> firstResp(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        request.getSession().setAttribute("request Url", request.getRequestURL());
        map.put("request Url", request.getRequestURL());
        return map;
    }

    @RequestMapping(value = "/sessions", method = RequestMethod.GET)
    public Object sessions(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sessionId", request.getSession().getId());
        map.put("message", request.getSession().getAttribute("request Url"));
        return map;
    }

}
