package com.example.myapplication.xml.writers_in_hashtable;

import android.util.Xml;

import com.example.myapplication.model.User;
import com.example.myapplication.xml.readers.XmlReader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class UserXmlWriter extends XmlReader {
    private XmlSerializer xmlSerializer;

    public UserXmlWriter(OutputStream out) throws IOException {
        this.xmlSerializer= Xml.newSerializer();
        xmlSerializer.setOutput(out,"UTF-8");
        xmlSerializer.startDocument(null, Boolean.valueOf(true));
    }

    public Map<String,User> parseUsers(InputStream in, String notLogin, String notCode, String notPassword) throws XmlPullParserException, IOException {
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

    private Map<String,User> readDocId(XmlPullParser parser, String notLogin, String notCode, String notPassword) throws XmlPullParserException, IOException {
        Map<String,User> users = null;
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

    private Map<String,User> readUser(XmlPullParser parser, String notLogin, String notCode, String notPassword) throws XmlPullParserException, IOException {
        Map<String,User> users =new HashMap<String,User>();
        parser.require(XmlPullParser.START_TAG, ns, "User");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("UserData")) {
                readUserData(parser,users, notLogin, notCode, notPassword);
                //users.add(new User("user1","777","cxjhjh"));
            } else {
                skip(parser);
            }
        }
        return users;
    }

    private void readUserData(XmlPullParser parser,Map<String,User> users, String notLogin, String notCode, String notPassword) throws XmlPullParserException, IOException {
        User user=null;
        parser.require(XmlPullParser.START_TAG, ns, "UserData");
        String userLoginXML = null;
        String userCodeXML = null;
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
            } else {
                skip(parser);
            }
        }
        user = new User(userLoginXML, userCodeXML,"", userPasswordXML);
        users.put(userLoginXML,user);
    }

    public void usersWrite(Map<String,User> users) throws IOException {
        xmlSerializer.startTag(ns,"Файл");
        xmlSerializer.startTag(ns,"User");
        for(String userLogin:users.keySet()){
            User user=users.get(userLogin);
            xmlSerializer.startTag(ns,"UserData");
            xmlSerializer.startTag(ns,"UserLogin");
            xmlSerializer.text(user.getUserLogin());
            xmlSerializer.endTag(ns,"UserLogin");
            xmlSerializer.startTag(ns,"UserCode");
            xmlSerializer.text(user.getUserCode());
            xmlSerializer.endTag(ns,"UserCode");
            xmlSerializer.startTag(ns,"UserPassword");
            xmlSerializer.text(user.getUserPassword());
            xmlSerializer.endTag(ns,"UserPassword");
            xmlSerializer.endTag(ns,"UserData");
        }
        xmlSerializer.endTag(ns,"User");
        xmlSerializer.endTag(ns,"Файл");
        xmlSerializer.endDocument();
        xmlSerializer.flush();
    }
}
