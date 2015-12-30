package network.scau.com.hycalendarcard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by ZhengHY on 2015/12/31 0031.
 */
public class Calendar extends View{


    private String TAG = "Calendar";
    // 上下文
    private Context context;

    // view的宽度
    private int mViewWidth;

    // view的高度
    private int mViewHeight;

    // 日历背景
    private int calendarBg = 0xfff9f9f9;

    // 左按钮可点击性
    private boolean leftClickable = true;

    // 左按钮可见性
    private boolean leftBtnVisiable = true;

    // 右按钮可点击性
    private boolean rightClickable = true;

    // 右按钮可见性
    private boolean rightBtnVisiable = true;

    // 普通字体颜色
    private int mNormalTextColor = 0xff000000;

    // 选中字体的颜色
    private int mBeSelectedTextColor = 0xffffffff;

    // 不可选字体的颜色
    private int mUnBeSelectedTextColor = 0xff999999;

    // 不可选日期的背景
    private int mUnBeSelectedTextBgColor = 0xff000000;

    // 可选日期的背景
    private int mBeSelectedTextBgColor = 0xffff4000;

    // z正常日期背景
    private int mNormalTextBgColor = 0xffffffff;

    // 日期背景半径大小
    private int mBgRadius = 20;

    // 周字体颜色
    private int mWeekTextColor = 0xff000000;

    // 画笔
    private Paint mPaint;

    // 以下是真实世界的年月日，固定不可随意改变
    private int NowDay;
    private int NowMonth;
    private int NowYear;

    // 周字体大小
    private int mWeekTextSize = 25;

    // 日字体大小
    private int mDayTextsize = 25;

    // 第一天星期几
    private int weekOfFirstDay = 1;

    // 日历显示的年份
    private int year;

    // 日历显示的月份
    private int month;
    // 日历显示的今天
    private int today = 0;

    // 所有天数
    private int[] allDays;

    // 周数
    private String[] weekName = new String[] { "日", "一", "二", "三", "四", "五", "六" };

    // 高亮可用
    private boolean needDays = false;

    // 可用日期 开始日期 20151210
    private String startDay = null;

    // 可用日期 结束日期 20151210
    private String endDay = null;

    // 被选择的年
    private int selectedYear = -3;

    // 被选择的月
    private int selectedMonth = -3;

    // 被选择的天
    private int selectedDay = -3;

    private boolean leftClick = false;

    private boolean rightClick = false;

    private Handler mHandler;

    public Calendar(Context context) {
        super(context);
        init(context);

    }

    public Calendar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public Calendar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * 初始化方法
     *
     * @param context
     */
    private void init(Context context) {
        this.context = context;
        this.year = CalendarUtils.getCurrentYear();
        this.month = CalendarUtils.getCurrentMonth();
        this.NowDay = CalendarUtils.getToday();
        this.NowMonth = CalendarUtils.getTomonth();
        this.NowYear = CalendarUtils.getToyear();
        this.weekOfFirstDay = CalendarUtils.getCurrentFirstWeekdayOfMoth();
        this.today = this.NowDay;
        allDays = getAllDays(CalendarUtils.getCurrentMaxNumOfMonth());
        mPaint = new Paint();

    }

    //-------------以下为get和set方法-------------

