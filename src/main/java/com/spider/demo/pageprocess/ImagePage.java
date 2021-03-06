package com.spider.demo.pageprocess;

import com.spider.demo.pipeline.ImagePipeline;
import com.spider.demo.utils.ImageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.Date;
import java.util.List;

public class ImagePage implements PageProcessor {

    private final static Logger logger = LoggerFactory.getLogger(ImagePage.class);

    private final static int RETRY_TIMES = 3;
    private final static int SLEEP_TIME = 1000;

    private Site site = Site.me().setRetryTimes(RETRY_TIMES).setSleepTime(SLEEP_TIME);

    public static int i = 0;

    @Override
    public Site getSite() {
        return site;
    }



    /**
     * url = http://www.win4000.com/zt/gaoqing_1.html
     * @param page
     */
//    @Override
//    public void process(Page page) {
//        List<String> links = page.getHtml().xpath("//ul[@class='clearfix']/li/a").links().all();
//        page.addTargetRequests(links);
//        if (page.getUrl().regex("http://www\\.win4000\\.com/zt/gaoqing_\\w+.html").match()) {
//            List<String> detailUrl = page.getHtml().xpath("//ul[@class='clearfix']/li/a").links().all();
//            for (String temp : detailUrl) {
//                System.out.println("============" + temp);
//            }
//
//
//            page.addTargetRequests(detailUrl);
//        }else {
//            String picUrl = page.getHtml().xpath("//div[@class='pic-meinv']/a").css("img","src").toString();
//            System.out.println("============"+picUrl);
//            page.putField("url", picUrl);
//            ImageUtils.PicDownload(picUrl, String.valueOf(i)+".jpg");
//            i++;
//        }
//
//    }

    /**
     * url = http://www.win4000.com/zt/meinv.html
     * @param page
     */
//    @Override
//    public void process(Page page) {
//        List<String> links = page.getHtml().xpath("//div[@class='pages']/div/a").links().all();
//        page.addTargetRequests(links);
//        if (page.getUrl().regex("http://www\\.win4000\\.com/zt/meinv_\\w+.html").match()
//                || page.getUrl().regex("http://www\\.win4000\\.com/zt/meinv\\.html").match()) {
////            System.out.println(page.getHtml().toString());
////            System.out.println(page.getRequest().toString());
////            System.out.println(page.getUrl().toString());
////            System.out.println(page.getRequest().getExtra("level"));
//            List<String> links2 = page.getHtml().xpath("//div[@class='Left_bar']//ul[@class='clearfix']/li/a").links().all();
//            page.addTargetRequests(links2);
//        } else {
//            String picUrl = page.getHtml().xpath("//div[@class='pic-meinv']/a").css("img","src").toString();
//            String nextPageUrl = page.getHtml().xpath("//div[@class='pic-next-img']").css("a","href").toString();
//            page.putField("url", picUrl);
//            page.putField("createTime", new Date());
//            page.addTargetRequest(nextPageUrl);
////            ImageUtils.PicDownload(picUrl, String.valueOf(i)+".jpg");
//            logger.info("=======page : {}, url: {} , i:{}",page.getUrl().toString(), picUrl, i);
//            i++;
//        }
//    }


    /**
     *特定网页视频下载
     * @param page
     */
    @Override
    public void process(Page page) {
        List<String> links = page.getHtml().xpath("//div[@class='outline-list']//ul[@class='video-list']/li/a/@href").all();
        page.addTargetRequests(links);
        try {
            Thread.sleep(25000L);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        if (page.getUrl().regex("http://www\\.win4000\\.com/zt/meinv_\\w+.html").match()
                || page.getUrl().regex("http://www\\.win4000\\.com/zt/meinv\\.html").match()) {
//            System.out.println(page.getHtml().toString());
//            System.out.println(page.getRequest().toString());
//            System.out.println(page.getUrl().toString());
//            System.out.println(page.getRequest().getExtra("level"));
            List<String> links2 = page.getHtml().xpath("//div[@class='Left_bar']//ul[@class='clearfix']/li/a").links().all();
            page.addTargetRequests(links2);
        } else {
            String videoUrl = page.getHtml().xpath("//div[@class='sewiseplayer-container']").css("video", "src").toString();
//            String nextPageUrl = page.getHtml().xpath("//div[@class='pic-next-img']").css("a", "href").toString();
            page.putField("url", videoUrl);
            page.putField("createTime", new Date());
//            page.addTargetRequest(nextPageUrl);
//            ImageUtils.PicDownload(videoUrl, String.valueOf(i)+".mp3");
            logger.info("=======page : {}, url: {} , i:{}", page.getUrl().toString(), videoUrl, i);
            i++;
        }
    }


    public static void main(String[] args) {
//        System.setProperties();
        Date beginTime = new Date();
        Spider.create(new ImagePage())
                .addUrl("http://www.icoolxue.com/album/show/45")
                .thread(4)
                .addPipeline(new ImagePipeline())
                .start();
        Date endTime = new Date();
        System.out.println("====================="+ (endTime.getTime() - beginTime.getTime()));

    }


}
