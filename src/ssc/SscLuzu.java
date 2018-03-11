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
			temp=temp.replace("�ղ�", "");
			temp=temp.replaceAll("ģʽ", "");
			temp=temp.replaceAll("��", "");
			temp=temp.replaceAll("��", "");
			temp=temp.replaceAll("�°볡", "");
			temp=temp.replaceAll("�ϰ볡", "");
			temp=temp.replaceAll("��", "z");
			temp=temp.replaceAll("Ӯ", "w");
			temp=temp.replaceAll("С", "s");
			temp=temp.replaceAll("��", "b");
			temp=temp.replaceAll("˫", "dan");
			temp=temp.replaceAll("��", "shu");
			temp=temp.replaceAll("����", "");
			temp=temp.replaceAll("һ", "one");
			temp=temp.replaceAll("��", "two");
			temp=temp.replaceAll("��", "tre");
			temp=temp.replaceAll("��", "for");
			temp=temp.replaceAll("��", "fiv");
			temp=temp.replaceAll("˳", "sun");
			temp=temp.replaceAll("��", "fan");

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
 * ��ȡָ���ַ������ֵĴ���
 * 
 * @param srcText Դ�ַ���
 * @param findText Ҫ���ҵ��ַ���
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
