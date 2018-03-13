package com.example.sma.databaseexample;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

    Button addButton;
    Button showButton;
    EditText money, name;
    TextView showData;
    CheckBox debtOrCredit;
    private DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showButton      = (Button)  findViewById(R.id.showInfo);
        money           = (EditText)findViewById(R.id.money);
        name            = (EditText)findViewById(R.id.name);
        debtOrCredit    = (CheckBox)findViewById(R.id.debt);
        addButton       = (Button)  findViewById(R.id.addRecord);
        showData        = (TextView)findViewById(R.id.showInfo);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showData.setText("");
                showData.setVisibility(View.INVISIBLE);
                //забираем введенные пользователем данные
                double myMoney = Double.parseDouble(money.getText().toString());
                String nameHuman = name.getText().toString();
                int debt=0;
                if (debtOrCredit.isChecked()) debt = 1;
                //открываем базу для записи
                dataBaseHelper = new DataBaseHelper(getBaseContext());
                SQLiteDatabase sdb;
                sdb = dataBaseHelper.getWritableDatabase();

                //формируем запрос на добавление записи в базу
                String insertQuery = "INSERT INTO "+DataBaseHelper.DATABASE_TABLE+
                        " ("+DataBaseHelper.NAME_COLUMN+", "+DataBaseHelper.MONEY_COLUMN+
                        ", "+DataBaseHelper.DEBT_OR_CREDIT_COLUMN+") VALUES "+
                        "("+nameHuman+", "+myMoney+", "+debt+");";
                sdb.execSQL(insertQuery);
                sdb.close();
            }
        });

        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showData.setVisibility(View.VISIBLE);
                //открываем базу для чтения
                dataBaseHelper = new DataBaseHelper(getBaseContext());
                SQLiteDatabase sdb;
                sdb = dataBaseHelper.getReadableDatabase();
                //формируем запрос на извлечение информации
                String showQuery = "SELECT * FROM "+DataBaseHelper.DATABASE_TABLE+";";
                Cursor cursor = sdb.rawQuery(showQuery,null);
                cursor.moveToFirst();
                while (cursor.moveToNext()){
                    double myMoney = cursor.getDouble(cursor.getColumnIndex(DataBaseHelper.MONEY_COLUMN));
                    String nameHuman = cursor.getString(cursor.getColumnIndex(DataBaseHelper.NAME_COLUMN));
                    int dept = cursor.getInt(cursor.getColumnIndex(DataBaseHelper.DEBT_OR_CREDIT_COLUMN));
                    if (dept == 0)
                        showData.append(nameHuman+" должен мне "+Double.toString(myMoney)+"р.\n");
                    else
                        showData.append("Я должен "+nameHuman+" "+Double.toString(myMoney)+"р.\n");
                }
                cursor.close();
                sdb.close();
            }
        });
    }
}
