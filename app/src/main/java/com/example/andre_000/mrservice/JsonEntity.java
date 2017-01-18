package com.example.andre_000.mrservice;

/**
 * Created by andre_000 on 11/22/2015.
 */

import java.lang.reflect.Field;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class JsonEntity {
    /**
     * Llena el objeto con los valores obtenidos de un string json (el nombre de las variables y los key del json deben coincidir)
     *
     * @param json
     * @throws JSONException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    public void FillFromJson(String json) throws JSONException, IllegalAccessException, IllegalArgumentException {
        JSONObject o = new JSONObject(json);

        for(Field f : this.getClass().getDeclaredFields()) {
            f.set(this, o.get(f.getName()));
        }
    }
}

