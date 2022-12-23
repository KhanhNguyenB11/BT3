package com.example.bt3;

import android.util.Log;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import android.util.Log;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class RSSParser {
    private ArrayList<RSSItem> mItem;
    public RSSParser() {
    }

    public ArrayList<RSSItem> getXmlFromUrl(String url1) throws XmlPullParserException, IOException, ParserConfigurationException {
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(url1);
            urlConnection = (HttpURLConnection) url.openConnection();
            int code = urlConnection.getResponseCode();
            if (code != 200) {
                throw new IOException("Invalid response from server: " + code);
            }

            mItem = new ArrayList<RSSItem>();
            RSSItem item = null;
            InputStream istream = urlConnection.getInputStream();

            //XML DOM parser
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = builderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(istream);
            NodeList nList = doc.getElementsByTagName("item");
            for (int i = 0; i < nList.getLength(); i++) {
                if (nList.item(0).getNodeType() == Node.ELEMENT_NODE) {
                    item = new RSSItem();
                    Element elm = (Element) nList.item(i);
                    item.title = getNodeValue("title", elm);
                    item.pubdate = getNodeValue("pubDate", elm);
                    item.description = getNodeValue("description", elm);
                    item.guid = getNodeValue("guid", elm);
                    item.link = getNodeValue("link", elm);
                    item.img = getNodeValue("media:content", elm);
                    item.img_type = getNodeValue("media:description", elm);
                    Log.i("IMG",item.getImg());
                    mItem.add(item);
                }
            }
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return mItem;
    }
    protected String getNodeValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag);
        Node node = nodeList.item(0);
        int i =0;
        Node child;
        if (node != null) {
            if (tag == "media:content") {
                Element contentElement = (Element) node;
                if (contentElement.hasAttribute("url")) {
                    String img_link = contentElement.getAttribute("url");
                    return img_link;
                }
            }
            if (tag == "description") {
                for (child = node.getFirstChild(); child != null; child = child
                        .getNextSibling()) {
                    if(child.getNodeType() == 4){
                        return child.getNodeValue();
                    }
                }
            }
                if (node.hasChildNodes()) {
                    for (child = node.getFirstChild(); child != null; child = child
                            .getNextSibling()) {
                        if (child.getNodeType() == Node.TEXT_NODE || (child.getNodeType() == Node.CDATA_SECTION_NODE)) {
                            return child.getNodeValue();
                        }
                    }

                }
        }
        return "";
    }
}

