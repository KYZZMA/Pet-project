import java.util.ArrayList;
import java.util.Random;

public class Bank {
    private String name;
    private ArrayList<User> users;
    private ArrayList<Account> accounts;

    public Bank(String name) {

        this.name = name;
        this.users = new ArrayList<User>();
        this.accounts = new ArrayList<Account>();
    }

    public String getNewUserID() {
        String id;
        Random rng = new Random();
        int len = 6;
        boolean nonUniq;

      //  поиск уникаль ID
        do {
            id = "";
            for (int i = 0; i < len; i++) {
                id += ((Integer)rng.nextInt(10)).toString();
            }
            // проверка на уникальность
            nonUniq = false;
            for (User u : this.users){
                if (id.compareTo(u.getId())==0){
                    nonUniq = true;
                    break;
                }
            }


        }while (nonUniq);

    return id;
    }
    public String getNewAccountID(){
        String id;
        Random rng = new Random();
        int len = 10;
        boolean nonUniq;

        //поиск уникаль ID
        do {
            id = "";
            for (int i = 0; i < len; i++) {
                id += ((Integer)rng.nextInt(10)).toString();
            }
            // проверка на уникальность
            nonUniq = false;
            for (Account a: this.accounts){
                if (id.compareTo(a.getId())==0){
                    nonUniq = true;
                    break;
                }
            }


        }while (nonUniq);

        return id;

    }
   public void addAccount(Account ans){
       this.accounts.add(ans);
  }
    public User addUser(String firstName, String lastName, String pin ){
        // создаем нового пользователя и добавляем в list
        User newUser = new User(firstName, lastName, pin, this);
        this.users.add(newUser);
        // создаме сохраненный аккаунт для пользователя
        Account newAccount = new Account("Saving", newUser,this);
        newUser.addAccount(newAccount);
        this.addAccount(newAccount);

        return newUser;
    }
    public User userLogin(String userID, String pin){
        for (User u : this.users){
            if (u.getId().compareTo(userID) == 0 && u.validatePin(pin)){
                return u;
            }
        }
        return null;
    }

    public String getName() {
        return this.name;
    }
}
