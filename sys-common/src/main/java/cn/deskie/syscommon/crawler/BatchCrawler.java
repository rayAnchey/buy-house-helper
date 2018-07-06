package cn.deskie.syscommon.crawler;

import cn.deskie.syscommon.utils.DateUtils;
import cn.deskie.syscommon.utils.IdGen;
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

public class BatchCrawler extends BreadthCrawler {

    public static List fileList = null;

    private String firstPageUrl = "";


    public BatchCrawler(String crawlPath, boolean autoParse) {
        super(crawlPath, autoParse);
    }

    @Override
    public void visit(Page page, CrawlDatums crawlDatums) {
        Document doc = page.doc();
        String baseUrl = doc.baseUri();
        String webUrl = baseUrl.substring(0, baseUrl.lastIndexOf("/")) + "/";
        String attachedUrl = "http://wjj.xa.gov.cn/";
        String title = doc.title();

        //批次列表页面ifream获取
        Elements listPage = page.select("iframe[id=iframecenter]");
        if (ListUtil.isNotEmpty(listPage)) {
            for (Element element : listPage) {
                String href = element.attr("src");
                if (StringUtils.isNotBlank(href)) {
                    firstPageUrl = webUrl + href;
                    crawlDatums.add(firstPageUrl);
                }
            }
        }
        //添加分页的去i他url
        Elements pageSpan = page.select("span[class=pageCount]");
        if (ListUtil.isNotEmpty(pageSpan)) {
            Integer pageCount = Integer.parseInt(pageSpan.first().text().replaceAll("共", "").replaceAll("页", "").trim());
            for (int i = 1; i < pageCount; i++) {
                crawlDatums.add(firstPageUrl + "&_cimake=false&pageNumber=" + i);
            }

        }
        //批次列表页面
        Elements list = page.select("a[id=linkId]");
        if (ListUtil.isNotEmpty(list)) {
            for (Element element : list) {
                String href = element.attr("href");
                if (StringUtils.isNotBlank(href)) {
                    crawlDatums.add(webUrl + href);
                }
            }
        }

        //附件详情页
        Elements file = page.select("iframe[id=showconent1]");
        if (ListUtil.isNotEmpty(file)) {
            for (Element element : file) {
                String href = element.attr("src");
                if (StringUtils.isNotBlank(href)) {
                    crawlDatums.add(attachedUrl + href);
                }
            }
        }
        //下载链接
        Elements aLink = page.select("a");
        if (ListUtil.isNotEmpty(aLink)) {
            for (Element element : aLink) {
                String href = element.attr("href");
                if (StringUtils.isNotBlank(href) && href.startsWith("/attached/file") && href.endsWith(".zip")) {
                    //确定时下载页面，收集批次信息
                    Map<String,Object> map = new HashMap<>();
                    map.put("id",IdGen.uuid());
                    map.put("batchName",title);
                    map.put("pageUrl",baseUrl);
                    map.put("attachmentUrl",attachedUrl + href);
                    Elements details = page.select("div[id=divTopicInfo] font");
                    if (null != details && details.size() > 0) {
                        for (Element detail : details) {
                            String text = detail.text();
                            if (StringUtils.isNotBlank(text)) {
                                if(text.contains("发布时间")){
                                    map.put("publicTime",DateUtils.parseDate(text.replaceAll("发布时间：","").trim()));
                                }
                                if(text.contains("索 引 号")){
                                    map.put("reference",text.replaceAll("索 引 号：","").trim());
                                }
                            }
                        }
                    }
                    fileList.add(map);
                }
            }
        }
    }

    public static void main(String[] args) {

    }
}