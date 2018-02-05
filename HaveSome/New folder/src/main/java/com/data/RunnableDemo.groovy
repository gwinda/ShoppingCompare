package com.data

import org.python.core.PyFunction
import org.python.core.PyObject
import org.python.core.PyString
import org.python.util.PythonInterpreter

/**
 * Created by CHENMA11 on 2/5/2018.
 */

class RunnableDemo implements Runnable {
    private Thread t;
    private String threadName;

    RunnableDemo( String name) {
        threadName = name;
        System.out.println("Creating " +  threadName );
    }

    public void run() {
        System.out.println("Running " +  threadName );
        try {
            def url = ["https://item.jd.com/4805997.html","https://item.jd.com/5505126.html","https://item.jd.com/11805636597.html","https://item.jd.com/16683419775.html"]
            Properties props = new Properties();
            props.put("python.console.encoding", "UTF-8"); // Used to prevent: console: Failed to install '': java.nio.charset.UnsupportedCharsetException: cp0.
            props.put("python.security.respectJavaAccessibility", "false"); //don't respect java accessibility, so that we can access protected members on subclasses
            props.put("python.import.site","false");
            Properties preprops = System.getProperties();
            PythonInterpreter.initialize(preprops, props, new String[0]);
            PythonInterpreter interpreter = new PythonInterpreter();
            interpreter.exec("import sys");
            interpreter.exec("sys.path.append('C:/Users/CHENMA11/AppData/Local/Programs/Python/Python36-32/Lib')");
            // PythonInterpreter interpreter = new PythonInterpreter();
            for(int i = 0; i <url.size(); i++) {
                // interpreter.execfile("C:/Users/CHENMA11/Desktop/PO_XML_SCHEMA/answer2.py");
                interpreter.execfile("C:/Users/CHENMA11/Desktop/PO_XML_SCHEMA/jdPrice.py");
                PyFunction func = (PyFunction)interpreter.get("jd_price",PyFunction.class);
                PyObject pyobj = func.__call__(new PyString(url[i]));

                System.out.println("anwser = " + pyobj.toString());
                System.out.println("Thread: " + threadName + ", " + i);
                // 让线程睡眠一会
                Thread.sleep(50);
            }
        }catch (InterruptedException e) {
            System.out.println("Thread " +  threadName + " interrupted.");
        }
        System.out.println("Thread " +  threadName + " exiting.");
    }

    public void start () {
        System.out.println("Starting " +  threadName );
        if (t == null) {
            t = new Thread (this, threadName);
            t.start ();
        }
    }
}

public class TestThread {

    public static void main(String[] args) {
        RunnableDemo R1 = new RunnableDemo( "Thread-1");
        R1.start();

        RunnableDemo R2 = new RunnableDemo( "Thread-2");
        R2.start();
    }
}
