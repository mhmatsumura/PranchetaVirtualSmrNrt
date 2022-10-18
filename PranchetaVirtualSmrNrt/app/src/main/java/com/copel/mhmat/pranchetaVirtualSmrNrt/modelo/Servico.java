package com.copel.mhmat.pranchetaVirtualSmrNrt.modelo;

import java.io.Serializable;


    public class Servico implements Serializable {

    private Long id;
    private String numeroOs;
    private String executor1;
    private String executor2;
    private String data;
    private String tipoEquipamento;
    private String regiao;
    private String numeroEquipamento;
    private String localidade;
    private String numeroSDS;
    private String numeroOpr;
    private String horaSaida;
    private String horaInicio;
    private String horaTermino;
    private String horaRetorno;
    private String kmInicial;
    private String kmFinal;
    private String opr;
    private String tipoComunicacao;
    private String porta;
    private String dnp;
    private String ip;
    private String chaveIsolamento;
    private String chaveBypass;
    private String pep;
    private String descricaoAtividade;
    private String material1;
    private String quantidadeMaterial1;
    private String material2;
    private String quantidadeMaterial2;
    private String observacao;
    private String status;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroOs() {
        return numeroOs;
    }

    public String getExecutor1() {
        return executor1;
    }

    public void setExecutor1(String executor1) {
        this.executor1 = executor1;
    }

    public String getExecutor2() {
        return executor2;
    }

    public void setExecutor2(String executor2) {
        this.executor2 = executor2;
    }

    public void setNumeroOs(String numeroOs) {
        this.numeroOs = numeroOs;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTipoEquipamento() {
        return tipoEquipamento;
    }

    public void setTipoEquipamento(String tipoEquipamento) {
        this.tipoEquipamento = tipoEquipamento;
    }

    public String getRegiao() {
        return regiao;
    }

    public void setRegiao(String regiao) {
        this.regiao = regiao;
    }

    public String getNumeroEquipamento() {
        return numeroEquipamento;
    }

    public void setNumeroEquipamento(String numeroEquipamento) {
        this.numeroEquipamento = numeroEquipamento;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getNumeroSDS() {
        return numeroSDS;
    }

    public void setNumeroSDS(String numeroSDS) {
        this.numeroSDS = numeroSDS;
    }

    public String getNumeroOpr() {
        return numeroOpr;
    }

    public void setNumeroOpr(String numeroOpr) {
        this.numeroOpr = numeroOpr;
    }

    public String getHoraSaida() {
        return horaSaida;
    }

    public void setHoraSaida(String horaSaida) {
        this.horaSaida = horaSaida;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraTermino() {
        return horaTermino;
    }

    public void setHoraTermino(String horaTermino) {
        this.horaTermino = horaTermino;
    }

    public String getHoraRetorno() {
        return horaRetorno;
    }

    public void setHoraRetorno(String horaRetorno) {
        this.horaRetorno = horaRetorno;
    }

    public String getKmInicial() {
        return kmInicial;
    }

    public void setKmInicial(String kmInicial) {
        this.kmInicial = kmInicial;
    }

    public String getKmFinal() {
        return kmFinal;
    }

    public void setKmFinal(String kmFinal) {
        this.kmFinal = kmFinal;
    }



    public String getTipoComunicacao() {
        return tipoComunicacao;
    }

    public void setTipoComunicacao(String tipoComunicacao) {
        this.tipoComunicacao = tipoComunicacao;
    }

    public String getPorta() {
        return porta;
    }

    public void setPorta(String porta) {
        this.porta = porta;
    }

    public String getDnp() {
        return dnp;
    }

    public void setDnp(String dnp) {
        this.dnp = dnp;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getChaveIsolamento() {
        return chaveIsolamento;
    }

    public void setChaveIsolamento(String chaveIsolamento) {

        this.chaveIsolamento = chaveIsolamento;

    }

        public String getChaveBypass() {
            return chaveBypass;
        }

        public void setChaveBypass(String chaveBypass) {
            this.chaveBypass = chaveBypass;
        }

        public String getOpr() {
            return opr;
        }

        public void setOpr(String opr) {
            this.opr = opr;
        }

        public String getPep() {
        return pep;
    }

    public void setPep(String pep) {
        this.pep = pep;
    }

    public String getDescricaoAtividade() {
        return descricaoAtividade;
    }

    public void setDescricaoAtividade(String descricaoAtividade) {
        this.descricaoAtividade = descricaoAtividade;
    }

    public String getMaterial1() {
        return material1;
    }

    public void setMaterial1(String material1) {
        this.material1 = material1;
    }

    public String getQuantidadeMaterial1() {
        return quantidadeMaterial1;
    }

    public void setQuantidadeMaterial1(String quantidadeMaterial1) {
        this.quantidadeMaterial1 = quantidadeMaterial1;
    }

    public String getMaterial2() {
        return material2;
    }

    public void setMaterial2(String material2) {
        this.material2 = material2;
    }

    public String getQuantidadeMaterial2() {
        return quantidadeMaterial2;
    }

    public void setQuantidadeMaterial2(String quantidadeMaterial2) {
        this.quantidadeMaterial2 = quantidadeMaterial2;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }



/*@Override
    public String toString() {
        return getId() + " - " + getData();
    }*/
}
