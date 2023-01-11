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
    public SessionRequestContent(HashMap<String, Object> requestAttributes,
                                 HashMap<String, String[]> requestParameters,
                                 HashMap<String, Object> sessionAttributes) {
        this.requestAttributes = requestAttributes;
        this.requestParameters = requestParameters;
        this.sessionAttributes = sessionAttributes;
    }

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
    public void putValues(HttpServletRequest request){
        iterateAndSet(request, requestAttributes.entrySet());
        iterateAndSet(request, sessionAttributes.entrySet());
    }

    //getters
    public Object getRequestAttribute(String key){
        return requestAttributes.get(key);
    }

    public Object getSessionAttributes(String key){
        return sessionAttributes.get(key);
    }

    public String[] getParameters(Object key) {
        return requestParameters.get((String) key);
    }
    //


    private void iterateAndSet(HttpServletRequest request, Set<Map.Entry<String, Object>> attributes ) {
        for (Map.Entry<String, Object> entry : attributes) {
            String name = entry.getKey();
            Object value = entry.getValue();
            request.setAttribute(name, value);
        }
    }
}
