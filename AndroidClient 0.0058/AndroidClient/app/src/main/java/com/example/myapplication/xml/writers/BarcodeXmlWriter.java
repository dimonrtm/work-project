package com.example.myapplication.xml.writers;

import com.example.myapplication.model.Barcode;
import com.example.myapplication.xml.readers.XmlReader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.InputStream;

public class BarcodeXmlWriter extends XmlReader {
    XmlSerializer xmlSerializer;

    public BarcodeXmlWriter(XmlSerializer xmlSerializer) {
        this.xmlSerializer = xmlSerializer;
    }

    public void parseMapBarcodes(InputStream in) throws IOException, XmlPullParserException {
        try {
            this.parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            this.parser.setInput(in, null);
            this.parser.nextTag();
            readDocId(this.parser);
        } finally {
            in.close();
        }
    }

    private String readDocId(XmlPullParser parser) throws IOException, XmlPullParserException {
        String productId = null;
        parser.require(XmlPullParser.START_TAG, ns, "Файл");
        xmlSerializer.startTag(ns, "Файл");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("Barcode")) {
                xmlSerializer.startTag(ns, "Barcode");
                productId = readBarcode(parser);
                xmlSerializer.endTag(ns,"Barcode");
                xmlSerializer.endTag(ns,"Файл");
            } else {
                skip(parser);
            }
        }
        return productId;
    }

    private String readBarcode(XmlPullParser parser) throws IOException, XmlPullParserException {
        String productId=null;
        parser.require(XmlPullParser.START_TAG,ns,"Barcode");
        while(parser.next()!=XmlPullParser.END_TAG){
            if(parser.getEventType()!=XmlPullParser.START_TAG){
                continue;
            }
            String name=parser.getName();
            if (name.equals("BarcodeData")) {
                xmlSerializer.startTag(ns, "BarcodeData");
                Barcode barcode_xml = readBarcodeData(parser);
                productId = barcode_xml.getProductCode();
            }
            else{
                skip(parser);
            }
        }
        return productId;
    }

    private Barcode readBarcodeData(XmlPullParser parser) throws IOException, XmlPullParserException {
        Barcode barcode=null;
        String productCodeXml=null;
        String barcodeXml=null;
        parser.require(XmlPullParser.START_TAG,ns,"BarcodeData");
        while(parser.next()!=XmlPullParser.END_TAG){
            if(parser.getEventType()!=XmlPullParser.START_TAG){
                continue;
            }
            String name=parser.getName();
            if(name.equals("ProductCode")){
                xmlSerializer.startTag(ns,"ProductCode");
                productCodeXml=readData(parser,"ProductCode");
                xmlSerializer.text(productCodeXml);
                xmlSerializer.endTag(ns,"ProductCode");
            }
            else if(name.equals("BarcodeValue")){
                xmlSerializer.startTag(ns,"BarcodeValue");
                barcodeXml=readData(parser,"BarcodeValue");
                xmlSerializer.text(barcodeXml);
                xmlSerializer.endTag(ns,"BarcodeValue");
                xmlSerializer.endTag(ns,"BarcodeData");
            }
            else{
                skip(parser);
            }
        }
        barcode=new Barcode(barcodeXml,productCodeXml);
        return barcode;
    }
}
