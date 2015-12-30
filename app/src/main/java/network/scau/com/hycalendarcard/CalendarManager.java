package network.scau.com.hycalendarcard;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;


public class CalendarManager {
    private int nowYear, nowMonth;
    private int year, month;
    private Context context;
    private TextView year_month;
    private Button left, right;
    private Calendar calendar;

    private int minday;
    private int maxday;
    // private String[] sevenDay;

    private View calendar_view;

    private Dialog showdialog = null;

    private Handler mHandler;

    private String startTime, endTime;

    public CalendarManager(final Context context) {
        this.context = context;
        init(context);
    }

    // public CalendarManager(final Context context,String start,String end) {
    // this.context = context;
    //
    // this.startTime = start;
    // this.endTime = end;
    //
    // init(context);
    // }
    private void init(final Context context) {
        calendar_view = View.inflate(context, R.layout.view_calendar, null);
        calendar = (Calendar) calendar_view.findViewById(R.id.id_calendar);

    }

    public void setUsableDays(String start, String end) {
        startTime = start;
        endTime = end;
        calendar.setUsableDay(startTime, endTime);
        if (showdialog == null) {

            nowYear = calendar.getYear();
            nowMonth = calendar.getMonth();

            year_month = (TextView) calendar_view.findViewById(R.id.id_tv_year_month);
            left = (Button) calendar_view.findViewById(R.id.btn_left);
            right = (Button) calendar_view.findViewById(R.id.btn_right);

            // sevenDay = getSevenDay(calendar.getYear(), calendar.getMonth(),
            // calendar.getToday());
            // calendar.setSevenDay(sevenDay);

            mHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    // TODO Auto-generated method stub
                    super.handleMessage(msg);
                    if (showdialog != null) {
                        showdialog.dismiss();
                        reset();
                    }
                }

            };
            calendar.setmHandler(mHandler);

