package com.jingxi.officetest;

import com.jingxi.officetest.ui.TestUI;
import com.jingxi.officetest.util.PropertiesUtil;

public class Main {

    public static void main(String[] args){
        PropertiesUtil.init();
        new TestUI().createPanel().show();
    }
}
