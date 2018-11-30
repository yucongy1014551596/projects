package com.example.yucong.web.sax;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MyHandler extends DefaultHandler {


    private String nodeNAme;

    private StringBuilder id;
    private StringBuilder name;
    private StringBuilder version;





    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        id=new StringBuilder();
        name=new StringBuilder();
        version=new StringBuilder();
    }


    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);

      //记录当前节点
        nodeNAme=localName;

    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);

        if("id".equals(nodeNAme)){
            id.append(ch,start,length);
        }else  if ("name".equals(nodeNAme)){
            name.append(ch,start,length);
        }else  if ("version".equals(nodeNAme)){
            version.append(ch,start,length);
        }







    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);

        if("app".equals(localName)){
            Log.d("msg", "id is" + id.toString().trim());
            Log.d("msg", "name is" + name.toString().trim());
            Log.d("msg", "version is" + version.toString().trim());
            //最后要将StringBuilder 清空掉
            id.setLength(0);
            name.setLength(0);
            version.setLength(0);

        }





    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }
}
