package com.workspace.server.configuration

import groovy.json.JsonSlurper
import org.apache.http.HttpResponse
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.DefaultHttpClient

/**
 * Created by CHENMA11 on 1/25/2018.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class MyThread extends Thread{
    public void run() {
        try {
            def list=['https://item.taobao.com/item.htm?spm=5148.7631246.0.0.41e1f054HcYmMt&id=162446207','https://item.taobao.com/item.htm?spm=5148.7631246.0.0.41e1f054HcYmMt&id=540229317388','https://item.taobao.com/item.htm?spm=a21k9.8082836.354371.58.7daee5c7zI2LvO&scm=1007.12144.81309.68658_0&pvid=ba36301b-2d1c-4a16-9a3d-0f5e16ba3d44&id=562052482625','https://item.taobao.com/item.htm?spm=2013.1.20141001.4.6db29a47yaFbO9&id=563008872471&scm=1007.12144.81309.42296_42296&pvid=4118a795-af2f-49cc-b52e-b2a5f659c6f2',
                      'https://item.taobao.com/item.htm?spm=a21k9.8082836.354371.144.7daee5c7zI2LvO&scm=1007.12144.81309.68663_0&pvid=506af358-28ea-454d-8119-3837b98b586b&id=564472691726',
                      'https://item.taobao.com/item.htm?spm=2013.1.20141001.1.11ca5de9457u6d&id=564473847148&scm=1007.12144.81309.42296_42296&pvid=56c0cfdf-6e3a-4af5-883a-49725c9fc9bf',
                     'https://item.taobao.com/auction/noitem.htm?itemid=36103588764&catid=0&spm=a217x.7278569.1997506313.2.2ceb0202Arj00Q&scm=1029.newlist-0.1.28&ppath=&sku=&ug=',
                    'https://item.taobao.com/auction/noitem.htm?itemid=15626246454&catid=0&spm=a217x.7278569.1997506313.6.2ceb0202Arj00Q&scm=1029.newlist-0.1.28&ppath=&sku=&ug='
            ]
            for(int i=0;i<list.size();i++) {

                HttpClient httpclient = new DefaultHttpClient();
                String url = list[i]//'https://item.taobao.com/item.htm?spm=2013.1.20141001.3.44dc9059wfayUV&id=563569212451&scm=1007.12144.81309.42296_42296&pvid=2863fa09-9e22-4813-93e8-12512ae95ee2'

                String id = getStrByPrePost(url, "id=", "&");//根据url获取商品的id
                println "id"+id
                if (id != null) {
                    //组装出获取商品信息的url//&sellerId=745740386
                    // String detailUrl="http://mdskip.taobao.com/core/initItemDetail.htm?itemId="+id+"×tamp=1429065419751&tgTag=false&cartEnable=true&addressLevel=2&progressiveSupport=false&isUseInventoryCenter=false&household=false¬AllowOriginPrice=false&isRegionLevel=false&sellerUserTag=303632416&sellerUserTag2=18020085046181888&tmallBuySupport=true&isAreaSell=false&queryMemberRight=true&isIFC=false&sellerUserTag3=70368779862144&sellerUserTag4=8800522208643&service3C=true&tryBeforeBuy=false&isSecKill=false&isForbidBuyItem=false&sellerPreview=false&itemTags=843,1163,1478,1483,1547,1611,1803,1867,2049,2059,2443,2507,2635,3787,3974,4166,4555,4811,5323,21762,21826,25282,28802,29122,34178,56130,56194&isApparel=false&offlineShop=false&showShopProm=false&callback=setMdskip";
                    String detailUrl = 'https://detailskip.taobao.com/service/getData/1/p1/item/detail/sib.htm?itemId=' + id + '&modules=dynStock,qrcode,viewer,price,duty,xmpPromotion,delivery,upp,activity,fqg,zjys,couponActivity,soldQuantity,originalPrice,tradeContract&callback=onSibRequestSuccess'
                    HttpGet get = new HttpGet(detailUrl)
                    get.addHeader("Referer", url);//Referer必须要设置
                    HttpResponse response = httpclient.execute(get);
                    println response.class
                    String content = getContent(response);
                    //将价格随机便签换成固定值
                    content?.toString()?.replaceAll(';[0-9]\\d*:[0-9]\\d*;[0-9]\\d*:[0-9]\\d*;') { ch ->
                        content = content.replace(ch?.toString(), "ABCCCCCCC")
                    }
                    //println content
                    def jsonSlurper = new JsonSlurper()
                    def object = jsonSlurper.parseText(content?.substring(content.lastIndexOf('(') + 1, content.lastIndexOf(')') - 1))
                    println("原始标价： " + object.data.price);
                    println("发货城市： " + object.data.deliveryFee.data.sendCity)
                    println("淘宝价： " + object.data.promotion.promoData.ABCCCCCCC.price[0])
                    println("剩余数量： " + object.data.soldQuantity.confirmGoodsCount)
                    println("淘宝链接： " + url)
                    System.out.println("2任务运行结束----------------------");
                }
            }

        }catch(Exception e){

            }
    }

    /**
     * 获取返回内容的string
     * @param response
     * @return
     * @throws IllegalStateException
     * @throws IOException
     */
    private  String getContent(HttpResponse response) throws IllegalStateException, IOException {
        java.io.InputStream is = response.getEntity().getContent();
        BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));
        //response.getEntity().getContentEncoding().getName()
        String line = "";
        String temp = null;
        while ((temp = br.readLine()) != null) {
            line += temp;
        }
        return line;

    }

    /**
     * 根据前后的文本截取指定的内容。
     * @param str
     * @param pre
     * @param post
     * @return
     */
    public static String getStrByPrePost(String str, String pre, String post) {
        if (str != null) {
            if (pre != null) {
                int s = str.indexOf(pre);
                if (s > -1) {
                    str = str.substring(s + pre.length(), str.length());
                } else {
                    return null;
                }

            }
            if (post != null) {
                int e = str.indexOf(post);
                if (e > -1) {
                    str = str.substring(0, e);
                } else {
                    return null;
                }

            }
        }

        return str;
    }

    public static void main(String[] args){
        for (int i = 1; i <=5; i++) {
                new MyThread().start();
        }
    }

}