package com.copel.mhmat.pranchetaVirtualSmrNrt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.copel.mhmat.pranchetaVirtualSmrNrt.ListaServicosActivity;
import com.copel.mhmat.pranchetaVirtualSmrNrt.ListaServicosEncerradosActivity;
import com.copel.mhmat.pranchetaVirtualSmrNrt.R;
import com.copel.mhmat.pranchetaVirtualSmrNrt.modelo.Servico;

public class ServicosAdapter extends BaseAdapter {
    private final List<Servico> servicos;
    private final Context context;
    private ArrayList<Integer> auxArray;

    public ServicosAdapter(Context context, List<Servico> servicos) {
        this.context = context;
        this.servicos = servicos;
    }

    @Override
    public int getCount() {
        return servicos.size();
    }

    @Override
    public Object getItem(int position) {
        return servicos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return servicos.get(position).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {



        View view; final ViewHolder holder;
        if( convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
            if (context.getClass().equals(ListaServicosActivity.class)) {
                holder.campoFoto.setImageResource(R.drawable.pranchetaaberto);
            } else if (context.getClass().equals(ListaServicosEncerradosActivity.class)) {
                holder.campoFoto.setImageResource(R.drawable.pranchetaencerrado);
            }
        }else{
            view = convertView;
            holder = (ViewHolder)view.getTag();

            if (context.getClass().equals(ListaServicosActivity.class)) {
                auxArray=ListaServicosActivity.selecionados;
                if (auxArray.get(position)==0){
                    holder.campoFoto.setImageResource(R.drawable.pranchetaaberto);
                }else if (auxArray.get(position)==1){
                    holder.campoFoto.setImageResource(R.drawable.pranchetaamarelaaberto);
                }
            } else if (context.getClass().equals(ListaServicosEncerradosActivity.class)) {
                auxArray = ListaServicosEncerradosActivity.selecionados;
                if (auxArray.get(position)==0){
                    holder.campoFoto.setImageResource(R.drawable.pranchetaencerrado);
                }else if (auxArray.get(position)==1){
                    holder.campoFoto.setImageResource(R.drawable.pranchetaamarelaencerrado);
                }
            }




        }


        holder.campoFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                auxArray=null;

                if (context.getClass().equals(ListaServicosActivity.class)) {
                    auxArray=ListaServicosActivity.selecionados;
                    if (auxArray.get(position)==0){
                        holder.campoFoto.setImageResource(R.drawable.pranchetaamarelaaberto);
                        auxArray.set(position,1);
                    }else if (auxArray.get(position)==1){
                        holder.campoFoto.setImageResource(R.drawable.pranchetaaberto);
                        auxArray.set(position,0);
                    }
                } else if (context.getClass().equals(ListaServicosEncerradosActivity.class)) {
                    auxArray = ListaServicosEncerradosActivity.selecionados;
                    if (auxArray.get(position)==0){
                        holder.campoFoto.setImageResource(R.drawable.pranchetaamarelaencerrado);
                        auxArray.set(position,1);
                    }else if (auxArray.get(position)==1){
                        holder.campoFoto.setImageResource(R.drawable.pranchetaencerrado);
                        auxArray.set(position,0);
                    }
                }



            }

        });

        final Servico servico = servicos.get(position);

        holder.campoData.setText(servico.getData());
        holder.campoEquipamento.setText(servico.getTipoEquipamento()+" "+servico.getRegiao()+"-"+servico.getNumeroEquipamento());
        String[] palavras = servico.getLocalidade().split(" - ");
        if (palavras.length==2){
            holder.campoCidade.setText(servico.getLocalidade().split(" - ")[1]);
            holder.campoLocalidade.setText("LOCAL: "+servico.getLocalidade().split(" - ")[0]);
        }else {
            holder.campoCidade.setText(servico.getLocalidade());
            holder.campoLocalidade.setText("LOCAL: ");
        }

        holder.campoOs.setText("OS: "+servico.getNumeroOs());


      /*  ImageView campoFoto = (ImageView) view.findViewById(R.id.item_foto);
        String caminhoFoto = servico.getCaminhoFoto();

        if (caminhoFoto != null) {

            Bitmap bitmap = CarregadorDeFoto.carrega(caminhoFoto);
            Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
            campoFoto.setImageBitmap(bitmap);
            campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);
            campoFoto.setTag(caminhoFoto);
        }*/

        return view;
    }
}
