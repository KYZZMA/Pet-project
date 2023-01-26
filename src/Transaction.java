import java.util.Date;

public class Transaction {
    private double amount;
    private Date timestamp;
    private String memory;
    private Account inAccount;

    public Transaction(double amount, Account inAccount){
        this.amount = amount;
        this.inAccount = inAccount;
        this.timestamp = new Date();
        this.memory = "";
    }

    public Transaction(double amount, String memory,Account inAccount){

        this(amount, inAccount);
        this.memory = memory;

    }
    public double getAmount(){
        return this.amount;
    }
    public String getSummLine(){
        if (this.amount>=0){
            return  String.format("%s : $%.02f : %s",
                    this.timestamp.toString(), this.amount, this.memory);
        }
        else {
            return  String.format("%s : $(%.02f) : %s",
                    this.timestamp.toString(), -this.amount, this.memory);
        }
    }

}
