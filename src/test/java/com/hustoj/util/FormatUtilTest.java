package com.hustoj.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FormatUtilTest {

    @Test
    void testFormatTimeMs() {
        assertEquals("0 MS", FormatUtil.formatTimeMs(0));
        assertEquals("999 MS", FormatUtil.formatTimeMs(999));
        assertEquals("1.00 S", FormatUtil.formatTimeMs(1000));
        assertEquals("1.50 S", FormatUtil.formatTimeMs(1500));
        assertEquals("2.00 S", FormatUtil.formatTimeMs(2000));
    }

    @Test
    void testFormatMemory() {
        assertEquals("0 KB", FormatUtil.formatMemory(0));
        assertEquals("1 KB", FormatUtil.formatMemory(1));
        assertEquals("1023 KB", FormatUtil.formatMemory(1023));
        assertEquals("1.0 MB", FormatUtil.formatMemory(1024));
        assertEquals("1.5 MB", FormatUtil.formatMemory(1536));
        assertEquals("2.0 MB", FormatUtil.formatMemory(2048));
    }

    @Test
    void testFormatTimeLength() {
        assertEquals("59秒", FormatUtil.formatTimeLength(59));
        assertEquals("1分", FormatUtil.formatTimeLength(60));
        assertEquals("1分1秒", FormatUtil.formatTimeLength(61));
        assertEquals("1小时", FormatUtil.formatTimeLength(3600));
        assertEquals("1小时1分", FormatUtil.formatTimeLength(3660));
        assertEquals("1小时1分1秒", FormatUtil.formatTimeLength(3661));
        assertEquals("1天", FormatUtil.formatTimeLength(86400));
        assertEquals("1天1小时", FormatUtil.formatTimeLength(90000));
    }

    @Test
    void testSec2str() {
        assertEquals("00:00:00", FormatUtil.sec2str(0));
        assertEquals("00:01:00", FormatUtil.sec2str(60));
        assertEquals("01:00:00", FormatUtil.sec2str(3600));
        assertEquals("01:30:45", FormatUtil.sec2str(5445));
    }
}
