package com.example.myapplication.xml.writers_to_string;

import android.util.Xml;

import com.example.myapplication.model.User;

import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

public class UsersWriterToString {
    private XmlSerializer xmlSerializer;
    private StringWriter writer;

    public UsersWriterToString() throws IOException {
        xmlSerializer= Xml.newSerializer();
        writer = new StringWriter();
        xmlSerializer.setOutput(writer);
        xmlSerializer.startDocument("UTF8",true);
        xmlSerializer.startTag(null,"Файл");
    }

    public String writeUsers(List<User> users) throws IOException {
      xmlSerializer.startTag(null,"User");
      for(User u:users){
          xmlSerializer.startTag(null,"UserData");
          xmlSerializer.startTag(null,"UserLogin");
          xmlSerializer.text(u.getUserLogin());
          xmlSerializer.endTag(null,"UserLogin");
          xmlSerializer.startTag(null,"UserCode");
          xmlSerializer.text(u.getUserCode());
          xmlSerializer.endTag(null,"UserCode");
          xmlSerializer.startTag(null,"WarehouseCode");
          xmlSerializer.text(u.getWarehouseCode());
          xmlSerializer.endTag(null,"WarehouseCode");
          xmlSerializer.startTag(null,"UserPassword");
          xmlSerializer.text(u.getUserPassword());
          xmlSerializer.endTag(null,"UserPassword");
          xmlSerializer.endTag(null,"UserData");
      }
      xmlSerializer.endTag(null,"User");
      xmlSerializer.endTag(null,"Файл");
      xmlSerializer.endDocument();
      return writer.toString();
    }
}
