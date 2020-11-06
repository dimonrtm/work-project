package com.example.myapplication.xml.writers_in_hashtable;

import android.util.Xml;

import com.example.myapplication.xml.readers.XmlReader;

import org.apache.commons.collections4.MapIterator;
import org.apache.commons.collections4.keyvalue.MultiKey;
import org.apache.commons.collections4.map.MultiKeyMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class BalanceXmlWriter extends XmlReader {
   private XmlSerializer xmlSerializer;

    public BalanceXmlWriter(OutputStream out) throws IOException {
        this.xmlSerializer = Xml.newSerializer();
        xmlSerializer.setOutput(out, "UTF-8");
        xmlSerializer.startDocument(null, Boolean.valueOf(true));
    }

    public MultiKeyMap<String,Double> parseBalance(InputStream in) throws IOException, XmlPullParserException {
        try {
            this.parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            this.parser.setInput(in, null);
            this.parser.nextTag();
            return readDocId(this.parser);
        } finally {
            in.close();
        }
    }

    private MultiKeyMap<String,Double> readDocId(XmlPullParser parser) throws IOException, XmlPullParserException {
        MultiKeyMap<String,Double> balance = null;
        parser.require(XmlPullParser.START_TAG, ns, "Файл");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("Balance")) {
                balance = readBalance(parser);
            } else {
                skip(parser);
            }
        }
        return balance;
    }

    private MultiKeyMap<String,Double> readBalance(XmlPullParser parser) throws IOException, XmlPullParserException {
        MultiKeyMap<String,Double> balance=new MultiKeyMap<String,Double>();
        parser.require(XmlPullParser.START_TAG, ns, "Balance");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("BalanceData")) {
                readBalanceData(parser,balance);
            } else {
                skip(parser);
            }
        }
        return balance;
    }

    private void readBalanceData(XmlPullParser parser,MultiKeyMap<String,Double> balance) throws IOException, XmlPullParserException {
        String balance_xml = null;
        String productCodeXml = null;
        String warhouseCodeXml = null;
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
                balance_xml = readData(parser, "BalanceValue");
            } else {
                skip(parser);
            }
        }
        balance.put(productCodeXml,warhouseCodeXml,Double.parseDouble(balance_xml));
    }

    public void writeBalance(MultiKeyMap<String,Double> balance) throws IOException {
        xmlSerializer.startTag(ns,"Файл");
        xmlSerializer.startTag(ns,"Balance");
        MapIterator<MultiKey<? extends String>,Double> it =balance.mapIterator();
        while(it.hasNext()){
            xmlSerializer.startTag(ns,"BalanceData");
            MultiKey<? extends String> key=it.next();
            Double bal=it.getValue();
            xmlSerializer.startTag(ns,"ProductCode");
            xmlSerializer.text(key.getKey(0));
            xmlSerializer.endTag(ns,"ProductCode");
            xmlSerializer.startTag(ns,"WarehouseCode");
            xmlSerializer.text(key.getKey(1));
            xmlSerializer.endTag(ns,"WarehouseCode");
            xmlSerializer.startTag(ns,"BalanceValue");
            xmlSerializer.text(bal.toString());
            xmlSerializer.endTag(ns,"BalanceValue");
            xmlSerializer.endTag(ns,"BalanceData");
        }
        xmlSerializer.endTag(ns,"Balance");
        xmlSerializer.endTag(ns,"Файл");
        xmlSerializer.endDocument();
        xmlSerializer.flush();
    }
}
