package com.example.myapplication.xml.readers;

import com.example.myapplication.model.Balance;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

public class BalanceXmlReader extends XmlReader {
    public Balance parseBalance(InputStream in, String warehouseCode, String productCode) throws IOException, XmlPullParserException {
        try {
            this.parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            this.parser.setInput(in, null);
            this.parser.nextTag();
            return readDocId(this.parser, warehouseCode, productCode);
        } finally {
            in.close();
        }
    }

    private Balance readDocId(XmlPullParser parser, String warhouseCode, String productCode) throws IOException, XmlPullParserException {
        Balance balance =null;
        parser.require(XmlPullParser.START_TAG, ns, "Файл");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("Balance")) {
                balance = readBalance(parser, warhouseCode, productCode);
            } else {
                skip(parser);
            }
        }
        return balance;
    }

    private Balance readBalance(XmlPullParser parser, String warhouseCode, String productCode) throws IOException, XmlPullParserException {
        Balance balance = null;
        parser.require(XmlPullParser.START_TAG, ns, "Balance");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("BalanceData")) {
                Balance balance_xml = readBalanceData(parser);
                if (balance_xml.getWarehouseCode().equals(warhouseCode) && balance_xml.getProductCode().equals(productCode)) {
                    balance = balance_xml;
                }
            } else {
                skip(parser);
            }
        }
        return balance;
    }

    private Balance readBalanceData(XmlPullParser parser) throws IOException, XmlPullParserException {
        Balance balance_xml = null;
        String productCodeXml = null;
        String warhouseCodeXml = null;
        String balance = null;
        String reserve=null;
        parser.require(XmlPullParser.START_TAG, ns, "BalanceData");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("ProductCode")) {
                productCodeXml = readData(parser, "ProductCode");
            } else if (name.equals("WarehouseCode")) {
                warhouseCodeXml = readData(parser, "WarehouseCode");
            } else if (name.equals("BalanceValue")) {
                balance = readData(parser, "BalanceValue");
            }
            else if(name.equals("ReserveValue")){
               reserve=readData(parser,"ReserveValue");
            }
            else {
                skip(parser);
            }
        }
        balance_xml = new Balance(productCodeXml, warhouseCodeXml, balance,reserve);
        return balance_xml;
    }
}
