/*
 Created by Corey Caskey on 4/24/2017
 */

public enum WaterType {
	BOTTLED("Bottled"),
	WELL("Well"),
	STREAM("Stream"),
	LAKE("Lake"),
	SPRING("Spring"),
	OTHER("Other");

	private String value;

    WaterType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
