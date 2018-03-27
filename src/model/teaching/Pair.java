package model.teaching;

public class Pair<S,T> {

	private final S leftVal;
	private final T rightVal;
	
	public Pair(S left, T right) {
		leftVal = left;
		rightVal = right;
	}

	public S getLeftVal() {
		return leftVal;
	}

	public T getRightVal() {
		return rightVal;
	}

	public int hashCode() {
		return leftVal.hashCode() + rightVal.hashCode();
	}

	@SuppressWarnings("unchecked")
	public boolean equals(Object obj) {
		Pair<Integer, Generation> other;
		try {
			other =  (Pair<Integer, Generation>) obj;
		}catch (Exception e) {
			return false;
		}
		return leftVal.equals(other.leftVal)
				&& rightVal.equals(other.rightVal);
	}

	public String toString() {
		return leftVal.toString() + " - " + rightVal.toString();
	}
	
	
}
