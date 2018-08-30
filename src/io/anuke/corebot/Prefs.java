package io.anuke.corebot;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;

import java.io.*;
import java.util.Properties;

public class Prefs {
    private Properties prop;
    private File file;
    private Json json = new Json();

    public Prefs(File file){
        this.file = file;
        try {
            if(!file.exists()) file.createNewFile();
            prop = new Properties();
            prop.load(new FileInputStream(file));
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public Array<String> getArray(String property){
        String value = prop.getProperty(property, "[]");
        return json.fromJson(Array.class, value);
    }

    public void putArray(String property, Array<String> arr){
        prop.put(property, json.toJson(arr));
        save();
    }

    public String get(String property, String def){
        return prop.getProperty(property, def);
    }

    public int getInt(String property, int def){
        return Integer.parseInt(prop.getProperty(property, def+""));
    }

    public void put(String property, String value){
        prop.put(property, value);
        save();
    }

    public void save(){
        try {
            prop.store(new FileOutputStream(file), null);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
