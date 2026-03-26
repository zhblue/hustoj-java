package com.hustoj.util;

public class FormatUtil {

    public static String formatTimeLength(int seconds) {
        if (seconds < 60) {
            return seconds + "秒";
        }
        StringBuilder sb = new StringBuilder();
        int minute = seconds / 60;
        int second = seconds % 60;
        if (minute >= 60) {
            int hour = minute / 60;
            minute = minute % 60;
            if (hour >= 24) {
                int day = hour / 24;
                hour = hour % 24;
                sb.append(day).append("天");
            }
            if (hour > 0) sb.append(hour).append("小时");
        }
        if (minute > 0) sb.append(minute).append("分");
        if (second > 0) sb.append(second).append("秒");
        return sb.toString();
    }

    public static String sec2str(int sec) {
        return String.format("%02d:%02d:%02d", sec / 3600, (sec % 3600) / 60, sec % 60);
    }

    public static String formatMemory(int memoryKb) {
        if (memoryKb < 1024) {
            return memoryKb + " KB";
        }
        return String.format("%.1f MB", memoryKb / 1024.0);
    }

    public static String formatTimeMs(int timeMs) {
        if (timeMs < 1000) {
            return timeMs + " MS";
        }
        return String.format("%.2f S", timeMs / 1000.0);
    }
}
