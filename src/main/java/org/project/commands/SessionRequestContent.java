package org.project.commands;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/*
    1. extract values from Request
    2. set attr/params if necessary
    3. insert attributes in request
 */
public class SessionRequestContent {
    private HashMap<String, Object> requestAttributes;
    private HashMap<String, String[]> requestParameters;
    private HashMap<String, Object> sessionAttributes;

    {
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
        requestParameters = (HashMap<String, String[]>) request.getParameterMap();

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
        iterateAndSet(request, requestAttributes.entrySet());
        iterateAndSet(request, sessionAttributes.entrySet());
    }

    //getters
    public Object getRequestAttribute(String s){
        return requestAttributes.get(s);
    }
    public Object getSessionAttribute(String s){
        return sessionAttributes.get(s);
    }
    public String getParameter(String s) {
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

    private void iterateAndSet(HttpServletRequest request, Set<Map.Entry<String, Object>> attributes ) {
        for (Map.Entry<String, Object> entry : attributes) {
            String name = entry.getKey();
            Object value = entry.getValue();
            request.setAttribute(name, value);
        }
    }
}
