package com.example.myapplication.xml.readers_from_strings;

import android.util.Xml;

import com.example.myapplication.model.Balance;
import com.example.myapplication.xml.readers.XmlReader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

public class BalancesXmlReaderFromString extends XmlReader {
    public BalancesXmlReaderFromString(String str) throws XmlPullParserException, IOException {
        parser = Xml.newPullParser();
        parser.setInput(new StringReader(str));
        parser.nextTag();
    }

    public ArrayList<Balance> readBalances() throws IOException, XmlPullParserException {
        return readBalances(this.parser);
    }

    private ArrayList<Balance> readBalances(XmlPullParser parser) throws IOException, XmlPullParserException {
        ArrayList<Balance> balances = new ArrayList<Balance>();
        parser.require(XmlPullParser.START_TAG, ns, "Balances");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("Balance")) {
                balances.add(readBalance(parser));
            } else {
                skip(parser);
            }
        }
        return balances;
    }

    private Balance readBalance(XmlPullParser parser) throws IOException, XmlPullParserException {
        Balance balance_xml=null;
        String productCodeXml=null;
        String warehouseCodeXml=null;
        String balanceValueXml=null;
        String reserveValueXml=null;
        parser.require(XmlPullParser.START_TAG, ns, "Balance");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if(name.equals("productCode")){
                productCodeXml=readData(parser,"productCode");
            }
            else if(name.equals("warehouseCode")){
                warehouseCodeXml=readData(parser,"warehouseCode");
            }
            else if(name.equals("balanceValue")){
                balanceValueXml=readData(parser,"balanceValue");
            }
            else if(name.equals("reserveValue")){
               reserveValueXml=readData(parser,"reserveValue");
            }
            else{
                skip(parser);
            }
        }
        balance_xml=new Balance(productCodeXml,warehouseCodeXml,balanceValueXml,reserveValueXml);
        return balance_xml;
    }
}
