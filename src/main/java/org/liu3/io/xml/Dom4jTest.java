package org.liu3.io.xml;

import java.io.File;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class Dom4jTest {

    /*
     * <?xml version="1.0" encoding="GB2312"?>
        <RESULT>
        <VALUE>
          <NO>A1234</NO>
          <ADDR>河南省郑州市</ADDR>
        </VALUE>
        <VALUE>
          <NO>B1234</NO>
          <ADDR>河南省郑州市二七区</ADDR>
        </VALUE>
        </RESULT>
     */
    public static void main(String[] args) {
        
        SAXReader reader = new SAXReader();   
        try {
            Document doc = reader.read(new File("1.xml"));   
            Element root = doc.getRootElement();   
            Element foo;   
            for (Iterator<Element> i = root.elementIterator("VALUE"); i.hasNext();) {   
                foo = (Element) i.next();   
                System.out.print("车牌号码:" + foo.elementText("NO"));   
                System.out.println("车主地址:" + foo.elementText("ADDR"));   
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }finally {
        }
    }
}
