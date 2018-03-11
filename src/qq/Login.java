package qq;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import ssc.SscLuzu;

public class Login {
	static CookieBean  cb=new CookieBean();
	static SscLuzu ssc=new SscLuzu();

	public static void main(String[] args) {
		
		cb.setPgv_pvi("8144176128");
		cb.setPgv_si("s4415319040");
		Map<String, String> cookiesMap = new HashMap<String, String>();
		cookiesMap.put("pgv_pvi",cb.getPgv_pvi());
		cookiesMap.put("pgv_si",cb.getPgv_si());
		cookiesMap.putAll(getCk());
		String qrsig = makeQrCode().get("qrsig").toString();
		//显示二维码
		new QrCodePic();
		String url="";
		cookiesMap.put("qrsig", qrsig);
		boolean isLogionOn=false;
		while (true) {
			url=cheackQrcode(cookiesMap, qrsig);
			if(!"".equals(url)) {
				String ck=getPtwebqq(cookiesMap,url);
				getVfwebqq();
				getLogin2(ck);
				isLogionOn=true;
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
		
//		心跳
		String lastWinStr="";
        String lastLz="";
        String MsgId="";
        String from_uin="";
        String content="";
		  while (isLogionOn) {
			  String str=keepHeart();
			  String winStr=ssc.getWinNum();
	          String luzu=ssc.getLuzuNum();
	         
	          if(!"".equals(str)&&"".equals(MsgId)){
        		MsgId=str.split(",")[1];
        		from_uin=str.split(",")[0];
        		content=str.split(",")[3];
        		}

	          if(!lastWinStr.equals(winStr)&&!"".equals(MsgId)&&!"".equals(from_uin)&&"ssc".equals(content)) {
	          //发送消息
			  sendMsg(from_uin,MsgId, winStr, "message");
			  lastWinStr=winStr;
	          }
	          if(!lastLz.equals(luzu)&&!"".equals(MsgId)&&!"".equals(from_uin)&&"ssc".equals(content)) {
			  sendMsg(from_uin,MsgId, luzu, "message");
			  lastLz=luzu;
	          }
			  try {
					Thread.sleep(30000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		} 
	}
	public static Map<String, String> getCk() {
        System.out.println("获取cookies...");
        String url="https://xui.ptlogin2.qq.com/cgi-bin/xlogin?daid=164&target=self&style=40&pt_disable_pwd=1&mibao_css=m_webqq&appid=501004106&enable_qlogin=0&no_verifyimg=1&s_url=http%3A%2F%2Fweb2.qq.com%2Fproxy.html&f_url=loginerroralert&strong_login=1&login_state=10&t=20131024001";
		Connection conn = Jsoup.connect(url);
		conn.header("user-agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.52 Safari/537.36");
		conn.method(Method.GET);
		conn.followRedirects(true);
		conn.ignoreContentType(true);
		Response response;
		try {
			response = conn.execute();
			Map<String, String> cookie=response.cookies();
			System.out.println(cookie);
           return cookie;
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
		conn.followRedirects(true);
		conn.ignoreContentType(true);
		Response response;
		try {
			response = conn.execute();
			 
			Map<String, String> cookie=response.cookies();
			String html=response.body();
			if(String.valueOf(html.charAt(8)).equals("0")) {
				Map<String, List<String>> map= response.multiHeaders();
		        List<String> ss =map.get("Set-Cookie");
				String string=ss.toString();
				cb.setPtcz(string.substring(string.indexOf(";, ptcz=")+8, string.indexOf(";, ptcz=")+72));
				cb.setPtisp(cookie.get("ptisp").toString());
				cb.setRK(cookie.get("RK").toString());
				html=html.split("','")[2];
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
	
	public static String getPtwebqq(Map<String, String> cMap,String url) {
		System.out.println("正在登录...");
		System.out.println(url);
	    try {
	    	Response doc = Jsoup.connect(url).method(Method.GET).
	    			timeout(120000).followRedirects(false).execute();
           Map<String, String> cookies=doc.cookies();
           cb.setUin(cookies.get("uin").toString());
           cb.setSkey(cookies.get("skey").toString());
           cb.setPt2gguin(cookies.get("pt2gguin").toString());
           
           Map<String, List<String>> map= doc.multiHeaders();
           List<String> ss =map.get("Set-Cookie");
           String aa=ss.toString().replaceAll("Path=/;Domain=qq.com;,", "");
           aa=aa.replaceAll("Path=/;Domain=web2.qq.com;,", "");
           aa=aa.substring(1, aa.length()-1);
           cb.setCookies(aa);
           String s1=aa.substring(94, 221);
           s1="{"+s1+"}";
           JSONObject jsonObject=JSONObject.fromObject(s1);
           cb.setP_uin(jsonObject.get("p_uin").toString());
           cb.setPt4_token(jsonObject.get("pt4_token").toString());
           cb.setP_skey(jsonObject.get("p_skey").toString());
           String ckString="pgv_pvi="+cb.getPgv_pvi()+"; pt2gguin="+cb.getPt2gguin()+"; RK="+cb.getRK()+"; ptcz="+cb.getPtcz()+"; pgv_si="+cb.getPgv_si()+"; uin="+cb.getUin()+"; skey="+cb.getSkey()+"; ptisp="+cb.getPtisp()+"; p_uin=o1148432236; pt4_token="+cb.getPt4_token()+"; p_skey="+cb.getP_skey()+"";
           System.out.println(ckString);
           return ckString;
	    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}
	
	public static String getVfwebqq() {
		try {
			System.out.println("获取Vfwebqq...");
			Document document=Jsoup.connect("http://s.web2.qq.com/api/getvfwebqq?ptwebqq=&clientid=53999199&psessionid=&t=1520520656491")
					//.header("Cookie", "pgv_pvi="+cb.getPgv_pvi()+"; RK="+cb.getRK()+"; ptcz=f53eb90ddd1c407757f76b309f0492f0db54d395d58d57e49c105e7599ddad9d; pgv_si="+cb.getPgv_si()+"; pt2gguin="+cb.getPt2gguin()+"; ptisp="+cb.getPtisp()+"; uin="+cb.getUin()+"; skey="+cb.getSkey()+"; p_uin=o1148432236; pt4_token=oL0Hb8gvekYC4tVFzHKG-QzwZ0dp0B-uhcWwBPJfOsg_; p_skey=a")
					.header("Cookie", cb.getCookies())
					.header("Referer", "http://s.web2.qq.com/proxy.html?v=20130916001&callback=1&id=1")
					.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.52 Safari/537.36")
					.ignoreContentType(true)
					.timeout(120000)
					.get();
			String json=document.body().text();
			JSONObject jsonObject=JSONObject.fromObject(json);
			JSONObject jsonObject2=JSONObject.fromObject(jsonObject.get("result"));
                  
			return jsonObject2.get("vfwebqq").toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	//第二次登录
	public static void getLogin2(String ck) {
		try {
			System.out.println("第二次登录...");
			String jsBody = "{\"ptwebqq\":\"\""
					+",\"clientid\":53999199"
					+ ",\"psessionid\":\"\""
					+ ",\"status\":\"online\"}";
			System.out.println("等待消息...");
			jsBody = "r=" + URLEncoder.encode(jsBody);
			Document document=Jsoup.connect("http://d1.web2.qq.com/channel/login2")
					.header("Cookie", ck)
					.header("Referer", "http://d1.web2.qq.com/proxy.html?v=20151105001&callback=1&id=2")
					.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.52 Safari/537.36")
					.ignoreContentType(true)
					.requestBody(jsBody)
					.timeout(60000)
					.data("aaa", "ccc")
					.post();
			String reString=document.body().text();
			JSONObject jsonObject=JSONObject.fromObject(reString);
			JSONObject js2=JSONObject.fromObject(jsonObject.get("result").toString());
			cb.setPsessionid(js2.get("psessionid").toString());
			cb.setCookies(ck);
			System.out.println(document.body().text());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String keepHeart() {

		String url = "http://d1.web2.qq.com/channel/poll2";
		try {
			Map<String, String> headMap = new HashMap<String, String>();
			headMap.put("Accept", "*/*");
			headMap.put("Accept-Encoding", "gzip, deflate");
			headMap.put("Accept-Language", "zh-CN,zh;q=0.9");
			headMap.put("Connection", "keep-alive");
			headMap.put("Content-Length", "331");
			headMap.put("Cookie",cb.getCookies());
			headMap.put("Content-Type", "application/x-www-form-urlencoded");
			headMap.put("Host", "d1.web2.qq.com");
			headMap.put("Origin", "http://d1.web2.qq.com");		
			headMap.put("Referer", "http://d1.web2.qq.com/proxy.html?v=20151105001&callback=1&id=2");
			headMap.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.75 Safari/537.36");
			String jsBody = "{\"ptwebqq\":\"\""
					+",\"clientid\":\"53999199\""
					+ ",\"psessionid\":\""+cb.getPsessionid()+"\""
					+ ",\"key\":\"\""
					+ "}";
			System.out.println("等待消息...");
			jsBody = "r:" + URLEncoder.encode(jsBody);
			Connection connection = Jsoup.connect(url);
			connection.data("aaa", "ccc");
			//connection.cookies(cMap);
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
	public static void sendMsg(String send_uin,String MsgId,String msg,String msgType) {
		String url1 = "http://d1.web2.qq.com/channel/send_buddy_msg2";
		String url2 = "http://d1.web2.qq.com/channel/send_qun_msg2";
		System.out.println("cok:"+cb.getCookies());
		try {
			Map<String, String> headMap = new HashMap<String, String>();
			headMap.put("Accept", "*/*");
			headMap.put("Accept-Encoding", "gzip, deflate");
			headMap.put("Accept-Language", "zh-CN,zh;q=0.9");
			headMap.put("Cache-Control", "no-cache");
			headMap.put("Connection", "keep-alive");
			headMap.put("Content-Length", "585");
			headMap.put("Content-Type", "application/x-www-form-urlencoded");
			headMap.put("Cookie",cb.getCookies());
			headMap.put("Host", "d1.web2.qq.com");
			headMap.put("Origin", "http://d1.web2.qq.com");
			headMap.put("Referer",
					"http://d1.web2.qq.com/cfproxy.html?v=20151105001&callback=1");
			headMap.put(
					"User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.75 Safari/537.36");
          
			String content = msg.replaceAll("\"", "\\\"");
			
			String ClientID ="53999199";
			String PSessionID =cb.getPsessionid();
			System.out.println("PSessionID"+PSessionID);
			String s1="";
			String url="";
			if(msgType.equals("group_message")){
				 s1="group_uin";
				 url=url2;
			}else if(msgType.equals("message")){ 
				 s1="to";
				 url=url1;
			}
			String jsBody="{\""+s1+"\":"+send_uin+",\"content\":\"[\\\""+content+"\\\",[\\\"font\\\",{\\\"name\\\":\\\"宋体\\\",\\\"size\\\":10,\\\"style\\\":[0,0,0],\\\"color\\\":\\\"000000\\\"}]]\",\"face\":480,\"clientid\":"+ClientID+",\"msg_id\":"+MsgId+",\"psessionid\":\""+PSessionID+"\"}";
			jsBody = "r=" + URLEncoder.encode(jsBody);
			System.out.println("回复:"+content);
			Connection connection = Jsoup.connect(url);
			connection.data("aaa", "ccc");
			connection.headers(headMap);
			connection.requestBody(jsBody);
			Document doc = connection.post();
			String resString=doc.body().text();
			if("{\"retcode\":0}".equals(resString)){

				System.out.println("发送消息成功...");
			}else{
				System.out.println(resString);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
