package org.project.commands;

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
public class SessionRequestContent {
    private final HashMap<String, Object> requestAttributes;
    private final HashMap<String, String[]> requestParameters;
    private final HashMap<String, Object> sessionAttributes;

    {
        requestAttributes = new HashMap<>();
        requestParameters = new HashMap<>();
        sessionAttributes = new HashMap<>();
    }
    public SessionRequestContent() {
    }
    public SessionRequestContent(HttpServletRequest request) {
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
    }
    //Put attributes and params from fields to request
    public void insertAttributes(HttpServletRequest request){
        requestAttributes.forEach(request::setAttribute);
        sessionAttributes.forEach((key, value) -> request.getSession().setAttribute(key, value));
    }

    //getters
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
    public Map<String, String[]> getParameterMap(){
        return requestParameters;
    }
    public String[] getParameterValues(String s){
        return requestParameters.get(s);
    }
    //

    public void setRequestAttribute(String name, Object value) {
        requestAttributes.put(name, value);
    }
    public void setSessionAttribute(String name, Object value) {
        sessionAttributes.put(name, value);
    }
    public void setRequestParameters(String name, String ... values) {
        requestParameters.put(name, values);
    }

    public void removeRequestAttribute(String s) {
        requestAttributes.remove(s);
    }
    public void removeSessionAttribute(String s) {
        sessionAttributes.remove(s);
    }
}
