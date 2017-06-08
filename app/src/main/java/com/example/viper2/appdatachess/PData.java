package com.example.viper2.appdatachess;

import java.util.logging.StreamHandler;

/**
 * Created by Viper2 on 07/06/2017.
 */

public class PData {
    String id,puntos_acu;

    public PData() {
    }

    public PData(String id, String puntos_acu) {
        this.id = id;
        this.puntos_acu = puntos_acu;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPuntos_acu() {
        return puntos_acu;
    }

    public void setPuntos_acu(String puntos_acu) {
        this.puntos_acu = puntos_acu;
    }
}
