package com.gmail.ZiomuuSs.Utils;

public class TimeUtilities {
  public static String formatTime(long from) {
    long time = System.currentTimeMillis()-from;
    if (time < 60) {
      return format(time, " sekundê", " sekundy", " sekund");
    } else {
      long minutes = (long) Math.floor((double) time/60);
      time -= minutes*60;
      return new StringBuilder(format(minutes, " minutê", " minuty", " minut")).append((time != 0 ? format(time, " sekundê", " sekundy", " sekund") : "")).toString();
    }
  }
  
  private static String format(Long time, String one, String two, String three) {
    if (time == 1)
      return new StringBuilder(Long.toString(time)).append(one).toString();
    else if (time > 1 && time < 5)
      return new StringBuilder(Long.toString(time)).append(two).toString();
    else
      return new StringBuilder(Long.toString(time)).append(three).toString();
  }
}
