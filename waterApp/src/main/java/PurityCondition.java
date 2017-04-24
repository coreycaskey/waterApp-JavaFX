public enum PurityCondition {
	SAFE("Safe"),
	TREATABLE("Treatable"),
	UNSAFE("Unsafe");

	private String value;

    PurityCondition(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}