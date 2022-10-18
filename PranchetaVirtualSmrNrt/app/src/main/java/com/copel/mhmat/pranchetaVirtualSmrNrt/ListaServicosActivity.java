package com.copel.mhmat.pranchetaVirtualSmrNrt;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.core.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.copel.mhmat.pranchetaVirtualSmrNrt.adapter.ServicosAdapter;
import com.copel.mhmat.pranchetaVirtualSmrNrt.dao.PranchetaVirtualDAO;
import com.copel.mhmat.pranchetaVirtualSmrNrt.modelo.Servico;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

public class ListaServicosActivity extends AppCompatActivity {



    private ListView listaServicos;

    public static ArrayList<Integer> selecionados;
    private ArrayList<Servico> servicos= new ArrayList<Servico>();
    private PranchetaVirtualDAO dao;

    private FormularioHelper helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lista_servicos);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_lista_servico);
        toolbar.setTitle("PRANCHETA VIRTUAL");

        setSupportActionBar(toolbar);

        carregaRelacaoIps();

        dao = new PranchetaVirtualDAO(this);
        dao.removeAntigos();

        listaServicos = (ListView) findViewById(R.id.lista_servicos);
        listaServicos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int position, long id) {
                Servico servico = (Servico) listaServicos.getItemAtPosition(position);


                Intent intentVaiProFormulario = new Intent(ListaServicosActivity.this, FormularioActivity.class);
                intentVaiProFormulario.putExtra("servico", servico);
                startActivity(intentVaiProFormulario);
            }
        });

        /*listaServicos.setOnTouchListener(new OnSwipeTouchListener(ListaServicosActivity.this) {

            public void onSwipeLeft() {

                //stuff to do list view left swipe
                Toast.makeText(ListaServicosActivity.this, "Tela atualizada", Toast.LENGTH_SHORT).show();
            }



        });*/

        Button pesquisaIp = (Button) findViewById(R.id.botao_ip);
        pesquisaIp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String data = "";

                try {
                    BufferedReader reader = new BufferedReader(new FileReader(new File(getExternalFilesDir(null) + "/relacaoipstotal.txt")));
                    data = reader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Intent intentVaiPraPesquisaIp = new Intent(ListaServicosActivity.this, PesquisaIpActivity.class);
                intentVaiPraPesquisaIp.putExtra("dataatualizacao", data);
                startActivity(intentVaiPraPesquisaIp);

            }
        });

        Button servicoEncerrado = (Button) findViewById(R.id.botao_encerrado);
        servicoEncerrado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentVaiPraListaEncerrados = new Intent(ListaServicosActivity.this, ListaServicosEncerradosActivity.class);
                startActivity(intentVaiPraListaEncerrados);
            }
        });

        Button novoServico = (Button) findViewById(R.id.novo_servico);
        novoServico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentVaiProFormulario = new Intent(ListaServicosActivity.this, FormularioActivity.class);
                startActivity(intentVaiProFormulario);
            }
        });



        registerForContextMenu(listaServicos);
    }

    private void carregaListaAbertos() {

        dao = new PranchetaVirtualDAO(this);
        List<Servico> servicos = dao.buscaServicosAbertos();
        dao.close();
        Collections.reverse(servicos);

        selecionados = new ArrayList<>();
        for(int i = 0; i < servicos.size(); i++){
            selecionados.add(0);
        }

        ServicosAdapter adapter = new ServicosAdapter(this, servicos);
        listaServicos.setAdapter(adapter);



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


    @Override
    protected void onResume() {
        super.onResume();

            carregaListaAbertos();

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Servico servico = (Servico) listaServicos.getItemAtPosition(info.position);

        /*MenuItem itemLigar = menu.add("Ligar");
        itemLigar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (ActivityCompat.checkSelfPermission(ListaServicosActivity.this, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ListaServicosActivity.this,
                            new String[]{Manifest.permission.CALL_PHONE}, 123);
                } else {
                    Intent intentLigar = new Intent(Intent.ACTION_CALL);
                    intentLigar.setData(Uri.parse("tel:" + servico.getTelefone()));
                    startActivity(intentLigar);
                }
                return false;
            }
        });

        MenuItem itemSMS = menu.add("Enviar SMS");
        Intent intentSMS = new Intent(Intent.ACTION_VIEW);
        intentSMS.setData(Uri.parse("sms:" + servico.getTelefone()));
        itemSMS.setIntent(intentSMS);

        MenuItem itemMapa = menu.add("Visualizar no mapa");
        Intent intentMapa = new Intent(Intent.ACTION_VIEW);
        intentMapa.setData(Uri.parse("geo:0,0?q=" + servico.getEquipamento()));
        itemMapa.setIntent(intentMapa);

        MenuItem itemSite = menu.add("Visitar site");
        Intent intentSite = new Intent(Intent.ACTION_VIEW);

         MenuItem itemMapa = menu.add("Visualizar no mapa");
        Intent intentMapa = new Intent(Intent.ACTION_VIEW);
        intentMapa.setData(Uri.parse("geo:0,0?q=" + servico.getEquipamento()));
        itemMapa.setIntent(intentMapa);

        String site = servico.getSite();
        if (!site.startsWith("http://")) {
            site = "http://" + site;
        }

        intentSite.setData(Uri.parse(site));
        itemSite.setIntent(intentSite);*/



        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                AlertDialog alertDialog = new AlertDialog.Builder(ListaServicosActivity.this)

                        .setIcon(android.R.drawable.ic_dialog_alert)

                        .setTitle("DELETAR SERVIÇO!")

                        .setMessage("Você tem certeza que deseja deletar este serviço?")

                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                dao = new PranchetaVirtualDAO(ListaServicosActivity.this);
                                //System.out.println(servico.getData());
                                dao.deleta(servico);
                                dao.close();

                                carregaListaAbertos();

                            }
                        })

                        .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .show();

                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lista_servicos, menu);

        menu.findItem(R.id.menu_lista_servico_share_aberto).getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                servicos = new ArrayList<Servico>();
                Servico s;
                boolean nenhumSelecionado = true;

                for(int i = 0; i < selecionados.size(); i++) {

                    if (selecionados.get(i) == 1) {

                        s = (Servico) listaServicos.getItemAtPosition(i);
                        servicos.add(s);
                        nenhumSelecionado = false;

                    }
                }

                if (nenhumSelecionado){
                    Toast.makeText(ListaServicosActivity.this, "Nenhum serviço foi selecionado!", Toast.LENGTH_SHORT).show();
                }else{
                    geraArquivoPdf(servicos);
                    new emailAssincrono().execute(sendEmail());
                }
            }

        });

        menu.findItem(R.id.menu_lista_servico_encerrado).getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean nenhumSelecionado = true;

                for (int i = 0; i < selecionados.size(); i++) {

                    Servico s;

                    if (selecionados.get(i) == 1) {

                        s = (Servico) listaServicos.getItemAtPosition(i);

                        s.setStatus("encerrado");

                        dao = new PranchetaVirtualDAO(ListaServicosActivity.this);
                        dao.altera(s);
                        dao.close();
                        nenhumSelecionado = false;

                    }

                }

                if (nenhumSelecionado) {
                    Toast.makeText(ListaServicosActivity.this, "Nenhum serviço foi selecionado!", Toast.LENGTH_SHORT).show();
                } else {
                    carregaListaAbertos();
                    Toast.makeText(ListaServicosActivity.this, "Serviços foram marcados como \"Encerrado\".", Toast.LENGTH_SHORT).show();
                }

            }

        });




        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean result = true;
        boolean nenhumSelecionado;
        switch (item.getItemId()) {



            case R.id.menu_lista_servico_cadastrar_colaborador:

                Intent intentVaiPraListaColaboradores = new Intent(ListaServicosActivity.this, ListaColaboradoresActivity.class);
                startActivity(intentVaiPraListaColaboradores);

                break;

            case R.id.menu_lista_servico_enviar_backup:

                dao = new PranchetaVirtualDAO(this);
                servicos = (ArrayList<Servico>) dao.buscaServicos();
                dao.close();
                if (servicos.size() == 0){
                    AlertDialog alertDialog = new AlertDialog.Builder(ListaServicosActivity.this)

                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("OPERAÇÃO CANCELADA!")
                            .setMessage("Não há serviços no banco de dados para realizar o backup. Arigatou.  ")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .show();
                }else{

                    new emailAssincrono().execute(sendEmailBackup(geraArquivoBackup()));

                }

                break;

            case R.id.menu_lista_servico_carregar_backup:

                dao = new PranchetaVirtualDAO(this);
                servicos = (ArrayList<Servico>) dao.buscaServicos();
                dao.close();
                if (servicos.size() != 0){
                    AlertDialog alertDialog = new AlertDialog.Builder(ListaServicosActivity.this)

                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("OPERAÇÃO CANCELADA!")
                            .setMessage("Existem serviços no banco de dados. Ao carregar um backup perde-se todos os serviços. Apague todos os serviços ou reinstale o aplicativo e então carregue o backup. Obrigado.  ")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .show();
                }else{
                    carregarBackup();
                }



                break;

            case R.id.menu_lista_servico_sobre:

                String versionName = null;
                int versionCode = 0;

                try {
                    versionName = ListaServicosActivity.this.getPackageManager()
                            .getPackageInfo(ListaServicosActivity.this.getPackageName(), 0).versionName;
                    versionCode = ListaServicosActivity.this.getPackageManager()
                            .getPackageInfo(ListaServicosActivity.this.getPackageName(), 0).versionCode;
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }

                AlertDialog alertDialog = new AlertDialog.Builder(ListaServicosActivity.this)

                        .setIcon(android.R.drawable.ic_menu_info_details)
                        .setTitle("VERSÃO INSTALADA")
                        .setMessage("Versão: "+versionName+" (VersionCode: "+versionCode+")")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .show();

                break;

            default:
                result = super.onOptionsItemSelected(item);
                break;
        }

        return result;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        if (requestCode == 777 && resultCode == Activity.RESULT_OK) {
            // The result data contains a URI for the document or directory that
            // the user selected.

            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();

                try {
                    copiaArquivoInToArquivoOut(getContentResolver().openInputStream(uri), new FileOutputStream(new File(getExternalFilesDir(null) + "/backupPv_temp")));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            String caminhoArquivo = getExternalFilesDir(null) + "/backupPv_temp";


            try {

                ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(caminhoArquivo));
                servicos = (ArrayList<Servico>) objectInputStream.readObject();
                objectInputStream.close();
                Toast.makeText(ListaServicosActivity.this, "Backup carregado com sucesso!", Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(ListaServicosActivity.this, "Arquivo invalido!", Toast.LENGTH_SHORT).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(ListaServicosActivity.this, "Arquivo invalido!!", Toast.LENGTH_SHORT).show();
            }finally{
                new File(getExternalFilesDir(null) + "/backupPv_temp").delete();

            }

            dao = new PranchetaVirtualDAO(this);
            dao.insereServicos(servicos);
            dao.close();


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

    private String geraArquivoBackup() {

        dao = new PranchetaVirtualDAO(this);
        servicos = (ArrayList<Servico>) dao.buscaServicos();
        dao.close();

        String  caminhoArquivo = "";

        try {
            caminhoArquivo = getExternalFilesDir(null) + "/backupPv"+new SimpleDateFormat("_dd_MM_yyyy").format(new Date());

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(caminhoArquivo));
            objectOutputStream.writeObject(servicos);
            objectOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(ListaServicosActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return caminhoArquivo;
    }

    private void carregarBackup() {

        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("application/octet-stream");
        startActivityForResult(intent,777);



    }


    public void geraArquivoPdf(ArrayList<Servico> servicos) {


        Document document = new Document(PageSize.A4,0,0,10,10);
        //Document document = new Document();

        String  caminhoPdf = getExternalFilesDir(null) + "/ServicoPranchetaVirtual.pdf";

        try {

            PdfWriter.getInstance(document,
                    new FileOutputStream(caminhoPdf));

            document.open();

            for(int i = 0; i < servicos.size(); i++) {

                document.add(FormularioActivity.geraTabelaPdf(servicos.get(i),document));
                if (i != servicos.size()-1) {
                    document.newPage();
                }
            }


        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        document.close();


    }

    private class emailAssincrono extends AsyncTask<Intent, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(Intent... intents) {
            Intent ei = intents[0];
            startActivity(ei);
            return null;
        }
    }




    protected Intent sendEmail() {


        Log.i("Send email", " ");

        //String[] TO = {"mhmatsumura@yahoo.com.br"};

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");



        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Síntese de serviço. ");
        //emailIntent.putExtra(Intent.EXTRA_TEXT,Html.fromHtml(tela));
        //emailIntent.putExtra(Intent.EXTRA_TEXT, tela);


        String  caminhoPdf = getExternalFilesDir(null) + "/ServicoPranchetaVirtual.pdf";
        File arquivoPdf = new File(caminhoPdf);
        Uri pdfURI = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileprovider", arquivoPdf);
        emailIntent.putExtra(Intent.EXTRA_STREAM, pdfURI);

        return emailIntent;

    }

    protected Intent sendEmailBackup(String  caminhoArquivo) {

        //String[] TO = {email1+"@COPEL.COM",email2+"@COPEL.COM"};

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");


        //emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Arquivo de Backup do app PranchetaVirtual. ");
        //emailIntent.putExtra(Intent.EXTRA_TEXT,Html.fromHtml(tela));
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Em anexo arquivo de backup do banco de dados contendo os servicos.");

        File arquivoBackupPv = new File(caminhoArquivo);
        Uri arquivoURI = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileprovider", arquivoBackupPv);
        emailIntent.putExtra(Intent.EXTRA_STREAM, arquivoURI);

        return emailIntent;

    }

}
