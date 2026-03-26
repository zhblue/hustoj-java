package com.hustoj.util;

import jakarta.servlet.http.HttpServletRequest;

public class IpUtil {
    public static String getClientIp(HttpServletRequest request) {
        String ip = null;
        
        // X-Forwarded-For
        String xff = request.getHeader("X-Forwarded-For");
        if (xff != null && !xff.isEmpty() && !"unknown".equalsIgnoreCase(xff)) {
            int commaIdx = xff.indexOf(',');
            if (commaIdx > 0) {
                ip = xff.substring(0, commaIdx).trim();
            } else {
                ip = xff.trim();
            }
        }
        
        // X-Real-IP
        if (ip == null || "unknown".equalsIgnoreCase(ip)) {
            String xri = request.getHeader("X-Real-IP");
            if (xri != null && !xri.isEmpty() && !"unknown".equalsIgnoreCase(xri)) {
                ip = xri.trim();
            }
        }
        
        // REMOTE_ADDR
        if (ip == null || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        
        // Validate IP format
        if (ip != null && !isValidIp(ip)) {
            ip = "0.0.0.0";
        }
        
        return ip;
    }

    private static boolean isValidIp(String ip) {
        if (ip == null) return false;
        String[] parts = ip.split("\\.");
        if (parts.length != 4) return false;
        try {
            for (String p : parts) {
                int val = Integer.parseInt(p);
                if (val < 0 || val > 255) return false;
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static long ipToLong(String ip) {
        if (ip == null || ip.isEmpty()) return 0;
        String[] parts = ip.split("\\.");
        if (parts.length != 4) return 0;
        return (Long.parseLong(parts[0]) << 24)
             | (Long.parseLong(parts[1]) << 16)
             | (Long.parseLong(parts[2]) << 8)
             | Long.parseLong(parts[3]);
    }

    public static boolean isIpInSubnet(String ip, String subnet) {
        if (ip == null || subnet == null) return false;
        if (!subnet.contains("/")) return ip.equals(subnet);
        
        String[] parts = subnet.split("/");
        String subnetIp = parts[0];
        int mask = Integer.parseInt(parts[1]);
        
        long subnetLong = ipToLong(subnetIp);
        long ipLong = ipToLong(ip);
        long maskLong = (0xFFFFFFFFL << (32 - mask)) & 0xFFFFFFFFL;
        
        return (ipLong & maskLong) == (subnetLong & maskLong);
    }
}
