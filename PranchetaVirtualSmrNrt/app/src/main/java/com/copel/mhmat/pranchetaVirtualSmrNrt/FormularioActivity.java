package com.copel.mhmat.pranchetaVirtualSmrNrt;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.copel.mhmat.pranchetaVirtualSmrNrt.dao.PranchetaVirtualDAO;
import com.copel.mhmat.pranchetaVirtualSmrNrt.modelo.Colaborador;
import com.copel.mhmat.pranchetaVirtualSmrNrt.modelo.Servico;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.core.content.FileProvider;


public class FormularioActivity extends AppCompatActivity {

    public static final int CODIGO_CAMERA = 567;
    private FormularioHelper helper;

    private ArrayAdapter adp;
    private Spinner spinner_regioes,spinner_cidades,spinner_equipamentos,spinner_comunicacao,spinner_dnp,spinner_descricao,
            spinner_material1,spinner_material2,spinner_executor1,spinner_executor2,spinner_frases;
    private static SharedPreferences preferencesSpinner;
    private SharedPreferences.Editor editorPreferencesSpinner;
    private EditText editTextEqp,editTextData,editTextSaida,editTextInicio,editTextTermino,editTextRetorno,
            editTextOpr,editTextOS,editTextSds,editTextPep,editTextQuantidade1,editTextQuantidade2,
            editTextExecutor1,editTextExecutor2,editTextObservacao,editTextKmInicial,editTextIpEquipamento;
    private Button botaoPesquisarIp;

    private Servico servico;
    private PranchetaVirtualDAO dao;
    private AlertDialog alertDialog,alertDialog2;

    public boolean isContinua() {
        return continua;
    }

    public void setContinua(boolean continua) {
        this.continua = continua;
    }

    boolean continua = true;

    private TextView textView;
    //private static final String[] regioes = new String[]{" ","84800","80492","82136"};

    private AutoCompleteTextView actvcidades;

    private static List cidadesArray = new ArrayList<String>();
    private boolean usuarioInteragindo = false;



    public static SharedPreferences getPreferencias() {

        return preferencesSpinner;
    }

