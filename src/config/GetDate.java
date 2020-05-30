package config;
// Gustavo Henrique de Souza - 20052 \\
// OOP - Dorset College \\

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class GetDate {

    public String getDate() {
        String dateOfTransaction;

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyy");
        Date date = new Date();
        dateOfTransaction = dateFormat.format(date);

        return dateOfTransaction;
    }

    public int getMonth() {
        Date date = new Date();
        int month = 0;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        month = (calendar.get(Calendar.MONTH) + 1);

        return month;
    }

    public int getYear() {
        Date date = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return (calendar.get(Calendar.YEAR));

    }

    public int getDay() {

        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return (calendar.get(Calendar.DAY_OF_MONTH));

    }

    public int getMonth(Date date) {
        int month = 0;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        month = (calendar.get(Calendar.MONTH) + 1);

        return month;
    }

    public int getYear(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return (calendar.get(Calendar.YEAR));

    }

    public int getDay(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return (calendar.get(Calendar.DAY_OF_MONTH));

    }

    public String getMonthName(Integer month) {

        String monthString = new DateFormatSymbols().getMonths()[month - 1];

        return monthString;
    }

    public String getTimeStamp() {

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();

        return formatter.format(date);
    }

    public Date stringToDate(String str_date, String pattern) {

        try {
            DateFormat format = new SimpleDateFormat(pattern, Locale.ENGLISH);
            Date date = format.parse(str_date);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
