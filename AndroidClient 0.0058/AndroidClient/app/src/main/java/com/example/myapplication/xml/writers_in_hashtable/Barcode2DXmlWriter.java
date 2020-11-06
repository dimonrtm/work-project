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

public class Barcode2DXmlWriter extends XmlReader {
    XmlSerializer xmlSerializer;

    public Barcode2DXmlWriter(OutputStream out) throws IOException {
        this.xmlSerializer = Xml.newSerializer();
        xmlSerializer.setOutput(out, "UTF-8");
        xmlSerializer.startDocument(null, Boolean.valueOf(true));
    }

    public Map<String, String> parseMap2DBarcodes(InputStream in) throws IOException, XmlPullParserException {
        try {
            this.parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            this.parser.setInput(in, null);
            this.parser.nextTag();
            return readDocId(this.parser);
        } finally {
            in.close();
        }
    }

    private Map<String, String> readDocId(XmlPullParser parser) throws IOException, XmlPullParserException {
        Map<String, String> barcodes2D = null;
        parser.require(XmlPullParser.START_TAG, ns, "Файл");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("Barcode2D")) {
                barcodes2D = readBarcode2D(parser);
            } else {
                skip(parser);
            }
        }
        return barcodes2D;
    }

    private Map<String,String> readBarcode2D(XmlPullParser parser) throws IOException, XmlPullParserException {
        Map<String,String> barcodes2D=new HashMap<String,String>();
        parser.require(XmlPullParser.START_TAG,ns,"Barcode2D");
        while(parser.next()!=XmlPullParser.END_TAG){
            if(parser.getEventType()!=XmlPullParser.START_TAG){
                continue;
            }
            String name=parser.getName();
            if (name.equals("Barcode2DData")){
                readBarcode2DData(parser,barcodes2D);
            }
            else{
                skip(parser);
            }
        }
        return barcodes2D;
    }

    private void readBarcode2DData(XmlPullParser parser,Map<String,String> barcodes2D) throws IOException, XmlPullParserException {
        String productCodeXml=null;
        String barcode2DXml=null;
        parser.require(XmlPullParser.START_TAG,ns,"Barcode2DData");
        while(parser.next()!=XmlPullParser.END_TAG){
            if(parser.getEventType()!=XmlPullParser.START_TAG){
                continue;
            }
            String name=parser.getName();
            if(name.equals("ProductCode")){
                productCodeXml=readData(parser,"ProductCode");
            }
            else if(name.equals("Barcode2DValue")){
                barcode2DXml=readData(parser,"Barcode2DValue");
            }
            else{
                skip(parser);
            }
        }
        barcodes2D.put(barcode2DXml,productCodeXml);
    }

    public void writeBarcodes2D(Map<String,String> barcodes2D) throws IOException {
        xmlSerializer.startTag(ns,"Файл");
        xmlSerializer.startTag(ns,"Barcode2D");
        for(String barcode:barcodes2D.keySet()){
            String productCode=barcodes2D.get(barcode);
            xmlSerializer.startTag(ns,"Barcode2DData");
            xmlSerializer.startTag(ns,"ProductCode");
            xmlSerializer.text(productCode);
            xmlSerializer.endTag(ns,"ProductCode");
            xmlSerializer.startTag(ns,"Barcode2DValue");
            xmlSerializer.text(barcode);
            xmlSerializer.endTag(ns,"Barcode2DValue");
            xmlSerializer.endTag(ns,"Barcode2DData");
        }
        xmlSerializer.endTag(ns,"Barcode2D");
        xmlSerializer.endTag(ns,"Файл");
        xmlSerializer.endDocument();
        xmlSerializer.flush();
    }
}
