package com.copel.mhmat.pranchetaVirtualSmrNrt;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;

import androidx.core.content.FileProvider;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.copel.mhmat.pranchetaVirtualSmrNrt.modelo.Servico;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PdfActivity extends AppCompatActivity {

    Servico servico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        ImageView imageView = (ImageView) findViewById(R.id.imageViewPdf);

        Intent intent = getIntent();
        servico = (Servico) intent.getSerializableExtra("servico");

        geraArquivoPdf();



        try {

            String  caminhoPdf = getExternalFilesDir(null) + "/Servico.pdf";
            File arquivoPdf = new File(caminhoPdf);
            Uri pdfURI = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileprovider", arquivoPdf);

            ParcelFileDescriptor fd = ParcelFileDescriptor.open(arquivoPdf, ParcelFileDescriptor.MODE_READ_ONLY);

            PdfRenderer renderer = new PdfRenderer(fd);
            PdfRenderer.Page page = renderer.openPage(0);
            Bitmap bitmap = Bitmap.createBitmap(page.getWidth(), page.getHeight(), Bitmap.Config.ARGB_4444);
            page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);

            imageView.setImageBitmap(bitmap);
            //imageView.setImageResource(R.drawable.ic_mapa);

        } catch (
                FileNotFoundException e) {
            e.printStackTrace();
        } catch (
                IOException e) {
            e.printStackTrace();
        }

    }

    public void geraArquivoPdf(){

        //Document document = new Document(PageSize.A4.rotate(),0,0,50,0);
        Document document = new Document();

        try {

            String  caminhoPdf = getExternalFilesDir(null) + "/Servico.pdf";

            PdfWriter.getInstance(document,
                    new FileOutputStream(caminhoPdf));

            document.open();

            PdfPTable table = new PdfPTable(4);

            table.setWidths(new int[]{1,1,1,1});

            table.setTotalWidth(document.getPageSize().getWidth()-20);

            table.setLockedWidth(true);

            Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 20);

            PdfPCell c = new PdfPCell(new Phrase("SÍNTESE DO SERVIÇO - DATA: "+servico.getData(),font));
            c.setColspan(4);
            c.setFixedHeight(30f);
            c.setHorizontalAlignment(Element.ALIGN_CENTER);
            c.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(c);

            c.setPhrase(new Phrase(servico.getExecutor1(),font));
            c.setColspan(2);
            table.addCell(c);

            c.setPhrase(new Phrase(servico.getExecutor2(),font));
            c.setColspan(2);
            table.addCell(c);

            c.setPhrase(new Phrase("EQP: "+servico.getTipoEquipamento()+"-"+servico.getRegiao()+"-"+servico.getNumeroEquipamento(),font));
            c.setColspan(2);
            table.addCell(c);

            c.setPhrase(new Phrase(servico.getLocalidade(),font));
            c.setColspan(2);
            table.addCell(c);

            c.setPhrase(new Phrase("OS: "+servico.getNumeroOs()+"   SDS: "+servico.getNumeroSDS()+"   OPR: "+servico.getNumeroOpr(),font));
            c.setColspan(4);
            table.addCell(c);

            c.setPhrase(new Phrase("H.S.: "+servico.getHoraSaida(),font));
            c.setHorizontalAlignment(Element.ALIGN_LEFT);
            c.setColspan(1);
            table.addCell(c);

            c.setPhrase(new Phrase("H.I.: "+servico.getHoraInicio(),font));
            c.setColspan(1);
            table.addCell(c);

            c.setPhrase(new Phrase("H.T.: "+servico.getHoraTermino(),font));
            c.setColspan(1);
            table.addCell(c);

            c.setPhrase(new Phrase("H.R.: "+servico.getHoraRetorno(),font));
            c.setColspan(1);
            table.addCell(c);

            c.setPhrase(new Phrase("KmI.: "+servico.getKmInicial(),font));
            c.setColspan(1);
            table.addCell(c);

            c.setPhrase(new Phrase("KmF.: "+servico.getKmFinal(),font));
            c.setColspan(1);
            table.addCell(c);

            c.setPhrase(new Phrase("PEP.: "+servico.getPep(),font));
            c.setColspan(2);
            table.addCell(c);

            c.setPhrase(new Phrase("T.COMUNIC.: "+servico.getTipoComunicacao()+"   PORTA: "+servico.getPorta()+" DNP: "+servico.getDnp()+" IP: "+servico.getIp(),font));
            c.setColspan(4);
            c.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c);

            c.setPhrase(new Phrase("CHAVE DE ISOLAMENTO: "+servico.getChaveIsolamento(),font));
            c.setColspan(2);
            c.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(c);

            c.setPhrase(new Phrase("CHAVE DE BYPASS: "+servico.getChaveBypass(),font));
            c.setColspan(2);
            table.addCell(c);

            c.setPhrase(new Phrase("MATERIAL1: "+servico.getMaterial1(),font));
            c.setColspan(3);
            table.addCell(c);

            c.setPhrase(new Phrase("QTD.: "+servico.getQuantidadeMaterial1(),font));
            c.setColspan(1);
            table.addCell(c);

            c.setPhrase(new Phrase("MATERIAL2: "+servico.getMaterial2(),font));
            c.setColspan(3);
            table.addCell(c);

            c.setPhrase(new Phrase("QTD.: "+servico.getQuantidadeMaterial2(),font));
            c.setColspan(1);
            table.addCell(c);

            c.setPhrase(new Phrase("OBSERVAÇÃO: \n\n"+servico.getObservacao(),font));
            c.setColspan(4);
            c.setVerticalAlignment(Element.ALIGN_TOP);
            c.setFixedHeight(200f);
            table.addCell(c);

            c.setPhrase(new Phrase("CENTRO DE CUSTO: "+calculaCentroDeCusto(),font));
            c.setColspan(4);
            c.setFixedHeight(30f);
            c.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(c);

            c.setPhrase(new Phrase("DURAÇÃO DE DESLOCAMENTO: "+calculaDuracaoDeslocamento(),font));
            c.setColspan(4);
            table.addCell(c);

            c.setPhrase(new Phrase("DURAÇÃO DE EXECUÇÃO DO SERVIÇO: "+calculaDuracaoExecucaoServico(),font));
            c.setColspan(4);
            table.addCell(c);

            c.setPhrase(new Phrase("LIBERAÇÃO DE EQUIPAMENTO: "+liberacaoEquipamento(),font));
            c.setColspan(4);
            c.setHorizontalAlignment(Element.ALIGN_LEFT);
            c.setVerticalAlignment(Element.ALIGN_TOP);
            c.setFixedHeight(100f);
            table.addCell(c);


            document.add(table);
            document.close();



        } catch(Exception e){

        }
    }

    private String calculaCentroDeCusto() {

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

    private String liberacaoEquipamento() {

        String aux = "\n\nBom dia," +
                       "\nEncontra-se liberado para operação e disponibilizado na tela do SASE." +
                       "\nGentileza atualizar o cadastro.";

        aux+="\n\nNúmero Operacional: "+servico.getTipoEquipamento()+" "+servico.getRegiao()+servico.getNumeroEquipamento();


       /*         Número Operacional:  RA 8216001765
        Tipo de operação:  Operação Manual/Telecomando com proteção
        Tipo de comunicação: GPRS
        Possui bypass: Sim
        Possui chave de isolamento: Não
        Informação confiável da corrente de curto circuito:  Sim
        Chave estratégica: Sim
        Execução: 30/06*/

        return aux;
    }

    private String calculaDuracaoExecucaoServico() {
        return"";
    }

    private String calculaDuracaoDeslocamento() {
        return "";
    }


}
