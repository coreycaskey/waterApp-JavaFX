/*
 Created by Corey Caskey on 4/24/2017
 */

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
