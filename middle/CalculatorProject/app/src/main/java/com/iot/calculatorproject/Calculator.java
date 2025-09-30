package com.iot.calculatorproject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Calculator extends AppCompatActivity {

    private Button zero, one, two, three, four, five, six, seven, eight, nine, div, plus, multi, minus
            , AC, Lparen, Rparen, remain, total, under;
    private TextView textView;

    StringBuilder input = new StringBuilder();
    StringBuilder view = new StringBuilder();

    List<Button> buttonList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_calculator);

        zero = findViewById(R.id.button18);
        one = findViewById(R.id.button13);
        two = findViewById(R.id.button14);
        three = findViewById(R.id.button15);
        four = findViewById(R.id.button9);
        five = findViewById(R.id.button10);
        six = findViewById(R.id.button11);
        seven = findViewById(R.id.button5);
        eight = findViewById(R.id.button6);
        nine = findViewById(R.id.button7);
        Lparen = findViewById(R.id.button);
        Rparen = findViewById(R.id.button2);
        remain = findViewById(R.id.button3);
        AC = findViewById(R.id.button4);
        div = findViewById(R.id.button8);
        multi = findViewById(R.id.button12);
        minus = findViewById(R.id.button17);
        plus = findViewById(R.id.button20);
        total = findViewById(R.id.button19);
        under = findViewById(R.id.button16);

        textView = findViewById(R.id.textView);

        buttonList = Arrays.asList(zero, one, two, three, four, five, six, seven, eight, nine, remain, AC, div, multi, minus, plus);

        under.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input.append("@");
                view.append("-");
            }
        });
        total.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                String str = String.valueOf(result(String.valueOf(input)));
                str = str.replace("@", "-");
                textView.setText(view + "\n\n= "+str);
            }
        });

        for(Button btn: buttonList) {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(btn.getText().toString().equals("AC"))
                    {
                        input.delete(0, input.length());
                        view.delete(0, view.length());
                    }
                    else
                    {
                        input.append(btn.getText().toString());
                        view.append(btn.getText().toString());
                    }
                    textView.setText(view.toString());
                }
            });
        }
    }

