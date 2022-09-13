public class User {

    private String name;
    private String log;
    private String pswrd;
    private int id;
    private static int counter;

    public User(String name, String log, String pswrd){
        id = counter;
        this.name = name;
        this.log = log;
        this.pswrd = pswrd;
        counter++;
    }

    @Override
    public String toString() {
        return "User{" +
                ", id=" + id + '\'' +
                "name='" + name + '\'' +
                ", log='" + log + '\'' +
                ", pswrd='" + pswrd +
                '}';
    }
}
