package com.example.viper2.appdatachess;

/**
 * Created by Viper2 on 06/06/2017.
 */

public class GameFData {
    String id,id_b,id_n,result;

    public GameFData() {
    }

    public GameFData(String id, String id_b, String id_n, String result) {
        this.id = id;
        this.id_b = id_b;
        this.id_n = id_n;
        this.result = result;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_b() {
        return id_b;
    }

    public void setId_b(String id_b) {
        this.id_b = id_b;
    }

    public String getId_n() {
        return id_n;
    }

    public void setId_n(String id_n) {
        this.id_n = id_n;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
