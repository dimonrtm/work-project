package com.example.myapplication.xml.readers_from_strings;

import android.util.Xml;

import com.example.myapplication.model.AdmissionMarkingProduct;
import com.example.myapplication.xml.readers.XmlReader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

public class SellMarkingProductsXmlReaderFromString extends XmlReader {
    public SellMarkingProductsXmlReaderFromString(String str) throws XmlPullParserException, IOException {
        parser = Xml.newPullParser();
        parser.setInput(new StringReader(str));
        parser.nextTag();
    }

    public ArrayList<AdmissionMarkingProduct> readSellMarkingProducts() throws IOException, XmlPullParserException {
        return readSellMarkingProducts(this.parser);
    }

    private ArrayList<AdmissionMarkingProduct> readSellMarkingProducts(XmlPullParser parser) throws IOException, XmlPullParserException {
        ArrayList<AdmissionMarkingProduct> sellMarkingProducts = new ArrayList<AdmissionMarkingProduct>();
        parser.require(XmlPullParser.START_TAG, ns, "SellMarkingProducts");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("SellMarkingProduct")) {
                sellMarkingProducts.add(readSellMarkingProduct(parser));
            } else {
                skip(parser);
            }
        }
        return sellMarkingProducts;
    }

    private AdmissionMarkingProduct readSellMarkingProduct(XmlPullParser parser) throws IOException, XmlPullParserException {
        AdmissionMarkingProduct sell_marking_product_xml=null;
        String sellMarkingProductIdXml=null;
        String productCodeXml=null;
        String barcodeLabelingXml=null;
        String markingCompletedXml=null;
        parser.require(XmlPullParser.START_TAG, ns, "SellMarkingProduct");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if(name.equals("productCode")){
                productCodeXml=readData(parser,"productCode");
            }
            else if(name.equals("barcodeLabeling")){
                barcodeLabelingXml=readData(parser,"barcodeLabeling");
            }
            else if(name.equals("markingCompleted")){
                markingCompletedXml=readData(parser,"markingCompleted");
            } else if(name.equals("sellMarkingProductId")){
              sellMarkingProductIdXml=readData(parser,"sellMarkingProductId");
            }
            else{
                skip(parser);
            }
        }
        sell_marking_product_xml=new AdmissionMarkingProduct(sellMarkingProductIdXml,productCodeXml,barcodeLabelingXml,markingCompletedXml);
        return sell_marking_product_xml;
    }
}
