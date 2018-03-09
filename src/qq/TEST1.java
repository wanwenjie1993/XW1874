package qq;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class TEST1 {
public static void main(String[] args) {
	try {
		Document document=Jsoup.connect("http://s.web2.qq.com/api/getvfwebqq?ptwebqq=&clientid=53999199&psessionid=&t=1520520656491")
				.header("Cookie", "pgv_pvi=5617957888; RK=O7Z5VzgFUv; ptcz=f53eb90ddd1c407757f76b309f0492f0db54d395d58d57e49c105e7599ddad9d; pgv_si=s219868160; pt2gguin=o1148432236; ptisp=ctc; uin=o1148432236; skey=@KWlUz8uPk; p_uin=o1148432236; pt4_token=oL0Hb8gvekYC4tVFzHKG-QzwZ0dp0B-uhcWwBPJfOsg_; p_skey=69ExSY2AmNgds9DHIdlh3UOuYizbkYgLMPHhO5a3JcY_")
				.header("Referer", "http://s.web2.qq.com/proxy.html?v=20130916001&callback=1&id=1")
				.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.52 Safari/537.36")
				.ignoreContentType(true)
				.get();
		System.out.println(document);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}
