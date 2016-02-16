package com.example.lucky_xiao.clock;
/**
 *
 * 停止更新原因：
 *      新版本android停止了对updatePeriodMillis属性的支持，也就是不会周期的发送更新广播,只有在创建时才会发送更新广播。
 * 而在broadcastreceiver中创建线程的话，由于broadcastreceiver的生命周期很短，所以线程会被杀掉。
 * 解决方法:
 *      1.启动一个高优先级的service，设置定时器，周期性发送broadcast，而在addwidget中重写OnReceive方法来更新组件.
 *      (无效，清理后台时还是会把这个service杀掉)
 *      2.接收系统发出的时间改变的广播。
 *      (不好，一分钟才发送一次，无法实现每秒更新的要求.)
 *      3.当时间停住时，用户手动点开clock应用，并点击启动，手动唤醒service.
 *      (勉强可以)
 */
import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.lucky_xiao.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class AppWidget extends AppWidgetProvider {


    int digits[]={R.drawable.n0,R.drawable.n1,R.drawable.n2,R.drawable.n3,R.drawable.n4,R.drawable.n5,R.drawable.n6,R.drawable.n7,
            R.drawable.n8,R.drawable.n9,
    };
    int id[]={R.id.im1,R.id.im2,R.id.im3,R.id.im4,R.id.im5,R.id.im6,};
    AppWidgetManager am;
    @Override
    public void onUpdate(Context context, final AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        am=appWidgetManager;
        RemoteViews rv=new RemoteViews(context.getPackageName(),R.layout.widget);
        ComponentName componentName=new ComponentName(context,AppWidget.class);

        SimpleDateFormat df=new SimpleDateFormat("HHmmss");
        String time=df.format(new Date());
        for(int i=0;i<6;i++)
        {
            int num=time.charAt(i)-48;
            rv.setImageViewResource(id[i],digits[num]);
        }
        appWidgetManager.updateAppWidget(componentName, rv);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        String action=intent.getAction();
        if(action.equals("com.example.lucky_xiao.braocastdemo.action.Alert"))
        {
            RemoteViews rv=new RemoteViews(context.getPackageName(),R.layout.widget);
            ComponentName componentName=new ComponentName(context,AppWidget.class);

            SimpleDateFormat df=new SimpleDateFormat("HHmmss");
            String time=df.format(new Date());
            for(int i=0;i<6;i++)
            {
                int num=time.charAt(i)-48;
                rv.setImageViewResource(id[i],digits[num]);
            }
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            appWidgetManager.updateAppWidget(componentName,rv);
        }

    }
}
