package com.example.myapplication.xml.writers_to_string;

import android.util.Xml;

import com.example.myapplication.model.Barcode;

import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

public class BarcodesWriterToString {

    private XmlSerializer xmlSerializer;
    private StringWriter writer;

    public BarcodesWriterToString() throws IOException {
        xmlSerializer= Xml.newSerializer();
        writer = new StringWriter();
        xmlSerializer.setOutput(writer);
        xmlSerializer.startDocument("UTF8",true);
        xmlSerializer.startTag(null,"Файл");
    }

    public String writeBarcodes(List<Barcode> barcodes) throws IOException {
        xmlSerializer.startTag(null,"Barcode");
        for(Barcode b:barcodes){
            xmlSerializer.startTag(null,"BarcodeData");
            xmlSerializer.startTag(null,"ProductCode");
            xmlSerializer.text(b.getProductCode());
            xmlSerializer.endTag(null,"ProductCode");
            xmlSerializer.startTag(null,"BarcodeValue");
            xmlSerializer.text(b.getBarcode());
            xmlSerializer.endTag(null,"BarcodeValue");
            xmlSerializer.endTag(null,"BarcodeData");
        }
        xmlSerializer.endTag(null,"Barcode");
        xmlSerializer.endTag(null,"Файл");
        xmlSerializer.endDocument();
        return writer.toString();
    }
}
