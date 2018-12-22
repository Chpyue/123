package com.example.demo.utils;

/**
 * @ProjectName: demo
 * @Package: com.example.demo.utils
 * @ClassName: ${TYPE_NAME}
 * @Description: java类作用描述
 * @Author: yueChunPeng
 * @CreateDate: 2018-12-21 15:30
 * @UpdateUser: Neil.Zhou
 * @UpdateDate: 2018-12-21 15:30
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */


public class Msg {
    private String title;
    private String content;
    private String etraInfo;

    public Msg(String title, String content, String etraInfo) {
        super();
        this.title = title;
        this.content = content;
        this.etraInfo = etraInfo;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getEtraInfo() {
        return etraInfo;
    }
    public void setEtraInfo(String etraInfo) {
        this.etraInfo = etraInfo;
    }

}
