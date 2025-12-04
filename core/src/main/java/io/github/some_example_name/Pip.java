package io.github.some_example_name;

public enum Pip{
    Ace(15,11,1),
    Two(2,0,2),
    Three(3,0,3),
    Four(4,0,4),
    Five(5,0,5),
    Six(6,0,6),
    Seven(7,0,7),
    Eight(8,0,8),
    Nine(9,0,9),
    Ten(14,10,10),
    Jack(11,1,11),
    Queen(12,2,12),
    King(13,3,13);

    public final int value;
    public final int index;
    public final int points;

    private Pip(int value, int points, int index){
        this.value = value;
	this.points = points;
        this.index = index;
    }
}
