public enum AccountType {
	USER("User"),
	WORKER("Worker"),
	MANAGER("Manager"),
	ADMIN("Admin");

	private String value;

    AccountType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

}