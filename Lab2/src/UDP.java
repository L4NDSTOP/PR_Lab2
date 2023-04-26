import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDP {
    public static void main(String[] args) throws IOException {
        InetAddress broadcastAddress = InetAddress.getByName("255.255.255.255");
        int port = 8888;
        DatagramSocket socket = new DatagramSocket();
        socket.setBroadcast(true);

        Thread receiveThread = new Thread(() -> {
            byte[] buffer = new byte[1000];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            try {
                while (true) {
                    socket.receive(packet);
                    String message = new String(packet.getData(), 0, packet.getLength());
                    System.out.println(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        receiveThread.start();

        Thread sendThread = new Thread(() -> {
            try {
                BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
                while (true) {
                    String message = input.readLine();
                    DatagramPacket packet = new DatagramPacket(message.getBytes(), message.length(), broadcastAddress, port);
                    socket.send(packet);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        sendThread.start();
    }
}