//    double result(String str)
//    {
//
//
//        List<String> stringList= new ArrayList<String>();
//
//        int indexFirst = 0;
//        int indexLast = 0;
//
//        indexFirst = str.indexOf("(");
//        indexLast = str.indexOf(")");
//
//        if(indexFirst != -1 && indexLast != -1)
//        {
//            str.replace(str.substring(indexFirst, indexLast),String.valueOf(result(str.substring(indexFirst, indexLast))));
//
//
//        }
//        else if(indexFirst != -1 ^ indexLast != -1) return -1;
//        else {
//            StringTokenizer stk = tokens(str);
//
//
//            while(stk.hasMoreTokens()) stringList.add(stk.nextToken());
//
//            stringList = after(stringList, "x");
//            stringList = after(stringList, "/");
//            stringList = after(stringList, "%");
//            stringList = after(stringList, "+");
//            stringList = after(stringList, "-");
//        }
//        return Double.parseDouble(stringList.get(0));
//    }

    double result(String str)
    {
        str = str.replace("@", "-");

//        int indexFirst = str.lastIndexOf("(");
//        int indexLast = str.indexOf(")", indexFirst);
//
//        if(indexFirst != -1 && indexLast != -1)
//        {
//            String inner = str.substring(indexFirst + 1, indexLast);
//            double innerResult = result(inner);
//            str = str.substring(0, indexFirst) +"x"+innerResult + str.substring(indexLast + 1);
//            return result(str);
//        }
//        else if(indexFirst != -1 ^ indexLast != -1) {
//            return -1;
//        }

        StringTokenizer stk = tokens(str);
        List<String> stringList= new ArrayList<>();

        while(stk.hasMoreTokens()) stringList.add(stk.nextToken());

        stringList = after(stringList, "x");
        stringList = after(stringList, "/");
        stringList = after(stringList, "%");
        stringList = after(stringList, "+");
        stringList = after(stringList, "-");

        String resultStr = stringList.get(0).replace("@", "-");
        return Double.parseDouble(resultStr);
    }


    List<String> after(List<String> stringList, String cal){
        switch (cal) {
            case "x":
                while(stringList.contains(cal)) {
                    int index = stringList.indexOf(cal);
                    if(index == -1) break;
                    if(index - 1 < 0 || index + 1 >= stringList.size()) break;

                    stringList.set(index, multi(stringList.get(index - 1), stringList.get(index + 1)));
                    stringList.remove(index + 1);
                    stringList.remove(index - 1);
                }
                break;
            case "/":
                while(stringList.contains(cal)) {
                    int index = stringList.indexOf(cal);
                    if(index == -1) break;
                    if(index - 1 < 0 || index + 1 >= stringList.size()) break;

                    stringList.set(index, divide(stringList.get(index - 1), stringList.get(index + 1)));
                    stringList.remove(index + 1);
                    stringList.remove(index - 1);
                }
                break;
            case "%":
                while(stringList.contains(cal)) {
                    int index = stringList.indexOf(cal);
                    if(index == -1) break;
                    if(index - 1 < 0 || index + 1 >= stringList.size()) break;

                    stringList.set(index, remains(stringList.get(index - 1), stringList.get(index + 1)));
                    stringList.remove(index + 1);
                    stringList.remove(index - 1);
                }
                break;
            case "+":
                while(stringList.contains(cal)) {
                    int index = stringList.indexOf(cal);
                    if(index == -1) break;
                    if(index - 1 < 0 || index + 1 >= stringList.size()) break;

                    stringList.set(index, plus(stringList.get(index - 1), stringList.get(index + 1)));
                    stringList.remove(index + 1);
                    stringList.remove(index - 1);
                }
                break;
            case "-":
                while(stringList.contains(cal))
                {
                    int index = stringList.indexOf(cal);
                    if(index == 0 || isOperator(stringList.get(index - 1))) {
                        String negativeNumber = "@" + stringList.get(index + 1);
                        stringList.set(index, negativeNumber);
                        stringList.remove(index + 1);
                    } else {
                        stringList.set(index, minus(stringList.get(index - 1),
                                stringList.get(index + 1)));
                        stringList.remove(index + 1);
                        stringList.remove(index - 1);
                    }
                }
                break;
        }
        return stringList;
    }

    boolean isOperator(String s) {
        return s.equals("x") || s.equals("/") || s.equals("%") || s.equals("+") || s.equals("-");
    }
    StringTokenizer tokens(String str)
    {
        StringTokenizer stk = new StringTokenizer(str, "x/%+-", true);
        return stk;
    }

    String plus(String a, String b)
    {
        if(a.contains("@")) a = a.replace("@", "-");
        if(b.contains("@")) b = b.replace("@", "-");


        double value1 = Double.parseDouble(a);
        double value2 = Double.parseDouble(b);
        double value3 = (value1 + value2);
        String value4 = String.valueOf(value3);

        if(value4.contains("-")) value4 = value4.replace("-", "@");

        return value4;
    }

    String minus(String a, String b)
    {
        if(a.contains("@")) a = a.replace("@", "-");
        if(b.contains("@")) b = b.replace("@", "-");


        double value1 = Double.parseDouble(a);
        double value2 = Double.parseDouble(b);
        double value3 = (value1 - value2);
        String value4 = String.valueOf(value3);

        if(value4.contains("-")) value4 = value4.replace("-", "@");

        return value4;
    }

    String multi(String a, String b)
    {
        if(a.contains("@")) a = a.replace("@", "-");
        if(b.contains("@")) b = b.replace("@", "-");


        double value1 = Double.parseDouble(a);
        double value2 = Double.parseDouble(b);
        double value3 = (value1 * value2);
        String value4 = String.valueOf(value3);

        if(value4.contains("-")) value4 = value4.replace("-", "@");

        return value4;


    }

    String divide(String a, String b)
    {
        if(a.contains("@")) a = a.replace("@", "-");
        if(b.contains("@")) b = b.replace("@", "-");

        double value1 = Double.parseDouble(a);
        double value2 = Double.parseDouble(b);
        double value3 = (value1 / value2);
        String value4 = String.valueOf(value3);

        if(value4.contains("-")) value4 = value4.replace("-", "@");

        return value4;
    }

    String remains(String a, String b)
    {
        if(a.contains("@")) a = a.replace("@", "-");
        if(b.contains("@")) b = b.replace("@", "-");

        int value1 = Integer.parseInt(a);
        int value2 = Integer.parseInt(b);
        int value3 = (value1 % value2);
        String value4 = String.valueOf(value3);

        if(value4.contains("-")) value4 = value4.replace("-", "@");

        return value4;
    }

}