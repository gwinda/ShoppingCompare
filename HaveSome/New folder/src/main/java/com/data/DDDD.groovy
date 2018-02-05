package com.data

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils
import groovy.json.JsonSlurper
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public static void main(String[] args) throws ClientProtocolException, IOException {

    def loginUrl='https://login.taobao.com/member/login.jhtml?style=mini&from=sm&full_redirect=false&redirectURL=https%3A%2F%2Fdetailskip.taobao.com%2F__x5__%2Fquery.htm%3Fstyle%3Dmiddle%26smApp%3Ddetailskip%26smPolicy%3Ddetailskip-service-anti_Spider-checklogin%26smCharset%3DGBK%26smTag%3DNjEuMTQ1LjI0Ni45LCw3NjFhNDI2M2E5NTM0MGIxYjk4ZjM4MmRiZjFjYTdjMA%253D%253D%26captcha%3Dhttps%253A%252F%252Fdetailskip.taobao.com%252F__x5__%252Fquery.htm%26smReturn%3Dhttps%253A%252F%252Fdetailskip.taobao.com%252Fservice%252FgetData%252F1%252Fp1%252Fitem%252Fdetail%252Fsib.htm%253FitemId%253D563569212451%2526modules%253DdynStock%252Cqrcode%252Cviewer%252Cprice%252Cduty%252CxmpPromotion%252Cdelivery%252Cupp%252Cactivity%252Cfqg%252Czjys%252CcouponActivity%252CsoldQuantity%252CoriginalPrice%252CtradeContract%2526callback%253DonSibRequestSuccess%26smSign%3DjMOmbxxrgMg6KdtVI51Qgw%253D%253D'
    HttpClient httpclient = new DefaultHttpClient();
    HttpGet get = new HttpGet(loginUrl)
    println get
    URL url = new URL(loginUrl);
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setDoOutput(true);
    OutputStreamWriter out = new OutputStreamWriter(connection
            .getOutputStream(), "GBK");
    //其中的memberName和password也是阅读html代码得知的，即为表单中对应的参数名称
    out.write("memberName=myMemberName&password=myPassword"); // post的关键所在！
// remember to clean up
    out.flush();
    out.close();

// 取得cookie，相当于记录了身份，供下次访问时使用
    String cookieVal = connection.getHeaderField("Set-Cookie");


//        HttpClient httpclient = new DefaultHttpClient();
//        String url = 'https://item.taobao.com/item.htm?spm=2013.1.20141001.3.44dc9059wfayUV&id=563569212451&scm=1007.12144.81309.42296_42296&pvid=2863fa09-9e22-4813-93e8-12512ae95ee2'
//        // "https://item.taobao.com/item.htm?spm=a219r.lm874.14.11.2969967cAiDhOw&id=563657798184&ns=1&abbucket=20";
//
//        String id = getStrByPrePost(url, "id=", "&");//根据url获取商品的id
//        println id
//        if (id != null) {
//            //组装出获取商品信息的url//&sellerId=745740386
//            // String detailUrl="http://mdskip.taobao.com/core/initItemDetail.htm?itemId="+id+"×tamp=1429065419751&tgTag=false&cartEnable=true&addressLevel=2&progressiveSupport=false&isUseInventoryCenter=false&household=false¬AllowOriginPrice=false&isRegionLevel=false&sellerUserTag=303632416&sellerUserTag2=18020085046181888&tmallBuySupport=true&isAreaSell=false&queryMemberRight=true&isIFC=false&sellerUserTag3=70368779862144&sellerUserTag4=8800522208643&service3C=true&tryBeforeBuy=false&isSecKill=false&isForbidBuyItem=false&sellerPreview=false&itemTags=843,1163,1478,1483,1547,1611,1803,1867,2049,2059,2443,2507,2635,3787,3974,4166,4555,4811,5323,21762,21826,25282,28802,29122,34178,56130,56194&isApparel=false&offlineShop=false&showShopProm=false&callback=setMdskip";
//            String detailUrl = 'https://detailskip.taobao.com/service/getData/1/p1/item/detail/sib.htm?itemId=' + id + '&modules=dynStock,qrcode,viewer,price,duty,xmpPromotion,delivery,upp,activity,fqg,zjys,couponActivity,soldQuantity,originalPrice,tradeContract&callback=onSibRequestSuccess'
//            HttpGet get = new HttpGet(detailUrl)
//            get.addHeader("Referer", url);//Referer必须要设置
//            HttpResponse response = httpclient.execute(get);
//            println response.class
//            String content = getContent(response);
//            //println content
////            String promotionPrice = getStrByPrePost(content, ";20509:28383;1627207:30155;", null);
////            promotionPrice = getStrByPrePost(promotionPrice, "\"price\":", "}");
////            System.out.println("商品价格:"+promotionPrice);
//            //将价格随机便签换成固定值
//            content?.toString()?.replaceAll(';[0-9]\\d*:[0-9]\\d*;[0-9]\\d*:[0-9]\\d*;') { ch ->
//                content = content.replace(ch?.toString(), "ABCCCCCCC")
//            }
//            //println content
//            def jsonSlurper = new JsonSlurper()
//            def object = jsonSlurper.parseText(content?.substring(content.lastIndexOf('(') + 1, content.lastIndexOf(')') - 1))
//            println("原始标价： " + object.data.price);
//            println("发货城市： " + object.data.deliveryFee.data.sendCity)
//            println("淘宝价： " + object.data.promotion.promoData.ABCCCCCCC.price)
//            println("剩余数量： " + object.data.soldQuantity.confirmGoodsCount)
//            println("淘宝链接： " + url)
//        }

    }

    /**
     * 获取返回内容的string
     * @param response
     * @return
     * @throws IllegalStateException
     * @throws IOException
     */
    private static String getContent(HttpResponse response) throws IllegalStateException, IOException {
        java.io.InputStream is = response.getEntity().getContent();
        BufferedReader br = new BufferedReader(new InputStreamReader(is, "GBK"));
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
