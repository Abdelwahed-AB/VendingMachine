import java.util.Objects;

public class Change {
    private int onesCount;
    private int twosCount;
    private int fivesCount;
    private int tensCount;

    public Change() {}

    public Change(int onesCount, int twosCount, int fivesCount, int tensCount) {
        this.onesCount = onesCount;
        this.twosCount = twosCount;
        this.fivesCount = fivesCount;
        this.tensCount = tensCount;
    }

    public int getOnesCount() {
        return onesCount;
    }

    public int getTwosCount() {
        return twosCount;
    }

    public int getFivesCount() {
        return fivesCount;
    }

    public int getTensCount() {
        return tensCount;
    }

    public int getCoinCount(int coin){
        return switch (coin){
            case 1 -> onesCount;
            case 2 -> twosCount;
            case 5 -> fivesCount;
            case 10 -> tensCount;

            default -> 0;
        };
    }

    public void addCoin(int coin){
        switch (coin){
            case 1 -> onesCount++;
            case 2 -> twosCount++;
            case 5 -> fivesCount++;
            case 10-> tensCount++;
        }
    }

    public void removeCoin(int coin){
        switch (coin){
            case 1 -> onesCount--;
            case 2 -> twosCount--;
            case 5 -> fivesCount--;
            case 10-> tensCount--;
        }
    }

    public int getTotal(){
        return onesCount + 2*twosCount + 5*fivesCount + 10*tensCount;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Change change = (Change) o;
        return onesCount == change.onesCount && twosCount == change.twosCount && fivesCount == change.fivesCount && tensCount == change.tensCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(onesCount, twosCount, fivesCount, tensCount);
    }

    @Override
    public String toString() {
        return "Change{" +
                "onesCount=" + onesCount +
                ", twosCount=" + twosCount +
                ", fivesCount=" + fivesCount +
                ", tensCount=" + tensCount +
                '}';
    }
}
