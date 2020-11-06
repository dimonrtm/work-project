package com.example.myapplication.xml.writers_in_hashtable;

import android.util.Xml;

import com.example.myapplication.xml.readers.XmlReader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class BarcodeXmlWriter extends XmlReader {
    private XmlSerializer xmlSerializer;

    public BarcodeXmlWriter(OutputStream out) throws IOException {
        this.xmlSerializer= Xml.newSerializer();
        xmlSerializer.setOutput(out,"UTF-8");
        xmlSerializer.startDocument(null, Boolean.valueOf(true));
    }

    public Map<String,String> parseMapBarcodes(InputStream in) throws IOException, XmlPullParserException {
        try {
            this.parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            this.parser.setInput(in, null);
            this.parser.nextTag();
            return readDocId(this.parser);
        }
        finally{
            in.close();
        }
    }

    private Map<String,String> readDocId(XmlPullParser parser) throws IOException, XmlPullParserException {
        Map<String,String> barcodes=null;
        parser.require(XmlPullParser.START_TAG,ns,"Файл");
        while(parser.next()!=XmlPullParser.END_TAG){
            if(parser.getEventType()!=XmlPullParser.START_TAG){
                continue;
            }
            String name=parser.getName();
            if(name.equals("Barcode"))
            {
                barcodes=readBarcode(parser);
            }
            else{
                skip(parser);
            }
        }
        return barcodes;
    }

    private Map<String,String> readBarcode(XmlPullParser parser) throws IOException, XmlPullParserException {
        Map<String,String> barcodes=new HashMap<String,String>();
        parser.require(XmlPullParser.START_TAG,ns,"Barcode");
        while(parser.next()!=XmlPullParser.END_TAG){
            if(parser.getEventType()!=XmlPullParser.START_TAG){
                continue;
            }
            String name=parser.getName();
            if (name.equals("BarcodeData")){
                readBarcodeData(parser,barcodes);
            }
            else{
                skip(parser);
            }
        }
        return barcodes;
    }

    private void readBarcodeData(XmlPullParser parser,Map<String,String> barcodes) throws IOException, XmlPullParserException {
        String productCodeXml=null;
        String barcodeXml=null;
        parser.require(XmlPullParser.START_TAG,ns,"BarcodeData");
        while(parser.next()!=XmlPullParser.END_TAG){
            if(parser.getEventType()!=XmlPullParser.START_TAG){
                continue;
            }
            String name=parser.getName();
            if(name.equals("ProductCode")){
                productCodeXml=readData(parser,"ProductCode");
            }
            else if(name.equals("BarcodeValue")){
                barcodeXml=readData(parser,"BarcodeValue");
            }
            else{
                skip(parser);
            }
        }
        barcodes.put(barcodeXml,productCodeXml);
    }

    public void writeBarcodes(Map<String,String> barcodes) throws IOException {
        xmlSerializer.startTag(ns,"Файл");
        xmlSerializer.startTag(ns,"Barcode");
        for(String barcode:barcodes.keySet()){
            String productCode=barcodes.get(barcode);
            xmlSerializer.startTag(ns,"BarcodeData");
            xmlSerializer.startTag(ns,"ProductCode");
            xmlSerializer.text(productCode);
            xmlSerializer.endTag(ns,"ProductCode");
            xmlSerializer.startTag(ns,"BarcodeValue");
            xmlSerializer.text(barcode);
            xmlSerializer.endTag(ns,"BarcodeValue");
            xmlSerializer.endTag(ns,"BarcodeData");
        }
        xmlSerializer.endTag(ns,"Barcode");
        xmlSerializer.endTag(ns,"Файл");
        xmlSerializer.endDocument();
        xmlSerializer.flush();
    }
}
