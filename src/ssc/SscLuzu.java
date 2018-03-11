package ssc;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import net.sf.json.JSONObject;

public class SscLuzu {

	public static void main(String[] args) {
		SscLuzu ssc=new SscLuzu();
		ssc.getLuzuNum();
		
	}

public String getLuzuNum(){
	String url ="https://www.1398p.com/shishicai/betmode";
	Document doc;
	String reString="";
	try {
		doc = Jsoup.connect(url)
				.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.52 Safari/537.36")
				.header("Referer", "https://www.1398p.com/shishicai/shipin?utp=topbar")
                .followRedirects(true)
				.ignoreContentType(true)
				.get();
	Elements  element=doc.select("div.list_content");
	for (Element link : element) {
		String win=link.select("div.lc_top").select("span.sp").select("i[style=color:red;]").text();
		if(win.length()==7&&Integer.parseInt(String.valueOf(win.charAt(6)))>=3){
			String temp=link.text();
			temp=temp.replace("收藏", "");
			temp=temp.replaceAll("模式", "");
			temp=temp.replaceAll("第", "");
			temp=temp.replaceAll("球", "");
			temp=temp.replaceAll("下半场", "");
			temp=temp.replaceAll("上半场", "");
			temp=temp.replaceAll("中", "z");
			temp=temp.replaceAll("赢", "w");
			temp=temp.replaceAll("小", "s");
			temp=temp.replaceAll("大", "b");
			temp=temp.replaceAll("双", "dan");
			temp=temp.replaceAll("单", "shu");
			temp=temp.replaceAll("今日", "");
			temp=temp.replaceAll("一", "one");
			temp=temp.replaceAll("二", "two");
			temp=temp.replaceAll("三", "tre");
			temp=temp.replaceAll("四", "for");
			temp=temp.replaceAll("五", "fiv");
			temp=temp.replaceAll("顺", "sun");
			temp=temp.replaceAll("反", "fan");

			System.out.println(temp);
			reString=reString+temp+"\n";
		}
		}
	
	return reString;
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return "";
	
}

/**
 * 获取指定字符串出现的次数
 * 
 * @param srcText 源字符串
 * @param findText 要查找的字符串
 * @return
 */
public static int appearNumber(String srcText, String findText) {
    int count = 0;
    Pattern p = Pattern.compile(findText);
    Matcher m = p.matcher(srcText);
    while (m.find()) {
        count++;
    }
    return count;
}
  public String getWinNum() {
	  Document document;
		try {
			document = Jsoup.connect("https://www.1398p.com/shishicai/getawarddata?r=1520760034437")
					.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.52 Safari/537.36")
					.ignoreContentType(true)
					.get();
			String reString=document.body().text();
			JSONObject jsonObject=JSONObject.fromObject(reString);
			JSONObject jsonObject2=JSONObject.fromObject(jsonObject.get("current"));
			String res="["+jsonObject2.get("period")+"]-["+jsonObject2.get("result")+"]-"+jsonObject2.get("awardTime");
			System.out.println(res);
           return res;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
  }
}
