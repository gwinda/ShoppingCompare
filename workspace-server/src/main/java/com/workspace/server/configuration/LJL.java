package com.workspace.server.configuration;

import org.python.core.PyFunction;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;

import java.io.IOException;
import java.util.Properties;

public class LJL {
    LJL(){
        Properties props = new Properties();
        props.put("python.console.encoding", "UTF-8"); // Used to prevent: console: Failed to install '': java.nio.charset.UnsupportedCharsetException: cp0.
        props.put("python.security.respectJavaAccessibility", "false"); //don't respect java accessibility, so that we can access protected members on subclasses
        props.put("python.import.site","false");
        Properties preprops = System.getProperties();
        PythonInterpreter.initialize(preprops, props, new String[0]);
        PythonInterpreter interpreter = new PythonInterpreter();
        String url = "https://item.jd.com/4805997.html";
        interpreter.exec("import sys");
        interpreter.exec("sys.path.append('C:/Users/CHENMA11/AppData/Local/Programs/Python/Python36-32/Lib')");
       // PythonInterpreter interpreter = new PythonInterpreter();
       // interpreter.execfile("C:/Users/CHENMA11/Desktop/PO_XML_SCHEMA/answer2.py");
        interpreter.execfile("C:/Users/CHENMA11/Desktop/PO_XML_SCHEMA/jdPrice.py");
        PyFunction func = (PyFunction)interpreter.get("jd_price",PyFunction.class);

        int a = 2010, b = 2 ;

        PyObject pyobj = func.__call__(new PyString(url));

        System.out.println("anwser = " + pyobj.toString());

    }
//    public static void main(String[] args) {
//        // TODO Auto-generated method stub
//        try {
//            Process proc=Runtime.getRuntime().exec("cmd /c C:/Users/CHENMA11/Desktop/PO_XML_SCHEMA/crawler1.py"); //执行py文件
//            InputStreamReader stdin=new InputStreamReader(proc.getInputStream());
//            LineNumberReader input=new LineNumberReader(stdin);
//            String line;
//            while((line=input.readLine())!=null ){
//                System.out.println(line);//得到输出
//            }
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
    public static void main(String[] args) throws IOException, InterruptedException{
//        try{
//            System.out.println("start");
//            Process pr = Runtime.getRuntime().exec("cmd C:\\Users\\CHENMA11\\Desktop\\PO_XML_SCHEMA\\answer.py");
//            System.out.println(pr.getOutputStream().equals(null));
//            BufferedReader in = new BufferedReader(new
//                    InputStreamReader(pr.getInputStream()));
//            System.out.println(in.readLine());
//            String line;
//            while ((line = in.readLine()) != null) {
//                System.out.println(line);
//            }
//            in.close();
//            pr.waitFor();
//            System.out.println("end");
//        } catch (Exception e){
//            e.printStackTrace();
//        }
        Process proc = null;
        Properties props = new Properties();
        props.put("python.console.encoding", "UTF-8"); // Used to prevent: console: Failed to install '': java.nio.charset.UnsupportedCharsetException: cp0.
        props.put("python.security.respectJavaAccessibility", "false"); //don't respect java accessibility, so that we can access protected members on subclasses
        props.put("python.import.site","false");
        Properties preprops = System.getProperties();
        PythonInterpreter.initialize(preprops, props, new String[0]);
        PythonInterpreter interpreter = new PythonInterpreter();

        interpreter.exec("import sys");
       // interpreter.exec("sys.path.append('C:/Users/CHENMA11/AppData/Local/Programs/Python/Python36-32/Lib')");//jython自己的

      //  interpreter.exec("sys.path.append('D:/jython2.7.0/Lib')");//jython自己的

        //interpreter.exec("sys.path.append('C:/Users/CHENMA11/AppData/Local/Programs/Python/Python36-32/Lib')");//jython自己的
      //  interpreter.exec("sys.path.append('D:/jython2.7.0/Lib/site-packages')");//jython自己的
       //interpreter.exec("sys.path.append('C:/Users/CHENMA11/AppData/Local/Programs/Python/Python36-32/Lib/site-packages')");//jython自己的
        //interpreter.exec("sys.path.append('D:/jython2.7.0/Lib/site-packages')");//jython自己的
     //   interpreter.execfile("C:\\Users\\CHENMA11\\Desktop\\PO_XML_SCHEMA\\crawler1.py");
//        PyFunction func = (PyFunction) interpreter.get("adder",
//                PyFunction.class);
//
//        int a = 2010, b = 2;
//        PyObject pyobj = func.__call__(new PyInteger(a), new PyInteger(b));
//        System.out.println("anwser = " + pyobj.toString());

        interpreter.execfile("C:/Users/CHENMA11/Desktop/PO_XML_SCHEMA/crawler1.py");

}

}

