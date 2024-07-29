package com.jingxi.officetest.observers;

public class MessageEvent {

    public static OnMessageAppend onMessageAppend;
    public static void setOnMessageAppend(OnMessageAppend onMessageAppend) {
        MessageEvent.onMessageAppend = onMessageAppend;
    }

    public static void append(String message){
        if(onMessageAppend == null){
            return;
        }
        onMessageAppend.onMessage(message);
    }

    public static void update(String oldMessage,String newMessage){
        if(onMessageAppend == null){
            return;
        }
        onMessageAppend.updateMessage(oldMessage,newMessage);
    }

    public interface OnMessageAppend{
        void onMessage(String message);
        void updateMessage(String oldMessage,String newMessage);
    }
}
