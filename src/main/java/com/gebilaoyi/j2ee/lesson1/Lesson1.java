package com.gebilaoyi.j2ee.lesson1;

import java.io.*;
import java.net.*;

/**
 * 第一课：了解网络与http协议
 */
public class Lesson1 {
    public static void main(String args[]) {
        Socket s = new Socket();
        try {
            s.connect(new InetSocketAddress("www.baidu.com", 80));
            OutputStream os = s.getOutputStream();
            BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(os));
            InputStreamReader isr = new InputStreamReader(s.getInputStream(), "UTF-8") ;
            BufferedReader br = new BufferedReader(isr) ;
            wr.write("GET /index.php HTTP/1.1\r\n");
            wr.write("Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8\r\n");
            wr.write("Accept-Encoding:gzip,deflate,sdch\r\n");
            wr.write("Accept-Language:zh-CN,zh;q=0.8\r\n");
            wr.write("Connection: close\r\n");
            wr.write("User-agent: Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36 SE 2.X MetaSr 1.0\r\n");
            wr.write("Host: www.baidu.com\r\n");
            wr.write("\r\n");

            wr.flush();

            String str = null ;
            while ((str = br.readLine()) != null) {
                System.out.println(str);
            }
            isr.close();
            os.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
