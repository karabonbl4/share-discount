package com.senla_ioc.context;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class PropertyScanner {

    private final Map<String, String> properties;

    public PropertyScanner() {
        this.properties = new HashMap<>();
    }

    public void scanProperties(String pathnameProperty) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(pathnameProperty));
            try {
                String str = bufferedReader.readLine();
                while (str != null) {
                    String[] splitedStr = str.split("=");
                    properties.put(splitedStr[0].trim(), splitedStr[1].trim());
                    str = bufferedReader.readLine();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, String> getProperties() {
        return properties;
    }
}

