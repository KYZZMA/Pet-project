import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class User {
    private String firstName;
    private String lastName;
    private String id;
    private byte[] pinHash;

    private ArrayList<Account> accounts;

    public User(String firstName, String lastName,
                String pin, Bank theBank){
        // вводим фио пользователей
        this.firstName = firstName;
        this.lastName = lastName;
        // сохраняем шифрованный пин
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.pinHash = md.digest(pin.getBytes());
        } catch (NoSuchAlgorithmException e) {
            System.err.println("ошибка, хеша");
            System.exit(1);
            throw new RuntimeException(e);

        }

        // получение нового уникального id пользователя
        this.id = theBank.getNewUserID();

        //создаме список аккаунтов
         this.accounts = new ArrayList<Account>();
         // выводим информацию
        System.out.printf("Новый пользователь %s, %s с ID %S создан.\n ",lastName,firstName, this.id);
    }
    public void addAccount(Account ans){
        this.accounts.add(ans);
    }
    public String getId(){
        return  this.id;
    }

    public  boolean validatePin(String aPin){

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(aPin.getBytes()),this.pinHash);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("ошибка, ошибка не алгоритма");
            System.exit(1);
            throw new RuntimeException(e);
        }
        //return false
    }
    public String getFirstName(){
        return this.firstName;
    }
    public void printAccountsSumm(){
        System.out.printf("\n\n%s, информация по счету:\n", this.firstName);
        for (int a = 0; a< this.accounts.size(); a++){
            System.out.printf(" %d) %s\n", a+1 ,this.accounts.get(a).getSummLine());
        }
        System.out.println();
    }
    public int numAccount(){
        return this.accounts.size();
    }
    public void printAccTransHistory(int accI){
      this.accounts.get(accI).printTransHistory();
    }
    public double getAccBalance(int accI){
        return this.accounts.get(accI).getBalance();
    }
    public String getAccId(int acI){
        return this.accounts.get(acI).getId();
    }
    public void  addAccTransaction(int acI, double amount, String memo){
        this.accounts.get(acI).addTransection(amount, memo);

    }


}
