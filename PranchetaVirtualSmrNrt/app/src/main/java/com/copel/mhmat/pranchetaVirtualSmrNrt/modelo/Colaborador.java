package com.copel.mhmat.pranchetaVirtualSmrNrt.modelo;

import java.io.Serializable;


public class Colaborador implements Serializable {

    private Long id;
    private String registro;

    private String email;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegistro() {
        return registro;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}




