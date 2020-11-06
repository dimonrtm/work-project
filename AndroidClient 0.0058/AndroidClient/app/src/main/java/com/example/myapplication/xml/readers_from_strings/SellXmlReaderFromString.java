package com.example.myapplication.xml.readers_from_strings;

import android.util.Xml;

import com.example.myapplication.model.Admission;
import com.example.myapplication.xml.readers.XmlReader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SellXmlReaderFromString extends XmlReader {
    public SellXmlReaderFromString(String str) throws XmlPullParserException, IOException {
        parser = Xml.newPullParser();
        parser.setInput(new StringReader(str));
        parser.nextTag();
    }

    public Admission readSell() throws IOException, XmlPullParserException, ParseException {
        return readSell(this.parser);
    }

    private Admission readSell(XmlPullParser parser) throws IOException, XmlPullParserException, ParseException {
        Admission sell = null;
        String sellIdXml = null;
        String warehouseCodeXml = null;
        String sellDateXml = null;
        String sellTimeXml = null;
        parser.require(XmlPullParser.START_TAG, ns, "Sell");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("sellId")) {
                sellIdXml = readData(parser, "sellId");
            } else if (name.equals("warehouseId")) {
                warehouseCodeXml = readData(parser, "warehouseId");
            } else if (name.equals("sellDate")) {
                sellDateXml = readData(parser, "sellDate");
            } else if (name.equals("sellTime")) {
                sellTimeXml = readData(parser, "sellTime");
            } else {
                skip(parser);
            }
        }
        Date date = parseDate(sellDateXml, "yyyy-MM-dd");
        Date time = parseDate(sellTimeXml, "HH:mm:ss");
        sell=new Admission(sellIdXml,date,time,warehouseCodeXml);
        return sell;
    }

    private Date parseDate(String date, String pattern) throws ParseException {
        Date dateh = null;
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        dateh = format.parse(date);
        return dateh;
    }
}
