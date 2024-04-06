package com.example.looper;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class MyLooper extends Thread{
    public Handler mHandler;
    private	Handler	mainHandler;
    public MyLooper(Handler mainThreadHandler)	{
        mainHandler	= mainThreadHandler;
    }
    public void	run(){
        Log.d("MyLooper","run");
        Looper.prepare();
        mHandler = new Handler(Looper.myLooper()){
            public	void handleMessage(Message msg)	{
                String	work	=	msg.getData().getString("work");
                int age = msg.getData().getInt("age");
                String str_age = Integer.toString(age);
                try {
                    Thread.sleep(age);
                    Log.d("loop",	str_age);
                }catch (Exception e){

                }
                Message	message	=	new	Message();
                Bundle bundle	=	new	Bundle();
                bundle.putString("result", "Words" + str_age );
                message.setData(bundle);
                //	Send	the	message	back	to	main	thread	message	queue	use	main	thread	message	Handler.
                mainHandler.sendMessage(message);
            }
        };
        Looper.loop();
    }
}

