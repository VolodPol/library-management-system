package com.library.commands;

import lombok.Getter;

/**
 * Encapsulates the result of ActionCommand and supports PRG pattern.
 */
@Getter
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

    public boolean isSendRedirect() {
        return isSendRedirect;
    }
}
