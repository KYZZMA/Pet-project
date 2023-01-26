import java.util.Scanner;

public class ATM {
    public static void main(String[] args) {
        // сканируем
        Scanner one = new Scanner(System.in);

        // банк
        Bank theBank = new Bank("Российский банк");

        // добавляем пользователя
        User aUser = theBank.addUser("Александр", "Кузьмин", "1234");

        // проверка аккаунтов
        Account newAccount = new Account("Checking", aUser, theBank);
        aUser.addAccount(newAccount);
        theBank.addAccount(newAccount);

        User curUse;
        while (true){
            // оставляем в логине успешный логин
            curUse = ATM.mainMenu(theBank, one);

            ATM.printUserMenu(curUse, one);


        }

    }
    public static User mainMenu(Bank theBank, Scanner one){
        String userID;
        String pin;
        User authUser;

        do {
            System.out.printf("\n\nДобро пожаловать в %s\n\n", theBank.getName());
            System.out.print("Введите ID пользователя: ");
            userID = one.nextLine();
            System.out.print("Введите пароль: ");
            pin = one.nextLine();

            // пробуем получить доступ к пользователю по пароль и ID
            authUser = theBank.userLogin(userID, pin);
            if (authUser == null ){
                System.out.println("Введен неверный ID пользователя или пароль. " + "Пожалуйста, попробуйте еще раз. ");
            }
        } while (authUser == null); // продолжаме искать подходящий логин
        return authUser;

    }
    public static void printUserMenu(User theUser, Scanner one){
        //
        theUser.printAccountsSumm();

        int choice;

        // меню пользователя
        do {
            System.out.printf("Здравствуте %s, какая банковская операция вас интересует?\n", theUser.getFirstName());
            System.out.println(" 1) Показать историю операций");
            System.out.println(" 2) Cнять со счета");
            System.out.println(" 3) Пополнение счета");
            System.out.println(" 4) Cделать перевод");
            System.out.println(" 5) Выйти");
            System.out.println();
            System.out.print("Введите необходимую операцию: ");
            choice = one.nextInt();

            if (choice <1 || choice >5){
                System.out.println("Вы ввели недопустимую операцию. Пожалуйста, введите операцию повторно.");
            }
        }while (choice <1 || choice >5);

        // процесс выбора
        switch (choice){
            case 1:
                ATM.showTransHistory(theUser, one);
                break;
            case 2:
                ATM.withdrawlFunds(theUser, one);
                break;
            case 3:
                ATM.depositFunds(theUser, one);
                break;
            case 4:
                ATM.transferFunds(theUser, one);
                break;
            case 5:
                one.nextLine();
                break;

        }
        // возвращаем меню если пользователь не выбрал выход
        if (choice != 5){
            ATM.printUserMenu(theUser, one);
        }
    }
    public static void showTransHistory(User theUser, Scanner one){
        int theAc;
        //проверяем историю операций
        do {
            System.out.printf("Введите номер (1-%d) аккаунта\n" + " с кем перевод вы хотите увидеть: ", theUser.numAccount());
            theAc = one.nextInt()-1;
            if (theAc <0 || theAc >= theUser.numAccount()){
                System.out.println("Неверный аккаунт. Пожалуйста, попробуйте ввести еще раз.");
            }
        }while (theAc <0 || theAc >= theUser.numAccount());

        //выводим транзакции из истории
        theUser.printAccTransHistory(theAc);
    }

    public static void transferFunds(User theUser, Scanner one){

        int fromAc;
        int toAcc;
        double amount;
        double accBal;

        do {
            System.out.printf("Введите номер (1-%d) аккаунта\n" + " от кого перевод: ", theUser.numAccount());
            fromAc = one.nextInt()-1;
            if (fromAc < 0|| fromAc >= theUser.numAccount()){
                System.out.println("Неверный аккаунт. Пожалуйста, попробуйте ввести еще раз.");
            }
        } while (fromAc <0 || fromAc >= theUser.numAccount());
        accBal = theUser.getAccBalance(fromAc);

        // создаем перевод кому-то
        do {
            System.out.printf("Введите номер (1-%d) аккаунта\n" + " кому перевести средства: ", theUser.numAccount());
            toAcc = one.nextInt()-1;
            if (toAcc < 0|| toAcc >= theUser.numAccount()){
                System.out.println("Неверный аккаунт. Пожалуйста, попробуйте ввести еще раз.");
            }
        } while (toAcc <0 || toAcc >= theUser.numAccount());

        // создать трансфер
        do {
            System.out.printf("Введите сумму перевода (max $%.02f): $", accBal);
            amount = one.nextDouble();
            if (amount < 0){
                System.out.println("На счете недостаточно средств");
            }else if(amount > accBal){
                System.out.println("Денежных средств недостаточно для совершения операции.");
            }
        }while (amount < 0|| amount >accBal);

        //после проверки совершаем транзакцию
        theUser.addAccTransaction(fromAc, -1*amount, String.format("Перевод на аккаунт %s",
                theUser.getAccId(toAcc)));
        theUser.addAccTransaction(toAcc, amount, String.format("Перевод на аккаунт %s",
                theUser.getAccId(fromAc)));

    }

    public static void withdrawlFunds(User theUser, Scanner one){
        int fromAc;
        double amount;
        double accBal;
        String memo;

        do {
            System.out.printf("Введите номер (1-%d) аккаунта\n" + " откуда списать средства: ", theUser.numAccount());
            fromAc = one.nextInt()-1;
            if (fromAc < 0|| fromAc >= theUser.numAccount()){
                System.out.println("Неверный аккаунт. Пожалуйста, попробуйте ввести еще раз.");
            }
        } while (fromAc <0 || fromAc >= theUser.numAccount());
        accBal = theUser.getAccBalance(fromAc);
        do {
            System.out.printf("Введите сумму списания (max $%.02f): $", accBal);
            amount = one.nextDouble();
            if (amount < 0){
                System.out.println("На счете недостаточно средств. Пожалуйста, пополните баланс.");
            }else if(amount > accBal){
                System.out.println("Денежных средств недостаточно для совершения операции.");
            }
        }while (amount < 0|| amount >accBal);

        one.nextLine();
        // получаем информацию
        System.out.print("Выберите информацию: ");
        memo = one.nextLine();

        // делаем снятие
        theUser.addAccTransaction(fromAc, -1*amount, memo);
    }

    public static void depositFunds(User theUser, Scanner one){
        int toAcc;
        double amount;
        double accBal;
        String memo;

        do {
            System.out.printf("Введите номер (1-%d) аккаунта\n" + " куда внести средства: ", theUser.numAccount());
            toAcc = one.nextInt()-1;
            if (toAcc < 0|| toAcc >= theUser.numAccount()){
                System.out.println("Неверный аккаунт. Пожалуйста, попробуйте ввести еще раз.");
            }
        } while (toAcc <0 || toAcc >= theUser.numAccount());
        accBal = theUser.getAccBalance(toAcc);
        do {
            System.out.printf("Введите сумму перевода (max $%.02f): $", accBal);
            amount = one.nextDouble();
            if (amount < 0){
                System.out.println("На счете недостаточно средств");
            }
        }while (amount < 0);

        one.nextLine();
        // получаем информацию
        System.out.print("Введите назначение операции: ");
        memo = one.nextLine();

        // делаем снятие
        theUser.addAccTransaction(toAcc, amount, memo);
    }
}

