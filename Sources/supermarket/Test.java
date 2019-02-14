package supermarket;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.Scanner;

public class Test {

	public static final String PATH = "";
	public static final String STORE_FILE = "store.txt";
	public static final String CUSTOMERS_FILE = "customers.txt";
	public static final String EVENTS_FILE = "events.txt";
	public static final String OUTPUT_FILE = "output.txt";
	
	public void readStoreData() throws Exception {
		Scanner lineSc = new Scanner(new File(PATH+STORE_FILE));
		//set the name of the store;
		String storeName = lineSc.nextLine();
		Store store = Store.getInstance();
		store.setName(storeName);
		try {
			//read line by line
			while(lineSc.hasNextLine()) {
				//read the department line
				String departmentLine = lineSc.nextLine();			
				String[] tokens = departmentLine.split(";");
				int depID = Integer.parseInt(tokens[1]);
				//add new current department
				Department current;
				if(tokens[0].equals("BookDepartment")) {
					current = new BookDepartment(tokens[0], depID);
				}else if(tokens[0].equals("MusicDepartment")) {
					current = new MusicDepartment(tokens[0], depID);
				}else if(tokens[0].equals("SoftwareDepartment")) {
					current = new SoftwareDepartment(tokens[0], depID);
				}else if(tokens[0].equals("VideoDepartment")) {
					current = new VideoDepartment(tokens[0], depID);
				}else {
					throw new Exception("Department type unidentified!");
				}
				store.addDepartment(current);
				//add given items into the current department
				int noItems = Integer.parseInt(lineSc.nextLine());
				for(int i = 0; i < noItems; i++) {
					String item = lineSc.nextLine();
					tokens = item.split(";");
					int itemID = Integer.parseInt(tokens[1]);
					float price = Float.parseFloat(tokens[2]);
					current.addItem(new Item(tokens[0], itemID, price));
				}
			}		
		}finally {
			//close scanner
			lineSc.close();			
		}
	}
	
	public void readCustomersData() throws Exception{
		Scanner lineSc = new Scanner(new File(PATH+CUSTOMERS_FILE));
		//get number of customers and add them sequentially
		int noCustomers = Integer.parseInt(lineSc.nextLine());
		for(int i = 0; i < noCustomers; i++) {
			String[] tokens = lineSc.nextLine().split(";");
			Customer newCustomer = new Customer(tokens[0], Double.parseDouble(tokens[1]), tokens[2].charAt(0));
			Store.getInstance().enter(newCustomer);
		}
		lineSc.close();
	}
	
	void readAndExecuteEvents() throws Exception{
		//create writer
		BufferedWriter writer = new BufferedWriter(new FileWriter(PATH+OUTPUT_FILE));
		Scanner lineSc = new Scanner(new File(PATH+EVENTS_FILE));
		int noEvents = Integer.parseInt(lineSc.nextLine());
		//create EventHandler object
		EventHandler handler = new EventHandler();
		//read and handle each event
		try {
			for(int i = 0; i < noEvents; i++) {
				String[] tokens = lineSc.nextLine().split(";");
				//handle event
				if(tokens[0].equals("addItem")) {
					handler.addItem(Integer.parseInt(tokens[1]), tokens[2], tokens[3]);
				}else if(tokens[0].equals("delItem")) {
					handler.delItem(Integer.parseInt(tokens[1]), tokens[2], tokens[3]);
				}else if(tokens[0].equals("addProduct")) {
					handler.addProduct(Integer.parseInt(tokens[1]), 
							Integer.parseInt(tokens[2]), 
							Float.parseFloat(tokens[3]), 
							tokens[4]);
				}else if(tokens[0].equals("modifyProduct")) {
					handler.modifyProduct(Integer.parseInt(tokens[1]), 
							Integer.parseInt(tokens[2]), 
							Float.parseFloat(tokens[3]));
				}else if(tokens[0].equals("delProduct")) {
					handler.delProduct(Integer.parseInt(tokens[1]));
				}else if(tokens[0].equals("getItem")) {
					Item item = handler.getItem(tokens[1]);
					writer.write(item + "\n");
				}else if(tokens[0].equals("getItems")) {
					List<Item> list = handler.getItems(tokens[1], tokens[2]);
					writer.write(list.toString() + "\n");
				}else if(tokens[0].equals("getTotal")) {
					Double total = handler.getTotal(tokens[1], tokens[2]);
					writer.write(String.format("%.02f", ((Float)total.floatValue())) + "\n");
				}else if(tokens[0].equals("accept")) {
					handler.accept(Integer.parseInt(tokens[1]), tokens[2]);
				}else if(tokens[0].equals("getObservers")) {
					List<Customer> list = handler.getObservers(Integer.parseInt(tokens[1]));
					writer.write(list.toString() + "\n");
				}else if(tokens[0].equals("getNotifications")) {
					List<Notification> list = handler.getNotifications(tokens[1]);
					writer.write(list.toString() + "\n");
				}else {
					throw new Exception("event unidentified!");
				}
			}
			
		}finally {
			//close scanner and writer
			lineSc.close();
			writer.close();
		}
	}
	
	public static void main(String[] args) {
		Test reader = new Test();
		try {
			reader.readStoreData();
			reader.readCustomersData();
			reader.readAndExecuteEvents();	
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
