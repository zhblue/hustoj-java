package com.hustoj.util;

import com.hustoj.db.DB;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class JudgeUtil {

    public static void triggerJudge(int solutionId) {
        if (!DB.isOjUdp()) {
            return;
        }
        new Thread(() -> {
            try {
                String[] servers = DB.getOjUdpServer().split(",");
                int total = servers.length;
                int select = solutionId % total;
                String server = servers[select].trim();
                int port = DB.getOjUdpPort();

                if (server.contains(":")) {
                    String[] parts = server.split(":");
                    server = parts[0];
                    port = Integer.parseInt(parts[1]);
                }

                InetAddress address = InetAddress.getByName(server);
                String message = DB.getOjJudgeHubPath();
                if (message == null || message.isEmpty()) {
                    message = String.valueOf(solutionId);
                }

                byte[] data = message.getBytes();
                DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
                
                try (DatagramSocket socket = new DatagramSocket()) {
                    socket.send(packet);
                }
            } catch (IOException e) {
                System.err.println("Failed to trigger judge for solution " + solutionId + ": " + e.getMessage());
            }
        }).start();
    }

    public static void sendUdpMessage(String host, int port, String message) {
        try {
            InetAddress address = InetAddress.getByName(host);
            byte[] data = message.getBytes();
            DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
            try (DatagramSocket socket = new DatagramSocket()) {
                socket.send(packet);
            }
        } catch (IOException e) {
            System.err.println("Failed to send UDP message: " + e.getMessage());
        }
    }
}
