package com.example.myapplication.xml.readers;

import com.example.myapplication.model.Barcode;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

public class BarcodeXmlReader extends XmlReader {
    public String parseMapBarcodes(InputStream in,String barcode) throws IOException, XmlPullParserException {
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
            if(name.equals("Barcode"))
            {
                productId=readBarcode(parser,barcode);
            }
            else{
                skip(parser);
            }
        }
        return productId;
    }

    private String readBarcode(XmlPullParser parser,String barcode) throws IOException, XmlPullParserException {
        String productId=null;
        parser.require(XmlPullParser.START_TAG,ns,"Barcode");
        while(parser.next()!=XmlPullParser.END_TAG){
          if(parser.getEventType()!=XmlPullParser.START_TAG){
              continue;
          }
          String name=parser.getName();
          if (name.equals("BarcodeData")){
              Barcode barcode_xml=readBarcodeData(parser);
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
                productCodeXml=readData(parser,"ProductCode");
            }
            else if(name.equals("BarcodeValue")){
                barcodeXml=readData(parser,"BarcodeValue");
            }
            else{
                skip(parser);
            }
        }
        barcode=new Barcode(barcodeXml,productCodeXml);
        return barcode;
    }
}