    public void setAllDays(int maxnum) {
        allDays = getAllDays(maxnum);
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getToday() {
        return today;
    }

    public void setToday(int today) {
        this.today = today;
    }

    public int getCalendarBg() {
        return calendarBg;
    }

    public void setCalendarBg(int calendarBg) {
        this.calendarBg = calendarBg;
    }

    public boolean isLeftClickable() {
        return leftClickable;
    }

    public void setLeftClickable(boolean leftClickable) {
        this.leftClickable = leftClickable;
    }

    public boolean isLeftBtnVisiable() {
        return leftBtnVisiable;
    }

    public void setLeftBtnVisiable(boolean leftBtnVisiable) {
        this.leftBtnVisiable = leftBtnVisiable;
    }

    public boolean isRightClickable() {
        return rightClickable;
    }

    public Handler getmHandler() {
        return mHandler;
    }

    public void setmHandler(Handler mHandler) {
        this.mHandler = mHandler;
    }

    public void setRightClickable(boolean rightClickable) {
        this.rightClickable = rightClickable;
    }

    public boolean isRightBtnVisiable() {
        return rightBtnVisiable;
    }

    public void setRightBtnVisiable(boolean rightBtnVisiable) {
        this.rightBtnVisiable = rightBtnVisiable;
    }

    public int getmNormalTextColor() {
        return mNormalTextColor;
    }

    public void setmNormalTextColor(int mNormalTextColor) {
        this.mNormalTextColor = mNormalTextColor;
    }

    public int getmBeSelectedTextColor() {
        return mBeSelectedTextColor;
    }

    public void setmBeSelectedTextColor(int mBeSelectedTextColor) {
        this.mBeSelectedTextColor = mBeSelectedTextColor;
    }

    public int getmUnBeSelectedTextColor() {
        return mUnBeSelectedTextColor;
    }

    public void setmUnBeSelectedTextColor(int mUnBeSelectedTextColor) {
        this.mUnBeSelectedTextColor = mUnBeSelectedTextColor;
    }

    public int getmUnBeSelectedTextBgColor() {
        return mUnBeSelectedTextBgColor;
    }

    public void setmUnBeSelectedTextBgColor(int mUnBeSelectedTextBgColor) {
        this.mUnBeSelectedTextBgColor = mUnBeSelectedTextBgColor;
    }

    public int getmBeSelectedTextBgColor() {
        return mBeSelectedTextBgColor;
    }

    public void setmBeSelectedTextBgColor(int mBeSelectedTextBgColor) {
        this.mBeSelectedTextBgColor = mBeSelectedTextBgColor;
    }

    public int getmNormalTextBgColor() {
        return mNormalTextBgColor;
    }

    public void setmNormalTextBgColor(int mNormalTextBgColor) {
        this.mNormalTextBgColor = mNormalTextBgColor;
    }

    public int getmBgRadius() {
        return mBgRadius;
    }

    public void setmBgRadius(int mBgRadius) {
        this.mBgRadius = mBgRadius;
    }

    public int getmWeekTextColor() {
        return mWeekTextColor;
    }

    public void setmWeekTextColor(int mWeekTextColor) {
        this.mWeekTextColor = mWeekTextColor;
    }

    public int getmWeekTextSize() {
        return mWeekTextSize;
    }

    public void setmWeekTextSize(int mWeekTextSize) {
        this.mWeekTextSize = mWeekTextSize;
    }

    public int getmDayTextsize() {
        return mDayTextsize;
    }

    public void setmDayTextsize(int mDayTextsize) {
        this.mDayTextsize = mDayTextsize;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int[] getAllDays() {
        return allDays;
    }

    public void setAllDays(int[] allDays) {
        this.allDays = allDays;
    }

    public String[] getWeekName() {
        return weekName;
    }

    public void setWeekName(String[] weekName) {
        this.weekName = weekName;
    }

    public boolean isNeedSeven() {
        return needDays;
    }

    public void setNeedSeven(boolean needSeven) {
        this.needDays = needSeven;
    }


    public int getNowDay() {
        return NowDay;
    }

    public void setNowDay(int nowDay) {
        NowDay = nowDay;
    }

    public void setWeekOfFirstDay(int weekOfFirstDay) {
        this.weekOfFirstDay = weekOfFirstDay;
    }

    public int getNowMonth() {
        return NowMonth;
    }

    public void setNowMonth(int nowMonth) {
        NowMonth = nowMonth;
    }

    public int getNowYear() {
        return NowYear;
    }

    public void setNowYear(int nowYear) {
        NowYear = nowYear;
    }

    //-------------以上为get和set方法-------------

    /**
     * 得到天数数组
     *
     * @param numOfDay
     * @return
     */
    private int[] getAllDays(int numOfDay) {
        int[] allDays = new int[numOfDay];
        for (int i = 0; i < numOfDay; i++) {
            allDays[i] = i + 1;
        }
        return allDays;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int widthMeasureSpec) {
        int width;

        int mode = MeasureSpec.getMode(widthMeasureSpec);

        int size = MeasureSpec.getSize(widthMeasureSpec);

        if (mode == MeasureSpec.EXACTLY) {
            width = size;
        } else {
            // 不是精确模式的话得自己结合paddin
            int desire = size + getPaddingLeft() + getPaddingRight();
            if (mode == MeasureSpec.AT_MOST) {
                width = Math.min(desire, size);
            } else {
                width = desire;
            }
        }
        mViewWidth = width;

        return width;
    }

    private int measureHeight(int heightMeasureSpec) {
        int height;

        int mode = MeasureSpec.getMode(heightMeasureSpec);

        int size = MeasureSpec.getSize(heightMeasureSpec);

        if (mode == MeasureSpec.EXACTLY) {
            height = size;
        } else {
            // 不是精确模式的话得自己结合paddin
            int desire = size + getPaddingTop() + getPaddingBottom();
            if (mode == MeasureSpec.AT_MOST) {
                height = Math.min(desire, size);
            } else {
                height = desire;
            }
        }
        mViewHeight = height;
        return height;
    }



    float xInterval;
    float yInterval;

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        Log.d(TAG, "mViewWidth=" + mViewWidth);
        Log.d(TAG, "mViewHeight=" + mViewHeight);
        canvas.drawColor(calendarBg);

        int hang_num = 8;
        int lie_num = 7;

        xInterval = mViewWidth / lie_num;
        yInterval = mViewHeight / hang_num;

        //背景圆的半径
        mBgRadius = (int) (Math.min(xInterval, yInterval) / 2);

        mWeekTextSize = Math.min(mViewHeight, mViewWidth) / 15;
        mDayTextsize = Math.min(mViewHeight, mViewWidth) / 15;

        float x = 0;
        float y = 0;
        float offset = 0;
        mPaint.reset();

        drawWeekText(canvas);

        drawDay(canvas);

    }

    /**
     * 绘制周
     *
     * @param canvas
     */
    private void drawWeekText(Canvas canvas) {
        float x, y;
        float offset;

        //注意，这里要先设置TextSize，后面用Paint测量String长度才会准，这样绘制文字才会准
        mPaint.setTextSize(mWeekTextSize);
        // 绘制一周
        for (int i = 0; i < weekName.length; i++) {
            x = i * xInterval + xInterval / 2;
            // 因为绘制在第一行
            y = 1 * yInterval;

            offset = mPaint.measureText(weekName[i]);
            if (i == 0 || i == weekName.length - 1) {
                mPaint.setColor(mWeekTextColor);
                mPaint.setAntiAlias(true);
                canvas.drawText(weekName[i], x - offset / 2, y, mPaint);
            } else {
                mPaint.setColor(mWeekTextColor);
                mPaint.setAntiAlias(true);
                canvas.drawText(weekName[i], x - offset / 2, y, mPaint);
            }
        }
        mPaint.reset();
    }

    /**
     * 绘制天
     *
     * @param canvas
     */
    private void drawDay(Canvas canvas) {
        mPaint.setTextSize(mDayTextsize);
        mPaint.setAntiAlias(true);
        int day = 0;
        int theday;
        boolean istoday = false;
        boolean isCheckDay = false;
        float offset = 0;

        float x, y;
        // 绘制的日期
        String str;
        for (int i = 2; i < 8; i++) {
            for (int j = 0; j < 7; j++) {

                if (i == 2 && j == 0) {
                    j = weekOfFirstDay;
                }
                if (day > allDays.length - 1) {
                    theday = -1;
                } else {
                    theday = allDays[day];
                }

                if (theday < 10) {
                    str = "0" + theday;
                } else {
                    str = "" + theday;
                }
                if (theday == -1) {
                    str = "";
                }
                offset = mPaint.measureText(str);
                x = j * xInterval + xInterval / 2;
                // 因为绘制在第一行
                y = i * yInterval;

                drawDayText(x, y, str, mNormalTextColor, isToday(theday), isTheday(theday, month, year),
                        isOverdue(theday, month, year), canvas, offset);
                day++;
            }
        }

    }

    public void setUsableDay(String minTime, String maxTime) {
        startDay = minTime;
        endDay = maxTime;
    }

    private boolean isTheday(int theday, int thmonth, int thyear) {
        // TODO Auto-generated method stub
        return theday == selectedDay && thmonth == selectedMonth && thyear == selectedYear;
    }

    private void drawDayText(float x, float y, String text, int textColor, boolean isToday, boolean isTheDay,
                             boolean isOverdue, Canvas canvas, float offset) {

        if (isToday) {
            textColor = 0xffffffff;
        }
        mPaint.reset();
        mPaint.setTextSize(mDayTextsize);
        mPaint.setAntiAlias(true);

        if (isOverdue) {
            textColor = mUnBeSelectedTextColor;
            if (isToday) {
                drawDayBg(x, y, isToday, isTheDay, isOverdue, canvas, offset);
            } else {

            }
        } else {
            if (!text.equals("")) {
                drawDayBg(x, y, isToday, isTheDay, isOverdue, canvas, offset);
            }
            textColor = mNormalTextColor;
        }
        if (isTheDay || isToday) {
            textColor = 0xffffffff;
        }
        mPaint.reset();
        mPaint.setTextSize(mDayTextsize);
        mPaint.setAntiAlias(true);

        mPaint.setColor(textColor);
        mPaint.setAntiAlias(true);
        canvas.drawText(text, x - offset / 2, y, mPaint);
    }

    private void drawDayBg(float x, float y, boolean isToday, boolean isTheDay, boolean isOverdue, Canvas canvas,
                           float offset) {
        // TODO Auto-generated method stub
        mPaint.setStyle(Paint.Style.FILL);
        if (isOverdue) {
            mPaint.setColor(mUnBeSelectedTextBgColor);
        } else {
            mPaint.setColor(mNormalTextBgColor);
        }

        if (isToday) {
            mPaint.setColor(0xff0a9ee0);
            mPaint.setStyle(Paint.Style.FILL);
        }

        if (isTheDay) {
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(mBeSelectedTextBgColor);
        }
        canvas.drawCircle(x + mBgRadius / 2 - offset * 5 / 12, y - mBgRadius / 4 - offset / 6, mBgRadius, mPaint);
        if (!isOverdue) {
            mPaint.setColor(0xffeaeaea);
            mPaint.setStyle(Paint.Style.STROKE);
            canvas.drawCircle(x + mBgRadius / 2 - offset * 5 / 12, y - mBgRadius / 4 - offset / 6, mBgRadius + 1,
                    mPaint);
        }
    }

    private boolean isOverdue(int theDay, int thMonth, int thYear) {

        if (theDay < 1) {
            return true;
        }

        // ---这里判断是否过去的日期
        int currentDate = CalendarUtils.getToday();
        int currentMonth = CalendarUtils.getTomonth();
        int currentYear = CalendarUtils.getToyear();
        boolean sign = true;

        if (CalendarUtils.getGapCount(thYear, thMonth, theDay, currentYear, currentMonth, currentDate) >= 0) {
            sign = false;
        }

        // if (year < currentYear) {
        // sign = true;
        // } else if (year == currentYear) {
        // if (month < currentMonth) {
        // sign = true;
        // } else if (month == currentMonth) {
        // if (theDay < currentDate) {
        // sign = true;
        // }
        // } else {
        // sign = false;
        // }
        // } else {
        // sign = false;
        // }

        if (isUsableDay(thYear, thMonth, theDay)) {
            sign = false;
        } else {
            sign = true;
        }

        // // ---这里需要判断日期是否可用
        // if (needSeven && !sign) {
        // if (sevenDay != null && sevenDay.length > 0) {
        // int i;
        // for (i = 0; i < 7; i++) {
        // String[] split = sevenDay[i].split("-");
        // if (split.length > 0) {
        // int month = Integer.parseInt(split[1]);
        // int day = Integer.parseInt(split[2]);
        // int year = Integer.parseInt(split[0]);
        // if (thYear == year && thMonth == month && theDay == day) {
        //
        // break;
        // }
        // }
        // }
        // if (i == 7) {
        // sign = true;
        // } else {
        // sign = false;
        // }
        // }
        // }

        return sign;
    }

    private boolean isUsableDay(int thYear, int thMonth, int theDay) {
        // TODO Auto-generated method stub
        int minyear = Integer.parseInt(startDay.subSequence(0, 4).toString());
        int minmonth = Integer.parseInt(startDay.substring(4, 6).toString());
        int minday = Integer.parseInt(startDay.substring(6, 8).toString());

        int maxyear = Integer.parseInt(endDay.subSequence(0, 4).toString());
        int maxmonth = Integer.parseInt(endDay.substring(4, 6).toString());
        int maxday = Integer.parseInt(endDay.substring(6, 8).toString());

        int min = CalendarUtils.getGapCount(thYear, thMonth, theDay, minyear, minmonth, minday);
        int max = CalendarUtils.getGapCount(thYear, thMonth, theDay, maxyear, maxmonth, maxday);

        if (CalendarUtils.getGapCount(thYear, thMonth, theDay, minyear, minmonth, minday) >= 0
                && CalendarUtils.getGapCount(thYear, thMonth, theDay, maxyear, maxmonth, maxday) <= 0) {
            return true;
        }

        return false;
    }

    private boolean isToday(int theday) {
        int currentDate = CalendarUtils.getToday();
        int currentMonth = CalendarUtils.getTomonth();
        int currentYear = CalendarUtils.getToyear();
        boolean sign = false;
        if (year == currentYear) {
            if (month == currentMonth) {
                if (theday == currentDate) {
                    sign = true;
                }
            }
        }
        return sign;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        event.getAction();

        // 获取事件的位置
        float touchX = event.getX();
        float touchY = event.getY();

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:

                if (touchY > yInterval) {

                    // 以下是对日历的事件处理
                    int theX = (int) ((touchX) / xInterval);// 获取第几列
                    int theY = (int) ((touchY - yInterval / 4) / yInterval);// 获取第几行
                    Log.d("click ds", "第" + theX + "列");
                    Log.d("click ds", "第" + theY + "行");
                    if (theY < 1) {
                        theY = 1;
                    }
                    // 得到是哪一天
                    int num = (theY - 1) * 7 + theX - weekOfFirstDay;
                    int day;
                    if (num < 0 || num > allDays.length - 1) {
                        num = -2;
                        day = 0;
                    } else {
                        day = allDays[num];
                    }


                    invalidate();
                    int gapCount = CalendarUtils.getGapCount(year, month, day, NowYear, NowMonth, NowDay);
                    if (!isOverdue(day, month, year)) {
                        selectedDay = day;
                        selectedMonth = month;
                        selectedYear = year;
                        if (onChooseListener != null) {
                            onChooseListener.onSingleChoose(year, month, day, false, gapCount, theX + 1);

                        }
                        if (mHandler != null) {
                            mHandler.sendEmptyMessageDelayed(0, 100);
                        }
                    } else {
                        if (onChooseListener != null) {
                            onChooseListener.onSingleChoose(year, month, day, true, gapCount, theX + 1);
                        }

                    }

                }


        }
        return true;
    }

