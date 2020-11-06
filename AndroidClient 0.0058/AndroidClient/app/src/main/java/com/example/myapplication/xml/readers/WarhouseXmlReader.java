package com.example.myapplication.xml.readers;

import com.example.myapplication.model.Warhouse;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class WarhouseXmlReader extends XmlReader {
    public List<Warhouse> parseListWarhouses(InputStream in) throws IOException, XmlPullParserException {
        try {
            this.parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            this.parser.setInput(in, null);
            this.parser.nextTag();
            return readDocId(this.parser);
        } finally {
            in.close();
        }
    }

    private List<Warhouse> readDocId(XmlPullParser parser) throws IOException, XmlPullParserException {
        List<Warhouse> warehouseList = null;
        parser.require(XmlPullParser.START_TAG, ns, "Файл");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("Warehouse")) {
                warehouseList = readWarehouse(parser);
            } else {
                skip(parser);
            }
        }
        return warehouseList;
    }

    private List<Warhouse> readWarehouse(XmlPullParser parser) throws IOException, XmlPullParserException {
        List<Warhouse> warhouseList = new ArrayList<Warhouse>();
        parser.require(XmlPullParser.START_TAG, ns, "Warehouse");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("WarehouseData")) {
                Warhouse warehouse_xml = readWarehouseData(parser);
                warhouseList.add(warehouse_xml);
            } else {
                skip(parser);
            }
        }
        return warhouseList;
    }

    private Warhouse readWarehouseData(XmlPullParser parser) throws IOException, XmlPullParserException {
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
        return warehouse_xml;
    }
}


