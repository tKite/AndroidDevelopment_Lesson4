package com.example.data_thread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.data_thread.databinding.ActivityMainBinding;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        final Runnable runn1 = new Runnable() {
            public void run() {
                binding.tvInfo.setText("1 по счету runOnUiThread - метод класса Activity, " +
                        "который выполняет определенный код " +
                        "на основном потоке пользовательского интерфейса");
            }
        };
        final Runnable runn2 = new Runnable() {
            public void run() {
                binding.tvInfo.setText("3 по счету post - метод класса Handler, " +
                        "который помещает сообщение в очередь сообщений обработчика и " +
                        "выполняет определенные действия сразу же после того, " +
                        "как приложение входит в состояние покоя");
            }
        };
        final Runnable runn3 = new Runnable() {
            public void run() {
                binding.tvInfo.setText("2 по счету postDelayed - метод класса Handler, " +
                        "который выполняет определенное действие через" +
                        " определенный промежуток времени");
            }
        };
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(2);
                    runOnUiThread(runn1);
                    Log.d("check", "runn1");
                    TimeUnit.SECONDS.sleep(1);
                    binding.tvInfo.postDelayed(runn3, 2000);
                    Log.d("check", "runn3");
                    binding.tvInfo.post(runn2);
                    Log.d("check", "runn2");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }
}