    /**
     * 切换成下个月
     */
    public void nextMonth() {
        CalendarUtils.nextMonth();
        int tyear = CalendarUtils.getCurrentYear();
        int tmonth = CalendarUtils.getCurrentMonth();
        int tday = CalendarUtils.getCurrentDate();
        int tdayOfWeek = CalendarUtils.getCurrentFirstWeekdayOfMoth();
        int tmaxDayNum = CalendarUtils.getCurrentMaxNumOfMonth();
        setYear(tyear);
        setMonth(tmonth);
        setAllDays(tmaxDayNum);
        setWeekOfFirstDay(tdayOfWeek);
        if (NowDay == tday && NowMonth == tmonth && NowYear == tyear) {
            setToday(tday);
        } else {
            setToday(0);
        }
        invalidate();
    }

    public void resetYearAndMonth(int year, int month) {
        CalendarUtils.reset();
        int tyear = CalendarUtils.getCurrentYear();
        int tmonth = CalendarUtils.getCurrentMonth();
        int tday = CalendarUtils.getCurrentDate();
        int tdayOfWeek = CalendarUtils.getCurrentFirstWeekdayOfMoth();
        int tmaxDayNum = CalendarUtils.getCurrentMaxNumOfMonth();
        setYear(tyear);
        setMonth(tmonth);
        setAllDays(tmaxDayNum);
        setWeekOfFirstDay(tdayOfWeek);
        if (NowDay == tday && NowMonth == tmonth && NowYear == tyear) {
            setToday(tday);
        } else {
            setToday(0);
        }
        invalidate();
    }

    /**
     * 切换成上个月
     */
    public void preMonth() {
        CalendarUtils.preMonth();
        int tyear = CalendarUtils.getCurrentYear();
        int tmonth = CalendarUtils.getCurrentMonth();
        int tday = CalendarUtils.getCurrentDate();
        int tdayOfWeek = CalendarUtils.getCurrentFirstWeekdayOfMoth();
        int tmaxDayNum = CalendarUtils.getCurrentMaxNumOfMonth();
        setYear(tyear);
        setMonth(tmonth);
        setAllDays(tmaxDayNum);
        setWeekOfFirstDay(tdayOfWeek);
        if (NowDay == tday && NowMonth == tmonth && NowYear == tyear) {
            setToday(tday);
        } else {
            setToday(0);
        }
        invalidate();
    }

    public interface OnChooseListener {
        void onSingleChoose(int year, int month, int day, boolean overdue, int gapToday, int weekday);
    }

    private OnChooseListener onChooseListener;

    public OnChooseListener getOnChooseListener() {
        return onChooseListener;
    }

    public void setOnChooseListener(OnChooseListener onChooseListener) {
        this.onChooseListener = onChooseListener;
    }

}