            left.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    calendar.preMonth();
                    changeBtnVisiable("left");
                    updateYearAndMonth();
                }
            });

            right.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    calendar.nextMonth();
                    changeBtnVisiable("right");
                    updateYearAndMonth();
                }
            });

            updateYearAndMonth();
            if (isKuaYue(startTime, endTime)) {
                right.setVisibility(View.VISIBLE);
                left.setVisibility(View.GONE);
            }

            showdialog = new Dialog(context);
            showdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            showdialog.setContentView(calendar_view);
            showdialog.setOnCancelListener(new OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    // TODO Auto-generated method stub
                    reset();
                }
            });
            // 这里设置dialog的背景透明
            showdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            // showdialog = new AlertDialog.Builder(context).create();
            // showdialog.setView(calendar_view);
        }

    }

    public void setOnBottom() {
        if (showdialog != null) {
            Window mWindow = showdialog.getWindow();
            WindowManager.LayoutParams lp = mWindow.getAttributes();
            // 透明度的范围为：0.0f-1.0f;0.0f表示完全透明,1.0f表示完全不透明(系统默认的就是这个)。
            lp.alpha = 1f;
            // 设置对话框在屏幕的底部显示，当然还有上下左右，任意位置
            // mWindow.setGravity(Gravity.LEFT);
            mWindow.setGravity(Gravity.BOTTOM);
        }
    }

    public void setOnChooseListener(Calendar.OnChooseListener listener) {
        calendar.setOnChooseListener(listener);
    }

    public void reset() {
        if (calendar != null) {
            calendar.resetYearAndMonth(nowYear, nowMonth);
        }
        updateYearAndMonth();
        if (isKuaYue(startTime, endTime)) {
            right.setVisibility(View.VISIBLE);
            left.setVisibility(View.GONE);
        }
        calendar.invalidate();
    }

    public void show() {
        if (showdialog != null) {
            calendar.invalidate();
            showdialog.show();
        }
    }

    public void dismiss() {
        reset();
        if (showdialog != null) {
            showdialog.dismiss();
        }
    }

    private void changeBtnVisiable(String string) {
        // TODO Auto-generated method stub
        if (string.equals("left")) {
            left.setVisibility(View.GONE);
            right.setVisibility(View.VISIBLE);
        } else if (string.equals("right")) {
            right.setVisibility(View.GONE);
            left.setVisibility(View.VISIBLE);
        }
    }

    private String[] getSevenDay(int year2, int month2, int today) {
        // TODO Auto-generated method stub

        String[] st = new String[7];
        // st[0] = "" + year2 + "-" + month2 + "-" + today;
        boolean newDay = false;
        boolean newMonth = false;
        boolean newYear = false;
        boolean addMonth = false;
        for (int i = 0; i < 7; i++) {

            if (newYear) {
                year2++;
                newYear = false;
            }
            if (newMonth) {
                month2 = 1;
                newMonth = false;
            }
            if (newDay) {
                today = 1;
                newDay = false;
            }
            if (addMonth) {
                month2++;
                addMonth = false;
            }
            if (year2 % 4 == 0 && year2 % 100 != 0 || year2 % 400 == 0) {
                // 是闰年

                if (today == 31) {
                    if (month2 == 12) {
                        newYear = true;
                        newMonth = true;
                        newDay = true;
                    } else if (is31(month2)) {
                        addMonth = true;
                        newDay = true;
                    }

                }
                if (today == 30) {
                    if (is30(month2)) {
                        addMonth = true;
                        newDay = true;
                    }
                }
                if (today == 29) {
                    if (month2 == 2) {
                        addMonth = true;
                        newDay = true;
                    }
                }
            } else {
                // 不是闰年
                if (today == 31) {
                    if (month2 == 12) {
                        newYear = true;
                        newMonth = true;
                        newDay = true;
                    } else if (is31(month2)) {
                        addMonth = true;
                        newDay = true;
                    }

                }
                if (today == 30) {
                    if (is30(month2)) {
                        addMonth = true;
                        newDay = true;
                    }
                }
                if (today == 28) {
                    if (month2 == 2) {
                        addMonth = true;
                        newDay = true;
                    }
                }
            }
            st[i] = "" + year2 + "-" + month2 + "-" + today;
            today = today + 1;
        }
        return st;
    }

    private boolean is30(int month2) {
        // TODO Auto-generated method stub
        int days[] = {4, 6, 9, 11};
        for (int i = 0; i < days.length; i++) {
            if (days[i] == month2) {
                return true;
            }
        }
        return false;
    }

    private boolean is31(int month2) {
        // TODO Auto-generated method stub
        int days[] = {1, 3, 5, 7, 8, 10};
        for (int i = 0; i < days.length; i++) {
            if (days[i] == month2) {
                return true;
            }
        }
        return false;
    }

    private void updateYearAndMonth() {
        year = calendar.getYear();
        month = calendar.getMonth();
        year_month.setText(year + "年" + month + "月");
    }

    private boolean isKuaYue(String[] sevenDay) {
        int themonth = -1;
        int i;
        for (i = 0; i < 7; i++) {
            String[] split = sevenDay[i].split("-");
            if (split.length > 0) {
                int month = Integer.parseInt(split[1]);
                int day = Integer.parseInt(split[2]);
                int year = Integer.parseInt(split[0]);
                if (themonth != -1 && themonth != month) {
                    break;
                }
                themonth = month;
            }
        }
        if (i == 7) {
            return false;
        } else {
            return true;
        }
    }

    private boolean isKuaYue(String start, String end) {
        int minmonth = Integer.parseInt(start.substring(4, 6).toString());
        int maxmonth = Integer.parseInt(end.substring(4, 6).toString());
        if (minmonth == maxmonth) {
            return false;
        } else {
            return true;
        }
    }

    public String getToday() {
        return calendar.getNowYear() + "-" + calendar.getNowMonth() + "-" + calendar.getNowDay() + " 今天";
    }

    public String getTodayString() {
        int month = calendar.getNowMonth();
        String monthStr;
        if (month < 10) {
            monthStr = "0" + calendar.getNowMonth();
        } else {
            monthStr = "" + calendar.getNowMonth();
        }
        int day = calendar.getNowDay();
        String dayStr;
        if (day < 10) {
            dayStr = "0" + calendar.getNowDay();
        } else {
            dayStr = "" + calendar.getNowDay();
        }
        return calendar.getNowYear() + "-" + monthStr + "-" + dayStr + " 今天";
    }

    public String getTodaySt() {
        int month = calendar.getNowMonth();
        String monthStr;
        if (month < 10) {
            monthStr = "0" + calendar.getNowMonth();
        } else {
            monthStr = "" + calendar.getNowMonth();
        }
        int day = calendar.getNowDay();
        String dayStr;
        if (day < 10) {
            dayStr = "0" + calendar.getNowDay();
        } else {
            dayStr = "" + calendar.getNowDay();
        }
        return calendar.getNowYear() + "-" + monthStr + "-" + dayStr;
    }

    public String getTodayData() {
        int month = calendar.getNowMonth();
        String monthStr;
        if (month < 10) {
            monthStr = "0" + calendar.getNowMonth();
        } else {
            monthStr = "" + calendar.getNowMonth();
        }
        int day = calendar.getNowDay();
        String dayStr;
        if (day < 10) {
            dayStr = "0" + calendar.getNowDay();
        } else {
            dayStr = "" + calendar.getNowDay();
        }
        return calendar.getNowYear() + monthStr + dayStr;
    }

    public String getSevenAfter(int year, int month, int day) {
        CalendarUtils.set(year, month - 1, day + 6);
        int year2 = CalendarUtils.getCurrentYear();
        int month2 = CalendarUtils.getCurrentMonth();
        int day2 = CalendarUtils.getCurrentDate();
        String strm;
        if (month2 < 10) {
            strm = "0" + month2;
        } else {
            strm = "" + month2;
        }
        String strd;
        if (day2 < 10) {
            strd = "0" + day2;
        } else {
            strd = "" + day2;
        }
        CalendarUtils.reset();
        return year2 + strm + strd;
    }

}
