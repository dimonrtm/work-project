package com.example.myapplication.xml.writers;
import com.example.myapplication.model.Admission;
import com.example.myapplication.model.AdmissionMarkingProduct;
import com.example.myapplication.model.AdmissionProduct;
import com.example.myapplication.xml.readers.XmlReader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;

public class AdmissionXmlWriter extends XmlReader {
    XmlSerializer xmlSerializer;
    public AdmissionXmlWriter(XmlSerializer xmlSerializer){
       this.xmlSerializer=xmlSerializer;
    }

    public Admission parseAdmission(InputStream in) throws IOException, XmlPullParserException, ParseException {
        try {
            this.parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            this.parser.setInput(in, null);
            this.parser.nextTag();
            return readDocId(this.parser);
        } finally {
            in.close();
        }
    }

    private Admission readDocId(XmlPullParser parser) throws IOException, XmlPullParserException, ParseException {
        Admission admission = null;
        parser.require(XmlPullParser.START_TAG, ns, "Файл");
        xmlSerializer.startTag(ns,"Файл");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("AdmissionData")) {
                xmlSerializer.startTag(ns,"AdmissionData");
                admission = readAdmissionData(parser);
                xmlSerializer.endTag(ns,"AdmissionData");
                xmlSerializer.endTag(ns,"Файл");
            } else {
                skip(parser);
            }
        }
        return admission;
    }

    private Admission readAdmissionData(XmlPullParser parser) throws IOException, XmlPullParserException, ParseException {
        Admission admission = null;
        parser.require(XmlPullParser.START_TAG, ns, "AdmissionData");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("AdmissionDok")) {
                xmlSerializer.startTag(ns,"AdmissionDok");
                admission = readAdmissionDoc(parser);
            } else if (name.equals("AdmissionTabProduct")) {
                xmlSerializer.startTag(ns,"AdmissionTabProduct");
                readAdmissionTabProduct(parser, admission);
            } else if (name.equals("AdmissionTabMarkingProduct")) {
                xmlSerializer.endTag(ns,"AdmissionTabProduct");
                xmlSerializer.startTag(ns,"AdmissionTabMarkingProduct");
                readAdmissionTabMarkingProduct(parser, admission);
            } else {
                skip(parser);
            }
        }
        return admission;
    }

    private Admission readAdmissionDoc(XmlPullParser parser) throws IOException, XmlPullParserException, ParseException {
        Admission admission_xml = null;
        String admissionNoXml = null;
        String admissionDataXml = null;
        String admissionTimeXml = null;
        String warhouseCodeXml = null;
        parser.require(XmlPullParser.START_TAG, ns, "AdmissionDok");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("AdmissionNo")) {
                xmlSerializer.startTag(ns,"AdmissionNo");
                admissionNoXml = readData(parser, "AdmissionNo");
                xmlSerializer.text(admissionNoXml);
                xmlSerializer.endTag(ns,"AdmissionNo");
            } else if (name.equals("AdmissionDate")) {
                xmlSerializer.startTag(ns,"AdmissionDate");
                admissionDataXml = readData(parser, "AdmissionDate");
                xmlSerializer.text(admissionDataXml);
                xmlSerializer.endTag(ns,"AdmissionDate");
            } else if (name.equals("AdmissionTime")) {
                xmlSerializer.startTag(ns,"AdmissionTime");
                admissionTimeXml = readData(parser, "AdmissionTime");
                xmlSerializer.text(admissionTimeXml);
                xmlSerializer.endTag(ns,"AdmissionTime");
            } else if (name.equals("Warehouse")) {
                xmlSerializer.startTag(ns,"Warehouse");
                warhouseCodeXml = readData(parser, "Warehouse");
                xmlSerializer.text(warhouseCodeXml);
                xmlSerializer.endTag(ns,"Warehouse");
                xmlSerializer.endTag(ns,"AdmissionDok");
            } else {
                skip(parser);
            }
        }
        admission_xml = new Admission(admissionNoXml, admissionDataXml, admissionTimeXml, warhouseCodeXml);
        return admission_xml;
    }

    private void readAdmissionTabProduct(XmlPullParser parser, Admission admission) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "AdmissionTabProduct");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("AdmissionProduct")) {
                xmlSerializer.startTag(ns,"AdmissionProduct");
                admission.addAdmissionProduct(readAdmissionProduct(parser));
            } else {
                skip(parser);
            }
        }
    }

    private void readAdmissionTabMarkingProduct(XmlPullParser parser, Admission admission) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "AdmissionTabMarkingProduct");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("AdmissionMarkingProduct")) {
                xmlSerializer.startTag(ns,"AdmissionMarkingProduct");
                admission.addAdmissionMarkingProduct(readAdmissionMarkingProduct(parser));
            } else {
                skip(parser);
            }
        }
    }

    private AdmissionProduct readAdmissionProduct(XmlPullParser parser) throws IOException, XmlPullParserException {
        AdmissionProduct admission_product_xml = null;
        String productCodeXml = null;
        String balanceValueXml = null;
        String balanceValueDocXml = null;
        String markingXml = null;
        parser.require(XmlPullParser.START_TAG, ns, "AdmissionProduct");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("ProductCode")) {
                xmlSerializer.startTag(ns,"ProductCode");
                productCodeXml = readData(parser, "ProductCode");
                xmlSerializer.text(productCodeXml);
                xmlSerializer.endTag(ns,"ProductCode");
            } else if (name.equals("BalanceValue")) {
                xmlSerializer.startTag(ns,"BalanceValue");
                balanceValueXml = readData(parser, "BalanceValue");
                xmlSerializer.text(balanceValueXml);
                xmlSerializer.endTag(ns,"BalanceValue");
            } else if (name.equals("BalanceValueDocs")) {
                xmlSerializer.startTag(ns,"BalanceValueDocs");
                balanceValueDocXml = readData(parser, "BalanceValueDocs");
                xmlSerializer.text(balanceValueDocXml);
                xmlSerializer.endTag(ns,"BalanceValueDocs");
            } else if (name.equals("Marking")) {
                xmlSerializer.startTag(ns,"Marking");
                markingXml = readData(parser, "Marking");
                xmlSerializer.endTag(ns,"Marking");
                xmlSerializer.endTag(ns,"AdmissionProduct");
            } else {
                skip(parser);
            }
        }
        admission_product_xml = new AdmissionProduct(productCodeXml, balanceValueXml, balanceValueDocXml, markingXml);
        return admission_product_xml;
    }

    private AdmissionMarkingProduct readAdmissionMarkingProduct(XmlPullParser parser) throws IOException, XmlPullParserException {
        AdmissionMarkingProduct admission_marking_product_xml = null;
        String productCodeXml = null;
        String barcodeLabelingXml = null;
        String markingCompletedXml = null;
        parser.require(XmlPullParser.START_TAG, ns, "AdmissionMarkingProduct");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("ProductCode")) {
                xmlSerializer.startTag(ns,"ProductCode");
                productCodeXml = readData(parser, "ProductCode");
                xmlSerializer.text(productCodeXml);
                xmlSerializer.endTag(ns,"ProductCode");
            } else if (name.equals("BarcodeLabeling")) {
                xmlSerializer.startTag(ns,"BarcodeLabeling");
                barcodeLabelingXml = readData(parser, "BarcodeLabeling");
                xmlSerializer.text(barcodeLabelingXml);
                xmlSerializer.endTag(ns,"BarcodeLabeling");
            } else if (name.equals("MarkingCompleted")) {
                xmlSerializer.startTag(ns,"MarkingCompleted");
                markingCompletedXml = readData(parser, "MarkingCompleted");
                xmlSerializer.text(markingCompletedXml);
                xmlSerializer.endTag(ns,"MarkingCompleted");
                xmlSerializer.endTag(ns,"AdmissionMarkingProduct");
                xmlSerializer.endTag(ns,"AdmissionTabMarkingProduct");
            } else {
                skip(parser);
            }
        }
        admission_marking_product_xml = new AdmissionMarkingProduct("",productCodeXml, barcodeLabelingXml, markingCompletedXml);
        return admission_marking_product_xml;
    }
}
