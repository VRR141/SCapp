import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static final int port = 8089;
    private static Authentication authentication;

    public static void main(String[] args) {
        System.out.println("Server started. Port: " + port);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while(true) {
                try (Socket client = serverSocket.accept();
                     PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()))) {

                    System.out.println("New connection");

                    initAuth();

                    int answer = chooseStep(out, in);


                    /*
                    Login Password for authorize
                    log2 123456
                    log0 qwerty
                    log1 qazwsx
                     */
                    switch (answer) {
                        case 2 -> {
                            sendMsg(out, "Enter login and password");
                            boolean flag = false;
                            while (!flag) {
                                flag = authorize(in, out);
                            }
                            sendMsg(out, "authorized");
                        }
                        case 1 -> {
                            registration(out, in);
                            sendMsg(out, "Enter login and password");
                            boolean flag2 = false;
                            while (!flag2) {
                                flag2 = authorize(in, out);
                            }
                            sendMsg(out, "authorized");
                        }
                        default -> System.out.println("something wrong");
                    }
                    System.out.println("done");

                } catch (IOException e){
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e){
            throw new RuntimeException();
        }
    }

    public static boolean authorize(BufferedReader in, PrintWriter out){
        boolean flag = false;
        while (!flag) {
            System.out.print("Log: ");
            String log = readMsg(in);
            System.out.print("Password: ");
            String pswrd = readMsg(in);
            if (authentication.authorized(log, pswrd)) {
                flag = true;
            } else {
                sendMsg(out, "not authorized try again");
                System.out.println("authorize error");
            }
        }
        return flag;
    }

    public static String readMsg(BufferedReader in){
        String str = null;
        try {
            str = in.readLine();
            System.out.println(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static void sendMsg(PrintWriter out, String msg) {
        out.println(msg);
    }

    private static void initAuth(){
       authentication = new Authentication();
    }

    private static void registration(PrintWriter out, BufferedReader in){
        sendMsg(out, "Enter name:");
        String name = readMsg(in);
        sendMsg(out, "Enter log:");
        String userLog = readMsg(in);
        sendMsg(out, "Enter pass:");
        String userPswrd = readMsg(in);
        User user = new User(name, userLog, userPswrd);
        authentication.addNewLogPass(userLog, userPswrd);
        sendMsg(out, user.toString());
    }
    private static int chooseStep(PrintWriter out, BufferedReader in){
        sendMsg(out, "choose your STEP: 1 for registration, 2 for authorize");
        return Integer.parseInt(readMsg(in));
    }
}
