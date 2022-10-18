package com.copel.mhmat.pranchetaVirtualSmrNrt.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.NonNull;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.copel.mhmat.pranchetaVirtualSmrNrt.modelo.Colaborador;
import com.copel.mhmat.pranchetaVirtualSmrNrt.modelo.Servico;

/**
 * Created by alura on 12/08/15.
 */
public class PranchetaVirtualDAO extends SQLiteOpenHelper {

    public PranchetaVirtualDAO(Context context) {
        super(context, "PranchetaVirtualSmrNrt", null, 3);
        System.out.println("Entrei no construtor");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {




        String sql = "CREATE TABLE IF NOT EXISTS Colaboradores (id INTEGER PRIMARY KEY, " +
                "registro TEXT , " +
                "email TEXT);";

        db.execSQL(sql);

        db.execSQL("INSERT INTO Colaboradores (registro, email) VALUES ('47739', 'anderson.almeida')");
        db.execSQL("INSERT INTO Colaboradores (registro, email) VALUES ('41829', 'anderson.favaro')");
        db.execSQL("INSERT INTO Colaboradores (registro, email) VALUES ('50433', 'charles.fogato')");
        db.execSQL("INSERT INTO Colaboradores (registro, email) VALUES ('45814', 'claudinei.furquim')");
        db.execSQL("INSERT INTO Colaboradores (registro, email) VALUES ('50435', 'fabiano.gaspar')");
        db.execSQL("INSERT INTO Colaboradores (registro, email) VALUES ('51982', 'fernando.roosewelt')");
        db.execSQL("INSERT INTO Colaboradores (registro, email) VALUES ('51695', 'giancarlo.pedroso')");
        db.execSQL("INSERT INTO Colaboradores (registro, email) VALUES ('42291', 'mauricio.matsumura')");
        db.execSQL("INSERT INTO Colaboradores (registro, email) VALUES ('41579', 'osvaldo.gimenez')");
        db.execSQL("INSERT INTO Colaboradores (registro, email) VALUES ('49120', 'samuel.nascimento')");
        db.execSQL("INSERT INTO Colaboradores (registro, email) VALUES ('43247', 'willian.roberto')");

        sql = "CREATE TABLE IF NOT EXISTS Servicos (id INTEGER PRIMARY KEY, " +
                "numeroOs TEXT , " +
                "executor1 TEXT , " +
                "executor2 TEXT , " +
                "data INTEGER , " +
                "tipoEquipamento TEXT, " +
                "regiao TEXT, " +
                "numeroEquipamento TEXT, " +
                "localidade TEXT, " +
                "numeroSDS TEXT, " +
                "numeroOpr TEXT, " +
                "horaSaida TEXT, " +
                "horaInicio TEXT, " +
                "horaTermino TEXT, " +
                "horaRetorno TEXT, " +
                "kmInicial TEXT, " +
                "kmFinal TEXT, " +
                "opr TEXT, " +
                "tipoComunicacao TEXT, " +
                "porta TEXT, " +
                "dnp TEXT, " +
                "ip TEXT, " +
                "chaveIsolamento TEXT, " +
                "chaveBypass TEXT, " +
                "pep TEXT, " +
                "descricaoAtividade TEXT, " +
                "material1 TEXT, " +
                "quantidadeMaterial1 TEXT, " +
                "material2 TEXT, " +
                "quantidadeMaterial2 TEXT, " +
                "observacao TEXT, " +
                "status TEXT);";

        db.execSQL(sql);




    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        /*String sql = "";
        switch (oldVersion) {
            case 1:
                sql = "ALTER TABLE Servicos ADD COLUMN caminhoFoto TEXT";
                db.execSQL(sql); // indo para versao 2
        }*/



        db.execSQL("DROP TABLE IF EXISTS Servicos");
        db.execSQL("DROP TABLE IF EXISTS Colaboradores");



        this.onCreate(db);


    }
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS Servicos");
        db.execSQL("DROP TABLE IF EXISTS Colaboradores");



        this.onCreate(db);

    }


