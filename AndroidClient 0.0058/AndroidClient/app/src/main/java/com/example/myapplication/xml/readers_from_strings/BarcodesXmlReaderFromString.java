package com.example.myapplication.xml.readers_from_strings;

import android.util.Xml;

import com.example.myapplication.model.Barcode;
import com.example.myapplication.xml.readers.XmlReader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

public class BarcodesXmlReaderFromString extends XmlReader {

    public BarcodesXmlReaderFromString(String str) throws XmlPullParserException, IOException {
        parser = Xml.newPullParser();
        parser.setInput(new StringReader(str));
        parser.nextTag();
    }

    public ArrayList<Barcode> readBarcodes() throws IOException, XmlPullParserException {
        return readBarcodes(this.parser);
    }

    private ArrayList<Barcode> readBarcodes(XmlPullParser parser) throws IOException, XmlPullParserException {
        ArrayList<Barcode> barcodes = new ArrayList<Barcode>();
        parser.require(XmlPullParser.START_TAG, ns, "Barcodes");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("Barcode")) {
                barcodes.add(readBarcode(parser));
            } else {
                skip(parser);
            }
        }
        return barcodes;
    }

    private Barcode readBarcode(XmlPullParser parser) throws IOException, XmlPullParserException {
        Barcode barcode_xml=null;
        String barcodeValueXml=null;
        String productCodeXml=null;
        parser.require(XmlPullParser.START_TAG, ns, "Barcode");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if(name.equals("barcodeValue")){
                barcodeValueXml=readData(parser,"barcodeValue");
            }
            else if(name.equals("productCodeBarcode")){
                productCodeXml=readData(parser,"productCodeBarcode");
            }
            else{
                skip(parser);
            }
        }
        barcode_xml=new Barcode(barcodeValueXml,productCodeXml);
        return barcode_xml;
    }
}
