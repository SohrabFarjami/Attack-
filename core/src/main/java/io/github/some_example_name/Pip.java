package io.github.some_example_name;

public enum Pip{
    Ace(15,1),
    Two(2,2),
    Three(3,3),
    Four(4,4),
    Five(5,5),
    Six(6,6),
    Seven(7,7),
    Eight(8,8),
    Nine(9,9),
    Ten(14,10),
    Jack(11,11),
    Queen(12,12),
    King(13,13);

    public final int value;
    public final int index;

    private Pip(int value,int index){
        this.value = value;
        this.index = index;
    }
}
