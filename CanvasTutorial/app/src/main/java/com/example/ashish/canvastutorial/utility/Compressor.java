package com.example.ashish.canvastutorial.utility;

import android.util.Log;

import com.diogoduailibe.lzstring4j.LZString;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ashish on 4/12/16.
 */
public class Compressor {
    public static String compress(String s){
        // Normal Compression and Decompression
        //String test = "{\"_id\":\"56e651efe50241d22cb89214\",\"updatedAt\":\"2016-03-15T15:10:24.122Z\",\"createdAt\":\"2016-03-14T05:53:51.000Z\",\"publishDate\":\"2016-03-01T00:00:00.000Z\",\"imageMobile\":\"https://storage.googleapis.com/public-prod-preppo/news/fomj2r4g3_1.jpg\",\"imageWeb\":\"https://storage.googleapis.com/public-prod-preppo/news/7zyoc2lwk_1.jpg\",\"content\":{\"hindi\":{\"heading\":\"\",\"points\":[]},\"english\":{\"heading\":\"Delhi’s IGI airport retains world’s number 1 position\",\"points\":[\"It retained it&#39;s position for the second consecutive year in 25-40 Million Passengers Per Annum (MPPA) category.\"]}}}";

        String output = LZString.compress(s);
        return output;
    }

    public static String decompress(String s){
        String decompressed = LZString.decompress(s);
        return decompressed;
    }

    public static void test(){
        // Normal Compression and Decompression
        String test = "{\"_id\":\"56e651efe50241d22cb89214\",\"updatedAt\":\"2016-03-15T15:10:24.122Z\",\"createdAt\":\"2016-03-14T05:53:51.000Z\",\"publishDate\":\"2016-03-01T00:00:00.000Z\",\"imageMobile\":\"https://storage.googleapis.com/public-prod-preppo/news/fomj2r4g3_1.jpg\",\"imageWeb\":\"https://storage.googleapis.com/public-prod-preppo/news/7zyoc2lwk_1.jpg\",\"content\":{\"hindi\":{\"heading\":\"\",\"points\":[]},\"english\":{\"heading\":\"Delhi’s IGI airport retains world’s number 1 position\",\"points\":[\"It retained it&#39;s position for the second consecutive year in 25-40 Million Passengers Per Annum (MPPA) category.\"]}}}";

        String output = compress(test);

        Log.d("__Compressor", "Compressed: " + output + " " + output.length());

        String decompressed = decompress(output);

        Log.d("__Compressor", "Decompressed: " + decompressed + " " + decompressed.length());

        Log.d("__Compressor", test.compareTo(decompressed) + "");

        try{
            JSONObject jT = new JSONObject(test);
            Log.d("__Compressor", "original _id=" + jT.getString("_id"));

            JSONObject jD = new JSONObject(decompressed);
            Log.d("__Compressor", "decompressed _id=" + jD.getString("_id"));
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }
}
