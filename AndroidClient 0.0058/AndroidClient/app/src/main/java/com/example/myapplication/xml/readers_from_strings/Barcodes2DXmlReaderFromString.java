package com.example.myapplication.xml.readers_from_strings;

import android.util.Xml;

import com.example.myapplication.model.Barcode2D;
import com.example.myapplication.xml.readers.XmlReader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

public class Barcodes2DXmlReaderFromString extends XmlReader {

    public Barcodes2DXmlReaderFromString(String str) throws XmlPullParserException, IOException {
        parser = Xml.newPullParser();
        parser.setInput(new StringReader(str));
        parser.nextTag();
    }

    public ArrayList<Barcode2D> readBarcodes2D() throws IOException, XmlPullParserException {
        return readBarcodes2D(this.parser);
    }

    private ArrayList<Barcode2D> readBarcodes2D(XmlPullParser parser) throws IOException, XmlPullParserException {
        ArrayList<Barcode2D> barcodes2D = new ArrayList<Barcode2D>();
        parser.require(XmlPullParser.START_TAG, ns, "Barcodes2D");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("Barcode2D")) {
                barcodes2D.add(readBarcode2D(parser));
            } else {
                skip(parser);
            }
        }
        return barcodes2D;
    }

    private Barcode2D readBarcode2D(XmlPullParser parser) throws IOException, XmlPullParserException {
        Barcode2D barcode2d_xml=null;
        String barcode2DValueXml=null;
        String productCodeXml=null;
        parser.require(XmlPullParser.START_TAG, ns, "Barcode2D");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if(name.equals("barcode2DValue")){
                barcode2DValueXml=readData(parser,"barcode2DValue");
            }
            else if(name.equals("productCode")){
                productCodeXml=readData(parser,"productCode");
            }
            else{
                skip(parser);
            }
        }
        barcode2d_xml=new Barcode2D(barcode2DValueXml,productCodeXml);
        return barcode2d_xml;
    }
}
