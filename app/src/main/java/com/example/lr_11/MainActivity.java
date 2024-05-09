package com.example.lr_11;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button calculateButton = findViewById(R.id.calculateButton);
        TextView resultTextView = findViewById(R.id.resultTextView);

        final int[] array = {1, -2, -4, 5, 6, 0, 8, 4, -7, 10};

        calculateButton.setOnClickListener(v -> {
            final int product = calculateProduct(array);

            Thread productThread = new Thread(() -> {
                new Handler(Looper.getMainLooper()).post(() -> {
                    resultTextView.append("\nПроизведение положительных элементов: " + product);
                });
            });
            final int sumBetweenZeros = calculateSumBetweenModules(array);

            Thread sumBetweenZerosThread = new Thread(() -> {
                new Handler(Looper.getMainLooper()).post(() -> {
                    resultTextView.append("\nСумма между максимальным и минимальным модулями: " + sumBetweenZeros);
                });
            });

            // Запускаем оба потока
            productThread.start();
            sumBetweenZerosThread.start();
        });
    }

    private int calculateProduct(int[] array) {
        int product = 0;
        for (int i : array) {
            if (i > 0) {
                product += i;
            }
        }
        return product;
    }

    private int calculateSumBetweenModules(int[] array) {

        int maxModuleValue = Integer.MIN_VALUE;
        int minModuleValue = Integer.MAX_VALUE;
        int maxModuleIndex = -1;
        int minModuleIndex = -1;

        for (int i = 0; i < array.length; i++) {
            int moduleValue = Math.abs(array[i]);
            if (moduleValue > maxModuleValue) {
                maxModuleValue = moduleValue;
                maxModuleIndex = i;
            }
            if (moduleValue < minModuleValue) {
                minModuleValue = moduleValue;
                minModuleIndex = i;
            }
        }

        if (Math.abs(maxModuleIndex - minModuleIndex) == 1) {
            return 0;
        }

        int sum = 0;
        int startIndex = Math.min(maxModuleIndex, minModuleIndex) + 1;
        int endIndex = Math.max(maxModuleIndex, minModuleIndex) - 1;

        for (int i = startIndex; i <= endIndex; i++) {
            sum += array[i];
        }
        return sum;
    }
}