package com.nlp.serialport.utils;

import android.util.Log;


/**
 * Created by zdd on 2019/9/6
 */
public class HexUtil {
    private static final String TAG = HexUtil.class.getSimpleName();

    private HexUtil(){

    }

    /**
     * hex字符串转byte数组
     * @param inHex 待转换的Hex字符串
     * @return  转换后的byte数组结果
     */
    public static byte[] hexToByteArray(String inHex){
        int hexlen = inHex.length();
        byte[] result;
        if (hexlen % 2 == 1){
            //奇数
            hexlen++;
            result = new byte[(hexlen/2)];
            inHex="0"+inHex;
        }else {
            //偶数
            result = new byte[(hexlen/2)];
        }
        int j=0;
        for (int i = 0; i < hexlen; i+=2){
            result[j]=hexToByte(inHex.substring(i,i+2));
            j++;
        }
        return result;
    }

    public static byte hexToByte(String inHex){
        return (byte)Integer.parseInt(inHex,16);
    }

    public static String integerToHexString(int s) {
        String ss = Integer.toHexString(s);
        if (ss.length() % 2 != 0) {
            ss = "0" + ss;
        }
        return ss.toUpperCase();
    }

    /**
     * byte数组转hex字符串
     */
    public static String toHexString(byte[] data) {
        if (data == null) {
            return "";
        }

        String string;
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < data.length; i++) {
            string = Integer.toHexString(data[i] & 0xFF);
            if (string.length() == 1) {
                stringBuilder.append("0");
            }

            stringBuilder.append(string.toUpperCase());
        }

        return stringBuilder.toString();
    }


    public static boolean checkFCS(String dataPack) {
        byte[] responseBytes = HexUtil.hexToByteArray(dataPack);

//        byte[] prefix = new byte[1];
//        byte[] type = new byte[1];
//        byte[] transId = new byte[1];
//        byte[] lenth = new byte[2];
//        byte[] requestId = new byte[2];
//        byte[] data = new byte[413];
        byte[] fcs = new byte[1];
//        System.arraycopy(responseBytes, 0, prefix, 0, 1);
//        System.arraycopy(responseBytes, 1, type, 0, 1);
//        System.arraycopy(responseBytes, 2, transId, 0, 1);
//        System.arraycopy(responseBytes, 3, lenth, 0, 2);
//        System.arraycopy(responseBytes, 5, requestId, 0, 2);
//        System.arraycopy(responseBytes, 7, data, 0, 413);
        System.arraycopy(responseBytes, 420, fcs, 0, 1);

        byte calculateFCS = xorBytes(responseBytes);

        Log.d(TAG,"fcs[0]=" + fcs[0] + " ||calculateFCS=" + calculateFCS);
        if (fcs[0] == calculateFCS){
            Log.d(TAG,"fcs校验成功=======================================================");
            return true;
        }else{
            Log.d(TAG,"fcs校验失败============================================================");
            return false;
        }



    }

    public static byte xorBytes(byte[] datas) {
        byte temp = (byte)0xFF;
        for (int i = 1; i <= 419; i++) {
            temp ^= datas[i];
        }
        return temp;
    }
}
