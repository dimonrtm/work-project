package com.example.myapplication.xml.readers_from_strings;

import android.util.Xml;

import com.example.myapplication.model.Warhouse;
import com.example.myapplication.xml.readers.XmlReader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

public class WarehousesXmlReaderFromString extends XmlReader {

    public WarehousesXmlReaderFromString(String str) throws XmlPullParserException, IOException {
        parser = Xml.newPullParser();
        parser.setInput(new StringReader(str));
        parser.nextTag();
    }

    public ArrayList<Warhouse> readWarehouses() throws IOException, XmlPullParserException {
        return readWarehouses(this.parser);
    }

    private ArrayList<Warhouse> readWarehouses(XmlPullParser parser) throws IOException, XmlPullParserException {
        ArrayList<Warhouse> warehouses = new ArrayList<Warhouse>();
        parser.require(XmlPullParser.START_TAG, ns, "Warehouses");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("Warehouse")) {
                warehouses.add(readWarehouse(parser));
            } else {
                skip(parser);
            }
        }
        return warehouses;
    }

    private Warhouse readWarehouse(XmlPullParser parser) throws IOException, XmlPullParserException {
        Warhouse warhouse_xml=null;
        String warehouseCodeXml=null;
        String warehouseNameXml=null;
        parser.require(XmlPullParser.START_TAG, ns, "Warehouse");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if(name.equals("warehouseCode")){
                warehouseCodeXml=readData(parser,"warehouseCode");
            }
            else if(name.equals("warehouseName")){
                warehouseNameXml=readData(parser,"warehouseName");
            }
            else{
                skip(parser);
            }
        }
        warhouse_xml=new Warhouse(warehouseNameXml,warehouseCodeXml);
        return warhouse_xml;
    }
}
