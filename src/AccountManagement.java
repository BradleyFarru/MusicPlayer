public class AccountManagement {
    Account[] accounts = new Account[10];

    public void createAccount() {
        String username = "Me";
        String password = "password";

        Account account = new Account();
        account.username = username;
        account.password = password;
    }

    public void removeAccount(String givenUsername) {
        for (int i = 0; i < accounts.length; i++) {
            if (accounts[i].username.equals(givenUsername)) {
                accounts[i] = null;
                break;
            }
        }
    }
}
