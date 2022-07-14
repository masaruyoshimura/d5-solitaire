package d5.solitaire.domain.model.game;

import java.util.stream.Stream;

/**
 * ランク。カードの数字。
 */
public enum Rank {
    
    RANK_A(1, "A"),
    RANK_2(2, "2"),
    RANK_3(3, "3"),
    RANK_4(4, "4"),
    RANK_5(5, "5"),
    RANK_6(6, "6"),
    RANK_7(7, "7"),
    RANK_8(8, "8"),
    RANK_9(9, "9"),
    RANK_10(10, "10"),
    RANK_J(11, "J"),
    RANK_Q(12, "Q"),
    RANK_K(13, "K");

    // 1 ～ 13(K)
    int rank;
    
    String display;
    
    private Rank(int rank, String display) {
        this.rank = rank;
        this.display = display;
    }
    
    public int rank() {
        return rank;
    }
    
    @Override
    public String toString() {
        return display;
    }
    
    public static Rank of(int rank) {
        if(rank < 1 || 13 < rank) throw new IllegalArgumentException("rank should be from 1 to 13 :"+rank);
        return values()[rank-1];
    }
    
    public static Stream<Rank> stream() {
        return Stream.of(values());
    }
    
    public boolean isKing() {
        return rank == 13;
    }

    public boolean isAdaptable(Rank other) {
        return rank == other.rank + 1;
    }
}
