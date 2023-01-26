package org.project.commands;

public class CommandResult {
    private String destinationPage;
    private boolean isSendRedirect;

    public CommandResult(){
    }

    public CommandResult (String page){
        destinationPage = page;
        isSendRedirect = false;
    }

    public CommandResult (String page, boolean isRedirect){
        destinationPage = page;
        isSendRedirect = isRedirect;
    }

    public String getDestinationPage() {
        return destinationPage;
    }

    public void setDestinationPage(String destinationPage) {
        this.destinationPage = destinationPage;
    }

    public boolean isSendRedirect() {
        return isSendRedirect;
    }

    public void setSendRedirect(boolean sendRedirect) {
        isSendRedirect = sendRedirect;
    }


}
