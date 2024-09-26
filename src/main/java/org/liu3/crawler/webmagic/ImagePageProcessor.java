package org.liu3.crawler.webmagic;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.selenium.SeleniumDownloader;
import us.codecraft.webmagic.processor.PageProcessor;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * @Author: liutianshuo
 * @Date: 2021/3/23
 */
public class ImagePageProcessor implements PageProcessor {

    public static InputStream inStream = null;
    Site site = Site.me();
    {
        site.setRetryTimes(3);
        site.setSleepTime(3000);
    }


    public static void main(String[] args){
        //系统配置文件
        System.setProperty("selenuim_config", "D:\\git\\liu3util\\src\\main\\resources\\config.ini");

        Spider sd = Spider.create(new ImagePageProcessor());
        sd.addUrl("https://pic.sogou.com/pics?query=%E5%AE%88%E6%9C%9B%E5%85%88%E9%94%8B&p=40230500&st=255&mode=255");	//要爬取的总链接

        sd.setDownloader(new SeleniumDownloader("C:/Program Files/Google/Chrome/Application/chromedriver.exe"));	//模拟启动浏览器
        sd.thread(2);	//线程
        sd.run();
    }

    @Override
    public void process(Page page) {

        //将匹配到的链接都存储到links集合中
        //https://i03piccdn.sogoucdn.com/b870cf141a325aec
        List<String> links = page.getHtml().regex("https://i03piccdn\\.sogoucdn\\.com/\\S+").all();
        //遍历links集合中的链接，然后下载
        for(int i = 0; i < links.size(); i++){
            String link = links.get(i);
            try {
                URL url = new URL(link);
                URLConnection con = url.openConnection();
                inStream = con.getInputStream();
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                int len = 0;
                while((len = inStream.read(buf)) != -1){
                    outStream.write(buf,0,len);
                }
                inStream.close();
                outStream.close();
                File file = new File("d://test//"+i+".jpg");	//图片下载地址
                FileOutputStream op = new FileOutputStream(file);
                op.write(outStream.toByteArray());
                op.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Site getSite() {
        return site;
    }
}
