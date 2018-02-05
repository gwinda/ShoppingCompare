

import edu.uci.ics.crawler4j.crawler.Page
import edu.uci.ics.crawler4j.crawler.WebCrawler
import edu.uci.ics.crawler4j.parser.HtmlParseData
import edu.uci.ics.crawler4j.url.WebURL
import groovy.json.JsonSlurper

/**
 * Created by LIBU on 2017/12/13.
 */
class MyCrawler extends WebCrawler{

    @Override
    boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase()  // 得到小写的url
        return href.indexOf('.html')>-1 || href.endsWith('jd.com/')
    }

    @Override
    void visit(Page page) {
        String url = page.getWebURL().getURL();  // 获取url
        System.out.println("URL: " + url);

        if(url.indexOf('item')>-1){
            if (page.getParseData() instanceof HtmlParseData) {  // 判断是否是html数据
                HtmlParseData htmlParseData = (HtmlParseData) page.getParseData(); // 强制类型转换，获取html数据对象
                String result = htmlParseData.getHtml();  // 获取页面Html

                //System.err.println("返回数据: ")
                //println result

                String titleElement = result.find("<title>([\\S\\s]*?)</title>")
                System.err.println("标题: \n${titleElement.substring(titleElement.indexOf('>')+1, titleElement.lastIndexOf('<'))}")

                System.err.println("套餐: ")
                String planJSON = result.find("colorSize: ([\\S\\s]*?)]")
                planJSON = planJSON.substring(11)
                JsonSlurper jsonSlurper = new JsonSlurper()
                jsonSlurper.parseText(planJSON)?.each {
                    String id = it.'skuId'
                    //URL priceURL = new URL("http://p.3.cn/prices/mgets?skuIds=J_${id}")
                    URL priceURL = new URL("http://p.3.cn/prices/mgets?skuIds=J_4318019")
                    System.err.println("套餐ID:  ${id}  详细:  ${it}  价格:  ￥${new JsonSlurper().parseText(request(priceURL))[0].'p'}")
                }
            }
        }

    }



}
