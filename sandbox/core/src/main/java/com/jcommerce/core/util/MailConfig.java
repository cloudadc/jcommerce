/**
* Author: Bob Chen
*/

package com.jcommerce.core.util;

public class MailConfig {
    private String user;
    private String password;
    private String smtpServer;
    private String pop3Server;
    private boolean needauthentication;
    private String senderAddress;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSmtpServer() {
        return smtpServer;
    }

    public void setSmtpServer(String smtpServer) {
        this.smtpServer = smtpServer;
    }

    public String getPop3Server() {
        return pop3Server;
    }

    public void setPop3Server(String pop3Server) {
        this.pop3Server = pop3Server;
    }

    public boolean isNeedauthentication() {
        return needauthentication;
    }

    public void setNeedauthentication(boolean needauthentication) {
        this.needauthentication = needauthentication;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }
}
