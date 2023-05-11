import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class DNS {
    private static String dnsServer = null;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print(">> ");

            String command = scanner.nextLine();
            String[] parts = command.split(" ");

            if (parts[0].equals("resolve") && parts.length == 2) {
                String input = parts[1];

                try {
                    if (isIPAddress(input)) {
                        InetAddress address = InetAddress.getByName(input);
                        String hostname = address.getHostName();
                        System.out.println(hostname);
                    } else {
                        InetAddress[] addresses = InetAddress.getAllByName(input);
                        for (InetAddress address : addresses) {
                            System.out.println(address.getHostAddress());
                        }
                    }
                } catch (UnknownHostException e) {
                    System.out.println("Could not resolve " + input);
                }
            } else if (parts[0].equals("use") && parts[1].equals("dns") && parts.length == 3) {
                String dns = parts[2];

                if (isValidIPAddress(dns)) {
                    dnsServer = dns;
                    System.out.println("DNS server changed to " + dns);
                } else {
                    System.out.println("Invalid DNS server address");
                }
            } else {
                System.out.println("Invalid command");
            }
        }
    }

    private static boolean isIPAddress(String input) {
        return input.matches("\\d+\\.\\d+\\.\\d+\\.\\d+");
    }

    private static boolean isValidIPAddress(String input) {
        try {
            InetAddress.getByName(input);
            return true;
        } catch (UnknownHostException e) {
            return false;
        }
    }
}
