package com.copel.mhmat.pranchetaVirtualSmrNrt;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class PesquisaIpActivity extends AppCompatActivity {

    private EditText editTextIpEquipamento,editTextIpObservacao;
    private TextView textViewDataAtualizacao;
    private RadioGroup locais;
    private String regiao,equipamento,dataAtualizacao;
    public static final int IP_ATUALIZACAO = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ip);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_pesquisa_ip);
        toolbar.setTitle("DADOS DE COMUNICAÇÃO");

        setSupportActionBar(toolbar);

        carregaRelacaoIps();

        Intent intent = getIntent();
        dataAtualizacao = (String) intent.getSerializableExtra("dataatualizacao");

        textViewDataAtualizacao = findViewById(R.id.ip_update);
        textViewDataAtualizacao.setText("Dados atualizados em: "+dataAtualizacao);

        locais = (RadioGroup) findViewById((R.id.radioGroupIpLocais));
        locais.check(R.id.rblocal1);
        locais.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

              public void onCheckedChanged(RadioGroup group, int chekedId) {

                  if (!editTextIpEquipamento.getText().toString().equals("")) {
                      switch (chekedId) {
                          case R.id.rblocal1:
                              regiao = "redelna";
                              break;
                          case R.id.rblocal2:
                              regiao = "redeapa";
                              break;
                          case R.id.rblocal3:
                              regiao = "redecpo";
                              break;
                          case R.id.rblocal4:
                              regiao = "satlna";
                              break;
                      }
                      equipamento = editTextIpEquipamento.getText().toString();
                      editTextIpObservacao.setText(pesquisarIp(PesquisaIpActivity.this,regiao, equipamento,getExternalFilesDir(null) + "/relacaoipstotal.txt"));
                  }
              }
        });


        editTextIpObservacao = (EditText) findViewById(R.id.ip_observacao);

        editTextIpEquipamento = (EditText) findViewById(R.id.ip_equipamento);

        editTextIpEquipamento.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                        if (!editTextIpEquipamento.getText().toString().equals("")){
                            switch (locais.getCheckedRadioButtonId()){
                                case R.id.rblocal1: regiao = "redelna"; break;
                                case R.id.rblocal2: regiao = "redeapa"; break;
                                case R.id.rblocal3: regiao = "redecpo"; break;
                                case R.id.rblocal4: regiao = "satlna";  break;
                            }
                            equipamento = editTextIpEquipamento.getText().toString();
                            editTextIpObservacao.setText(pesquisarIp(PesquisaIpActivity.this,regiao,equipamento,getExternalFilesDir(null) + "/relacaoipstotal.txt"));
                        }

                }
                return false;
            }
        });




    }


    public void carregaRelacaoIps() {

        try {

            File txtFile = new File(getExternalFilesDir(null) + "/relacaoipstotal.txt");
            if (!txtFile.exists()){
                copiaAssetsParaExternal(getAssets().open("relacaoipstotal.txt"), new FileOutputStream(txtFile));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void copiaAssetsParaExternal(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }

    public static String pesquisarIp(Context context,String regiao, String equipamento,String path) {
        String resultado;
        StringBuilder sb = new StringBuilder();

        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(new File(path)));

            // do reading, usually loop until end of file reading
            String Line;
            boolean inicio = false;

            while ((Line = reader.readLine()) != null) {
                if (Line.equals("#&%"+regiao+"fim")){
                    break;
                }
                if (inicio){

                   if(Line.contains(equipamento)){

                       sb.append(Line);
                       sb.append("\n\n");
                   }
                }
                if (Line.equals("#&%"+regiao)){

                   inicio = true;
                }
            }
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }

        if (sb.toString().equals("")){

            Toast.makeText(context, "Equipamento não encontrado.Tente sem os zeros.", Toast.LENGTH_SHORT).show();

        }

        return sb.toString();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pesquisa_ip, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean result = true;

        switch (item.getItemId()) {

            case R.id.menu__pesquisa_ip_atualizar:

                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.setType("text/plain");
                startActivityForResult(intent,IP_ATUALIZACAO);

                break;

            default:
                result = super.onOptionsItemSelected(item);
                break;
        }

        return result;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        if (requestCode == IP_ATUALIZACAO  && resultCode == AppCompatActivity.RESULT_OK) {
            // The result data contains a URI for the document or directory that
            // the user selected.


            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();

                try {
                    copiaArquivoInToArquivoOut(getContentResolver().openInputStream(uri), new FileOutputStream(new File(getExternalFilesDir(null) + "/tempPv.txt")));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            BufferedReader reader = null;

            try {

                reader = new BufferedReader(new FileReader(new File(getExternalFilesDir(null) + "/tempPv.txt")));

                String data = reader.readLine();
                String segundaLinha = reader.readLine();

                if (segundaLinha.contains("#&%")){
                    new SimpleDateFormat("dd/MM/yyyy").parse(data);
                    copiaArquivoInToArquivoOut(getContentResolver().openInputStream(uri), new FileOutputStream(new File(getExternalFilesDir(null) + "/relacaoipstotal.txt")));
                    textViewDataAtualizacao.setText("Dados atualizados em: "+data);
                    Toast.makeText(PesquisaIpActivity.this, "Atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(PesquisaIpActivity.this, "Arquivo invalido!!", Toast.LENGTH_SHORT).show();
                }

            }catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(PesquisaIpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }catch (ParseException e) {
                e.printStackTrace();
                Toast.makeText(PesquisaIpActivity.this, "Data invalida no arquivo!", Toast.LENGTH_SHORT).show();
            }finally{
                new File(getExternalFilesDir(null) + "/tempPv.txt").delete();
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    private void copiaArquivoInToArquivoOut(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
        in.close();
        out.close();
    }

}