    static Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 15);
    static Font font_bold = FontFactory.getFont(FontFactory.TIMES_BOLD, 15);


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_formulario_servico);
        toolbar.setTitle("SERVIÇO");

        setSupportActionBar(toolbar);

        preferencesSpinner = getSharedPreferences("preferenciasSpinner", MODE_PRIVATE);
        editorPreferencesSpinner = preferencesSpinner.edit();


        //inicializaArrayCidades();

        actvcidades = (AutoCompleteTextView) findViewById(R.id.actv_cidades);

        String[] cidades = getResources().getStringArray(R.array.Cidades);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,cidades);
        actvcidades.setAdapter(adapter);

        spinner_regioes = (Spinner) findViewById(R.id.spinner_regiao);

        editTextKmInicial = (EditText) findViewById(R.id.formulario_Kmi);
        editTextKmInicial.setText("0");

        editTextQuantidade1 = (EditText) findViewById(R.id.formulario_quantidade1);
        editTextQuantidade2 = (EditText) findViewById(R.id.formulario_quantidade2);

        editTextObservacao = (EditText) findViewById(R.id.formulario_observacao);

        editTextOpr = (EditText) findViewById(R.id.formulario_opr);
        editTextOpr.addTextChangedListener(Mask.insert("####/####", editTextOpr));


        editTextPep = (EditText) findViewById(R.id.formulario_pep);
        editTextPep.addTextChangedListener(Mask.insert("I##-#######", editTextPep));


        editTextData = (EditText) findViewById(R.id.formulario_data);
        editTextData.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        editTextData.addTextChangedListener(Mask.insert("##/##/####", editTextData));
        editTextData.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {

                editTextData.setText("");
                editTextData.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));


                return false;
            }
        });


        editTextSaida = (EditText) findViewById(R.id.formulario_saida);
        editTextSaida.addTextChangedListener(Mask.insert("##:##", editTextSaida));
        editTextSaida.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {


                editTextSaida.setText("");
                editTextSaida.setText(new SimpleDateFormat("HH:mm").format(new Date()));

                return false;
            }
        });

        editTextInicio = (EditText) findViewById(R.id.formulario_inicio);
        editTextInicio.addTextChangedListener(Mask.insert("##:##", editTextInicio));
        editTextInicio.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {

                editTextInicio.setText("");
                editTextInicio.setText(new SimpleDateFormat("HH:mm").format(new Date()));


                return false;
            }
        });

        editTextTermino = (EditText) findViewById(R.id.formulario_termino);
        editTextTermino.addTextChangedListener(Mask.insert("##:##", editTextTermino));
        editTextTermino.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {

                editTextTermino.setText("");
                editTextTermino.setText(new SimpleDateFormat("HH:mm").format(new Date()));


                return false;
            }
        });

        editTextRetorno = (EditText) findViewById(R.id.formulario_retorno);
        editTextRetorno.addTextChangedListener(Mask.insert("##:##", editTextRetorno));
        editTextRetorno.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {

                editTextRetorno.setText("");
                editTextRetorno.setText(new SimpleDateFormat("HH:mm").format(new Date()));


                return false;

            }
        });

        editTextIpEquipamento = (EditText) findViewById(R.id.formulario_equipamento);


        botaoPesquisarIp = (Button) findViewById(R.id.formulario_botao_pesquisar_ip);
        botaoPesquisarIp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String regiao = "";

                if (spinner_regioes.getSelectedItem().toString().equals("")) {
                    Toast.makeText(FormularioActivity.this, "Região não selecionada!", Toast.LENGTH_SHORT).show();
                }else if (editTextIpEquipamento.getText().toString().equals("")) {
                    Toast.makeText(FormularioActivity.this, "Qual o número do equipamento?", Toast.LENGTH_SHORT).show();
                }else{
                    switch (spinner_regioes.getSelectedItem().toString()){
                        case "84800": regiao = "redelna"; break;
                        case "80492": regiao = "redeapa"; break;
                        case "82160": regiao = "redecpo"; break;
                    }

                    AlertDialog.Builder builder = new AlertDialog.Builder(FormularioActivity.this);
                    builder.setIcon(android.R.drawable.ic_dialog_info);
                    builder.setTitle("PESQUISA DE DADOS DE COMUNICAÇÃO.");
                    builder.setMessage(PesquisaIpActivity.pesquisarIp(FormularioActivity.this, regiao, editTextIpEquipamento.getText().toString(), getExternalFilesDir(null) + "/relacaoipstotal.txt"));
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.setNegativeButton("PESQUISAR NA REDE SATÉLITE.", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String regiao = "satlna";
                            alertDialog2 = new AlertDialog.Builder(FormularioActivity.this)

                                    .setIcon(android.R.drawable.ic_dialog_info)
                                    .setTitle("PESQUISA NA REDE SATÉLITE.")
                                    .setMessage(PesquisaIpActivity.pesquisarIp(FormularioActivity.this, regiao, editTextIpEquipamento.getText().toString(), getExternalFilesDir(null) + "/relacaoipstotal.txt"))
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                        }
                                    })
                                    .setNegativeButton("VOLTAR", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            alertDialog.show();
                                        }
                                    })
                                    .show();

                        }
                    });
                    alertDialog = builder
                            .show();

                }

            }
        });

        populaSpinner(spinner_regioes,getResources().getStringArray(R.array.Regioes),R.id.spinner_regiao,R.layout.meu_spinner_dropdown,R.layout.meu_spinner);
        populaSpinner(spinner_equipamentos,getResources().getStringArray(R.array.Tipo_Eqp),R.id.spinner_tipoEqp,R.layout.meu_spinner_dropdown,R.layout.meu_spinner);
        populaSpinner(spinner_comunicacao,getResources().getStringArray(R.array.Tipo_Comunicacao),R.id.spinner_comunicação,R.layout.meu_spinner_dropdown,R.layout.meu_spinner);
        populaSpinner(spinner_dnp,getResources().getStringArray(R.array.Dnps),R.id.spinner_dnp,R.layout.meu_spinner_dropdown,R.layout.meu_spinner);
        populaSpinner(spinner_descricao,getResources().getStringArray(R.array.Descricoes),R.id.spinner_descricao,R.layout.spinner_dropdown_left,R.layout.meu_spinner);
        populaSpinner(spinner_frases,getResources().getStringArray(R.array.Frases),R.id.spinner_frases,R.layout.spinner_dropdown_left,R.layout.meu_spinner);
        populaSpinner(spinner_material1,getResources().getStringArray(R.array.Materiais),R.id.spinner_material1,R.layout.meu_spinner_dropdown,R.layout.meu_spinner);
        populaSpinner(spinner_material2,getResources().getStringArray(R.array.Materiais),R.id.spinner_material2,R.layout.meu_spinner_dropdown,R.layout.meu_spinner);

        populaSpinner(spinner_executor1,carregaDescricaoColaboradores(),R.id.spinner_executor1,R.layout.spinner_dropdown_left,R.layout.meu_spinner_left);
        populaSpinner(spinner_executor2,carregaDescricaoColaboradores(),R.id.spinner_executor2,R.layout.spinner_dropdown_left,R.layout.meu_spinner_left);

        editTextExecutor1 = (EditText) findViewById(R.id.formulario_executor1);
        editTextExecutor2 = (EditText) findViewById(R.id.formulario_executor2);

        spinner_executor1 = (Spinner) findViewById(R.id.spinner_executor1);
        spinner_executor1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                if (usuarioInteragindo) {

                    if(!editTextExecutor2.getText().toString().equals(spinner_executor1.getSelectedItem().toString())||
                            spinner_executor1.getSelectedItem().toString().equals("")&&
                                    (!spinner_executor1.getSelectedItem().toString().equals("SELECIONE:"))){

                        editTextExecutor1.setText(spinner_executor1.getSelectedItem().toString());

                    }else{
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Executor1 e Executor2 não podem ser o mesmo.", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    usuarioInteragindo = false;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) { }
        });

        spinner_executor2 = (Spinner) findViewById(R.id.spinner_executor2);
        spinner_executor2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                if (usuarioInteragindo) {

                    if(!editTextExecutor1.getText().toString().equals(spinner_executor2.getSelectedItem().toString())||
                            spinner_executor2.getSelectedItem().toString().equals("")&&
                                    (!spinner_executor2.getSelectedItem().toString().equals("SELECIONE:"))){

                        editTextExecutor2.setText(spinner_executor2.getSelectedItem().toString());

                    }else{
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Executor1 e Executor2 não podem ser o mesmo.", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    usuarioInteragindo = false;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) { }
        });

       /* adp = new ArrayAdapter<String>(this, R.layout.meu_spinner,cidadesArray);
        adp.setDropDownViewResource(R.layout.meu_spinner_dropdown);
        spinner_cidades = (Spinner) findViewById(R.id.spinner_cidades);
        spinner_cidades.setAdapter(adp);


        spinner_cidades.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {

                if (!spinner_cidades.getSelectedItem().equals("")){

                    String auxCidadesRecentes, auxCidades, auxItem;

                    auxItem = spinner_cidades.getSelectedItem().toString();
                    auxCidades = preferencesSpinner.getString("cidadesBd", "");
                    auxCidadesRecentes = preferencesSpinner.getString("cidadesBdRecentes", "");

                    ArrayList<String> listCidadesRecentes = new ArrayList<>(Arrays.asList(auxCidadesRecentes.split(",")));
                    ArrayList<String> listCidades = new ArrayList<>(Arrays.asList(auxCidades.split(",")));

                    listCidadesRecentes.remove(auxItem);

                    listCidades.add(auxItem);
                    Collections.sort(listCidades);

                    atualizaSpinnerPreferences(listCidadesRecentes,listCidades);

                    inicializaArrayCidades();

                    adp = new ArrayAdapter<String>(getApplicationContext(), R.layout.meu_spinner, cidadesArray);
                    adp.setDropDownViewResource(R.layout.meu_spinner_dropdown);
                    spinner_cidades.setAdapter(adp);

                    Toast toast = Toast.makeText(getApplicationContext(),
                            "A cidade "+auxItem+" foi retirada da lista recentes!!", Toast.LENGTH_LONG);
                    toast.show();

                }


                return false;
            }}

        );


        spinner_cidades.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                if ((usuarioInteragindo)&&(!spinner_cidades.getSelectedItem().equals(""))
                                        &&(!spinner_cidades.getSelectedItem().equals("---------------------------------------"))){

                    String auxCidadesRecentes, auxCidades, auxItem;

                    auxItem = spinner_cidades.getSelectedItem().toString();

                    auxCidades = preferencesSpinner.getString("cidadesBd", "");
                    auxCidadesRecentes = preferencesSpinner.getString("cidadesBdRecentes", "");

                    ArrayList<String> listCidadesRecentes = new ArrayList<>(Arrays.asList(auxCidadesRecentes.split(",")));
                    ArrayList<String> listCidades = new ArrayList<>(Arrays.asList(auxCidades.split(",")));

                    /*Toast toast = Toast.makeText(getApplicationContext(),
                            listCidades.toString().replaceAll("\\[|\\]", ""),Toast.LENGTH_LONG);
                    toast.show();*/

                    /*if (!listCidadesRecentes.contains(auxItem)) {

                        listCidadesRecentes.add(auxItem);
                        Collections.sort(listCidadesRecentes);

                        listCidades.remove(auxItem);

                        atualizaSpinnerPreferences(listCidadesRecentes,listCidades);

                        inicializaArrayCidades();

                        adp = new ArrayAdapter<String>(getApplicationContext(), R.layout.meu_spinner, cidadesArray);
                        adp.setDropDownViewResource(R.layout.meu_spinner_dropdown);
                        spinner_cidades.setAdapter(adp);
                        spinner_cidades.setSelection(cidadesArray.indexOf(auxItem));
                        //spinner_cidades.performClick();
                    }
                    usuarioInteragindo = false;
                }
                if (spinner_cidades.getSelectedItem().equals("---------------------------------------")){
                    spinner_cidades.setSelection(0);
                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) { }
        });*/

        spinner_material1 = (Spinner) findViewById(R.id.spinner_material1);
        spinner_material1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                if (usuarioInteragindo) {
                    if(position==0) {
                        editTextQuantidade1.setText("");
                    }
                 }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) { }

        });


        spinner_material2 = (Spinner) findViewById(R.id.spinner_material2);
        spinner_material2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                if (usuarioInteragindo) {
                    if(position==0) {
                        editTextQuantidade2.setText("");
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) { }

        });

        spinner_descricao = (Spinner) findViewById(R.id.spinner_descricao);
        spinner_descricao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                if (usuarioInteragindo) {

                    if(!spinner_descricao.getSelectedItem().toString().equals("")) {
                        if (editTextObservacao.getText().toString().equals("")) {
                            editTextObservacao.setText(spinner_descricao.getSelectedItem().toString());
                        } else {
                            editTextObservacao.setText(editTextObservacao.getText() + "\n" + spinner_descricao.getSelectedItem().toString());
                        }
                    }
                    spinner_descricao.setSelection(0);

                    if (position == 8) {
                        spinner_material1.setSelection(1);
                        editTextQuantidade1.setText("2");
                        spinner_material2.setSelection(0);
                        editTextQuantidade2.setText("");
                    }/*else  if (position == 4) {
                        spinner_material1.setSelection(2);
                        editTextQuantidade1.setText("1");
                        spinner_material2.setSelection(0);
                        editTextQuantidade2.setText("");
                    }else if (position == 5) {
                        spinner_material1.setSelection(1);
                        editTextQuantidade1.setText("2");
                        spinner_material2.setSelection(2);
                        editTextQuantidade2.setText("1");
                    }else if (position == 6) {
                        spinner_material1.setSelection(4);
                        editTextQuantidade1.setText("1");
                        spinner_material2.setSelection(0);
                        editTextQuantidade2.setText("");
                    }else if (position == 15) {
                        spinner_material1.setSelection(3);
                        editTextQuantidade1.setText("1");
                        spinner_material2.setSelection(0);
                        editTextQuantidade2.setText("");
                    }else if (position == 16) {
                        spinner_material1.setSelection(5);
                        editTextQuantidade1.setText("1");
                        spinner_material2.setSelection(0);
                        editTextQuantidade2.setText("");
                    }else if (position == 17) {
                        spinner_material1.setSelection(6);
                        editTextQuantidade1.setText("1");
                        spinner_material2.setSelection(0);
                        editTextQuantidade2.setText("");
                    }else {
                        spinner_material1.setSelection(0);
                        editTextQuantidade1.setText("");
                        spinner_material2.setSelection(0);
                        editTextQuantidade2.setText("");
                    }*/

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) { }
        });

        /*Button botaoDescricao = (Button) findViewById(R.id.formulario_botao_descricao);
        botaoDescricao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!spinner_descricao.getSelectedItem().toString().equals("")) {
                    if (editTextObservacao.getText().toString().equals("")) {
                        editTextObservacao.setText(spinner_descricao.getSelectedItem().toString());
                    } else {
                        editTextObservacao.setText(editTextObservacao.getText() + "\n" + spinner_descricao.getSelectedItem().toString());
                    }
                }
                spinner_descricao.setSelection(0);

            }
        });*/

        spinner_frases = (Spinner) findViewById(R.id.spinner_frases);
        spinner_frases.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(!spinner_frases.getSelectedItem().toString().equals("")) {
                    if (editTextObservacao.getText().toString().equals("")) {
                        editTextObservacao.setText(spinner_frases.getSelectedItem().toString());
                    } else {
                        editTextObservacao.setText(editTextObservacao.getText() + "\n" + spinner_frases.getSelectedItem().toString());
                    }
                }
                spinner_frases.setSelection(0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        /*Button botaoFrases = (Button) findViewById(R.id.formulario_botao_frases);
        botaoFrases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!spinner_frases.getSelectedItem().toString().equals("")) {
                    if (editTextObservacao.getText().toString().equals("")) {
                        editTextObservacao.setText(spinner_frases.getSelectedItem().toString());
                    } else {
                        editTextObservacao.setText(editTextObservacao.getText() + "\n" + spinner_frases.getSelectedItem().toString());
                    }
                }
                spinner_frases.setSelection(0);

            }
        });*/


        /*Button botaoFoto = (Button) findViewById(R.id.formulario_botao_foto);
        botaoFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                caminhoFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpg";
                File arquivoFoto = new File(caminhoFoto);
                Uri fotoURI = FileProvider.getUriForFile(FormularioActivity.this, BuildConfig.APPLICATION_ID + ".fileprovider", arquivoFoto);
                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, fotoURI);
                startActivityForResult(intentCamera, CODIGO_CAMERA);

            }
        });*/

        helper = new FormularioHelper(this);

        Intent intent = getIntent();
        servico = (Servico) intent.getSerializableExtra("servico");


        if (servico != null) {
            helper.preencheFormulario(servico);

        }
    }




    public static Context getContext(){
        return getContext();
}

    private void populaSpinner(Spinner spinner, String[] elementos, int idComponente,int layout_dropdown,int layout_spinner) {
        adp = new ArrayAdapter<String>(this, layout_spinner,elementos);
        adp.setDropDownViewResource(layout_dropdown);

        spinner =  (Spinner) findViewById(idComponente);
        spinner.setAdapter(adp);

    }

    private void populaSpinner(Spinner spinner, List elementos, int idComponente,int layout_dropdown,int layout_spinner) {
        adp = new ArrayAdapter<String>(this, layout_spinner,elementos);
        adp.setDropDownViewResource(layout_dropdown);

        spinner =  (Spinner) findViewById(idComponente);
        spinner.setAdapter(adp);

    }

    private List carregaDescricaoColaboradores(){

        PranchetaVirtualDAO dao = new PranchetaVirtualDAO(this);
        final List<Colaborador> colaboradores = dao.buscaColaboradores();
        dao.close();

        List <String> descricaoColaboradores = new ArrayList<String>();
        descricaoColaboradores.add("SELECIONE:");
        descricaoColaboradores.add("");
        for(Colaborador c : colaboradores) {
            descricaoColaboradores.add(c.getRegistro()+"-"+c.getEmail());
        }

        return descricaoColaboradores;

    }

    private void atualizaSpinnerPreferences(ArrayList<String> listCidadesRecentes, ArrayList<String> listCidades) {

        String auxCidadesRecentes = "";
        String auxCidades = "";

        boolean primeiraVez=true;

        //tomar cuidado pois metodo toString adiciona espaço em branco antes de cada elemento.
        //editorPreferencesSpinner.putString("cidadesBdRecentes", listCidadesRecentes.toString().replaceAll("\\[|\\]", ""));

        for(String s : listCidadesRecentes) {
            if (primeiraVez) {
                auxCidades = s;
                primeiraVez = false;
            }else{
                auxCidades = auxCidades+","+s;
            }
        }

        primeiraVez=true;

        for(String s : listCidades) {
            if (primeiraVez) {
                auxCidadesRecentes = s;
                primeiraVez = false;
            }else{
                auxCidadesRecentes = auxCidadesRecentes+","+s;
            }
        }

        editorPreferencesSpinner.putString("cidadesBdRecentes", auxCidades);
        editorPreferencesSpinner.putString("cidadesBd", auxCidadesRecentes);

        editorPreferencesSpinner.commit();
    }

    public void onUserInteraction() {
        super.onUserInteraction();
        usuarioInteragindo = true;
    }



   /* private void inicializaArrayCidades() {

        String auxCidadesRecentes,auxCidades,auxSoma;

        auxCidadesRecentes = preferencesSpinner.getString("cidadesBdRecentes", "");

        auxCidades = preferencesSpinner.getString("cidadesBd", "");

        if((auxCidades.length()==0)&&(auxCidadesRecentes.length()==0)){

            editorPreferencesSpinner.putString("cidadesBd", cidadesString);
            editorPreferencesSpinner.commit();

            auxSoma = " ,"+cidadesString;

        }else{

            auxSoma = auxCidadesRecentes+",---------------------------------------,"+auxCidades;
        }

        cidadesArray = Arrays.asList(auxSoma.split(","));

    }*/

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




    protected Intent sendEmail(Servico serv) {


        Log.i("Send email", " ");

        //String[] TO = {"mhmatsumura@yahoo.com.br"};

        String email1 = serv.getExecutor1().substring(serv.getExecutor1().indexOf("-")+1,serv.getExecutor1().length());
        String email2 = serv.getExecutor2().substring(serv.getExecutor2().indexOf("-")+1,serv.getExecutor2().length());
        String[] TO = {email1+"@COPEL.COM",email2+"@COPEL.COM"};

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");


        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Síntese de serviço. ");
        //emailIntent.putExtra(Intent.EXTRA_TEXT,Html.fromHtml(tela));
        //emailIntent.putExtra(Intent.EXTRA_TEXT, tela);


        String  caminhoPdf = getExternalFilesDir(null) + "/ServicoPranchetaVirtual.pdf";
        File arquivoPdf = new File(caminhoPdf);
        Uri pdfURI = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileprovider", arquivoPdf);
        emailIntent.putExtra(Intent.EXTRA_STREAM, pdfURI);

        return emailIntent;



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CODIGO_CAMERA) {
                //helper.carregaImagem(caminhoFoto);
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_formulario, menu);

        menu.findItem(R.id.menu_formulario_report).getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                servico = helper.pegaServico();

                geraArquivoPdf(servico);

                String  caminhoPdf = getExternalFilesDir(null) + "/ServicoPranchetaVirtual.pdf";
                File arquivoPdf = new File(caminhoPdf);
                Uri pdfURI = FileProvider.getUriForFile(FormularioActivity.this, BuildConfig.APPLICATION_ID + ".fileprovider", arquivoPdf);


                Intent target = new Intent(Intent.ACTION_VIEW);

                target.setDataAndType(pdfURI,"application/pdf");
                target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                target.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                //Intent intent = Intent.createChooser(target, "Open File");

                try {
                    startActivity(target);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(FormularioActivity.this,
                            "Não foi encontrado uma aplicação para visualizar o PDF.",
                            Toast.LENGTH_SHORT).show();
                }

            }

        });

        menu.findItem( R.id.menu_formulario_share).getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                servico = helper.pegaServico();
                geraArquivoPdf(servico);
                new emailAssincrono().execute(sendEmail(servico));

            }

        });


        menu.findItem(R.id.menu_formulario_ok).getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean dataEhValida = true;
                boolean dataEhRecente = true;
                long dataUsuario =0,dataAtual = 0;

                try {
                    dataUsuario = new SimpleDateFormat("dd/MM/yyyy").parse(editTextData.getText().toString()).getTime()/1000;
                    dataAtual = new Date().getTime()/1000;
                } catch (ParseException e) {
                    e.printStackTrace();
                    dataEhValida=false;

                }

                if (dataEhValida){

                    if (dataUsuario < (dataAtual-15552000)){
                        dataEhRecente=false;
                    }
                }

                if (( dataEhValida) && (dataEhRecente)){

                    servico = helper.pegaServico();

                    dao = new PranchetaVirtualDAO(FormularioActivity.this);

                    if (servico.getId() != null) {

                        dao.altera(servico);

                    } else {

                        servico.setStatus("aberto");
                        dao.insere(servico);

                    }
                    dao.close();

                    finish();

                }else {
                    if (dataEhRecente) {
                        Toast.makeText(FormularioActivity.this, "Data invalida!", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(FormularioActivity.this, "Data retroativa com mais de 6 meses!", Toast.LENGTH_SHORT).show();
                    }
                }

            }

        });

        return true;
    }

    @Override
    public void onBackPressed(){


        boolean dataEhValida = true;
        boolean dataEhRecente = true;
        long dataUsuario =0,dataAtual = 0;
        try {
            dataUsuario = new SimpleDateFormat("dd/MM/yyyy").parse(editTextData.getText().toString()).getTime()/1000;
            dataAtual = new Date().getTime()/1000;
        } catch (ParseException e) {
            e.printStackTrace();
            dataEhValida=false;

        }

        if (dataEhValida){

            if (dataUsuario < (dataAtual-15552000)){
                dataEhRecente=false;
            }
        }

        if (( dataEhValida) && (dataEhRecente)){

            servico = helper.pegaServico();

            dao = new PranchetaVirtualDAO(this);

            if (servico.getId() != null) {

                dao.altera(servico);

            } else {

                servico.setStatus("aberto");
                dao.insere(servico);

            }
            dao.close();

            finish();

        }else {
            if (dataEhRecente) {
                Toast.makeText(FormularioActivity.this, "Data invalida!", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(FormularioActivity.this, "Data retroativa com mais de 6 meses!", Toast.LENGTH_SHORT).show();
            }
        }

    }



    public static PdfPTable geraTabelaPdf(Servico servico, Document document) {

            PdfPTable table = new PdfPTable(4);
        try {
            table.setWidths(new int[]{1,1,1,1});
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        table.setTotalWidth(document.getPageSize().getWidth()-20);
            table.setLockedWidth(true);

            Paragraph p;

            PdfPCell c = new PdfPCell(aplicaNegrito("SÍNTESE DO SERVIÇO - DATA: ",servico.getData()));
            c.setColspan(4);
            c.setFixedHeight(23);
            c.setHorizontalAlignment(Element.ALIGN_CENTER);
            c.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(c);



            c.setPhrase(new Phrase(servico.getExecutor1(),font));
            c.setColspan(2);
            table.addCell(c);

            c.setPhrase(new Phrase(servico.getExecutor2(),font));
            c.setColspan(2);
            table.addCell(c);

            c = new PdfPCell(aplicaNegrito("EQP: ",servico.getTipoEquipamento()+"-"+servico.getRegiao()+"-"+servico.getNumeroEquipamento()));
            c.setFixedHeight(23);
            c.setColspan(2);
            table.addCell(c);

            c.setPhrase(new Phrase(servico.getLocalidade(),font));
            c.setHorizontalAlignment(Element.ALIGN_CENTER);
            c.setColspan(2);
            table.addCell(c);

            p = new Paragraph();
            p.add(aplicaNegrito("OS: ",servico.getNumeroOs()));
            p.add(aplicaNegrito("   SDS: ",servico.getNumeroSDS()));
            p.add(aplicaNegrito("   OPR: ",servico.getNumeroOpr()));
            c = new PdfPCell(p);
            c.setColspan(4);
            c.setFixedHeight(23);
            c.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c);

            c = new PdfPCell(aplicaNegrito("H.S.: ",servico.getHoraSaida()));
            c.setFixedHeight(23);
            c.setHorizontalAlignment(Element.ALIGN_LEFT);
            c.setColspan(1);
            table.addCell(c);

            c = new PdfPCell(aplicaNegrito("H.I.: ",servico.getHoraInicio()));
            c.setColspan(1);
            table.addCell(c);

            c = new PdfPCell(aplicaNegrito("H.T.: ",servico.getHoraTermino()));
            c.setColspan(1);
            table.addCell(c);

            c = new PdfPCell(aplicaNegrito("H.R.: ",servico.getHoraRetorno()));
            c.setColspan(1);
            table.addCell(c);

            c = new PdfPCell(aplicaNegrito("KmI.: ",servico.getKmInicial()));
            c.setFixedHeight(23);
            c.setColspan(1);
            table.addCell(c);

            c = new PdfPCell(aplicaNegrito("KmF.: ",servico.getKmFinal()));
            c.setColspan(1);
            table.addCell(c);

            c = new PdfPCell(aplicaNegrito("PEP.: ",servico.getPep()));
            c.setColspan(2);
            table.addCell(c);

            p = new Paragraph();
            p.add(aplicaNegrito("T.COMUNIC.: ",servico.getTipoComunicacao()));
            p.add(aplicaNegrito("   PORTA: ",servico.getPorta()));
            p.add(aplicaNegrito(" DNP: ",servico.getDnp()));
            p.add(aplicaNegrito(" IP: ",servico.getIp()));
            c = new PdfPCell(p);
            c.setColspan(4);
            c.setHorizontalAlignment(Element.ALIGN_CENTER);
            c.setFixedHeight(23);
            table.addCell(c);

            c = new PdfPCell(aplicaNegrito("CHAVE DE ISOLAMENTO: ",servico.getChaveIsolamento()));
            c.setFixedHeight(23);
            c.setColspan(2);
            c.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(c);

            c = new PdfPCell(aplicaNegrito("CHAVE DE BYPASS: ",servico.getChaveBypass()));
            c.setColspan(2);
            table.addCell(c);

            c = new PdfPCell(aplicaNegrito("MATERIAL1: ",servico.getMaterial1()));
            c.setFixedHeight(23);
            c.setColspan(3);
            table.addCell(c);

            c = new PdfPCell(aplicaNegrito("QTD.: ",servico.getQuantidadeMaterial1()));
            c.setColspan(1);
            table.addCell(c);

            c = new PdfPCell(aplicaNegrito("MATERIAL2: ",servico.getMaterial2()));
            c.setFixedHeight(23);
            c.setColspan(3);
            table.addCell(c);

            c = new PdfPCell(aplicaNegrito("QTD.: ",servico.getQuantidadeMaterial2()));
            c.setColspan(1);
            table.addCell(c);

            c = new PdfPCell(aplicaNegrito("OBSERVAÇÃO: \n\n",servico.getObservacao()));
            c.setColspan(4);
            c.setVerticalAlignment(Element.ALIGN_TOP);
            c.setFixedHeight(250f);
            table.addCell(c);

            c = new PdfPCell(aplicaNegrito("CENTRO DE CUSTO: ",calculaCentroDeCusto(servico)));
            c.setColspan(4);
            c.setFixedHeight(23);
            c.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(c);

            c = new PdfPCell(aplicaNegrito("DURAÇÃO DE DESLOCAMENTO: ",calculaDuracaoDeslocamento(servico)));
            c.setFixedHeight(23);
            c.setColspan(4);
            table.addCell(c);

            c = new PdfPCell(aplicaNegrito("DURAÇÃO DE EXECUÇÃO DO SERVIÇO: ",calculaDuracaoExecucaoServico(servico)));
            c.setFixedHeight(23);
            c.setColspan(4);
            table.addCell(c);

            c = new PdfPCell(aplicaNegrito("LIBERAÇÃO DE EQUIPAMENTO: ",liberacaoEquipamento(servico)));
            c.setColspan(4);
            c.setHorizontalAlignment(Element.ALIGN_LEFT);
            c.setVerticalAlignment(Element.ALIGN_TOP);
            c.setFixedHeight(250f);
            table.addCell(c);

        return table;
    }

    public void geraArquivoPdf(Servico servico) {

        Document document = new Document(PageSize.A4,0,0,10,10);
        //Document document = new Document();

            String  caminhoPdf = getExternalFilesDir(null) + "/ServicoPranchetaVirtual.pdf";

        try {

            PdfWriter.getInstance(document,
                   new FileOutputStream(caminhoPdf));

            document.open();
            document.add(geraTabelaPdf(servico,document));

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        document.close();


    }

    private static Paragraph aplicaNegrito(String rotulo, String valor) {

        Phrase texto1 = new Phrase(rotulo,font_bold);
        Phrase texto2 = new Phrase(valor,font);
        Paragraph p = new Paragraph();
        p.add(texto1);
        p.add(texto2);

        return new Paragraph(p);
    }

    private static String calculaCentroDeCusto(Servico servico) {

        String aux = servico.getLocalidade();

        if (aux.length()>6){

            aux = aux.substring(0,6);

            if (aux.charAt(0)=='8'){
                return "D40"+aux.substring(0,6);
            }else if (aux.charAt(0)=='6'){
                return "D41"+aux.substring(0,6);
            }else{
                Log.d("depuração custo: ","cheguei");
                return "";
            }

        }else{
            return"";
        }


    }

    private static String liberacaoEquipamento(Servico servico) {

        String aux = "\n\n  Bom dia," +
                "\n  Encontra-se liberado para operação e disponibilizado na tela do SASE." +
                "\n  Gentileza atualizar o cadastro.";

        aux+="\n\n  Número Operacional: "+servico.getTipoEquipamento()+" "+servico.getRegiao()+servico.getNumeroEquipamento();
        aux+="\n  Tipo de operação:  Operação Manual/Telecomando com proteção";
        aux+="\n  Tipo de comunicação: "+servico.getTipoComunicacao();
        aux+="\n  Possui bypass: "+servico.getChaveBypass();
        aux+="\n  Possui chave de isolamento: "+servico.getChaveIsolamento();
        aux+="\n  Informação confiável da corrente de curto circuito:  Sim";
        aux+="\n  Chave estratégica: Sim";
        aux+="\n  Execução: "+servico.getData();
        aux+="\n\n\n";


        /*

          Número Operacional:  RA 8216001765
          Tipo de operação:  Operação Manual/Telecomando com proteção
          Tipo de comunicação: GPRS
          Possui bypass: Sim
          Possui chave de isolamento: Não
          Informação confiável da corrente de curto circuito:  Sim
          Chave estratégica: Sim
          Execução: 30/06

          */

        return aux;
    }

    private static String calculaDuracaoExecucaoServico(Servico servico) {

        String msgErro = "Algum horário inválido.";

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            //dizendo ao parser para não tolerar horas acima de 24
            sdf.setLenient(false);

            Calendar horaInicio = Calendar.getInstance();
            horaInicio.setTime(sdf.parse(servico.getHoraInicio()));

            Calendar horaTermino = Calendar.getInstance();
            horaTermino.setTime(sdf.parse(servico.getHoraTermino()));

            Calendar tempoExecucao = Calendar.getInstance();

            tempoExecucao = horaTermino;
            tempoExecucao.add(Calendar.HOUR_OF_DAY, -horaInicio.get(Calendar.HOUR_OF_DAY));
            tempoExecucao.add(Calendar.MINUTE, -horaInicio.get(Calendar.MINUTE));



            String tempoExecucaoTxt = sdf.format(tempoExecucao.getTime());

            return tempoExecucaoTxt;

        } catch (ParseException e) {
            e.printStackTrace();
            return msgErro;
        }

    }

    private static String calculaDuracaoDeslocamento(Servico servico) {

        String msgErro = "Algum horário inválido.";

            try {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                //dizendo ao parser para não tolerar horas acima de 24
                sdf.setLenient(false);

                Calendar horaSaida = Calendar.getInstance();
                horaSaida.setTime(sdf.parse(servico.getHoraSaida()));

                Calendar horaInicio = Calendar.getInstance();
                horaInicio.setTime(sdf.parse(servico.getHoraInicio()));

                Calendar horaTermino = Calendar.getInstance();
                horaTermino.setTime(sdf.parse(servico.getHoraTermino()));

                Calendar horaRetorno = Calendar.getInstance();
                horaRetorno.setTime(sdf.parse(servico.getHoraRetorno()));

                Calendar tempoIda = Calendar.getInstance();
                Calendar tempoVolta = Calendar.getInstance();


                tempoIda = horaInicio;
                tempoIda.add(Calendar.HOUR_OF_DAY, -horaSaida.get(Calendar.HOUR_OF_DAY));
                tempoIda.add(Calendar.MINUTE, -horaSaida.get(Calendar.MINUTE));

                tempoVolta = horaRetorno;
                tempoVolta.add(Calendar.HOUR_OF_DAY, -horaTermino.get(Calendar.HOUR_OF_DAY));
                tempoVolta.add(Calendar.MINUTE, -horaTermino.get(Calendar.MINUTE));

                tempoVolta.add(Calendar.HOUR_OF_DAY, tempoIda.get(Calendar.HOUR_OF_DAY));
                tempoVolta.add(Calendar.MINUTE, tempoIda.get(Calendar.MINUTE));

                String tempoDeslocamentoTxt = sdf.format(tempoVolta.getTime());


                return tempoDeslocamentoTxt;

            } catch (ParseException e) {
                e.printStackTrace();
                return msgErro;
            }


    }



}
