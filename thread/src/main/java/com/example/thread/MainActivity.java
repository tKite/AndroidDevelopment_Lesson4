package com.example.thread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.thread.databinding.ActivityMainBinding;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private int counter = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        TextView infoTextView = binding.textView;
        Thread mainThread = Thread.currentThread();
        infoTextView.setText("Имя текущего потока: " + mainThread.getName());
        // Меняем имя и выводим в текстовом поле
        mainThread.setName("МОЙ НОМЕР ГРУППЫ: 9, НОМЕР ПО СПИСКУ: 10, МОЙ ЛЮБИМЫЙ ФИЛЬМ: Дьявол всегда здесь");
        infoTextView.append("\n Новое имя потока: " + mainThread.getName());
        Log.d(MainActivity.class.getSimpleName(),	"Stack:	"	+	Arrays.toString(mainThread.getStackTrace()));

//        binding.button.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public	void onClick(View v){
//                long endTime = System.currentTimeMillis() +	20 * 1000;
//                while (System.currentTimeMillis() <	endTime) {
//                    synchronized(this){
//                        try	{
//                            wait(endTime	- System.currentTimeMillis());
//                        }	catch (Exception e){
//                            throw new RuntimeException(e);
//                        }
//                    }
//                }
//            }
//        });
        Log.d(MainActivity.class.getSimpleName(),"Group:	" + mainThread.getThreadGroup());
        binding.button.setOnClickListener(new View.OnClickListener()	{
            @Override
            public	void onClick(View v){
                new	Thread(new	Runnable(){
                    public	void run(){
                        int	numberThread = counter++;
                        Log.d("ThreadProject",String.format("Запущен поток № %d студентом группы № %s номер по списку № %d ",
                                numberThread, "БСБО-09-21", 10));
                        long endTime = System.currentTimeMillis() +	20	* 1000;
                        while (System.currentTimeMillis() <	endTime){
                            synchronized(this){
                                try{
                                    wait(endTime	- System.currentTimeMillis());
                                    Log.d(MainActivity.class.getSimpleName(),"Endtime:	" +	endTime);
                                }	catch(Exception	e){
                                    throw new RuntimeException(e);
                                }
                            }
                            Log.d("ThreadProject",	"Выполнен поток №	" +	numberThread);
                        }
                    }
                }).start();
            }
        });
    }
}