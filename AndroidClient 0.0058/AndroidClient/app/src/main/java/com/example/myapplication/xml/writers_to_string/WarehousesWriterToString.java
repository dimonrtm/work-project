package com.example.myapplication.xml.writers_to_string;

import android.util.Xml;

import com.example.myapplication.model.Warhouse;

import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

public class WarehousesWriterToString {
    private XmlSerializer xmlSerializer;
    private StringWriter writer;

    public WarehousesWriterToString() throws IOException {
        xmlSerializer= Xml.newSerializer();
        writer = new StringWriter();
        xmlSerializer.setOutput(writer);
        xmlSerializer.startDocument("UTF8",true);
        xmlSerializer.startTag(null,"Файл");
    }

    public String writeWarehouses(List<Warhouse> warehouses) throws IOException {
        xmlSerializer.startTag(null,"Warehouse");
        for(Warhouse w:warehouses){
            xmlSerializer.startTag(null,"WarehouseData");
            xmlSerializer.startTag(null,"WarehouseName");
            xmlSerializer.text(w.getName());
            xmlSerializer.endTag(null,"WarehouseName");
            xmlSerializer.startTag(null,"WarehouseCode");
            xmlSerializer.text(w.getCode());
            xmlSerializer.endTag(null,"WarehouseCode");
            xmlSerializer.endTag(null,"WarehouseData");
        }
        xmlSerializer.endTag(null,"Warehouse");
        xmlSerializer.endTag(null,"Файл");
        xmlSerializer.endDocument();
        return writer.toString();
    }
}
