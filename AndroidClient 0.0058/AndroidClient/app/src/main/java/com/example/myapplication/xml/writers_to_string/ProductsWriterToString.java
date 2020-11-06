package com.example.myapplication.xml.writers_to_string;

import android.util.Xml;

import com.example.myapplication.model.Product;

import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

public class ProductsWriterToString {
    private XmlSerializer xmlSerializer;
    private StringWriter writer;

    public ProductsWriterToString() throws IOException {
        xmlSerializer= Xml.newSerializer();
        writer = new StringWriter();
        xmlSerializer.setOutput(writer);
        xmlSerializer.startDocument("UTF8",true);
        xmlSerializer.startTag(null,"Файл");
    }

    public String writeProducts(List<Product> products) throws IOException {
        xmlSerializer.startTag(null,"Product");
        for(Product p:products){
            xmlSerializer.startTag(null,"ProductData");
            xmlSerializer.startTag(null,"Name");
            xmlSerializer.text(p.getName());
            xmlSerializer.endTag(null,"Name");
            xmlSerializer.startTag(null,"VendorCode");
            xmlSerializer.text(p.getVendorCode());
            xmlSerializer.endTag(null,"VendorCode");
            xmlSerializer.startTag(null,"ProductCode");
            xmlSerializer.text(p.getProductCode());
            xmlSerializer.endTag(null,"ProductCode");
            xmlSerializer.endTag(null,"ProductData");
        }
        xmlSerializer.endTag(null,"Product");
        xmlSerializer.endTag(null,"Файл");
        xmlSerializer.endDocument();
        return writer.toString();
    }
}
