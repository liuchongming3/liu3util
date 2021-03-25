package org.liu3.crawler.webmagic;

import us.codecraft.webmagic.*;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;
import java.util.Map;

/**
 * @Author: liutianshuo
 * @Date: 2021/3/23
 */
public class OschinaBlogPageProcesser implements PageProcessor {

    private Site site = Site.me().setDomain("my.oschina.net");

    public static void main(String[] args) {

        OschinaBlogPageProcesser processer = new OschinaBlogPageProcesser();

        Spider spider = Spider.create(processer);
        spider.addUrl("http://my.oschina.net/flashsword/blog");
        //spider.addPipeline(new ConsolePipeline());

        spider.addPipeline(new Pipeline() {
            @Override
            public void process(ResultItems resultItems, Task task) {
                //功能和ConsolePipeline一样
                for(Map.Entry<String, Object> entry : resultItems.getAll().entrySet()){
                    System.out.println(entry.getKey()+"="+entry.getValue());
                }
            }
        });
        spider.run();


        Downloader dl = null;
        PageProcessor pp = null;
        Spider sp = null;

    }

    @Override
    public void process(Page page) {
        //http://my.oschina.net/flashsword/blog/
        List<String> links = page.getHtml().links().regex("http://my\\.oschina\\.net/flashsword/blog/\\d+").all();
        page.addTargetRequests(links);

        String title = page.getHtml().xpath("//div[@class='BlogEntity']/div[@class='BlogTitle']/h1").toString();
        String content = page.getHtml().$("div.content").toString();
        List<String> tags = page.getHtml().xpath("//div[@class='BlogTags']/a/text()").all();

        page.putField("title", title);
        page.putField("content", content);
        page.putField("tags", tags);
    }

    @Override
    public Site getSite() {
        return site;

    }


}
