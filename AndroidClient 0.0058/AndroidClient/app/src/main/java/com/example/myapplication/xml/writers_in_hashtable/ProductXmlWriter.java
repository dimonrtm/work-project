package com.example.myapplication.xml.writers_in_hashtable;

import android.util.Xml;

import com.example.myapplication.model.Product;
import com.example.myapplication.xml.readers.XmlReader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class ProductXmlWriter extends XmlReader {
    XmlSerializer xmlSerializer;

    public ProductXmlWriter(OutputStream out) throws IOException {
        this.xmlSerializer = Xml.newSerializer();
        xmlSerializer.setOutput(out, "UTF-8");
        xmlSerializer.startDocument(null, Boolean.valueOf(true));
    }

    public Map<String, Product> parseMapProducts(InputStream in) throws IOException, XmlPullParserException {
        try {
            this.parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            this.parser.setInput(in, null);
            this.parser.nextTag();
            return readDocId(this.parser);
        } finally {
            in.close();
        }
    }

    private Map<String, Product> readDocId(XmlPullParser parser) throws IOException, XmlPullParserException {
        Map<String, Product> products = null;
        parser.require(XmlPullParser.START_TAG, ns, "Файл");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("Product")) {
                products = readProduct(parser);
            } else {
                skip(parser);
            }
        }
        return products;
    }

    private Map<String,Product> readProduct(XmlPullParser parser) throws IOException, XmlPullParserException {
        Map<String,Product> products =new HashMap<String,Product>();
        parser.require(XmlPullParser.START_TAG, ns, "Product");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("ProductData")) {
                readProductData(parser,products);
            } else {
                skip(parser);
            }
        }
        return products;
    }

    private void readProductData(XmlPullParser parser,Map<String,Product> products) throws IOException, XmlPullParserException {
        Product product_xml = null;
        String nameXml = null;
        String vendorCodeXml = null;
        String productCodeXml = null;
        parser.require(XmlPullParser.START_TAG, ns, "ProductData");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("Name")) {
                nameXml = readData(parser, "Name");
            } else if (name.equals("VendorCode")) {
                vendorCodeXml = readData(parser, "VendorCode");
            } else if (name.equals("ProductCode")) {
                productCodeXml = readData(parser, "ProductCode");
            } else {
                skip(parser);
            }
        }
        product_xml = new Product(nameXml, productCodeXml, vendorCodeXml);
        products.put(productCodeXml, product_xml);
    }

    public void writeProducts(Map<String,Product> products) throws IOException {
        xmlSerializer.startTag(ns,"Файл");
        xmlSerializer.startTag(ns,"Product");
        for(String productCode:products.keySet()){
            Product product=products.get(productCode);
            xmlSerializer.startTag(ns,"ProductData");
            xmlSerializer.startTag(ns,"Name");
            xmlSerializer.text(product.getName());
            xmlSerializer.endTag(ns,"Name");
            xmlSerializer.startTag(ns,"VendorCode");
            xmlSerializer.text(product.getVendorCode());
            xmlSerializer.endTag(ns,"VendorCode");
            xmlSerializer.startTag(ns,"ProductCode");
            xmlSerializer.text(productCode);
            xmlSerializer.endTag(ns,"ProductCode");
            xmlSerializer.endTag(ns,"ProductData");
        }
        xmlSerializer.endTag(ns,"Product");
        xmlSerializer.endTag(ns,"Файл");
        xmlSerializer.endDocument();
        xmlSerializer.flush();
    }
}
