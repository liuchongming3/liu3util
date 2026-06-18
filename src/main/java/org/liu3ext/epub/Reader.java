package org.liu3ext.epub;

import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Metadata;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.epub.EpubReader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Reader {


    public static void main(String[] args) {
        try (InputStream epubStream = new FileInputStream("D:/图片/mgwh.epub")) {
            // 1. 读取EPUB文件为Book对象
            Book book = new EpubReader().readEpub(epubStream);

            // 2. 获取元数据 (Metadata)
            Metadata metadata = book.getMetadata();
            System.out.println("标题: " + metadata.getFirstTitle());
            System.out.println("作者: " + metadata.getAuthors());
            book.getResources().getResourceMap();
            // 3. 获取所有资源 (Resources)
            Collection<String> allHrefs = book.getResources().getAllHrefs();
            System.out.println("总资源数: " + allHrefs.size());

            List<Resource> list = book.getContents();

            List<List<String>> textList = new ArrayList<>();

            for(Resource resource : list){
                if (resource.getMediaType().getName().contains("html")) {

                    ArrayList<String> subList = new ArrayList<>();
                    String content = new String(resource.getData());

                    Document doc = Jsoup.parse(content);
                    // 遍历 body 下的直接子元素
                    for (Element element : doc.body().children()) {
                        String text = element.text().trim();
                        if (!text.isEmpty()) {
                            subList.add(text);
                        }
                    }
                    textList.add(subList);
                }
            }
            // 4. 遍历资源并处理 (如打印文本内容)
            for(List<String> s : textList){
                System.out.println(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
