package com.example.myapplication.xml.writers_in_hashtable;

import android.util.Xml;

import com.example.myapplication.model.Admission;
import com.example.myapplication.model.AdmissionMarkingProduct;
import com.example.myapplication.model.AdmissionProduct;

import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.List;

public class SaveAdmissionsXmlWriter {
    String ns = null;
    XmlSerializer xmlSerializer;
    SimpleDateFormat format=null;

    public SaveAdmissionsXmlWriter(OutputStream out) throws IOException {
        this.xmlSerializer = Xml.newSerializer();
        xmlSerializer.setOutput(out, "UTF-8");
        xmlSerializer.startDocument(null, Boolean.valueOf(true));
    }

    public void admissionWrite(Admission admission) throws IOException {
        xmlSerializer.startTag(ns, "Файл");
        xmlSerializer.startTag(ns, "AdmissionData");
        xmlSerializer.startTag(ns, "AdmissionDok");
        xmlSerializer.startTag(ns, "AdmissionNo");
        xmlSerializer.text(admission.getId());
        xmlSerializer.endTag(ns, "AdmissionNo");
        xmlSerializer.startTag(ns, "AdmissionDate");
        format=new SimpleDateFormat("dd.MM.yyyy");
        String date=format.format(admission.getDate());
        xmlSerializer.text(date);
        xmlSerializer.endTag(ns, "AdmissionDate");
        xmlSerializer.startTag(ns, "AdmissionTime");
        format=new SimpleDateFormat("HH:mm:ss");
        String time=format.format(admission.getTime());
        xmlSerializer.text(time);
        xmlSerializer.endTag(ns, "AdmissionTime");
        xmlSerializer.startTag(ns, "Warehouse");
        xmlSerializer.text(admission.getWarehouse());
        xmlSerializer.endTag(ns, "Warehouse");
        xmlSerializer.endTag(ns, "AdmissionDok");
        xmlSerializer.startTag(ns, "AdmissionTabProduct");
        List<AdmissionProduct> listAdmissionProduct=admission.getAdmissionProducts();
        for (AdmissionProduct admProd : listAdmissionProduct) {
            xmlSerializer.startTag(ns, "AdmissionProduct");
            xmlSerializer.startTag(ns, "ProductCode");
            xmlSerializer.text(admProd.getProductCode());
            xmlSerializer.endTag(ns, "ProductCode");
            xmlSerializer.startTag(ns, "BalanceValue");
            xmlSerializer.text(admProd.getBalanceValue() + "");
            xmlSerializer.endTag(ns, "BalanceValue");
            xmlSerializer.startTag(ns, "BalanceValueDocs");
            xmlSerializer.text(admProd.getBalanceValueDocs() + "");
            xmlSerializer.endTag(ns, "BalanceValueDocs");
            xmlSerializer.startTag(ns, "Marking");
            xmlSerializer.text(admProd.getMarking() + "");
            xmlSerializer.endTag(ns, "Marking");
            xmlSerializer.endTag(ns, "AdmissionProduct");
        }
        xmlSerializer.endTag(ns, "AdmissionTabProduct");
        xmlSerializer.startTag(ns, "AdmissionTabMarkingProduct");
        List<AdmissionMarkingProduct> listAdmissionMarkingProduct=admission.getAdmissionMarkingProducts();
        for (AdmissionMarkingProduct mProd : listAdmissionMarkingProduct) {
            xmlSerializer.startTag(ns, "AdmissionMarkingProduct");
            xmlSerializer.startTag(null,"admissionMarkingProductId");
            xmlSerializer.text(mProd.getAdmissionMarkingProductId());
            xmlSerializer.endTag(null,"admissionMarkingProductId");
            xmlSerializer.startTag(ns, "ProductCode");
            xmlSerializer.text(mProd.getProductCode());
            xmlSerializer.endTag(ns, "ProductCode");
            xmlSerializer.startTag(ns, "BarcodeLabeling");
            xmlSerializer.text(mProd.getBartcodeLabeling());
            xmlSerializer.endTag(ns, "BarcodeLabeling");
            xmlSerializer.startTag(ns, "MarkingCompleted");
            xmlSerializer.text(mProd.getMarkingCompleted() + "");
            xmlSerializer.endTag(ns, "MarkingCompleted");
            xmlSerializer.endTag(ns, "AdmissionMarkingProduct");
        }
        xmlSerializer.endTag(ns, "AdmissionTabMarkingProduct");
        xmlSerializer.endTag(ns, "AdmissionData");
        xmlSerializer.endTag(ns, "Файл");
        xmlSerializer.endDocument();
        xmlSerializer.flush();
    }
}
