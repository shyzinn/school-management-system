package config;

import java.text.ParseException;
import java.time.YearMonth;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class CalculateDays {
// Gustavo Henrique de Souza - 20052 \\
// OOP - Dorset College \\

    public long calculateDaysUntil(String issued_date) {

        SimpleDateFormat date_format = new SimpleDateFormat("dd-MM-yyyy");

        String current_date = new GetDate().getDate();

        try {
            Date date1 = date_format.parse(issued_date);
            Date date2 = date_format.parse(current_date);
            long diff = (date2.getTime() - date1.getTime());
            long days = -(long) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            if (days < -1) {
                return 0;
            } else {
                return days+1;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public int calculateDaysInMonth(int month, int year) {

        YearMonth year_month_object = YearMonth.of(1999, 2);
        int days_in_month = year_month_object.lengthOfMonth(); // 28

        return days_in_month;
    }

}