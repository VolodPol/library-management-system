package org.project.commands;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SessionRequestContent {
    private HashMap<String, Object> requestAttributes;
    private HashMap<String, String[]> requestParameters;
    private HashMap<String, Object> sessionAttributes;

    public SessionRequestContent() {
    }

    /*
    Retrieve attributes and params from request and put to fields
     */
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
    /*
     *   Put attributes ands params from field to request
     */
    public void putValues(HttpServletRequest request){
        iterateAndSet(request, requestAttributes.entrySet());
        iterateAndSet(request, sessionAttributes.entrySet());
    }

    //getters

    public HashMap<String, Object> getRequestAttributes() {
        return requestAttributes;
    }

    public HashMap<String, String[]> getRequestParameters() {
        return requestParameters;
    }

    public HashMap<String, Object> getSessionAttributes() {
        return sessionAttributes;
    }

    //

    public void putRequestAttribute(String name, Object value) {
        requestAttributes.put(name, value);
    }
    public void putSessionAttribute(String name, Object value) {
        sessionAttributes.put(name, value);
    }
    public void putRequestParameters(String name, String ... values) {
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
