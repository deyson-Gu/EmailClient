package com.exercise.p.emailclient.dto.data;

/**
 * Created by p on 2017/12/12.
 */

public class MailBoxResponse {
    private int id;
    private String account;
    private String password;
    private String smtpServer;
    private String smtpPort;
    private String pop3Server;
    private String pop3Port;
    private String alias;


    public MailBoxResponse() {
        this.account = "";
        this.password = "";
        this.smtpServer = "";
        smtpPort = "";
        this.pop3Server = "";
        pop3Port = "";
        alias = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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

    public String getSmtpPort() {
        return smtpPort;
    }

    public void setSmtpPort(String smtpPort) {
        this.smtpPort = smtpPort;
    }

    public String getPop3Server() {
        return pop3Server;
    }

    public void setPop3Server(String pop3Server) {
        this.pop3Server = pop3Server;
    }

    public String getPop3Port() {
        return pop3Port;
    }

    public void setPop3Port(String pop3Port) {
        this.pop3Port = pop3Port;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
