package com.example.myapplication.xml.writers_to_string;

import android.util.Xml;

import com.example.myapplication.model.Balance;

import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

public class BalancesWriterToString {
    private XmlSerializer xmlSerializer;
    private StringWriter writer;

    public BalancesWriterToString() throws IOException {
        xmlSerializer= Xml.newSerializer();
        writer = new StringWriter();
        xmlSerializer.setOutput(writer);
        xmlSerializer.startDocument("UTF8",true);
        xmlSerializer.startTag(null,"Файл");
    }

    public String writeBalances(List<Balance> balances) throws IOException {
        xmlSerializer.startTag(null,"Balance");
        for(Balance b:balances){
            xmlSerializer.startTag(null,"BalanceData");
            xmlSerializer.startTag(null,"ProductCode");
            xmlSerializer.text(b.getProductCode());
            xmlSerializer.endTag(null,"ProductCode");
            xmlSerializer.startTag(null,"WarehouseCode");
            xmlSerializer.text(b.getWarehouseCode());
            xmlSerializer.endTag(null,"WarehouseCode");
            xmlSerializer.startTag(null,"BalanceValue");
            xmlSerializer.text(b.getBalance()+"");
            xmlSerializer.endTag(null,"BalanceValue");
            xmlSerializer.startTag(null,"ReserveValue");
            xmlSerializer.text(b.getReserve()+"");
            xmlSerializer.endTag(null,"ReserveValue");
            xmlSerializer.endTag(null,"BalanceData");
        }
        xmlSerializer.endTag(null,"Balance");
        xmlSerializer.endTag(null,"Файл");
        xmlSerializer.endDocument();
        return writer.toString();
    }
}
