package qq;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Login {
	public static void main(String[] args) {
		Map<String, String> cookiesMap = new HashMap<String, String>();
		cookiesMap.put("pgv_pvi", "5617957888");
		cookiesMap.put("pgv_si", "s1788083200");
		cookiesMap.putAll(getCk());
		cookiesMap.putAll(getCk());
		String qrsig = makeQrCode().get("qrsig").toString();
		String url="";
		cookiesMap.put("qrsig", qrsig);
		boolean isLogionOn=false;
		while (true) {
			url=cheackQrcode(cookiesMap, qrsig);
			if(!"".equals(url)) {
				cookiesMap.putAll(getPtwebqq(cookiesMap,url));
				isLogionOn=true;
				 getPtwebqq(cookiesMap);

				break;
			}
			new Thread();
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		/* while (isLogionOn) {
			//keepHeart(cookiesMap);
		}*/ 
	}
	public static Map<String, String> getCk() {
        System.out.println("获取cookies...");
        String url="https://xui.ptlogin2.qq.com/cgi-bin/xlogin?daid=164&target=self&style=40&pt_disable_pwd=1&mibao_css=m_webqq&appid=501004106&enable_qlogin=0&no_verifyimg=1&s_url=http%3A%2F%2Fweb2.qq.com%2Fproxy.html&f_url=loginerroralert&strong_login=1&login_state=10&t=20131024001";
		Connection conn = Jsoup.connect(
		url);
		conn.header("user-agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.52 Safari/537.36");
		conn.method(Method.GET);
		conn.followRedirects(false);
		conn.ignoreContentType(true);
		Response response;
		try {
			response = conn.execute();
			System.out.println(response.cookies());
           return response.cookies();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
	public static Map<String, String> makeQrCode() {
        System.out.println("生成二维码...");
		Connection conn = Jsoup.connect(
		"https://ssl.ptlogin2.qq.com/ptqrshow?appid=501004106&e=2&l=M&s=3&d=72&v=4&t=0.09160128565347292&daid=164&pt_3rd_aid=0");
		conn.header("user-agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.52 Safari/537.36");
		conn.method(Method.GET);
		conn.followRedirects(false);
		Response response;
		FileOutputStream fos;
		try {
			response = conn.execute();
			InputStream inputStream = response.bodyStream();
			fos = new FileOutputStream("D:\\jj\\test.jpg");
			byte[] data = new byte[1024];
			int len = 0;
			while ((len = inputStream.read(data)) != -1) {
				fos.write(data, 0, len);
			}
			inputStream.close();
			fos.close();
			System.out.println(response.cookies());
           return response.cookies();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
	
	public static String cheackQrcode(Map<String, String> cMap,String t) {
		System.out.println("验证二维码...");
		Connection conn = Jsoup.connect(
		"https://ssl.ptlogin2.qq.com/ptqrlogin?u1=http%3A%2F%2Fweb2.qq.com%2Fproxy.html&ptqrtoken="+getTok(t)+"&ptredirect=0&h=1&t=1&g=1&from_ui=1&ptlang=2052&action=0-0-1520430187592&js_ver=10233&js_type=1&login_sig=XXBW58BPS1jf6-kRBAn5Ye1D47ZQlr0iljf0HJh3A7-Bgu*w2Q7nhoP1jF*r*Lox&pt_uistyle=40&aid=501004106&daid=164&mibao_css=m_webqq&");
		conn.header("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.52 Safari/537.36");
		conn.cookies(cMap);
		conn.header("referer", "https://xui.ptlogin2.qq.com/cgi-bin/xlogin?daid=164&target=self&style=40&pt_disable_pwd=1&mibao_css=m_webqq&appid=501004106&enable_qlogin=0&no_verifyimg=1&s_url=http%3A%2F%2Fweb2.qq.com%2Fproxy.html&f_url=loginerroralert&strong_login=1&login_state=10&t=20131024001");
        conn.method(Method.GET);
		conn.followRedirects(false);
		conn.ignoreContentType(true);
		Response response;
		try {
			response = conn.execute();
			String html=response.body();
			System.out.println(html);
			if(String.valueOf(html.charAt(8)).equals("0")) {
				html=html.split("','")[2];
				System.out.println(html);
				return html;
			}			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";

	}
	public static long getTok(String t) {
		long e=0,n=0;
		int i=0;
        for ( n = t.length(); n > i;++i) {
        e += (e << 5)+charCodeAt(t, i);
        }
        return 2147483647&e;
        }
	public static int charCodeAt(String string, int index) {
	    return (int) string.charAt(index);
	}
	
	public static Map<String, String> getPtwebqq(Map<String, String> cMap,String url) {
		System.out.println("正在登录...");
	    try {
	    	Response doc = Jsoup.connect(url).method(Method.GET).
	    			timeout(120000).followRedirects(true).execute();
			System.out.println(doc.body());
System.out.println(doc.cookies());
	    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}
	public static Map<String, String> getPtwebqq(Map<String, String> cMap) {
		System.out.println("正在登录2...");
		Connection conn = Jsoup.connect("http://s.web2.qq.com/api/get_self_info2?t=1520518114512");
		conn.header("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.52 Safari/537.36");
		conn.header("Content-Type", "utf-8");
		conn.method(Method.GET);
        conn.cookies(cMap);
		conn.followRedirects(false);
		conn.ignoreContentType(true);
		Response response;
		try {
			response = conn.execute();
			System.out.println(response.cookies());
			System.out.println(response.body());
			return response.cookies();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
	public static String keepHeart(Map<String, String> cMap) {

		String url = "http://d1.web2.qq.com/channel/poll2";
		try {
			Map<String, String> headMap = new HashMap<String, String>();
			headMap.put("Accept", "*/*");
			headMap.put("Accept-Encoding", "gzip, deflate");
			headMap.put("Accept-Language", "zh-CN,zh;q=0.9");
			headMap.put("Connection", "keep-alive");
			headMap.put("Content-Length", "331");
			headMap.put("Content-Type", "application/x-www-form-urlencoded");
			headMap.put("Host", "d1.web2.qq.com");
			headMap.put("Origin", "http://d1.web2.qq.com");		
			headMap.put("Referer", "http://d1.web2.qq.com/proxy.html?v=20151105001&callback=1&id=2");
			headMap.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.75 Safari/537.36");
			String MsgId = "123";
			String PSessionID = "123";
			String jsBody = "{\"ptwebqq\":\"\""
					+",\"clientid\":\"" + MsgId + "\""
					+ ",\"psessionid\":\""+ PSessionID + "\""
					+ ",\"key\":\"\""
					+ "}";
			System.out.println("等待消息...");
			jsBody = "r:" + URLEncoder.encode(jsBody);
			Connection connection = Jsoup.connect(url);
			connection.data("aaa", "ccc");
			connection.cookies(cMap);
			connection.headers(headMap);
			connection.ignoreContentType(true);
			connection.requestBody(jsBody);
			connection.timeout(60000);
			Document doc = connection.post();
			String json=doc.body().text();
			System.out.println(doc);
			return  fomatJson(doc.body().text());
		}catch (SocketTimeoutException e1) {
			System.out.println("连接超时!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
		
	}
	
	public static String fomatJson(String string) {
	       try {
	    	   System.out.println(string);
			JSONObject jsonbObject = JSONObject.fromObject(string);
			JSONArray JsonArray = JSONArray.fromObject(jsonbObject.get("result"));

			JSONObject js1 = JSONObject.fromObject(JsonArray.get(0));

			JSONArray js2 = JSONArray.fromObject(js1.get("value"));

			JSONObject js3 = JSONObject.fromObject(js2.get(0));

			JSONArray js4 = JSONArray.fromObject(js3.get("content"));

			JSONArray js5 = JSONArray.fromObject(js3.get("from_uin"));

			JSONArray js6 = JSONArray.fromObject(js3.get("msg_id"));

			JSONArray js7 = JSONArray.fromObject(js3.get("msg_type"));
			JSONArray js8 = JSONArray.fromObject(js3.get("send_uin"));
			String from_uin = js5.get(0) + "";

			String msg_id = js6.get(0) + "";
			String msg_type = js7.get(0) + "";
			String send_uin = js8.get(0) + "";
			String content = js4.get(1) + "";
			System.out.println(from_uin+":"+content);
			return from_uin + "," + msg_id + "," + msg_type + "," + content+","+js1.get("poll_type")+","+send_uin;
	       } catch (Exception e) {
	   		return "";
	   	}
		}
}
