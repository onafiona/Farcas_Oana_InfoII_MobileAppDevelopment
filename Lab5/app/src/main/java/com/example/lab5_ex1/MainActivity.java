package com.example.lab5_ex1;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void addnmb(View view)
    {
        EditText firstNumbertxt=findViewById(R.id.firstNumbertxt);
        EditText secondNumbertxt=findViewById(R.id.secondNumbertxt);
        int number1=Integer.parseInt(firstNumbertxt.getText().toString());
        int number2=Integer.parseInt(secondNumbertxt.getText().toString());
        int res=number1+number2;
        TextView resulttxt=findViewById(R.id.resulttxt);
        resulttxt.setText(String.valueOf(res));
    }

    public void subnmb(View view)
    {
        EditText firstNumbertxt=findViewById(R.id.firstNumbertxt);
        EditText secondNumbertxt=findViewById(R.id.secondNumbertxt);
        int number1=Integer.parseInt(firstNumbertxt.getText().toString());
        int number2=Integer.parseInt(secondNumbertxt.getText().toString());
        int res=number1-number2;
        TextView resulttxt=findViewById(R.id.resulttxt);
        resulttxt.setText(String.valueOf(res));
    }

    public void divnmb(View view)
    {
        EditText firstNumbertxt=findViewById(R.id.firstNumbertxt);
        EditText secondNumbertxt=findViewById(R.id.secondNumbertxt);
        int number1=Integer.parseInt(firstNumbertxt.getText().toString());
        int number2=Integer.parseInt(secondNumbertxt.getText().toString());
        int res=number1/number2;
        TextView resulttxt=findViewById(R.id.resulttxt);
        resulttxt.setText(String.valueOf(res));
    }

    public void mulnmb(View view)
    {
        EditText firstNumbertxt=findViewById(R.id.firstNumbertxt);
        EditText secondNumbertxt=findViewById(R.id.secondNumbertxt);
        int number1=Integer.parseInt(firstNumbertxt.getText().toString());
        int number2=Integer.parseInt(secondNumbertxt.getText().toString());
        int res=number1*number2;
        TextView resulttxt=findViewById(R.id.resulttxt);
        resulttxt.setText(String.valueOf(res));
    }
}