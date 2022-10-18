package com.copel.mhmat.pranchetaVirtualSmrNrt;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.copel.mhmat.pranchetaVirtualSmrNrt.adapter.ColaboradoresAdapter;
import com.copel.mhmat.pranchetaVirtualSmrNrt.dao.PranchetaVirtualDAO;
import com.copel.mhmat.pranchetaVirtualSmrNrt.modelo.Colaborador;

import java.util.List;

public class ListaColaboradoresActivity extends AppCompatActivity {

    private ListView listaColaboradores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_colaboradores);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_lista_colaboradores);
        toolbar.setTitle("COLABORADORES");

        setSupportActionBar(toolbar);

        listaColaboradores = (ListView) findViewById(R.id.lista_colaboradores);

        listaColaboradores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int position, long id) {
                Colaborador colaborador = (Colaborador) listaColaboradores.getItemAtPosition(position);

                editaColaborador(colaborador);
            }
        });

        Button novoColaborador = (Button) findViewById(R.id.novo_colaborador);
        novoColaborador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                criaColaborador();
            }
        });

        registerForContextMenu(listaColaboradores);
    }

    private void carregaLista() {

        PranchetaVirtualDAO dao = new PranchetaVirtualDAO (this);
        List<Colaborador> colaboradores = dao.buscaColaboradores();
        dao.close();

        ColaboradoresAdapter adapter = new ColaboradoresAdapter(this, colaboradores);
        listaColaboradores.setAdapter(adapter);

    }

    private void editaColaborador(final Colaborador colaborador){

        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(ListaColaboradoresActivity.this);
        alertDialogBuilder.setTitle("EDITAR COLABORADOR.");

        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_formulario_colaborador, null);


        ((EditText) dialogView.findViewById(R.id.formulario_colaborador_registro)).setText(colaborador.getRegistro());
        ((EditText) dialogView.findViewById(R.id.formulario_colaborador_email)).setText(colaborador.getEmail());

        alertDialogBuilder.setView(dialogView);
        alertDialogBuilder.setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                PranchetaVirtualDAO  dao = new PranchetaVirtualDAO (ListaColaboradoresActivity.this);

                colaborador.setRegistro(((EditText) dialogView.findViewById(R.id.formulario_colaborador_registro)).getText().toString());

                colaborador.setEmail(((EditText) dialogView.findViewById(R.id.formulario_colaborador_email)).getText().toString());

                dao.altera(colaborador);
                dao.close();

                //Toast.makeText(FormularioActivity.this, "Servico " + servico.getData() + " salvo!", Toast.LENGTH_SHORT).show();

                carregaLista();
            }



        });

        alertDialogBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {


            }



        });


        android.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    private void criaColaborador(){

        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(ListaColaboradoresActivity.this);
        alertDialogBuilder.setTitle("CADASTRO DE COLABORADOR.");


        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_formulario_colaborador, null);
        alertDialogBuilder.setView(dialogView);

        alertDialogBuilder.setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                PranchetaVirtualDAO  dao = new PranchetaVirtualDAO (ListaColaboradoresActivity.this);
                Colaborador colaboradorHelper = new Colaborador();

                colaboradorHelper.setRegistro(((EditText) dialogView.findViewById(R.id.formulario_colaborador_registro)).getText().toString());

                colaboradorHelper.setEmail(((EditText) dialogView.findViewById(R.id.formulario_colaborador_email)).getText().toString());

                dao.insere(colaboradorHelper);
                dao.close();

                carregaLista();
            }



        });

        alertDialogBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {


            }

        });


        android.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaLista();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Colaborador colaborador = (Colaborador) listaColaboradores.getItemAtPosition(info.position);


        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                AlertDialog alertDialog = new AlertDialog.Builder(ListaColaboradoresActivity.this)

                        .setIcon(android.R.drawable.ic_dialog_alert)

                        .setTitle("DELETAR COLABORADOR!")

                        .setMessage("Você tem certeza que deseja deletar este colaborador?")

                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                PranchetaVirtualDAO  dao = new PranchetaVirtualDAO (ListaColaboradoresActivity.this);
                                dao.deleta(colaborador);
                                dao.close();

                                carregaLista();

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
        //getMenuInflater().inflate(R.menu.menu_lista_servicos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            //case R.id.menu_lista_servico_cadastrar_colaborador:

                //cadastraColaborador();
               // return true;


            default:
                return super.onOptionsItemSelected(item);
        }

    }

}
