package util;

public class IDObject {
	private static int ID_COUNTER = 0;
	private int ID;
	private String IDType;

	public IDObject(String type) {
		ID = ID_COUNTER;
		ID_COUNTER++;

		this.IDType = type;
	}

	public boolean equals(Object obj) {
		if (obj instanceof IDObject) {
			IDObject idobj = (IDObject) obj;
			return idobj.getID() == ID && idobj.getIDType().equals(IDType);
		}

		return false;
	}

	public int getID() {
		return ID;
	}

	public String getIDType() {
		return IDType;
	}
}
