package com.example.myapplication.xml.readers;

import android.util.Xml;

import com.example.myapplication.model.User;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class UserXmlReader extends XmlReader {

    public List<User> parseUsers(InputStream in, String notLogin, String notCode, String notPassword) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readDocId(parser, notLogin, notCode, notPassword);
        } finally {
            in.close();
        }
    }

    private List<User> readDocId(XmlPullParser parser, String notLogin, String notCode, String notPassword) throws XmlPullParserException, IOException {
        List<User> users = null;
        parser.require(XmlPullParser.START_TAG, ns, "Файл");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("User")) {
                users = readUser(parser, notLogin, notCode, notPassword);
            } else {
                skip(parser);
            }
        }
        return users;
    }

    private List<User> readUser(XmlPullParser parser, String notLogin, String notCode, String notPassword) throws XmlPullParserException, IOException {
        List<User> users = new ArrayList<User>();
        parser.require(XmlPullParser.START_TAG, ns, "User");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("UserData")) {
                users.add(readUserData(parser, notLogin, notCode, notPassword));
                //users.add(new User("user1","777","cxjhjh"));
            } else {
                skip(parser);
            }
        }
        return users;
    }

    private User readUserData(XmlPullParser parser, String notLogin, String notCode, String notPassword) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "UserData");
        User user = null;
        String userLoginXML = null;
        String userCodeXML = null;
        String warehouseCodeXML=null;
        String userPasswordXML = null;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("UserLogin")) {
                userLoginXML = readData(parser, "UserLogin");
                if (userLoginXML == null || userLoginXML.equals("")) {
                    throw new IOException(notLogin);
                }
            } else if (name.equals("UserCode")) {
                userCodeXML = readData(parser, "UserCode");
                if (userCodeXML == null || userCodeXML.equals("")) {
                    throw new IOException(notCode);
                }
            } else if (name.equals("UserPassword")) {
                userPasswordXML = readData(parser, "UserPassword");
                if (userPasswordXML == null || userPasswordXML.equals("")) {
                    throw new IOException(notPassword);
                }
            }
            else if(name.equals("WarehouseCode")){
                warehouseCodeXML=readData(parser,"WarehouseCode");
            }
            else {
                skip(parser);
            }
        }
        user = new User(userLoginXML, userCodeXML,warehouseCodeXML, userPasswordXML);
        return user;
    }

}
