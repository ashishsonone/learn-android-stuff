package com.example.ashish.canvastutorial.utility;

import android.util.Base64;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by ashish on 13/04/16.
 */
public class Base64ConvertorUtility {
    public static final String LOGTAG = "__B64C";
    public static final boolean SHOWLOG = false;

    //====== internals =====
    public static ArrayList<Byte> intToTwoByteArray(int value){
        ArrayList<Byte> bA = new ArrayList<>();
        byte b0 = (byte) (value & 0xff);
        byte b1 = (byte) ((value >> 8) & 0xff);
        bA.add(b0);
        bA.add(b1);
        return bA;
    }

    public static int twoByteArrayToInt(ArrayList<Byte> byteArrayList, int index){
        int b0 = byteArrayList.get(index) & 0xFF; //to prevent int from becoming -ve
        int b1 = byteArrayList.get(index + 1) & 0xFF;

        int value = b0;
        value = value | (b1 << 8);
        return value;
    }

    public static ArrayList<Byte> intArrayToByteStream(ArrayList<Integer> intArrayList){
        ArrayList<Byte> byteStream = new ArrayList<>();
        for(int value : intArrayList){
            ArrayList<Byte> twoByteArray = intToTwoByteArray(value);
            byteStream.addAll(twoByteArray);
        }
        return byteStream;
    }

    public static ArrayList<Integer> byteStreamToIntArray(ArrayList<Byte> byteStream){
        ArrayList<Integer> intArrayList = new ArrayList<>();
        for(int i=0; i < byteStream.size(); i = i + 2){
            int value = twoByteArrayToInt(byteStream, i);
            intArrayList.add(value);
        }
        return intArrayList;
    }

    public static String byteStreamToBase64String(byte[] byteStream){
        byte[] bytesEncoded = Base64.encode(byteStream, Base64.DEFAULT);
        String base64String = new String(bytesEncoded);
        if(SHOWLOG) Log.d(LOGTAG, "byteStreamToBase64String : " + base64String);
        return base64String;
    }

    public static byte[] base64StringToByteStream(String base64String) {
        byte[] byteStream = Base64.decode(base64String, Base64.DEFAULT);

        print(byteStream, "base64StringToByteStream : ");

        return byteStream;
    }

    //======= wrappers ========
    public static String intArrayToBase64String(ArrayList<Integer> intArrayList){
        ArrayList<Byte> byteStream = intArrayToByteStream(intArrayList);
        byte[] byteStreamArray = new byte[byteStream.size()];
        for(int i = 0; i < byteStream.size(); i++){
            byteStreamArray[i] = byteStream.get(i);
        }
        String base64String = byteStreamToBase64String(byteStreamArray);
        return base64String;
    }

    public static ArrayList<Integer> base64StringToIntArray(String base64String){
        byte[] byteStreamArray = base64StringToByteStream(base64String);
        ArrayList<Byte> byteStream = new ArrayList<Byte>();
        for(byte b : byteStreamArray){
            byteStream.add(b);
        }
        ArrayList<Integer> intArray = byteStreamToIntArray(byteStream);
        return intArray;
    }

    public static void test(){
        Integer[] intArray = new Integer[]{23819, 14082, 93, 11373, 1338, 9938, 238, 492, 4232, 633, 1120, 923,
                23819, 14082, 93, 11373, 1338, 9938, 238, 492, 4232, 633, 1120, 923};
        ArrayList<Integer> intArrayList = new ArrayList<>(Arrays.asList(intArray));
        print(intArrayList, "original : ");

        String base64String = intArrayToBase64String(intArrayList);

        ArrayList<Integer> recoveredIntArrayList = base64StringToIntArray(base64String);

        print(recoveredIntArrayList, "recovered : ");
    }

    public static void print(ArrayList arrayList, String prefix){
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for(Object o : arrayList){
            builder.append(o + ",");
        }
        builder.append("]");
        if(SHOWLOG) Log.d(LOGTAG, prefix + builder.toString());
    }

    public static void print(byte[] array, String prefix){
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for(byte o : array){
            int byteValue = (int) (o & 0xFF);
            builder.append(byteValue + ",");
        }
        builder.append("]");
        if(SHOWLOG) Log.d(LOGTAG, prefix + builder.toString());
    }
}
