package cn.deskie.syscommon.crawler;

import cn.deskie.syscommon.utils.ListUtil;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PricePublicCrawler extends BreadthCrawler {

    public  static  List<Object> fileList = null;

    private String firstPageUrl = "";


    public PricePublicCrawler(String crawlPath, boolean autoParse) {
        super(crawlPath, autoParse);
    }

    @Override
    public void visit(Page page, CrawlDatums crawlDatums) {
        Document doc=page.doc();
        String baseUrl = doc.baseUri();
        String webUrl = baseUrl.substring(0,baseUrl.lastIndexOf("/"))+"/";
        String attachedUrl = "http://wjj.xa.gov.cn/";
        String title = doc.title();

        //批次列表页面ifream获取
        Elements listPage = page.select("iframe[id=iframecenter]");
        if(ListUtil.isNotEmpty(listPage)){
            for(Element element:listPage) {
                String href = element.attr("src");
                if(StringUtils.isNotBlank(href)) {
                    firstPageUrl = webUrl + href;
                    crawlDatums.add(firstPageUrl);
                }
            }
        }
        //添加分页的去i他url
        Elements pageSpan = page.select("span[class=pageCount]");
        if(ListUtil.isNotEmpty(pageSpan)){
            Integer pageCount = Integer.parseInt(pageSpan.first().text().replaceAll("共","").replaceAll("页","").trim());
            for(int i=1;i<pageCount;i++){
                crawlDatums.add(firstPageUrl+"&_cimake=false&pageNumber="+i);
            }

        }
        //批次列表页面
        Elements list = page.select("a[id=linkId]");
        if(ListUtil.isNotEmpty(list)){
            for(Element element:list){
                String href = element.attr("href");
                if(StringUtils.isNotBlank(href)) {
                    crawlDatums.add(webUrl + href);
                }
            }
        }

        //附件详情页
            Elements file = page.select("iframe[id=showconent1]");
            if(ListUtil.isNotEmpty(file)){
                for(Element element:file) {
                    String href = element.attr("src");
                    if(StringUtils.isNotBlank(href)) {
                        crawlDatums.add(attachedUrl + href);
                    }
                }
            }
        //下载链接
        Elements aLink = page.select("a");
        if(ListUtil.isNotEmpty(aLink)) {
            for (Element element : aLink) {
                String href = element.attr("href");
                if(StringUtils.isNotBlank(href)&&href.startsWith("/attached/file")&&href.endsWith(".zip")){
                    Map map = new HashMap<>();
                    map.put("title",title);
                    map.put("file",attachedUrl + href);
                    fileList.add(map);
                }
            }
        }
        System.out.println("URL:" + page.url() + "  标题:" + title);
    }
    public static void main(String[] args) {
        PricePublicCrawler.fileList = new ArrayList<>();
        PricePublicCrawler crawler2 = new PricePublicCrawler("d:/opt/crawler",true);
        crawler2.addSeed("http://wjj.xa.gov.cn/ptl/def/def/index_1285_3887_ci_trid_4416419.html");
        crawler2.setThreads(1);
        crawler2.setResumable(false);
        try {
            crawler2.start(30);
        } catch (Exception e) {

        }
        System.out.println(PricePublicCrawler.fileList);
        PricePublicCrawler.fileList = null;
    }
}