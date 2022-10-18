package com.copel.mhmat.pranchetaVirtualSmrNrt.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.copel.mhmat.pranchetaVirtualSmrNrt.R;
import com.copel.mhmat.pranchetaVirtualSmrNrt.modelo.Colaborador;
import com.copel.mhmat.pranchetaVirtualSmrNrt.modelo.Servico;

import java.util.List;

/**
 * Created by renan on 12/01/16.
 */
public class ColaboradoresAdapter extends BaseAdapter {

    private final List<Colaborador> colaboradores;
    private final Context context;

    public ColaboradoresAdapter(Context context, List<Colaborador> colaboradores) {
        this.context = context;
        this.colaboradores = colaboradores;
    }

    @Override
    public int getCount() {
        return colaboradores.size();
    }

    @Override
    public Object getItem(int position) {
        return colaboradores.get(position);
    }

    @Override
    public long getItemId(int position) {
        return colaboradores.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Colaborador colaborador = colaboradores.get(position);

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.list_colaboradores_item, parent, false);
        }


        TextView campoRegistro = (TextView) view.findViewById(R.id.item_registro);
        campoRegistro.setText("Registro: "+colaborador.getRegistro());

        TextView campoEmail = (TextView) view.findViewById(R.id.item_email);
        campoEmail.setText("Email: "+colaborador.getEmail()+"@COPEL.COM");

        return view;
    }
}
