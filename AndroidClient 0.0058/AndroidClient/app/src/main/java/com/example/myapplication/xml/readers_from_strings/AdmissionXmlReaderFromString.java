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

public class AdmissionXmlReaderFromString extends XmlReader {
    public AdmissionXmlReaderFromString(String str) throws XmlPullParserException, IOException {
        parser = Xml.newPullParser();
        parser.setInput(new StringReader(str));
        parser.nextTag();
    }

    public Admission readAdmission() throws IOException, XmlPullParserException, ParseException {
        return readAdmission(this.parser);
    }

    private Admission readAdmission(XmlPullParser parser) throws IOException, XmlPullParserException, ParseException {
        Admission admission = null;
        String admissionIdXml = null;
        String warehouseCodeXml = null;
        String admissionDateXml = null;
        String admissionTimeXml = null;
        parser.require(XmlPullParser.START_TAG, ns, "Admission");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("admissionId")) {
                admissionIdXml = readData(parser, "admissionId");
            } else if (name.equals("warehouseId")) {
                warehouseCodeXml = readData(parser, "warehouseId");
            } else if (name.equals("admissionDate")) {
                admissionDateXml = readData(parser, "admissionDate");
            } else if (name.equals("admissionTime")) {
                admissionTimeXml = readData(parser, "admissionTime");
            } else {
                skip(parser);
            }
        }
        Date date = parseDate(admissionDateXml, "yyyy-MM-dd");
        Date time = parseDate(admissionTimeXml, "HH:mm:ss");
        admission=new Admission(admissionIdXml,date,time,warehouseCodeXml);
        return admission;
    }

    private Date parseDate(String date, String pattern) throws ParseException {
        Date dateh = null;
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        dateh = format.parse(date);
        return dateh;
    }
}
