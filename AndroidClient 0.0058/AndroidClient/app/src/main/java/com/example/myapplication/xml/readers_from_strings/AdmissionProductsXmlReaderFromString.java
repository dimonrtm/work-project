package com.example.myapplication.xml.readers_from_strings;

import android.util.Xml;

import com.example.myapplication.model.AdmissionProduct;
import com.example.myapplication.xml.readers.XmlReader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

public class AdmissionProductsXmlReaderFromString extends XmlReader {

    public AdmissionProductsXmlReaderFromString(String str) throws XmlPullParserException, IOException {
        parser = Xml.newPullParser();
        parser.setInput(new StringReader(str));
        parser.nextTag();
    }

    public ArrayList<AdmissionProduct> readAdmissionProducts() throws IOException, XmlPullParserException {
        return readAdmissionProducts(this.parser);
    }

    private ArrayList<AdmissionProduct> readAdmissionProducts(XmlPullParser parser) throws IOException, XmlPullParserException {
        ArrayList<AdmissionProduct> admissionProducts = new ArrayList<AdmissionProduct>();
        parser.require(XmlPullParser.START_TAG, ns, "AdmissionProducts");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("AdmissionProduct")) {
                admissionProducts.add(readAdmissionProduct(parser));
            } else {
                skip(parser);
            }
        }
        return admissionProducts;
    }

    private AdmissionProduct readAdmissionProduct(XmlPullParser parser) throws IOException, XmlPullParserException {
        AdmissionProduct admission_product_xml=null;
        String productCodeXml=null;
        String balanceValueXml=null;
        String balanceValueDocXml=null;
        String markingXml=null;
        String admissionIdXML=null;
        parser.require(XmlPullParser.START_TAG, ns, "AdmissionProduct");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if(name.equals("productCode")){
                productCodeXml=readData(parser,"productCode");
            }
            else if(name.equals("admissionId")){
                admissionIdXML=readData(parser,"admissionId");
            }
            else if(name.equals("balanceValueAdmissionProducts")){
                balanceValueXml=readData(parser,"balanceValueAdmissionProducts");
            }
            else if(name.equals("balanceValueDoc")){
                balanceValueDocXml=readData(parser,"balanceValueDoc");
            }
            else if(name.equals("marking")){
                markingXml=readData(parser,"marking");
            }
            else{
                skip(parser);
            }
        }
        admission_product_xml=new AdmissionProduct(productCodeXml,balanceValueXml,balanceValueDocXml,markingXml);
        return admission_product_xml;
    }
}
