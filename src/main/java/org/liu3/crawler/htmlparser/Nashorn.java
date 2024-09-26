package org.liu3.crawler.htmlparser;

import jdk.nashorn.api.scripting.NashornScriptEngine;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import org.apache.commons.codec.binary.Base64;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.liu3.http.HttpClientUtil;
import sun.misc.BASE64Decoder;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: liutianshuo
 * @Date: 2021/2/23
 */
public class Nashorn {

    //static String base64decode

    public static void main(String[] args) throws Exception {

//        String url = "http://www.baidu.com";
//        url = "http://m.taduo.net/manhua/24/402.html";
//
//        String data = HttpClientUtil.get(url);
//
//        Document doc = Jsoup.parse(data);
//
//        Elements scripts = doc.getElementsByTag("script");
//
//        Element script = scripts.get(5);
//
//        String js = script.data();
//        System.out.println(js.trim());
//
//        Pattern pat = Pattern.compile("cp=\"(\\S+?)\"");
//        Matcher mat = pat.matcher(js);
//
//        if(!mat.find()){
//            return;
//        }
//
//        String base64String = mat.group(1);
//        String eval = new String(Base64.decodeBase64(base64String));
//
//
//        ScriptEngineManager manager = new ScriptEngineManager();
//        NashornScriptEngine engine = (NashornScriptEngine) manager.getEngineByName("nashorn");
//
//        eval = eval.substring(4);
//        Object o1 = engine.eval(eval);
//        engine.eval(o1.toString());
//        ScriptObjectMirror packedarr = (ScriptObjectMirror) engine.get("packedarr");
//
//        System.out.println(packedarr.values());
//        packedarr.forEach((s, o) -> {
//            System.out.println(s+"="+o);
//        });

        eval();
    }

    public static void eval() throws ScriptException {

        ScriptEngineManager manager = new ScriptEngineManager();
        NashornScriptEngine engine = (NashornScriptEngine) manager.getEngineByName("nashorn");

        String data = "function aa(){return '123';}; aa();";

        Object o1 = engine.eval(data);
        System.out.println(o1.getClass());
        ScriptObjectMirror packedarr = (ScriptObjectMirror) engine.get("packedarr");

        System.out.println(packedarr.values());
        packedarr.forEach((s, o) -> {
            System.out.println(s+"="+o);
        });
    }

}
