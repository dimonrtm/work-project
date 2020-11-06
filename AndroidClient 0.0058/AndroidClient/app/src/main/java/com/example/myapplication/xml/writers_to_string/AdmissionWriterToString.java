package com.example.myapplication.xml.writers_to_string;

import android.util.Xml;

import com.example.myapplication.model.Admission;
import com.example.myapplication.model.AdmissionMarkingProduct;
import com.example.myapplication.model.AdmissionProduct;

import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.List;

public class AdmissionWriterToString {
    private XmlSerializer xmlSerializer;
    private StringWriter writer;
    SimpleDateFormat format=null;

    public AdmissionWriterToString() throws IOException {
        xmlSerializer= Xml.newSerializer();
        writer = new StringWriter();
        xmlSerializer.setOutput(writer);
        xmlSerializer.startDocument("UTF8",true);
        xmlSerializer.startTag(null,"Файл");
    }

    public String writeAdmission(Admission admission) throws IOException {
        xmlSerializer.startTag(null, "AdmissionData");
        xmlSerializer.startTag(null, "AdmissionDok");
        xmlSerializer.startTag(null, "AdmissionNo");
        xmlSerializer.text(admission.getId());
        xmlSerializer.endTag(null, "AdmissionNo");
        xmlSerializer.startTag(null, "AdmissionDate");
        format=new SimpleDateFormat("dd.MM.yyyy");
        String date=format.format(admission.getDate());
        xmlSerializer.text(date);
        xmlSerializer.endTag(null, "AdmissionDate");
        xmlSerializer.startTag(null, "AdmissionTime");
        format=new SimpleDateFormat("HH:mm:ss");
        String time=format.format(admission.getTime());
        xmlSerializer.text(time);
        xmlSerializer.endTag(null, "AdmissionTime");
        xmlSerializer.startTag(null, "Warehouse");
        xmlSerializer.text(admission.getWarehouse());
        xmlSerializer.endTag(null, "Warehouse");
        xmlSerializer.endTag(null, "AdmissionDok");
        xmlSerializer.startTag(null, "AdmissionTabProduct");
        List<AdmissionProduct> listAdmissionProduct=admission.getAdmissionProducts();
        for (AdmissionProduct admProd : listAdmissionProduct) {
            xmlSerializer.startTag(null, "AdmissionProduct");
            xmlSerializer.startTag(null, "ProductCode");
            xmlSerializer.text(admProd.getProductCode());
            xmlSerializer.endTag(null, "ProductCode");
            xmlSerializer.startTag(null, "BalanceValue");
            xmlSerializer.text(admProd.getBalanceValue() + "");
            xmlSerializer.endTag(null, "BalanceValue");
            xmlSerializer.startTag(null, "BalanceValueDocs");
            xmlSerializer.text(admProd.getBalanceValueDocs() + "");
            xmlSerializer.endTag(null, "BalanceValueDocs");
            xmlSerializer.startTag(null, "Marking");
            xmlSerializer.text(admProd.getMarking() + "");
            xmlSerializer.endTag(null, "Marking");
            xmlSerializer.endTag(null, "AdmissionProduct");
        }
        xmlSerializer.endTag(null, "AdmissionTabProduct");
        xmlSerializer.startTag(null, "AdmissionTabMarkingProduct");
        List<AdmissionMarkingProduct> listAdmissionMarkingProduct=admission.getAdmissionMarkingProducts();
        for (AdmissionMarkingProduct mProd : listAdmissionMarkingProduct) {
            xmlSerializer.startTag(null, "AdmissionMarkingProduct");
            xmlSerializer.startTag(null,"admissionMarkingProductId");
            xmlSerializer.text(mProd.getAdmissionMarkingProductId());
            xmlSerializer.endTag(null,"admissionMarkingProductId");
            xmlSerializer.startTag(null, "ProductCode");
            xmlSerializer.text(mProd.getProductCode());
            xmlSerializer.endTag(null, "ProductCode");
            xmlSerializer.startTag(null, "BarcodeLabeling");
            xmlSerializer.text(mProd.getBartcodeLabeling());
            xmlSerializer.endTag(null, "BarcodeLabeling");
            xmlSerializer.startTag(null, "MarkingCompleted");
            xmlSerializer.text(mProd.getMarkingCompleted() + "");
            xmlSerializer.endTag(null, "MarkingCompleted");
            xmlSerializer.endTag(null, "AdmissionMarkingProduct");
        }
        xmlSerializer.endTag(null, "AdmissionTabMarkingProduct");
        xmlSerializer.endTag(null, "AdmissionData");
        xmlSerializer.endTag(null, "Файл");
        xmlSerializer.endDocument();
        return writer.toString();
    }
}
