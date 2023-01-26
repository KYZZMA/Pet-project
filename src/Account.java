import java.util.ArrayList;

public class Account {
    private String name;
    private String id;
    private User holder;
    private ArrayList<Transaction> transactions;

    public Account(String name, User holder,
                 Bank theBank){
        // указываем имя пользователя и держателя
        this.name = name;
        this.holder = holder;

        // получаем новый аккаунт ID
        this.id = theBank.getNewAccountID();

        // получыаем транзакции
        this.transactions = new ArrayList<Transaction>();

    }
    public String getId(){
        return  this.id;
    }
    public String getSummLine(){
        // получаем баланс аккаунта
        double balabce = this.getBalance();

        // проверяем остаток на балансе
        if (balabce >= 0){
            return String.format("%s : $%.02f : %s", this.id, balabce, this.name);
        }else {
            return String.format("%s : $(%.02f) : %s", this.id, balabce, this.name);
        }

    }
    public double getBalance(){
        double balance =0;
        for (Transaction t : this.transactions){
            balance += t.getAmount();
        }
        return  balance;
    }
    public void printTransHistory(){
        System.out.printf("\nИстория операций аккаунта %s\n", this.id);
        for (int t = this.transactions.size()-1; t >= 0; t--){
            System.out.println(this.transactions.get(t).getSummLine());
        }
        System.out.println();
    }
    public  void addTransection(double amount, String memo){
        // создаем новую операцию и добавляем ее в list
    Transaction newTrans = new Transaction(amount, memo, this);
    this.transactions.add(newTrans);
    }


}
