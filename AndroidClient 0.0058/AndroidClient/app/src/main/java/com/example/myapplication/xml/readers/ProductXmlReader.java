package com.example.myapplication.xml.readers;

import com.example.myapplication.model.Product;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

public class ProductXmlReader extends XmlReader {

    public Product parseMapProducts(InputStream in, String productCode) throws IOException, XmlPullParserException {
        try {
            this.parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            this.parser.setInput(in, null);
            this.parser.nextTag();
            return readDocId(this.parser, productCode);
        } finally {
            in.close();
        }
    }

    private Product readDocId(XmlPullParser parser, String productCode) throws IOException, XmlPullParserException {
        Product product = null;
        parser.require(XmlPullParser.START_TAG, ns, "Файл");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("Product")) {
                product = readProduct(parser, productCode);
            } else {
                skip(parser);
            }
        }
        return product;
    }

    private Product readProduct(XmlPullParser parser, String productCode) throws IOException, XmlPullParserException {
        Product product = null;
        parser.require(XmlPullParser.START_TAG, ns, "Product");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("ProductData")) {
                Product product_xml = readProductData(parser);
                if (product_xml.getProductCode().equals(productCode)) {
                    product = product_xml;
                }
            } else {
                skip(parser);
            }
        }
        return product;
    }

    private Product readProductData(XmlPullParser parser) throws IOException, XmlPullParserException {
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
                nameXml = readData(parser,"Name");
            } else if (name.equals("VendorCode")) {
                vendorCodeXml = readData(parser,"VendorCode");
            } else if (name.equals("ProductCode")) {
                productCodeXml = readData(parser,"ProductCode");
            } else {
                skip(parser);
            }
        }
        product_xml = new Product(nameXml, productCodeXml, vendorCodeXml);
        return product_xml;
    }
}
