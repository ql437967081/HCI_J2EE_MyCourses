package edu.nju.wsql.beans;

public class InfoBean {
    private String title;
    private String h1;
    private String content;
    private String returnUrl;

    public InfoBean() {
    }

    public InfoBean(String title, String h1, String content, String returnUrl) {
        this.title = title;
        this.h1 = h1;
        this.content = content;
        this.returnUrl = returnUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getH1() {
        return h1;
    }

    public void setH1(String h1) {
        this.h1 = h1;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }
}
