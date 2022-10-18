package com.copel.mhmat.pranchetaVirtualSmrNrt;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.copel.mhmat.pranchetaVirtualSmrNrt.modelo.Servico;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by alura on 12/08/15.
 */
public class FormularioHelper {

    private ArrayAdapter<String> adp;

    //private static final List<String> regioes = Arrays.asList(" ","84800","80492","82136");


    private final EditText  campoNumeroOs,campoData,campoNumeroEquipamento,campoNumeroSDS,campoNumeroOpr,campoHoraSaida,campoHoraInicio,campoHoraTermino,
            campoHoraRetorno,campoKmInicial,campoKmFinal,campoPorta,campoIp,campoPep,campoQuantidadeMaterial1,
            campoQuantidadeMaterial2,campoObservacao,campoExecutor1,campoExecutor2;

    private final Spinner campoTipoEquipamento,campoRegiao,campoTipoComunicacao,campoDnp,campoDescricaoAtividade,campoMaterial1,campoMaterial2;
    private final RadioGroup campoChaveIsolamento,campoChaveBypass;
    private final AutoCompleteTextView campoLocalidade;

    //private final ImageView campoFoto;

    private Servico servico;

    private Activity activity;

    private SharedPreferences preferencias;
    private SharedPreferences.Editor editorPreferencesSpinner;

    public FormularioHelper(FormularioActivity activity) {

        this.activity = activity;

        preferencias = FormularioActivity.getPreferencias();

        campoNumeroOs = (EditText) activity.findViewById(R.id.formulario_os);
        campoData = (EditText) activity.findViewById(R.id.formulario_data);
        campoExecutor1 = (EditText) activity.findViewById(R.id.formulario_executor1);
        campoExecutor2 = (EditText) activity.findViewById(R.id.formulario_executor2);
        campoNumeroEquipamento = (EditText) activity.findViewById(R.id.formulario_equipamento);
        campoNumeroSDS = (EditText) activity.findViewById(R.id.formulario_sds);
        campoNumeroOpr = (EditText) activity.findViewById(R.id.formulario_opr);
        campoHoraSaida = (EditText) activity.findViewById(R.id.formulario_saida);
        campoHoraInicio = (EditText) activity.findViewById(R.id.formulario_inicio);
        campoHoraTermino = (EditText) activity.findViewById(R.id.formulario_termino);
        campoHoraRetorno = (EditText) activity.findViewById(R.id.formulario_retorno);
        campoKmInicial = (EditText) activity.findViewById(R.id.formulario_Kmi);
        campoKmFinal = (EditText) activity.findViewById(R.id.formulario_Kmf);

        campoPorta = (EditText) activity.findViewById(R.id.formulario_porta);
        campoIp = (EditText) activity.findViewById(R.id.formulario_ip);
        campoChaveIsolamento = (RadioGroup) activity.findViewById(R.id.radioGroupChaveIsolamento);
        campoChaveBypass = (RadioGroup) activity.findViewById(R.id.radioGroupChaveBypass);
        campoPep = (EditText) activity.findViewById(R.id.formulario_pep);
        campoQuantidadeMaterial1 = (EditText) activity.findViewById(R.id.formulario_quantidade1);
        campoQuantidadeMaterial2 = (EditText) activity.findViewById(R.id.formulario_quantidade2);
        campoObservacao = (EditText) activity.findViewById(R.id.formulario_observacao);
        campoRegiao = (Spinner) activity.findViewById(R.id.spinner_regiao);
        campoMaterial1 = (Spinner) activity.findViewById(R.id.spinner_material1);
        campoMaterial2 = (Spinner) activity.findViewById(R.id.spinner_material2);
        campoDnp = (Spinner) activity.findViewById(R.id.spinner_dnp);
        campoTipoComunicacao = (Spinner) activity.findViewById(R.id.spinner_comunicação);
        campoTipoEquipamento = (Spinner) activity.findViewById(R.id.spinner_tipoEqp);
        campoLocalidade = (AutoCompleteTextView) activity.findViewById(R.id.actv_cidades);
        campoDescricaoAtividade = (Spinner) activity.findViewById(R.id.spinner_descricao);
        //campoFoto = (ImageView) activity.findViewById(R.id.formulario_foto);

        servico = new Servico();
    }



    public Servico pegaServico() {

        servico.setNumeroOs(campoNumeroOs .getText().toString());
        servico.setExecutor1(campoExecutor1.getText().toString());
        servico.setExecutor2(campoExecutor2.getText().toString());
        servico.setData(campoData .getText().toString());
        servico.setNumeroEquipamento(campoNumeroEquipamento .getText().toString());
        servico.setNumeroSDS(campoNumeroSDS .getText().toString());
        servico.setNumeroOpr(campoNumeroOpr .getText().toString());
        servico.setHoraSaida(campoHoraSaida .getText().toString());
        servico.setHoraInicio(campoHoraInicio .getText().toString());
        servico.setHoraTermino(campoHoraTermino .getText().toString());
        servico.setHoraRetorno(campoHoraRetorno .getText().toString());
        servico.setKmInicial(campoKmInicial .getText().toString());
        servico.setKmFinal(campoKmFinal .getText().toString());

        servico.setPorta(campoPorta .getText().toString());
        servico.setIp(campoIp .getText().toString());
        servico.setPep(campoPep .getText().toString());
        servico.setQuantidadeMaterial1(campoQuantidadeMaterial1.getText().toString());
        servico.setQuantidadeMaterial2(campoQuantidadeMaterial2 .getText().toString());
        servico.setObservacao(campoObservacao .getText().toString());
        
        servico.setTipoEquipamento(campoTipoEquipamento.getSelectedItem().toString());
        servico.setRegiao(campoRegiao.getSelectedItem().toString());
        servico.setLocalidade(campoLocalidade.getText().toString());
        servico.setTipoComunicacao(campoTipoComunicacao.getSelectedItem().toString());
        servico.setDnp(campoDnp.getSelectedItem().toString());
        servico.setDescricaoAtividade(campoDescricaoAtividade.getSelectedItem().toString());
        servico.setMaterial1(campoMaterial1.getSelectedItem().toString());
        servico.setMaterial2(campoMaterial2.getSelectedItem().toString());
        
        servico.setChaveIsolamento(converteRadioButtonChaveIsolamentoParaString());
        servico.setChaveBypass(converteRadioButtonChaveBypassParaString());

        //servico.setCaminhoFoto((String) campoFoto.getTag());
        
        return servico;
    }

