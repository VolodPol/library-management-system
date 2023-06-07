package com.library.commands;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/*
    1. extract values from Request
    2. set attr/params if necessary
    3. insert attributes in request
 */

/**
 * The class that wraps the content of HttpRequest class for the sake of safe manipulation of request's attributes,
 * parameters and cookies. After class initialization, the class extracts values from request. Has the possibility
 * to set attributes/parameters manually by the corresponding setters. And finally inserts the data to request after
 * the process of request's data handling is finished.
 */
public class RequestContent {
    private final HashMap<String, Object> requestAttributes;
    private final HashMap<String, String[]> requestParameters;
    private final HashMap<String, Object> sessionAttributes;
    private Cookie[] cookies;

    {
        requestAttributes = new HashMap<>();
        requestParameters = new HashMap<>();
        sessionAttributes = new HashMap<>();
    }

    /**
     * Constructor for empty initialization
     */
    public RequestContent() {}

    /**
     * Constructor for complete initialization
     * @param request the object of HttpRequest with the data to retrieve
     */
    public RequestContent(HttpServletRequest request) {
        extractValues(request);
    }

    //Retrieve attributes and params from request and put to fields
    public void extractValues(HttpServletRequest request){
        Enumeration<?> reqAttributeNames  = request.getAttributeNames();
        while (reqAttributeNames.hasMoreElements()) {
            String name = (String) reqAttributeNames.nextElement();
            Object value = request.getAttribute(name);
            requestAttributes.put(name, value);
        }
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String name = paramNames.nextElement();
            String[] values = request.getParameterValues(name);
            requestParameters.put(name, values);
        }
        HttpSession session = request.getSession();
        Enumeration<?> sessionAttributeNames = session.getAttributeNames();
        while (sessionAttributeNames.hasMoreElements()) {
            String name = (String) sessionAttributeNames.nextElement();
            Object value = session.getAttribute(name);
            sessionAttributes.put(name, value);
        }
        cookies = request.getCookies();
    }
    //Put attributes and params from fields to request
    public void insertAttributes(HttpServletRequest request){
        requestAttributes.forEach(request::setAttribute);
        sessionAttributes.forEach((key, value) -> request.getSession().setAttribute(key, value));
    }

    //getters
    @SuppressWarnings("unused")
    public Object getRequestAttribute(String s){
        return requestAttributes.get(s);
    }
    public Object getSessionAttribute(String s){
        return sessionAttributes.get(s);
    }
    public String getParameter(String s) {
        if (requestParameters.get(s) == null) {
            return null;
        }
        return requestParameters.get(s)[0];
    }
    @SuppressWarnings("unused")
    public Map<String, String[]> getParameterMap(){
        return requestParameters;
    }
    @SuppressWarnings("unused")
    public String[] getParameterValues(String s){
        return requestParameters.get(s);
    }
    public Cookie[] getCookies() {
        return cookies;
    }

    public void setRequestAttribute(String name, Object value) {
        requestAttributes.put(name, value);
    }
    public void setSessionAttribute(String name, Object value) {
        sessionAttributes.put(name, value);
    }
    @SuppressWarnings("unused")
    public void setRequestParameters(String name, String ... values) {
        requestParameters.put(name, values);
    }
    @SuppressWarnings("unused")
    public void setCookies(Cookie... cookies) {
        this.cookies = cookies;
    }
    @SuppressWarnings("unused")
    public void removeRequestAttribute(String s) {
        requestAttributes.remove(s);
    }
    public void removeSessionAttribute(String s) {
        sessionAttributes.remove(s);
    }
}
