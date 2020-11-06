package com.example.myapplication.xml.readers_from_strings;

import android.util.Xml;

import com.example.myapplication.model.Product;
import com.example.myapplication.xml.readers.XmlReader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

public class ProductsXmlReaderFromString extends XmlReader {
    public ProductsXmlReaderFromString(String str) throws XmlPullParserException, IOException {
        parser = Xml.newPullParser();
        parser.setInput(new StringReader(str));
        parser.nextTag();
    }

    public ArrayList<Product> readProducts() throws IOException, XmlPullParserException {
        return readProducts(this.parser);
    }

    private ArrayList<Product> readProducts(XmlPullParser parser) throws IOException, XmlPullParserException {
        ArrayList<Product> products = new ArrayList<Product>();
        parser.require(XmlPullParser.START_TAG, ns, "Products");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("Product")) {
                products.add(readProduct(parser));
            } else {
                skip(parser);
            }
        }
        return products;
    }

    private Product readProduct(XmlPullParser parser) throws IOException, XmlPullParserException {
        Product product_xml=null;
        String nameXml=null;
        String productCodeXml=null;
        String vendorCodeXml=null;
        parser.require(XmlPullParser.START_TAG, ns, "Product");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if(name.equals("productName")){
                nameXml=readData(parser,"productName");
            }
            else if(name.equals("productCode")){
                productCodeXml=readData(parser,"productCode");
            }
            else if(name.equals("vendorCode")){
                vendorCodeXml=readData(parser,"vendorCode");
            }
            else{
                skip(parser);
            }
        }
        product_xml=new Product(nameXml,productCodeXml,vendorCodeXml);
        return product_xml;
    }
}
