package com.library.commands;

/**
 * Encapsulates the result of ActionCommand and supports PRG pattern.
 */
public class ActionResult {
    private String destinationPage;
    private boolean isSendRedirect;

    public ActionResult(){
    }

    public ActionResult(String page){
        destinationPage = page;
        isSendRedirect = false;
    }

    public ActionResult(String page, boolean isRedirect){
        destinationPage = page;
        isSendRedirect = isRedirect;
    }

    public String getDestinationPage() {
        return destinationPage;
    }

    public boolean isSendRedirect() {
        return isSendRedirect;
    }
}
