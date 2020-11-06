package com.example.myapplication.xml.readers;

import com.example.myapplication.model.Barcode2D;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

public class Barcode2DXmlReader extends XmlReader {
    public String parseMapBarcodes2D(InputStream in,String barcode) throws IOException, XmlPullParserException {
        try {
            this.parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            this.parser.setInput(in, null);
            this.parser.nextTag();
            return readDocId(this.parser,barcode);
        }
        finally{
            in.close();
        }
    }

    private String readDocId(XmlPullParser parser,String barcode) throws IOException, XmlPullParserException {
        String productId=null;
        parser.require(XmlPullParser.START_TAG,ns,"Файл");
        while(parser.next()!=XmlPullParser.END_TAG){
            if(parser.getEventType()!=XmlPullParser.START_TAG){
                continue;
            }
            String name=parser.getName();
            if(name.equals("Barcode2D"))
            {
                productId=readBarcode2D(parser,barcode);
            }
            else{
                skip(parser);
            }
        }
        return productId;
    }

    private String readBarcode2D(XmlPullParser parser,String barcode) throws IOException, XmlPullParserException {
        String productId=null;
        parser.require(XmlPullParser.START_TAG,ns,"Barcode2D");
        while(parser.next()!=XmlPullParser.END_TAG){
            if(parser.getEventType()!=XmlPullParser.START_TAG){
                continue;
            }
            String name=parser.getName();
            if (name.equals("Barcode2DData")){
                Barcode2D barcode_xml=readBarcode2DData(parser);
                if(barcode_xml.getBarcode().equals(barcode)){
                    productId=barcode_xml.getProductCode();
                }
            }
            else{
                skip(parser);
            }
        }
        return productId;
    }

    private Barcode2D readBarcode2DData(XmlPullParser parser) throws IOException, XmlPullParserException {
        Barcode2D barcode=null;
        String productCodeXml=null;
        String barcodeXml=null;
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
                barcodeXml=readData(parser,"Barcode2DValue");
            }
            else{
                skip(parser);
            }
        }
        barcode=new Barcode2D(barcodeXml,productCodeXml);
        return barcode;
    }
}
