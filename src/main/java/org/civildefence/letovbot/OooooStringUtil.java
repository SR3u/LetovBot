package org.civildefence.letovbot;

public class OooooStringUtil
{
    public static String process(String string){
        string = string.replace("o","");
        string = string.replace("о","");
        string = string.replace("!","");
        string = string.replace("о́","");
        string = string.replace("0","");
        string = string.replace("\n","");
        string = string.replace("○","");
        string = string.replace("-","");
        string = string.replace("*","");
        //string = string.replaceAll("\\W", "");
        string = string.trim();
        return string;
    }
}
