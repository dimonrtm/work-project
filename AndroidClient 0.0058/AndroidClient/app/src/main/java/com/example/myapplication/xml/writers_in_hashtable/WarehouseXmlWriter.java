package com.example.myapplication.xml.writers_in_hashtable;

import android.util.Xml;

import com.example.myapplication.model.Warhouse;
import com.example.myapplication.xml.readers.XmlReader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class WarehouseXmlWriter extends XmlReader {
   private XmlSerializer xmlSerializer;

    public WarehouseXmlWriter(OutputStream out) throws IOException {
        this.xmlSerializer = Xml.newSerializer();
        xmlSerializer.setOutput(out, "UTF-8");
        xmlSerializer.startDocument(null, Boolean.valueOf(true));
    }

    public Map<String,Warhouse> parseMapWarhouses(InputStream in) throws IOException, XmlPullParserException {
        try {
            this.parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            this.parser.setInput(in, null);
            this.parser.nextTag();
            return readDocId(this.parser);
        } finally {
            in.close();
        }
    }

    private Map<String,Warhouse> readDocId(XmlPullParser parser) throws IOException, XmlPullParserException {
        Map<String,Warhouse> warehouses = null;
        parser.require(XmlPullParser.START_TAG, ns, "Файл");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("Warehouse")) {
                warehouses = readWarehouse(parser);
            } else {
                skip(parser);
            }
        }
        return warehouses;
    }

    private Map<String,Warhouse> readWarehouse(XmlPullParser parser) throws IOException, XmlPullParserException {
        Map<String,Warhouse> warhouses = new HashMap<String,Warhouse>();
        parser.require(XmlPullParser.START_TAG, ns, "Warehouse");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("WarehouseData")) {
                readWarehouseData(parser,warhouses);
            } else {
                skip(parser);
            }
        }
        return warhouses;
    }

    private void readWarehouseData(XmlPullParser parser,Map<String,Warhouse> warhouses) throws IOException, XmlPullParserException {
        Warhouse warehouse_xml = null;
        String nameXml = null;
        String codeXml = null;
        parser.require(XmlPullParser.START_TAG, ns, "WarehouseData");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("WarehouseName")) {
                nameXml = readData(parser, "WarehouseName");
            } else if (name.equals("WarehouseCode")) {
                codeXml = readData(parser, "WarehouseCode");
            } else {
                skip(parser);
            }
        }
        warehouse_xml = new Warhouse(nameXml, codeXml);
        warhouses.put(nameXml,warehouse_xml);
    }

    public void writeWarehouse(Map<String, Warhouse> warehouses) throws IOException {
        xmlSerializer.startTag(ns,"Файл");
        xmlSerializer.startTag(ns,"Warehouse");
        for(String name:warehouses.keySet()){
             Warhouse warehouse=warehouses.get(name);
             xmlSerializer.startTag(ns,"WarehouseData");
             xmlSerializer.startTag(ns,"WarehouseName");
             xmlSerializer.text(name);
             xmlSerializer.endTag(ns,"WarehouseName");
             xmlSerializer.startTag(ns,"WarehouseCode");
             xmlSerializer.text(warehouse.getCode());
             xmlSerializer.endTag(ns,"WarehouseCode");
             xmlSerializer.endTag(ns,"WarehouseData");
        }
        xmlSerializer.endTag(ns,"Warehouse");
        xmlSerializer.endTag(ns,"Файл");
        xmlSerializer.endDocument();
        xmlSerializer.flush();
    }
}
