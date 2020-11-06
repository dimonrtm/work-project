package com.example.myapplication.xml.readers_from_strings;

import android.util.Xml;

import com.example.myapplication.model.User;
import com.example.myapplication.xml.readers.XmlReader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

public class UsersXmlReaderFromString extends XmlReader {

    public UsersXmlReaderFromString(String str) throws XmlPullParserException, IOException {
        parser = Xml.newPullParser();
        parser.setInput(new StringReader(str));
        parser.nextTag();
    }

    public ArrayList<User> readUsers() throws IOException, XmlPullParserException {
        return readUsers(this.parser);
    }

    private ArrayList<User> readUsers(XmlPullParser parser) throws IOException, XmlPullParserException {
        ArrayList<User> users = new ArrayList<User>();
        parser.require(XmlPullParser.START_TAG, ns, "Users");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("User")) {
                users.add(readUser(parser));
            } else {
                skip(parser);
            }
        }
        return users;
    }

    private User readUser(XmlPullParser parser) throws IOException, XmlPullParserException {
        User user_xml=null;
        String userCodeXml=null;
        String warehouseCodeXml=null;
        String userLoginXml=null;
        String userPasswordXml=null;
        parser.require(XmlPullParser.START_TAG, ns, "User");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if(name.equals("userCode")){
              userCodeXml=readData(parser,"userCode");
            }
            else if(name.equals("userLogin")){
              userLoginXml=readData(parser,"userLogin");
            }
            else if(name.equals("userPassword")){
                userPasswordXml=readData(parser,"userPassword");
            }
            else if(name.equals("warehouseCode")){
                warehouseCodeXml=readData(parser,"warehouseCode");
            }
            else{
                skip(parser);
            }
        }
        user_xml=new User(userLoginXml,userCodeXml,warehouseCodeXml,userPasswordXml);
        return user_xml;
    }
}
