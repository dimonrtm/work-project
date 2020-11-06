package com.example.myapplication.xml.readers_from_strings;

import android.util.Xml;

import com.example.myapplication.model.AdmissionProduct;
import com.example.myapplication.xml.readers.XmlReader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

public class SellProductsXmlReaderFromString extends XmlReader {
    public SellProductsXmlReaderFromString(String str) throws XmlPullParserException, IOException {
        parser = Xml.newPullParser();
        parser.setInput(new StringReader(str));
        parser.nextTag();
    }

    public ArrayList<AdmissionProduct> readSellProducts() throws IOException, XmlPullParserException {
        return readSellProducts(this.parser);
    }

    private ArrayList<AdmissionProduct> readSellProducts(XmlPullParser parser) throws IOException, XmlPullParserException {
        ArrayList<AdmissionProduct> sellProducts = new ArrayList<AdmissionProduct>();
        parser.require(XmlPullParser.START_TAG, ns, "SellProducts");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("SellProduct")) {
                sellProducts.add(readSellProduct(parser));
            } else {
                skip(parser);
            }
        }
        return sellProducts;
    }

    private AdmissionProduct readSellProduct(XmlPullParser parser) throws IOException, XmlPullParserException {
        AdmissionProduct sell_product_xml=null;
        String productCodeXml=null;
        String balanceValueXml=null;
        String balanceValueDocXml=null;
        String markingXml=null;
        String sellIdXML=null;
        parser.require(XmlPullParser.START_TAG, ns, "SellProduct");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if(name.equals("productCode")){
                productCodeXml=readData(parser,"productCode");
            }
            else if(name.equals("sellId")){
                sellIdXML=readData(parser,"sellId");
            }
            else if(name.equals("balanceValueSellProducts")){
                balanceValueXml=readData(parser,"balanceValueSellProducts");
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
        sell_product_xml=new AdmissionProduct(productCodeXml,balanceValueXml,balanceValueDocXml,markingXml);
        return sell_product_xml;
    }
}
