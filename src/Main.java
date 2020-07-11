import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.management.MemoryUsage;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main implements NativeKeyListener {


  public static   int[] i = {2, 3};

    public Main() throws NativeHookException {
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);

        GlobalScreen.registerNativeHook();
        GlobalScreen.addNativeKeyListener(this);


// Don't forget to disable the parent handlers.
        logger.setUseParentHandlers(false);
    }

    public static void main(String[] args) throws Exception {
     HashMap<String, ArrayList> a = new HashMap<>();
     new Main();
       System.out.println((String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor) );

    }


    public static void compute(String input) throws Exception {


        String rawl = "https://lmgtfy.com/?q=";
        String[] comb = input.split(" ");
        for (String s : comb) {
            rawl += s;
            rawl += "+";
        }
        rawl = rawl.substring(0, rawl.length() - 1);
        rawl += "&iie=1";

        rawl = rawl.replaceAll("ä", "ae");
        rawl = rawl.replaceAll("ö", "oe");
        rawl = rawl.replaceAll("ü", "ue");
        rawl = rawl.replaceAll("ä".toUpperCase(), "Ae");
        rawl = rawl.replaceAll("ö".toUpperCase(), "Oe");
        rawl = rawl.replaceAll("ü".toUpperCase(), "Ue");

        HttpURLConnection conn = getrawHttpUrl(new URL("https://link.dr4gonfighter.de/index.php"));
        Map<String, Object> params = new LinkedHashMap<>();

        params.put("link", rawl);
        sendData(conn, params);
        String data = getResp(conn);
     //   System.out.println(data);
       // System.out.println(rawl);
        String url = "http://r.dr4gonfighter.de/" + data.split("http://r.dr4gonfighter.de/")[1].split("'")[0];

        StringSelection stringSelection = new StringSelection(url);
        Clipboard cp = Toolkit.getDefaultToolkit().getSystemClipboard();
        cp.setContents(stringSelection, null);


    }

    private static String getResp(HttpURLConnection conn) throws IOException {

        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        StringBuilder resp = new StringBuilder();
        for (int c; (c = in.read()) >= 0; )
            resp.append((char) c);

        return resp.toString();
    }

    private static void sendData(HttpURLConnection conn, Map<String, Object> params) throws IOException {

        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String, Object> param : params.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }

        byte[] postDataBytes = postData.toString().getBytes("UTF-8");
        conn.getOutputStream().write(postDataBytes);
    }

    private static HttpURLConnection getrawHttpUrl(URL url) throws IOException {

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Host", "support.smg-gun.de");
        conn.setRequestProperty("Connection", "keep-alive");
        conn.setRequestProperty("User-gent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.97 Safari/537.36");
        conn.setRequestProperty("Accepte", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        conn.setRequestProperty("Accept-Language", "keep-alive");
        conn.setRequestProperty("keep-alive", "timeout=1000");
        conn.setRequestProperty("Connection", "en-US,en;q=0.9,de-DE;q=0.8,de;q=0.7,ro;q=0.6");
        conn.setDoOutput(true);
        return conn;
    }


    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {

    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {

        if (nativeKeyEvent.getKeyCode() == 27) {
            System.out.println("dd");
            try {
                compute((String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor));
            } catch (UnsupportedFlavorException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ;
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {

    }
}
