package bean;

import model.Books;

public class UniqueItem {
	private int objId;
	private Books b;
	private int copies = 0;

	public UniqueItem() {
	}

	public UniqueItem(int objId, Books b, int copies) {
		super();
		this.objId = objId;
		this.b = b;
		this.copies = copies;
	}

	public int getObjId() {
		return objId;
	}

	public Books getB() {
		return b;
	}

	public int getCopies() {
		return copies;
	}

	public void setObjId(int objId) {
		this.objId = objId;
	}

	public void setB(Books b) {
		this.b = b;
	}

	public void setCopies(int copies) {
		this.copies = copies;
	}

	public void incrementObject(int inc) {
		copies += inc;
		if (copies < 0) {
			copies = 0;
		}
	}

	@Override
	public String toString() {
		return "UniqueItem [objId=" + objId + ", b=" + b + ", copies=" + copies + "]";
	}
	
	
}
