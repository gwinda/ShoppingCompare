package com.workspace.server.configuration

import groovy.json.JsonSlurper
import org.apache.http.HttpResponse
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.DefaultHttpClient

/**
 * Created by CHENMA11 on 1/24/2018.
 */
class CatchMess {
      CatchMess() {


          HttpClient httpclient = new DefaultHttpClient();
        String url = 'https://item.taobao.com/item.htm?spm=2013.1.20141001.3.44dc9059wfayUV&id=563569212451&scm=1007.12144.81309.42296_42296&pvid=2863fa09-9e22-4813-93e8-12512ae95ee2'

        String id = getStrByPrePost(url, "id=", "&");//根据url获取商品的id
        println id
        if (id != null) {
            //组装出获取商品信息的url//&sellerId=745740386
            // String detailUrl="http://mdskip.taobao.com/core/initItemDetail.htm?itemId="+id+"×tamp=1429065419751&tgTag=false&cartEnable=true&addressLevel=2&progressiveSupport=false&isUseInventoryCenter=false&household=false¬AllowOriginPrice=false&isRegionLevel=false&sellerUserTag=303632416&sellerUserTag2=18020085046181888&tmallBuySupport=true&isAreaSell=false&queryMemberRight=true&isIFC=false&sellerUserTag3=70368779862144&sellerUserTag4=8800522208643&service3C=true&tryBeforeBuy=false&isSecKill=false&isForbidBuyItem=false&sellerPreview=false&itemTags=843,1163,1478,1483,1547,1611,1803,1867,2049,2059,2443,2507,2635,3787,3974,4166,4555,4811,5323,21762,21826,25282,28802,29122,34178,56130,56194&isApparel=false&offlineShop=false&showShopProm=false&callback=setMdskip";
            String detailUrl = 'https://detailskip.taobao.com/service/getData/1/p1/item/detail/sib.htm?itemId=' + id + '&modules=dynStock,qrcode,viewer,price,duty,xmpPromotion,delivery,upp,activity,fqg,zjys,couponActivity,soldQuantity,originalPrice,tradeContract&callback=onSibRequestSuccess'
            HttpGet get = new HttpGet(detailUrl)
            get.addHeader("Referer", url);//Referer必须要设置
            HttpResponse response = httpclient.execute(get);
            String content = getContent(response);

                //将价格随机便签换成固定值
                content?.toString()?.replaceAll(';[0-9]\\d*:[0-9]\\d*;[0-9]\\d*:[0-9]\\d*;') { ch ->
                    content = content.replace(ch?.toString(), "ABCCCCCCC")
                }
            print content.contains('ABCCCCCCC')
            if(content.contains('ABCCCCCCC')){
                //println content
                def jsonSlurper = new JsonSlurper()
                def object = jsonSlurper.parseText(content?.substring(content.lastIndexOf('(') + 1, content.lastIndexOf(')') - 1))
                println("原始标价： " + object.data.price);
                println("发货城市： " + object.data.deliveryFee.data.sendCity)
                println("淘宝价： " + object.data.promotion.promoData.ABCCCCCCC.price[0])
                println("剩余数量： " + object.data.soldQuantity.confirmGoodsCount)
                println("淘宝链接： " + url)
            }else{
                println ('访问不成功')
            }

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




}