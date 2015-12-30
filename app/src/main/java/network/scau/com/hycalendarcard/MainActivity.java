package network.scau.com.hycalendarcard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    CalendarManager  manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = new CalendarManager(this);
        final Map weekMap = new HashMap<Integer, String>();
        weekMap.put(1, "日");
        weekMap.put(2, "一");
        weekMap.put(3, "二");
        weekMap.put(4, "三");
        weekMap.put(5, "四");
        weekMap.put(6, "五");
        weekMap.put(7, "六");
        manager.setUsableDays(manager.getTodayData(), getSevenAfter(manager.getTodayData()));
        manager.setOnChooseListener(new Calendar.OnChooseListener() {
            @Override
            public void onSingleChoose(int year, int month, int day, boolean overdue, int gapToday, int weekday) {
                // TODO Auto-generated method stub
                if (overdue) {
                    Toast.makeText(MainActivity.this, "该日期不能选哦", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, year+"年"+month+"月"+day+"日", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    public void show(View v){
        manager.show();
    }

    private String getSevenAfter(String todayData) {
        // TODO Auto-generated method stub
        int gyear = Integer.parseInt(todayData.subSequence(0, 4).toString());
        int gmonth = Integer.parseInt(todayData.substring(4, 6).toString());
        int gday = Integer.parseInt(todayData.substring(6, 8).toString());

        return manager.getSevenAfter(gyear, gmonth, gday);
    }
}
