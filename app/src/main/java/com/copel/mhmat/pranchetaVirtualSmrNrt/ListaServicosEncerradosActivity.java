package com.copel.mhmat.pranchetaVirtualSmrNrt;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.copel.mhmat.pranchetaVirtualSmrNrt.adapter.ServicosAdapter;
import com.copel.mhmat.pranchetaVirtualSmrNrt.dao.PranchetaVirtualDAO;
import com.copel.mhmat.pranchetaVirtualSmrNrt.modelo.Servico;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.core.view.MenuItemCompat;

public class ListaServicosEncerradosActivity extends AppCompatActivity {

    private ListView listaServicosEncerrados;

    public static ArrayList<Integer> selecionados;
    private ArrayList<Servico> servicos= new ArrayList<Servico>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lista_servicos_encerrados);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_lista_servico_encerrado);
        toolbar.setTitle("SERVIÇOS ENCERRADOS");

        setSupportActionBar(toolbar);

        listaServicosEncerrados = (ListView) findViewById(R.id.lista_servicos_encerrados);
        listaServicosEncerrados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int position, long id) {
                Servico servico = (Servico) listaServicosEncerrados.getItemAtPosition(position);

                Intent intentVaiProFormulario = new Intent(ListaServicosEncerradosActivity.this, FormularioActivity.class);
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

        registerForContextMenu(listaServicosEncerrados);

    }



    private void carregaListaEncerrados() {

        PranchetaVirtualDAO dao = new PranchetaVirtualDAO(this);
        List<Servico> servicos = dao.buscaServicosEncerrados();
        dao.close();
        Collections.reverse(servicos);

        ServicosAdapter adapter = new ServicosAdapter(this, servicos);
        listaServicosEncerrados.setAdapter(adapter);

        selecionados = new ArrayList<>();
        for(int i = 0; i < servicos.size(); i++){
            selecionados.add(0);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();


            carregaListaEncerrados();

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Servico servico = (Servico) listaServicosEncerrados.getItemAtPosition(info.position);

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

                AlertDialog alertDialog = new AlertDialog.Builder(ListaServicosEncerradosActivity.this)

                        .setIcon(android.R.drawable.ic_dialog_alert)

                        .setTitle("DELETAR SERVIÇO!")

                        .setMessage("Você tem certeza que deseja deletar este serviço?")

                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                PranchetaVirtualDAO dao = new PranchetaVirtualDAO(ListaServicosEncerradosActivity.this);
                                dao.deleta(servico);
                                dao.close();

                                carregaListaEncerrados();

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
        getMenuInflater().inflate(R.menu.menu_lista_servicos_encerrados, menu);

        MenuItemCompat.getActionView(menu.findItem(R.id.menu_lista_servico_aberto)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean nenhumSelecionado = true;

                for(int i = 0; i < selecionados.size(); i++){

                    Servico s;

                    if(selecionados.get(i)==1){

                        s = (Servico) listaServicosEncerrados.getItemAtPosition(i);

                        s.setStatus("aberto");

                        PranchetaVirtualDAO dao = new PranchetaVirtualDAO(ListaServicosEncerradosActivity.this);
                        dao.altera(s);
                        dao.close();
                        nenhumSelecionado = false;

                    }

                }

                if (nenhumSelecionado){
                    Toast.makeText(ListaServicosEncerradosActivity.this, "Nenhum serviço foi selecionado!", Toast.LENGTH_SHORT).show();
                }else{
                    carregaListaEncerrados();
                    Toast.makeText(ListaServicosEncerradosActivity.this, "Serviços foram marcados como \"Em Aberto\".", Toast.LENGTH_SHORT).show();
                }
            }
         });

        MenuItemCompat.getActionView(menu.findItem(R.id.menu_lista_servico_share_encerrado)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                servicos = new ArrayList<Servico>();
                Servico s;
                boolean nenhumSelecionado = true;

                for(int i = 0; i < selecionados.size(); i++) {

                    if (selecionados.get(i) == 1) {

                        s = (Servico) listaServicosEncerrados.getItemAtPosition(i);
                        servicos.add(s);
                        nenhumSelecionado = false;

                    }
                }

                if (nenhumSelecionado){
                    Toast.makeText(ListaServicosEncerradosActivity.this, "Nenhum serviço foi selecionado!", Toast.LENGTH_SHORT).show();
                }else{
                    geraArquivoPdf(servicos);
                    new emailAssincrono().execute(sendEmail());
                }
            }
        });


        return true;
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

}
