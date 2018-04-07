/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avinash.example;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 *
 * @author hp
 */
public class DateUtil {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
    public static String returnCurrDate()   {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }

    public static String returnFormattedDate(int date, int month, int year)   {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, date);
        return dateFormat.format(cal.getTime());
    }

    public static int returnDayOfWeek(String inputDate)   {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        Calendar cal = Calendar.getInstance();
        int dayOfWeek = 0;
        try {
            Date date = dateFormat.parse(inputDate);
            cal.setTime(date);
            dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
            System.out.println("DateUtil : dayOfWeek : " + dayOfWeek);
        }
        catch (ParseException ex)    {
            ex.printStackTrace();
        }
        return dayOfWeek;
    }

    public static String returnDayOfWeekStr(String inputDate)   {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        Calendar cal = Calendar.getInstance();
        int dayOfWeek = 0;
        try {
            Date date = dateFormat.parse(inputDate);
            cal.setTime(date);
            dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        }
        catch (Exception ex)    {
            ex.printStackTrace();
        }
        switch (dayOfWeek)  {
            case 1: return "Sun";
            case 2: return "Mon";
            case 3: return "Tue";
            case 4: return "Wed";
            case 5: return "Thu";
            case 6: return "Fri";
            case 7: return "Sat";
        }
        return "";
    }

    public static String returnCurrentDayOfWeek()   {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        Calendar cal = Calendar.getInstance();
        int dayOfWeek = 0;
        try {
            Date date = dateFormat.parse(dateFormat.format(cal.getTime()));
            cal.setTime(date);
            dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        }
        catch (ParseException ex)    {
            ex.printStackTrace();
        }
        return retDayOfWeekInStr(dayOfWeek);
    }

    public static String returnTimeFromMillis(long punchTime)   {
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(punchTime);
        return dateFormat.format(cal.getTime());
    }

    public static int returnCurrDateId()   {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Calendar cal = Calendar.getInstance();
        String currentDate = dateFormat.format(cal.getTime());
        return Integer.parseInt(currentDate.replaceAll("-", ""));
    }

    public static int returnDateId(Calendar cal)   {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        String currentDate = dateFormat.format(cal.getTime());
//        Log.e("AppUtil", "returnDateId > currentDate : " + currentDate);
        return Integer.parseInt(currentDate.replaceAll("-", ""));
    }

    public static String retDayOfWeekInStr(int input)  {
        switch (input)  {
            case 1: return "Sun";
            case 2: return "Mon";
            case 3: return "Tue";
            case 4: return "Wed";
            case 5: return "Thu";
            case 6: return "Fri";
            case 7: return "Sat";
        }
        return "";
    }

    public static Map<Integer, String> returnDateIds(int startDateId, int endDateId)  {
        Map<Integer, String> dateIdsArr = new HashMap<>();
        int startYear = startDateId/10000;
        int endYear = endDateId/10000;

        for(int k=startYear; k<=endYear; k++){
            for(int i=1; i<=12; i++)  {
                for(int j=1; j<=31; j++)    {
                    String generatedDate = i + "-" + j + "-" + k;
                    if(isDateValid(generatedDate)) {
                        String valMon = j > 9 ? "" : "0";
                        String valDay = i > 9 ? "" : "0";
                        int intGenerateDate = Integer.parseInt(String.valueOf(k) +
                                (valDay + String.valueOf(i)) + (valMon + String.valueOf(j)));
                        if(intGenerateDate >= startDateId && intGenerateDate <= endDateId) {
                            generatedDate = returnFormattedDate(j, i-1, k);
                            dateIdsArr.put(intGenerateDate, generatedDate);
                        }
                    }
                }
            }
        }
        return dateIdsArr;
    }

    private static boolean isDateValid(String date) {
        DateFormat df = new SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH);
        try {
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static TimeDifferenceData calculateTimeDiff(int shiftDuration, long inTime, long outTime) {
        TimeDifferenceData timeDiffData = new TimeDifferenceData();

        long diffTime = outTime - inTime;
        int diffTimeMin = (int) (diffTime / (60 * 1000));
        int diffHr = (diffTimeMin / 60);
        int diffMin = diffTimeMin % 60;
        String diffTimeStr = diffHr > 1 ? diffHr + " hours " : diffHr > 0 ? diffHr + " hour " : "";
        diffTimeStr += diffMin + " mins";
        timeDiffData.setTimeDurationStr(diffTimeStr);

        int extraShortTime = diffTimeMin - shiftDuration;
        timeDiffData.setExtraShortStr(extraShortTime < 0 ? "Short Time : " : "Extra Time : ");

        int extraShortHr = (extraShortTime / 60);
        int extraShortMin = extraShortTime % 60;
        int modExtraShortHr = Math.abs(extraShortHr);
        String extraShortValStr = modExtraShortHr > 1 ? modExtraShortHr + " hours " :
                modExtraShortHr > 0 ? modExtraShortHr + " hour " : "";
        extraShortValStr += Math.abs(extraShortMin) + " mins";
        timeDiffData.setExtraShortValStr(extraShortValStr);
        return timeDiffData;
    }

    public static TimeDifferenceData calculateTimeDiff(long diffTimeVal) {
        TimeDifferenceData timeDiffData = new TimeDifferenceData();

        int diffTimeMin = (int) (diffTimeVal / (60 * 1000));
        int diffHr = (diffTimeMin / 60);
        int diffMin = diffTimeMin % 60;
        String diffTimeStr = diffHr > 1 ? diffHr + " hours " : diffHr > 0 ? " hour " : "";
        diffTimeStr += diffMin + " mins";
        timeDiffData.setTimeDurationStr(diffTimeStr);

//        int shiftDuration = dbHelper.returnConfigData().getShiftDuration();
//        int extraShortTime = diffTimeMin - shiftDuration;
        timeDiffData.setExtraShortStr(diffTimeMin < 0 ? "Short Time : " : "Extra Time : ");

        int extraShortHr = (diffTimeMin / 60);
        int extraShortMin = diffTimeMin % 60;
        int modExtraShortHr = Math.abs(extraShortHr);
        String extraShortValStr = modExtraShortHr > 1 ? modExtraShortHr + " hours " :
                modExtraShortHr > 0 ? modExtraShortHr + " hour " : "";
        extraShortValStr += Math.abs(extraShortMin) + " mins";
        timeDiffData.setExtraShortValStr(extraShortValStr);
        return timeDiffData;
    }
}
