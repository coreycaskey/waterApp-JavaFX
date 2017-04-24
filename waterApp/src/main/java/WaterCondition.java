public enum WaterCondition {
	WASTE("Waste"),
	TREATABLE_MUDDY("Treatable_muddy"),
	TREATABLE_CLEAR("Treatable_clear"),
	POTABLE("Potable");

	private String value;

    WaterCondition(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}