    public void preencheFormulario(Servico servico) {


        campoNumeroOs.setText(servico.getNumeroOs());
        campoExecutor1.setText(servico.getExecutor1());
        campoExecutor2.setText(servico.getExecutor2());
        campoData.setText(servico.getData());
        campoNumeroEquipamento.setText(servico.getNumeroEquipamento());
        campoNumeroSDS.setText(servico.getNumeroSDS());
        campoNumeroOpr.setText(servico.getNumeroOpr());
        campoHoraSaida.setText(servico.getHoraSaida());
        campoHoraInicio.setText(servico.getHoraInicio());
        campoHoraTermino.setText(servico.getHoraTermino());
        campoHoraRetorno.setText(servico.getHoraRetorno());
        campoKmInicial.setText(servico.getKmInicial());
        campoKmFinal.setText(servico.getKmFinal());

        campoPorta.setText(servico.getPorta());
        campoIp.setText(servico.getIp());
        campoPep.setText(servico.getPep());
        campoQuantidadeMaterial1.setText(servico.getQuantidadeMaterial1());
        campoQuantidadeMaterial2.setText(servico.getQuantidadeMaterial2());
        campoObservacao.setText(servico.getObservacao());
        campoLocalidade.setText(servico.getLocalidade());


        campoTipoEquipamento.setSelection(Arrays.asList(activity.getResources().getStringArray(R.array.Tipo_Eqp)).indexOf(servico.getTipoEquipamento()));
        campoRegiao.setSelection(Arrays.asList(activity.getResources().getStringArray(R.array.Regioes)).indexOf(servico.getRegiao()));
        campoTipoComunicacao.setSelection(Arrays.asList(activity.getResources().getStringArray(R.array.Tipo_Comunicacao)).indexOf(servico.getTipoComunicacao()));
        campoDnp.setSelection(Arrays.asList(activity.getResources().getStringArray(R.array.Dnps)).indexOf(servico.getDnp()));
        campoDescricaoAtividade.setSelection(Arrays.asList(activity.getResources().getStringArray(R.array.Descricoes)).indexOf(servico.getDescricaoAtividade()));

        campoMaterial1.setSelection(Arrays.asList(activity.getResources().getStringArray(R.array.Materiais)).indexOf(servico.getMaterial1()));
        campoMaterial2.setSelection(Arrays.asList(activity.getResources().getStringArray(R.array.Materiais)).indexOf(servico.getMaterial2()));

        campoChaveIsolamento.check(converteParaRadioButtonChaveIsolamento(servico.getChaveIsolamento()));
        campoChaveBypass.check(converteParaRadioButtonChaveBypass(servico.getChaveBypass()));

        //carregaImagem(servico.getCaminhoFoto());

        this.servico = servico;
    }

    private int converteParaRadioButtonChaveIsolamento(String chaveIsolamento) {

        if (chaveIsolamento.equals("sim")){
            return R.id.rbChaveSim;
        }else if (chaveIsolamento.equals("não")){
            return R.id.rbChaveNao;
        }else{
            return 0;
        }
    }

    private int converteParaRadioButtonChaveBypass(String chaveBypass) {

        if (chaveBypass.equals("sim")){
            return R.id.rbBypassSim;
        }else if (chaveBypass.equals("não")){
            return R.id.rbBypassNao;
        }else{
            return 0;
        }
    }

    private String converteRadioButtonChaveIsolamentoParaString() {

        if (campoChaveIsolamento.getCheckedRadioButtonId()==R.id.rbChaveSim){
            return "sim";
        }else if (campoChaveIsolamento.getCheckedRadioButtonId()==R.id.rbChaveNao){
            return "não";
        }else{
            return "indeterminado";
        }
    }

    private String converteRadioButtonChaveBypassParaString() {

        if (campoChaveBypass.getCheckedRadioButtonId()==R.id.rbBypassSim){
            return "sim";
        }else if (campoChaveBypass.getCheckedRadioButtonId()==R.id.rbBypassNao){
            return "não";
        }else{
            return "indeterminado";
        }
    }


    private List<String> carregaArrayCidades() {

        String auxCidadesRecentes,auxCidades,auxSoma;

        auxCidadesRecentes = preferencias.getString("cidadesBdRecentes", "");

        auxCidades = preferencias.getString("cidadesBd", "");

        auxSoma = auxCidadesRecentes+",---------------------------------------,"+auxCidades;

        return Arrays.asList(auxSoma.split(","));

    }

    /*public void carregaImagem(String caminhoFoto) {
        if (caminhoFoto != null) {
            Bitmap bitmap = CarregadorDeFoto.carrega(caminhoFoto);
            Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
            campoFoto.setImageBitmap(bitmap);
            campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);
            campoFoto.setTag(caminhoFoto);

        }
    }*/
}