    public void insere(Servico servico) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = pegaDadosDoServico(servico);

        db.insert("Servicos", null, dados);

    }

    public void insereServicos(ArrayList<Servico> servicos) {

        SQLiteDatabase db = getWritableDatabase();

        for(Servico s:servicos){
            ContentValues dados = pegaDadosDoServico(s);
            db.insert("Servicos", null, dados);
        }

    }

    public void removeAntigos() {

        SQLiteDatabase db = getWritableDatabase();

        String[] params = {""+(1000*((new Date().getTime()/1000)-15552000))};
        db.delete("Servicos", "data < ?", params);


    }




    @NonNull
    private ContentValues pegaDadosDoServico(Servico servico) {
        ContentValues dados = new ContentValues();

        dados.put("numeroOs", servico.getNumeroOs());
        dados.put("executor1", servico.getExecutor1());
        dados.put("executor2", servico.getExecutor2());
        try {
            dados.put("data", new SimpleDateFormat("dd/MM/yyyy").parse(servico.getData()).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dados.put("tipoEquipamento", servico.getTipoEquipamento());
        dados.put("regiao", servico.getRegiao());
        dados.put("numeroEquipamento", servico.getNumeroEquipamento());
        dados.put("localidade", servico.getLocalidade());
        dados.put("numeroSDS", servico.getNumeroSDS());
        dados.put("numeroOpr", servico.getNumeroOpr());
        dados.put("horaSaida", servico.getHoraSaida());
        dados.put("horaInicio", servico.getHoraInicio());
        dados.put("horaTermino", servico.getHoraTermino());
        dados.put("horaRetorno", servico.getHoraRetorno());
        dados.put("kmInicial", servico.getKmInicial());
        dados.put("kmFinal", servico.getKmFinal());
        dados.put("opr",servico.getOpr());
        dados.put("tipoComunicacao", servico.getTipoComunicacao());
        dados.put("porta", servico.getPorta());
        dados.put("dnp", servico.getDnp());
        dados.put("ip", servico.getIp());
        dados.put("chaveIsolamento", servico.getChaveIsolamento());
        dados.put("chaveBypass", servico.getChaveBypass());
        dados.put("pep", servico.getPep());
        dados.put("material1", servico.getMaterial1());
        dados.put("quantidadeMaterial1", servico.getQuantidadeMaterial1());
        dados.put("material2", servico.getMaterial2());
        dados.put("quantidadeMaterial2", servico.getQuantidadeMaterial2());
        dados.put("observacao", servico.getObservacao());
        dados.put("status", servico.getStatus());

        return dados;
    }

    public List<Servico> buscaServicos() {

        String sql = "SELECT * FROM Servicos;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        return preencheLista(c);
    }

    public List<Servico> buscaServicosEncerrados() {

        String sql = "SELECT * FROM Servicos WHERE status='encerrado' ;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        return preencheLista(c);
    }

    public List<Servico> buscaServicosAbertos() {

        String sql = "SELECT * FROM Servicos WHERE status='aberto' ;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        return preencheLista(c);
    }

    public List<Servico> preencheLista(Cursor c ){

    List<Servico> servicos = new ArrayList<Servico>();
        while (c.moveToNext()) {
        Servico servico = new Servico();

        servico.setId(c.getLong(c.getColumnIndex("id")));
        servico.setNumeroOs(c.getString(c.getColumnIndex("numeroOs")));
        servico.setExecutor1(c.getString(c.getColumnIndex("executor1")));
        servico.setExecutor2(c.getString(c.getColumnIndex("executor2")));
        servico.setData(new SimpleDateFormat("dd/MM/yyyy").format(c.getLong(c.getColumnIndex("data"))));
        servico.setTipoEquipamento(c.getString(c.getColumnIndex("tipoEquipamento")));
        servico.setRegiao(c.getString(c.getColumnIndex("regiao")));
        servico.setNumeroEquipamento(c.getString(c.getColumnIndex("numeroEquipamento")));
        servico.setLocalidade(c.getString(c.getColumnIndex("localidade")));
        servico.setNumeroSDS(c.getString(c.getColumnIndex("numeroSDS")));
        servico.setNumeroOpr(c.getString(c.getColumnIndex("numeroOpr")));
        servico.setHoraSaida(c.getString(c.getColumnIndex("horaSaida")));
        servico.setHoraInicio(c.getString(c.getColumnIndex("horaInicio")));
        servico.setHoraTermino(c.getString(c.getColumnIndex("horaTermino")));
        servico.setHoraRetorno(c.getString(c.getColumnIndex("horaRetorno")));
        servico.setKmInicial(c.getString(c.getColumnIndex("kmInicial")));
        servico.setKmFinal(c.getString(c.getColumnIndex("kmFinal")));
        servico.setOpr(c.getString(c.getColumnIndex("opr")));
        servico.setTipoComunicacao(c.getString(c.getColumnIndex("tipoComunicacao")));
        servico.setPorta(c.getString(c.getColumnIndex("porta")));
        servico.setDnp(c.getString(c.getColumnIndex("dnp")));
        servico.setIp(c.getString(c.getColumnIndex("ip")));
        servico.setChaveIsolamento(c.getString(c.getColumnIndex("chaveIsolamento")));
        servico.setChaveBypass(c.getString(c.getColumnIndex("chaveBypass")));
        servico.setPep(c.getString(c.getColumnIndex("pep")));
        servico.setDescricaoAtividade(c.getString(c.getColumnIndex("descricaoAtividade")));
        servico.setMaterial1(c.getString(c.getColumnIndex("material1")));
        servico.setQuantidadeMaterial1(c.getString(c.getColumnIndex("quantidadeMaterial1")));
        servico.setMaterial2(c.getString(c.getColumnIndex("material2")));
        servico.setQuantidadeMaterial2(c.getString(c.getColumnIndex("quantidadeMaterial2")));
        servico.setObservacao(c.getString(c.getColumnIndex("observacao")));
        servico.setStatus(c.getString(c.getColumnIndex("status")));

        servicos.add(servico);
    }
        c.close();

        return servicos;
}



    public void deleta(Servico servico) {
        SQLiteDatabase db = getWritableDatabase();

        String[] params = {servico.getId().toString()};
        db.delete("Servicos", "id = ?", params);
    }

    public void altera(Servico servico) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = pegaDadosDoServico(servico);

        String[] params = {servico.getId().toString()};
        db.update("Servicos", dados, "id = ?", params);
    }

    public void insere(Colaborador colaborador) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = pegaDadosDoColaborador(colaborador);

        db.insert("Colaboradores", null, dados);

    }




    @NonNull
    private ContentValues pegaDadosDoColaborador(Colaborador colaborador) {

        ContentValues dados = new ContentValues();

        dados.put("registro", colaborador.getRegistro());
        dados.put("email", colaborador.getEmail());


        return dados;
    }

    public List<Colaborador> buscaColaboradores() {

        Log.d("log de depuração","buscando colaboradores");

        String sql = "SELECT * FROM Colaboradores;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        List<Colaborador> colaboradores = new ArrayList<Colaborador>();

        while (c.moveToNext()) {
            Colaborador colaborador = new Colaborador();

            colaborador.setId(c.getLong(c.getColumnIndex("id")));
            colaborador.setRegistro(c.getString(c.getColumnIndex("registro")));

            colaborador.setEmail(c.getString(c.getColumnIndex("email")));


            colaboradores.add(colaborador);
        }
        c.close();

        return colaboradores;
    }

    public void deleta(Colaborador colaborador) {
        SQLiteDatabase db = getWritableDatabase();

        String[] params = {colaborador.getId().toString()};
        db.delete("Colaboradores", "id = ?", params);
    }

    public void altera(Colaborador colaborador) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = pegaDadosDoColaborador(colaborador);

        String[] params = {colaborador.getId().toString()};
        db.update("Colaboradores", dados, "id = ?", params);
    }
}
