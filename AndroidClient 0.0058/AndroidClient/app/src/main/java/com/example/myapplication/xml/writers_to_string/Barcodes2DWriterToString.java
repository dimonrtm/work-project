package com.example.myapplication.xml.writers_to_string;

import android.util.Xml;

import com.example.myapplication.model.Barcode2D;

import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

public class Barcodes2DWriterToString {

    private XmlSerializer xmlSerializer;
    private StringWriter writer;

    public Barcodes2DWriterToString() throws IOException {
        xmlSerializer= Xml.newSerializer();
        writer = new StringWriter();
        xmlSerializer.setOutput(writer);
        xmlSerializer.startDocument("UTF8",true);
        xmlSerializer.startTag(null,"Файл");
    }

    public String writeBarcodes2D(List<Barcode2D> barcodes2D) throws IOException {
        xmlSerializer.startTag(null,"Barcode2D");
        for(Barcode2D b:barcodes2D){
            xmlSerializer.startTag(null,"Barcode2DData");
            xmlSerializer.startTag(null,"ProductCode");
            xmlSerializer.text(b.getProductCode());
            xmlSerializer.endTag(null,"ProductCode");
            xmlSerializer.startTag(null,"Barcode2DValue");
            xmlSerializer.text(b.getBarcode());
            xmlSerializer.endTag(null,"Barcode2DValue");
            xmlSerializer.endTag(null,"Barcode2DData");
        }
        xmlSerializer.endTag(null,"Barcode2D");
        xmlSerializer.endTag(null,"Файл");
        xmlSerializer.endDocument();
        return writer.toString();
    }
}
