package com.copel.mhmat.pranchetaVirtualSmrNrt.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.copel.mhmat.pranchetaVirtualSmrNrt.R;

/**
 * Created by renan on 12/01/16.
 */
public class ViewHolder {

    final TextView campoData, campoEquipamento,campoLocalidade,campoCidade,campoOs;
    final ImageView campoFoto;

    public ViewHolder(View view) {
        campoFoto = (ImageView) view.findViewById(R.id.item_foto);
        campoData = (TextView) view.findViewById(R.id.item_data);
        campoEquipamento = (TextView) view.findViewById(R.id.item_equipamento);
        campoCidade = (TextView) view.findViewById(R.id.item_cidade);
        campoLocalidade = (TextView) view.findViewById(R.id.item_localidade);
        campoOs = (TextView) view.findViewById(R.id.item_os);
    }
}
