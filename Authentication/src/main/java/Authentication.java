import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Authentication {

    HashMap<String, char[]> logPass;

    public Authentication() {
        logPass = generateLoginPasswords();
    }

    public boolean authorized(String login, String password) {
        boolean flag = false;
        for (String log : logPass.keySet()) {
            if (log.equals(login) && Arrays.equals(password.toCharArray(), logPass.get(log))) {
                flag = true;
            }
        }
        return flag;
    }

    public void addNewLogPass(String log, String pass){
        logPass.put(log, pass.toCharArray());
    }

    private HashMap<String, char[]> generateLoginPasswords() {
        char[] pswrd0 = {'q', 'w', 'e', 'r', 't', 'y'};
        char[] pswrd1 = {'q', 'a', 'z', 'w', 's', 'x'};
        char[] pswrd2 = {'1', '2', '3', '4', '5', '6'};
        List<char[]> pswrds = new ArrayList<>();
        pswrds.add(pswrd0);
        pswrds.add(pswrd1);
        pswrds.add(pswrd2);
        String[] logins = {"log0", "log1", "log2"};
        HashMap<String, char[]> result = new HashMap<>();
        for (int i = 0; i < logins.length; i++) {
            result.put(logins[i], pswrds.get(i));
        }
        return result;
    }
}
