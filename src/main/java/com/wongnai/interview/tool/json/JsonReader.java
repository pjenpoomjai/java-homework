package com.wongnai.interview.tool.json;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonReader {

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static ArrayList<String> changeStringToArrayList(JSONObject object, String key) throws JSONException {
        ArrayList<String> a = new ArrayList<>();
        String words = object.get(key).toString();
        String wordsWithOutMark = words.substring(1,object.get(key).toString().length()-1);
        String wordsWithOutMarkWithOutQutation = wordsWithOutMark.replaceAll("\"","");
        ArrayList<String> wordsArray = new ArrayList<>();
        for (String genre : wordsWithOutMarkWithOutQutation.split(",")
        ) {
            wordsArray.add(genre);
        }

        return wordsArray;
    }

    public static ArrayList<JSONObject> readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            ArrayList<JSONObject> jsonObjectArrayList = new ArrayList<>();
            String copyString = jsonText;
            while (copyString.indexOf("{")!= -1){
                int indexFirst = copyString.indexOf("{");
                int indexLast = copyString.indexOf("}");

                JSONObject json = new JSONObject(copyString.substring(indexFirst,indexLast+1));

                jsonObjectArrayList.add(json);
                if (indexLast+1 >= copyString.length()){
                    break;
                }
                copyString = copyString.substring(indexLast+1);
//                System.out.println(json);
            }







            return jsonObjectArrayList;
        } finally {
            is.close();
        }
    }

}