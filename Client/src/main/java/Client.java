import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static int authorizedCount = 0;

    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port = 8089;
        try (Socket clientSocket = new Socket(host, port);
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            Scanner scanner = new Scanner(System.in);

            int answer = chooseStep(out, in, scanner);

            switch (answer) {
                case 2 -> authorize(out, in, scanner);
                case 1 -> {
                    register(out, in, scanner);
                    authorize(out, in, scanner);
                }
            }
            System.out.println("done");


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void authorize(PrintWriter out, BufferedReader in, Scanner scanner) throws IOException {
        String temp;
        try {
            temp = in.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(temp);
        while (true) {
            String log = scanner.nextLine();
            out.println(log);
            String password = scanner.nextLine();
            out.println(password);
            temp = in.readLine();
            System.out.println(temp);
            if (temp.equals("authorized")) {
                break;
            } else {
                authorizedCount++;
                System.out.printf("You have %d more attempt. " +
                        "Enter login, password\n", 3 - authorizedCount);
                if (authorizedCount == 3) {
                    throw new RuntimeException("authorize error");
                }
            }
        }
    }

    private static void register(PrintWriter out, BufferedReader in, Scanner scanner) throws IOException {
        System.out.println(in.readLine());
        out.println(scanner.nextLine());
        System.out.println(in.readLine());
        out.println(scanner.nextLine());
        System.out.println(in.readLine());
        out.println(scanner.nextLine());
        System.out.println(in.readLine());
    }

    private static int chooseStep(PrintWriter out, BufferedReader in, Scanner scanner) throws IOException {
        String temp;
        temp = in.readLine();
        System.out.println(temp);
        String answer = scanner.nextLine();
        out.println(answer);
        return Integer.parseInt(answer);
    }
}
