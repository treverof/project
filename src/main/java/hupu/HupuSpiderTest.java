package hupu;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.text.html.parser.Entity;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017/12/4.
 */
public class HupuSpiderTest {
    private static List<String> arrayList = new ArrayList<String>();
    private static List<String> result = new ArrayList<String>();
    private static List<String> urls = new ArrayList<String>();
    public static void main(String[] args) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String sxa = "https://bbs.hupu.com/selfie";
        HttpGet httpGet = null;
        for(int i=0;i<=10;i++) {
            if(i == 0)
                continue;
            else if(i == 1)
                sxa = sxa + "-1";
            else
                sxa = sxa.substring(0,sxa.length()-2)+"-"+String.valueOf(i);
            try {
                httpGet = new HttpGet(sxa);
                CloseableHttpResponse response = httpClient.execute(httpGet);
                HttpEntity entity = response.getEntity();
                String s = EntityUtils.toString(entity);
                Document doc = Jsoup.parse(s);
                Elements elements = doc.getElementsByClass("for-list").select("li");
                for (Element e : elements) {
                    String sx = e.select("a").attr("href");
                    System.out.println(e.select("a").attr("href"));
                    arrayList.add(sx.replace(sx, "https://bbs.hupu.com/" + sx));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        HttpGet get = null;
        HttpEntity entity = null;
        Document doc = null;
        for(String str:arrayList){
            get = new HttpGet(str);
            get.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
            get.setHeader("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
            get.setHeader("Accept-Encoding", "gzip, deflate, br");
            get.setHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
            get.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");
            get.setHeader("Cookie","_dacevid3=c0e123b2.c102.4744.6782.2bfcb187a0cf; __gads=ID=898b2d3c22fd97c5:T=1509428682:S=ALNI_MbxID9GCFxcsVQNwmLl9J9Lu-IY1A; PHPSESSID=cb2c9c54f36a016ac5cd5db144ce7bc6; UM_distinctid=16020344378139-0aaf7eca9ecf63-5b452a1d-e1000-1602034437940c; Hm_lvt_3d37bd93521c56eba4cc11e2353632e2=1512368784; Hm_lpvt_3d37bd93521c56eba4cc11e2353632e2=1512368784; Hm_lvt_39fc58a7ab8a311f2f6ca4dc1222a96e=1512365086; Hm_lpvt_39fc58a7ab8a311f2f6ca4dc1222a96e=1512369041; _cnzz_CV30020080=buzi_cookie%7Cc0e123b2.c102.4744.6782.2bfcb187a0cf%7C-1; _fmdata=BD4F7FD1FDCA99F6CC8B59BA377780600EC15B8C9B8E1B8937B807E1CE09118BE684E3BAC99A6A8D62EEEC549A03BBCDFB74BEBFF7695DA3; __dacevst=6f4430f5.35bce4aa|1512371012552");
            try {
                response = closeableHttpClient.execute(get);
                entity = response.getEntity();
                doc = Jsoup.parse(EntityUtils.toString(entity));
                Elements elements = doc.getElementsByClass("quote-content").select("p");
                for(Element e:elements){
                    if(null != e.getElementsByAttribute("src")) {
                        System.out.println(e.getElementsByAttribute("src"));
                        result.add(e.getElementsByAttribute("src").toString());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //获取最终的图片链接
        for(String s:result){
            String[] tmp = s.split(" ");
            if(tmp.length == 4) {
                int index = tmp[1].lastIndexOf("\"");
                urls.add(tmp[1].substring(5,index));
            }else if(tmp.length == 5){
                int index = tmp[2].lastIndexOf("\"");
                urls.add(tmp[2].substring(15,index));
            }
        }

        for(String url:urls){
            System.out.println(url);
        }

        //下载图片到本地
        /*OutputStream out = null;
        InputStream in  = null;
        for(String s:urls){
            try {
                URL url = new URL(s);
                URLConnection urlConnection = url.openConnection();
                in = urlConnection.getInputStream();
                byte[] bs = new byte[1024];
                        // 读取到的数据长度
                        int len;
                        // 输出的文件流
                       File sf=new File("D:\\test");
                        out = new FileOutputStream(sf);
                        // 开始读取
                       while ((len = in.read(bs)) != -1) {
                              out.write(bs, 0, len);
                            }
                       // 完毕，关闭所有链接
                        out.close();
                        in.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(null != in){
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(null != out) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }*/
    }
}
