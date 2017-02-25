package com.chailijun.mtime.utils;


public class MsgEvent {

//    private static final int WHAT = 1;
    private Object msg;
    private Object msg1;
    private Object msg2;
    /**消息标识*/
    private int what;

    public MsgEvent() {
    }

    public MsgEvent(Object msg) {
        this.msg = msg;
    }

    public Object getMsg1() {
        return msg1;
    }

    public void setMsg1(Object msg1) {
        this.msg1 = msg1;
    }

    public Object getMsg2() {
        return msg2;
    }

    public void setMsg2(Object msg2) {
        this.msg2 = msg2;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

    public int getWhat() {
        return what;
    }

    public void setWhat(int what) {
        this.what = what;
    }
}
