package com.workspace.server.configuration
/**
 * Created by CHENMA11 on 1/24/2018.
 */

public class Timmer {
//http://blog.csdn.net/sang1203/article/details/51286221
    public static void main(String[] args) {
        Date date = new Date();

        Timer t = new Timer("No1");  // Create a new timer No1  , Or you can directly create, don't name   Timer t = new Timer();

        TimerTask tt = new TimerTask() {

            @Override
            public void run() {
                // 连接地址（通过阅读html源代码获得，即为登陆表单提交的URL）
                String surl = "http://login.goodjobs.cn/index.php/action/UserLogin";

                /**
                 * 首先要和URL下的URLConnection对话。 URLConnection可以很容易的从URL得到。比如： // Using
                 * java.net.URL and //java.net.URLConnection
                 */
                URL url = new URL(surl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                /**
                 * 然后把连接设为输出模式。URLConnection通常作为输入来使用，比如下载一个Web页。
                 * 通过把URLConnection设为输出，你可以把数据向你个Web页传送。下面是如何做：
                 */
                connection.setDoOutput(true);
                /**
                 * 最后，为了得到OutputStream，简单起见，把它约束在Writer并且放入POST信息中，例如： ...
                 */
                OutputStreamWriter out = new OutputStreamWriter(connection
                        .getOutputStream(), "GBK");
                //其中的memberName和password也是阅读html代码得知的，即为表单中对应的参数名称
                out.write("memberName=myMemberName&password=myPassword"); // post的关键所在！
                // remember to clean up
                out.flush();
                out.close();

                // 取得cookie，相当于记录了身份，供下次访问时使用
                String cookieVal = connection.getHeaderField("Set-Cookie");
                CatchMess dd=new CatchMess()
                System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++");
            }

        };

        t.schedule(tt, date, 10000000);    // Starting from the current time, per  1 Second to repeatedly perform a scheduled task, I only used here is the current time  , Time can according to their needs.  .
//             t.schedule(tt,date);               // Scheduled to perform a task
//             t.cancel();                              // Terminate this timer, discard all currently scheduled tasks  .
//             t.purge();                              // From this timer job queue to remove all the task has been canceled
//             tt.cancel();                            // Cancel this timer tasks  .
    }
}
