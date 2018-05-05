package spider.douban;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by lenovo on 2018/5/6.
 */
public class DoubanSpider {
    public static void main(String[] args) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String url = "https://book.douban.com/tag/历史";
        HttpGet httpGet = null;
        try {
            httpGet = new HttpGet(url);
            CloseableHttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            String s = EntityUtils.toString(entity);
            Document doc = Jsoup.parse(s);
            Element element = doc.getElementById("subject_list");
            Elements elements = element.select("ul").select("li");
            for(Element e:elements){
                System.out.print("url:"+e.child(1).child(0).select("a").attr("href"));
                System.out.print(e.child(1).child(0).text()+":");
                System.out.println(e.child(1).child(1).text());
                System.out.println(e.child(1).child(2).child(1).text());
                System.out.println(e.child(1).child(3).select("p").text());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
