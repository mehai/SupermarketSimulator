package supermarket;

import java.util.Date;

public class Notification {
	
	private Date date;
	private NotificationType type;
	private int depID;
	private int prodID;
	
	public Notification(Date date, NotificationType type, int depId, int prodID) {
		this.date = date;
		this.type = type;
		this.depID = depId;
		this.prodID = prodID;
	}

	public Date getDate() {
		return date;
	}

	public NotificationType getType() {
		return type;
	}

	public int getDepID() {
		return depID;
	}

	public int getProdID() {
		return prodID;
	}
	
	public String toString() {
		return type + ";" + prodID + ";" + depID;
	}
}

enum NotificationType{
	ADD,
	REMOVE,
	MODIFY
}
