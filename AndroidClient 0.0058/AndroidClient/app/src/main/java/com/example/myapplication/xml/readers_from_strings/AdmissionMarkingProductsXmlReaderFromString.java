package com.example.myapplication.xml.readers_from_strings;

import android.util.Xml;

import com.example.myapplication.model.AdmissionMarkingProduct;
import com.example.myapplication.xml.readers.XmlReader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

public class AdmissionMarkingProductsXmlReaderFromString extends XmlReader {
    public AdmissionMarkingProductsXmlReaderFromString(String str) throws XmlPullParserException, IOException {
        parser = Xml.newPullParser();
        parser.setInput(new StringReader(str));
        parser.nextTag();
    }

    public ArrayList<AdmissionMarkingProduct> readAdmissionMarkingProducts() throws IOException, XmlPullParserException {
        return readAdmissionMarkingProducts(this.parser);
    }

    private ArrayList<AdmissionMarkingProduct> readAdmissionMarkingProducts(XmlPullParser parser) throws IOException, XmlPullParserException {
        ArrayList<AdmissionMarkingProduct> admissionMarkingProducts = new ArrayList<AdmissionMarkingProduct>();
        parser.require(XmlPullParser.START_TAG, ns, "AdmissionMarkingProducts");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("AdmissionMarkingProduct")) {
                admissionMarkingProducts.add(readAdmissionMarkingProduct(parser));
            } else {
                skip(parser);
            }
        }
        return admissionMarkingProducts;
    }

    private AdmissionMarkingProduct readAdmissionMarkingProduct(XmlPullParser parser) throws IOException, XmlPullParserException {
        AdmissionMarkingProduct admission_marking_product_xml=null;
        String admissionMarkingProductIdXml=null;
        String productCodeXml=null;
        String barcodeLabelingXml=null;
        String markingCompletedXml=null;
        parser.require(XmlPullParser.START_TAG, ns, "AdmissionMarkingProduct");
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
            }else if(name.equals("admissionMarkingProductId")){
               admissionMarkingProductIdXml=readData(parser,"admissionMarkingProductId");
            }
            else{
                skip(parser);
            }
        }
        admission_marking_product_xml=new AdmissionMarkingProduct(admissionMarkingProductIdXml,productCodeXml,barcodeLabelingXml,markingCompletedXml);
        return admission_marking_product_xml;
    }
}
