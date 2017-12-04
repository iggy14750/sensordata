public class Pair<L, R> {
    public final L left;
    public final R right;

    public Pair(L l, R r) {
        left = l;
        right = r;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Pair) {
            Pair that = (Pair) obj;
            return that.left.equals(this.left)
                && that.right.equals(this.right);
        }
        return false;
    }
}