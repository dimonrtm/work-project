package com.example.myapplication.xml.writers_in_hashtable;

import android.util.Xml;

import com.example.myapplication.model.Admission;
import com.example.myapplication.model.AdmissionMarkingProduct;
import com.example.myapplication.model.AdmissionProduct;
import com.example.myapplication.xml.readers.XmlReader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;

public class AdmissionXmlWriter extends XmlReader {
    private XmlSerializer xmlSerializer;

    public AdmissionXmlWriter(OutputStream out) throws IOException {
        this.xmlSerializer = Xml.newSerializer();
        xmlSerializer.setOutput(out, "UTF-8");
        xmlSerializer.startDocument(null, Boolean.valueOf(true));
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
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("AdmissionData")) {
                admission = readAdmissionData(parser);
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
                admission = readAdmissionDoc(parser);
            } else if (name.equals("AdmissionTabProduct")) {
                readAdmissionTabProduct(parser, admission);
            } else if (name.equals("AdmissionTabMarkingProduct")) {
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
                admissionNoXml = readData(parser, "AdmissionNo");
            } else if (name.equals("AdmissionDate")) {
                admissionDataXml = readData(parser, "AdmissionDate");
            } else if (name.equals("AdmissionTime")) {
                admissionTimeXml = readData(parser, "AdmissionTime");
            } else if (name.equals("Warehouse")) {
                warhouseCodeXml = readData(parser, "Warehouse");
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
                productCodeXml = readData(parser, "ProductCode");
            } else if (name.equals("BalanceValue")) {
                balanceValueXml = readData(parser, "BalanceValue");
            } else if (name.equals("BalanceValueDocs")) {
                balanceValueDocXml = readData(parser, "BalanceValueDocs");
            } else if (name.equals("Marking")) {
                markingXml = readData(parser, "Marking");
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
                productCodeXml = readData(parser, "ProductCode");
            } else if (name.equals("BarcodeLabeling")) {
                barcodeLabelingXml = readData(parser, "BarcodeLabeling");
            } else if (name.equals("MarkingCompleted")) {
                markingCompletedXml = readData(parser, "MarkingCompleted");
            } else {
                skip(parser);
            }
        }
        admission_marking_product_xml = new AdmissionMarkingProduct("",productCodeXml, barcodeLabelingXml, markingCompletedXml);
        return admission_marking_product_xml;
    }

    public void writeAdmission(Admission admission) throws IOException {
//        xmlSerializer.startTag(ns,"Файл");
//        xmlSerializer.startTag(ns,"AdmissionData");
//        xmlSerializer.startTag(ns,"AdmissionDok");
//        xmlSerializer.startTag(ns,"AdmissionNo");
//        xmlSerializer.text(admission.getId());
//        xmlSerializer.endTag(ns,"AdmissionNo");
//        xmlSerializer.
////        while(it.hasNext()){
////            xmlSerializer.startTag(ns,"BalanceData");
////            MultiKey<? extends String> key=it.next();
////            Double bal=it.getValue();
////            xmlSerializer.startTag(ns,"ProductCode");
////            xmlSerializer.text(key.getKey(0));
////            xmlSerializer.endTag(ns,"ProductCode");
////            xmlSerializer.startTag(ns,"WarehouseCode");
////            xmlSerializer.text(key.getKey(1));
////            xmlSerializer.endTag(ns,"WarehouseCode");
////            xmlSerializer.startTag(ns,"BalanceValue");
////            xmlSerializer.text(bal.toString());
////            xmlSerializer.endTag(ns,"BalanceValue");
////            xmlSerializer.endTag(ns,"BalanceData");
////        }
//        xmlSerializer.endTag(ns,"AdmissionData");
//        xmlSerializer.endTag(ns,"Файл");
//        xmlSerializer.endDocument();
//        xmlSerializer.flush();
    }
}
