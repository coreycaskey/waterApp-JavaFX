/*
 Created by Corey Caskey on 4/24/2017
 */

public class User {
    
	private String password;
    private AccountType accountType;
    private String email;

    public User(String email, String password, AccountType accountType) {
        this.email = email;
        this.password = password;
        this.accountType = accountType;
    }

    public String getPassword() {
        return this.password;
    }

    public String getEmail() {
        return this.email;
    }

    public AccountType getAccountType() {
        return this.accountType;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public String toString() {
        return email + "; " + password + "; " + accountType.toString() + "; " + email;
    }


}
