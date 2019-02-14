package supermarket.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.CardLayout;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Comparator;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import supermarket.*;

public class SupermarketGUI extends JFrame {

	//The backend objects from supermarket package
	EventHandler handler;
	Test uploader;
	Customer currentCustomer;
	
	//Parent panel
	private JPanel contentPane;
	
	//Start panel components
	private JPanel startPanel;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JRadioButton rdbtnAdministrator, rdbtnCustomer;
	private JButton uploadBtn;
	
	//Admin panel components
	private JPanel adminPanel;
	private JButton btnAbc, btnPriceAsc, btnPriceDesc;
	
	//customer panels components
	private JPanel customerPanel;
	private JTextField customerName;
	private JPanel menuPanel;
	private JPanel shoppingCartPanel, wishListPanel;
	private JTextField textItemID;
	
	//general purpose components
	private JList<Item> list;
	private JTextField textDepId, textProdId, textName, textPrice;
	private JButton btnAdd;
	private JButton btnRemove;

	/*************************************************
	 * Launch the application.
	 *************************************************/
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SupermarketGUI frame = new SupermarketGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/*************************************************
	 * Create the frame and main start panel
	 *************************************************/
	public SupermarketGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));
		
		startPanel = new JPanel();
		contentPane.add(startPanel, "startPanel");
		startPanel.setLayout(null);
		
		JLabel title = new JLabel("SUPERMARKET");
		title.setFont(new Font("Waree", Font.BOLD, 20));
		title.setBounds(215, 26, 170, 20);
		startPanel.add(title);
		
		
		JLabel lblAreYouA = new JLabel("Are you a customer or the administrator?");
		lblAreYouA.setBounds(150, 80, 300, 20);
		startPanel.add(lblAreYouA);
		
		AscultatorStart asc = new AscultatorStart();
		
		rdbtnAdministrator = new JRadioButton("Administrator");
		buttonGroup.add(rdbtnAdministrator);
		rdbtnAdministrator.setBounds(235, 120, 150, 23);
		rdbtnAdministrator.addActionListener(asc);
		startPanel.add(rdbtnAdministrator);
		
		rdbtnCustomer = new JRadioButton("Customer");
		buttonGroup.add(rdbtnCustomer);
		rdbtnCustomer.setBounds(235, 147, 149, 23);
		rdbtnCustomer.addActionListener(asc);
		startPanel.add(rdbtnCustomer);
		
		uploadBtn = new JButton("Upload");
		uploadBtn.setEnabled(false);
		uploadBtn.addActionListener(asc);
		
		uploadBtn.setBounds(215, 200, 150, 50);
		startPanel.add(uploadBtn);
	}
	
	/**************************************************************
	 * INIT PANELS METHODS
	 **************************************************************/
	
	private void initAdminPanel() {

		//create the event handler
		handler = new EventHandler();
		
		adminPanel = new JPanel();
		contentPane.add(adminPanel, "adminPanel");
		adminPanel.setLayout(null);
		
		//PANEL_1 FOR ORDER BY AND REMOVE 
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(12, 12, 385, 336);
		adminPanel.add(panel_1);
		panel_1.setLayout(null);
		
		//CREATE LIST		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 30, 200, 280);
		panel_1.add(scrollPane);
		
		DefaultListModel<Item> listModel = getAllItems();
		list = new JList<>(listModel);
		scrollPane.setViewportView(list);
		
		JLabel lblProducts = new JLabel("Products");
		lblProducts.setBounds(63, 12, 70, 15);
		panel_1.add(lblProducts);
		
		//CREATE ASCULTATOR
		AscultatorAdmin asc = new AscultatorAdmin();
		
		//ORDER BY AND REMOVE BUTTONS
		JLabel lblOrderBy = new JLabel("Order by:");
		lblOrderBy.setBounds(236, 47, 70, 15);
		panel_1.add(lblOrderBy);
		
		btnAbc = new JButton("ABC");
		btnAbc.setBounds(215, 74, 117, 25);
		btnAbc.addActionListener(asc);
		panel_1.add(btnAbc);
		
		btnPriceAsc = new JButton("Price asc");
		btnPriceAsc.setBounds(215, 111, 117, 25);
		btnPriceAsc.addActionListener(asc);
		panel_1.add(btnPriceAsc);
		
		btnPriceDesc = new JButton("Price desc");
		btnPriceDesc.setBounds(215, 148, 117, 25);
		btnPriceDesc.addActionListener(asc);
		panel_1.add(btnPriceDesc);
		
		btnRemove = new JButton("Remove");
		btnRemove.setBounds(215, 228, 117, 25);
		btnRemove.addActionListener(asc);
		panel_1.add(btnRemove);
		
		//PANEL FOR ADDING
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(398, 12, 180, 336);
		adminPanel.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblAddProduct = new JLabel("Add Product");
		lblAddProduct.setBounds(50, 10, 90, 20);
		panel_2.add(lblAddProduct);
		
		JLabel lblDepId = new JLabel("Dep ID");
		lblDepId.setBounds(0, 80, 70, 15);
		panel_2.add(lblDepId);
		
		JLabel lblProdId = new JLabel("Prod ID");
		lblProdId.setBounds(0, 110, 70, 15);
		panel_2.add(lblProdId);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(0, 140, 70, 15);
		panel_2.add(lblName);
		
		JLabel lblPrice = new JLabel("Price");
		lblPrice.setBounds(0, 170, 70, 15);
		panel_2.add(lblPrice);
		
		textDepId = new JTextField();
		textDepId.setBounds(66, 78, 114, 19);
		panel_2.add(textDepId);
		textDepId.setColumns(10);
		
		textProdId = new JTextField();
		textProdId.setBounds(66, 108, 114, 19);
		panel_2.add(textProdId);
		textProdId.setColumns(10);
		
		textName = new JTextField();
		textName.setBounds(66, 138, 114, 19);
		panel_2.add(textName);
		textName.setColumns(30);
		
		textPrice = new JTextField();
		textPrice.setBounds(66, 168, 114, 19);
		panel_2.add(textPrice);
		textPrice.setColumns(10);
		
		btnAdd = new JButton("Add");
		btnAdd.setBounds(32, 228, 117, 25);
		btnAdd.addActionListener(new ActionListener() {
			
			@Override
			//throws exceptions if depId, prodId or Price contains letters
			public void actionPerformed(ActionEvent e) {
				if(e.getSource().equals(btnAdd)) {
					if(textDepId.getText().isEmpty() || textProdId.getText().isEmpty() 
							|| textName.getText().isEmpty() || textPrice.getText().isEmpty()) {
						JOptionPane.showMessageDialog(contentPane, 
								"Please complete all fields!", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}else {
						if(!validateDepId(textDepId.getText())) {
							JOptionPane.showMessageDialog(contentPane, 
									"This department ID is invalid!", "Error", JOptionPane.ERROR_MESSAGE);
							return;
						}
						if(!validateProdId(textProdId.getText())) {
							JOptionPane.showMessageDialog(contentPane, 
									"This ID is invalid or already used!", "Error", JOptionPane.ERROR_MESSAGE);
							return;
						}
						if(!validatePrice(textPrice.getText())) {
							JOptionPane.showMessageDialog(contentPane, 
									"This Price is invalid!", "Error", JOptionPane.ERROR_MESSAGE);
							return;
						}
						//add product on the list and in backend
						int depId = Integer.parseInt(textDepId.getText());
						int itemId = Integer.parseInt(textProdId.getText());
						Item item = handler.addProduct(depId, itemId, Float.parseFloat(textPrice.getText()), textName.getText());
						((DefaultListModel<Item>)list.getModel()).addElement(item);
					}
				}
				
			}
		});
		panel_2.add(btnAdd);
	}
	
	private void initCustomerPanel() {
		//init handler
		handler = new EventHandler();
		customerPanel = new JPanel();
		customerPanel.setLayout(null);
		
		JLabel lblInsertName = new JLabel("Please insert your name...");
		lblInsertName.setBounds(200, 100, 200, 15);
		customerPanel.add(lblInsertName);
		
		customerName = new JTextField();
		customerName.setBounds(215, 148, 154, 19);
		customerPanel.add(customerName);
		customerName.setColumns(15);
		
		JButton btnLogIn = new JButton("LOG IN");
		btnLogIn.setBounds(235, 197, 110, 20);
		btnLogIn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == btnLogIn) {
					if(customerName.getText().isEmpty()) {
						JOptionPane.showMessageDialog(contentPane, 
								"Please insert your Customer Name!", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					if(handler.getCustomer(customerName.getText()) == null) {
						JOptionPane.showMessageDialog(contentPane, 
								"Customer not found!", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					currentCustomer = handler.getCustomer(customerName.getText());
					//init next Panel - menuPanel
					initMenuPanel();
					contentPane.add(menuPanel, "menuPanel");
					((CardLayout)contentPane.getLayout()).next(contentPane);
				}
				
			}
		});
		customerPanel.add(btnLogIn);
	}
	
	private void initMenuPanel(){
		menuPanel = new JPanel();
		contentPane.add(menuPanel, "menuPanel");
		menuPanel.setLayout(null);
		
		JLabel lblMenu = new JLabel("Menu");
		lblMenu.setFont(new Font("Waree", Font.BOLD, 23));
		lblMenu.setBounds(260, 50, 80, 30);
		menuPanel.add(lblMenu);
		
		JButton btnShoppingcart = new JButton("ShoppingCart");
		btnShoppingcart.setBounds(220, 120, 150, 30);
		btnShoppingcart.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == btnShoppingcart) {
					initShoppingCartPanel();
					contentPane.add(shoppingCartPanel, "shoppingCartPanel");
					((CardLayout)contentPane.getLayout()).show(contentPane, "shoppingCartPanel");
				}
				
			}
		});
		menuPanel.add(btnShoppingcart);
		
		JButton btnWishlist = new JButton("WishList");
		btnWishlist.setBounds(220, 170, 150, 30);
		btnWishlist.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == btnWishlist) {
					initWishListPanel();
					contentPane.add(wishListPanel, "wishListPanel");
					((CardLayout)contentPane.getLayout()).show(contentPane, "wishListPanel");
				}
				
			}
		});
		menuPanel.add(btnWishlist);
	}
	
	private void initShoppingCartPanel() {
		shoppingCartPanel = new JPanel();
		shoppingCartPanel.setLayout(null);
		
		//load components for the info of selected product
		JPanel infoProdPanel = new JPanel();
		infoProdPanel.setBounds(0, 261, 590, 99);
		shoppingCartPanel.add(infoProdPanel);
		infoProdPanel.setLayout(null);
		
		JLabel lblSelectedProduct = new JLabel("Selected product");
		lblSelectedProduct.setFont(new Font("Dialog", Font.BOLD | Font.BOLD, 12));
		lblSelectedProduct.setBounds(12, 12, 140, 20);
		infoProdPanel.add(lblSelectedProduct);
		
		JLabel lblItemid = new JLabel("ID");
		lblItemid.setFont(new Font("Dialog", Font.ITALIC, 12));
		lblItemid.setBounds(15, 33, 50, 15);
		infoProdPanel.add(lblItemid);
		
		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Dialog", Font.ITALIC, 12));
		lblName.setBounds(88, 33, 50, 15);
		infoProdPanel.add(lblName);
		
		JLabel lblPrice = new JLabel("Price");
		lblPrice.setFont(new Font("Dialog", Font.ITALIC, 12));
		lblPrice.setBounds(297, 33, 70, 15);
		infoProdPanel.add(lblPrice);
		
		JLabel lblDepartmentid = new JLabel("departmentID");
		lblDepartmentid.setFont(new Font("Dialog", Font.ITALIC, 12));
		lblDepartmentid.setBounds(373, 33, 96, 15);
		infoProdPanel.add(lblDepartmentid);
		
		JTextField textID = new JTextField();
		textID.setEditable(false);
		textID.setBounds(12, 52, 50, 19);
		infoProdPanel.add(textID);
		textID.setColumns(10);
		
		JTextField textName = new JTextField();
		textName.setEditable(false);
		textName.setBounds(80, 52, 200, 19);
		infoProdPanel.add(textName);
		textName.setColumns(30);
		
		JTextField textPrice = new JTextField();
		textPrice.setEditable(false);
		textPrice.setColumns(10);
		textPrice.setBounds(297, 52, 50, 19);
		infoProdPanel.add(textPrice);
		
		JTextField textdepID = new JTextField();
		textdepID.setEditable(false);
		textdepID.setBounds(373, 52, 70, 19);
		infoProdPanel.add(textdepID);
		textdepID.setColumns(10);
		
		JButton btnBack = new JButton("←");
		btnBack.setFont(new Font("Dialog", Font.BOLD, 18));
		btnBack.setBounds(509, 33, 50, 40);
		btnBack.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == btnBack) {
					((CardLayout)contentPane.getLayout()).show(contentPane, "menuPanel");
				}
			}
		});
		infoProdPanel.add(btnBack);
		
		//make panel with list and list actions
		JPanel itemListPanel = new JPanel();
		itemListPanel.setBounds(0, 0, 590, 260);
		shoppingCartPanel.add(itemListPanel);
		itemListPanel.setLayout(null);
		
		//make list
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 12, 259, 236);
		itemListPanel.add(scrollPane);
		
		DefaultListModel<Item> model = new DefaultListModel<>();
		try {
			for(Item item : handler.getItems("ShoppingCart", currentCustomer.getName())) {
				model.addElement(item);
			}
		}catch(Exception e) {
			JOptionPane.showMessageDialog(contentPane, 
					"Unable to load ShoppingCart items!", "Error", JOptionPane.ERROR_MESSAGE);
		}
		list = new JList<>(model);
		list.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(list.isSelectionEmpty()) {
					return;
				}else if(!e.getValueIsAdjusting()){
					Item item = list.getSelectedValue();
					textID.setText(String.valueOf(item.getID()));
					textName.setText(item.getName());
					textPrice.setText(String.valueOf(item.getPrice()));
					textdepID.setText(String.valueOf(handler.getDeprtmentFromItem(item.getID()).getID()));
				}
				
			}
		});
		scrollPane.setViewportView(list);
		
		JLabel lblTitle = new JLabel("Shopping Cart");
		lblTitle.setFont(new Font("Waree", Font.BOLD, 25));
		lblTitle.setBounds(320, 14, 220, 30);
		itemListPanel.add(lblTitle);
		
		JLabel lblItemId = new JLabel("item ID");
		lblItemId.setBounds(289, 80, 70, 15);
		itemListPanel.add(lblItemId);
		
		textItemID = new JTextField();
		textItemID.setBounds(283, 109, 76, 19);
		itemListPanel.add(textItemID);
		textItemID.setColumns(10);
		
		//make ShoppingCart specific elements
		JLabel lblTotal = new JLabel("Total");
		lblTotal.setBounds(460, 80, 70, 15);
		itemListPanel.add(lblTotal);
		
		JTextField textTotal = new JTextField();
		textTotal.setEditable(false);
		textTotal.setBounds(460, 100, 76, 19);
		itemListPanel.add(textTotal);
		textTotal.setColumns(10);
		Double total = currentCustomer.getCart().getTotalPrice();
		textTotal.setText(String.valueOf(total.floatValue()));
		
		JLabel lblBudget = new JLabel("Budget");
		lblBudget.setBounds(460, 140, 70, 15);
		itemListPanel.add(lblBudget);
		
		JTextField textBudget = new JTextField();
		textBudget.setEditable(false);
		textBudget.setBounds(460, 160, 76, 19);
		itemListPanel.add(textBudget);
		textBudget.setColumns(10);
		Double budget = currentCustomer.getCart().getBudget();
		textBudget.setText(String.valueOf(budget.floatValue()));
		
		btnAdd = new JButton("ADD");
		btnAdd.setBounds(283, 140, 91, 25);
		btnAdd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == btnAdd) {
					if(textItemID.getText().isEmpty()) {
						JOptionPane.showMessageDialog(contentPane, 
								"Please insert the ID of the item you wish to add!", 
								"Error", JOptionPane.ERROR_MESSAGE);
					}else if(!validateItemId(textItemID.getText())) {
						JOptionPane.showMessageDialog(contentPane, 
								"Please insert a valid item ID!", 
								"Error", JOptionPane.ERROR_MESSAGE);
					}else {
						try {
							Item item = handler.addItem(Integer.parseInt(textItemID.getText()), 
									"ShoppingCart", currentCustomer.getName());
							((DefaultListModel<Item>)list.getModel()).addElement(item);
							Double total = currentCustomer.getCart().getTotalPrice();
							textTotal.setText(String.valueOf(total.floatValue()));
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(contentPane, 
									"Unable to add item!", 
									"Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
				
			}
		});
		itemListPanel.add(btnAdd);
		
		btnRemove = new JButton("REMOVE");
		btnRemove.setBounds(283, 190, 91, 25);
		btnRemove.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == btnRemove) {
					if(list.isSelectionEmpty()) {
						return;
					}else {
						Item item = list.getSelectedValue();
						try {
							list.clearSelection();
							((DefaultListModel<Item>)list.getModel()).removeElement(item);
							handler.delItem(item.getID(), "ShoppingCart", currentCustomer.getName());
							//update total
							Double total = currentCustomer.getCart().getTotalPrice();
							textTotal.setText(String.valueOf(total.floatValue()));
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(contentPane, 
									"Unable to remove item!", 
									"Error", JOptionPane.ERROR_MESSAGE);
							e1.printStackTrace();
						}
						
					}
				}
				
			}
		});
		itemListPanel.add(btnRemove);
		
		JButton btnBuy = new JButton("BUY");
		btnBuy.setBounds(460, 190, 91, 25);
		btnBuy.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == btnBuy) {
					ShoppingCart cart = currentCustomer.getCart();
					Double remainingBudget = cart.getBudget();
					//create new cart with the remaining budget
					currentCustomer.setCart(remainingBudget);
					//clear the list
					((DefaultListModel<Item>)list.getModel()).clear();
					//update total and budget
					textBudget.setText(String.valueOf(remainingBudget.floatValue()));
					textTotal.setText(String.valueOf(0));
				}
			}
		});
		itemListPanel.add(btnBuy);
	}
	
	private void initWishListPanel() {
		wishListPanel = new JPanel();
		wishListPanel.setLayout(null);
		
		//load components for the info of selected product
		JPanel infoProdPanel = new JPanel();
		infoProdPanel.setBounds(0, 261, 590, 99);
		wishListPanel.add(infoProdPanel);
		infoProdPanel.setLayout(null);
		
		JLabel lblSelectedProduct = new JLabel("Selected product");
		lblSelectedProduct.setFont(new Font("Dialog", Font.BOLD | Font.BOLD, 12));
		lblSelectedProduct.setBounds(12, 12, 140, 20);
		infoProdPanel.add(lblSelectedProduct);
		
		JLabel lblItemid = new JLabel("ID");
		lblItemid.setFont(new Font("Dialog", Font.ITALIC, 12));
		lblItemid.setBounds(15, 33, 50, 15);
		infoProdPanel.add(lblItemid);
		
		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Dialog", Font.ITALIC, 12));
		lblName.setBounds(88, 33, 50, 15);
		infoProdPanel.add(lblName);
		
		JLabel lblPrice = new JLabel("Price");
		lblPrice.setFont(new Font("Dialog", Font.ITALIC, 12));
		lblPrice.setBounds(297, 33, 70, 15);
		infoProdPanel.add(lblPrice);
		
		JLabel lblDepartmentid = new JLabel("departmentID");
		lblDepartmentid.setFont(new Font("Dialog", Font.ITALIC, 12));
		lblDepartmentid.setBounds(373, 33, 96, 15);
		infoProdPanel.add(lblDepartmentid);
		
		JTextField textID = new JTextField();
		textID.setEditable(false);
		textID.setBounds(12, 52, 50, 19);
		infoProdPanel.add(textID);
		textID.setColumns(10);
		
		JTextField textName = new JTextField();
		textName.setEditable(false);
		textName.setBounds(80, 52, 200, 19);
		infoProdPanel.add(textName);
		textName.setColumns(30);
		
		JTextField textPrice = new JTextField();
		textPrice.setEditable(false);
		textPrice.setColumns(10);
		textPrice.setBounds(297, 52, 50, 19);
		infoProdPanel.add(textPrice);
		
		JTextField textdepID = new JTextField();
		textdepID.setEditable(false);
		textdepID.setBounds(373, 52, 70, 19);
		infoProdPanel.add(textdepID);
		textdepID.setColumns(10);
		
		//make back button
		JButton btnBack = new JButton("←");
		btnBack.setFont(new Font("Dialog", Font.BOLD, 18));
		btnBack.setBounds(509, 33, 50, 40);
		btnBack.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == btnBack) {
					((CardLayout)contentPane.getLayout()).show(contentPane, "menuPanel");
				}
			}
		});
		infoProdPanel.add(btnBack);
		
		//make panel with list and list actions
		JPanel itemListPanel = new JPanel();
		itemListPanel.setBounds(0, 0, 590, 260);
		wishListPanel.add(itemListPanel);
		itemListPanel.setLayout(null);
		
		//load list
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 12, 259, 236);
		itemListPanel.add(scrollPane);
		
		DefaultListModel<Item> model = new DefaultListModel<>();
		try {
			for(Item item : handler.getItems("WishList", currentCustomer.getName())) {
				model.addElement(item);
			}
		}catch(Exception e) {
			JOptionPane.showMessageDialog(contentPane, 
					"Unable to load WishList items!", "Error", JOptionPane.ERROR_MESSAGE);
		}
		list = new JList<>(model);
		list.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(list.isSelectionEmpty()) {
					return;
				}else if(!e.getValueIsAdjusting()){
					Item item = list.getSelectedValue();
					textID.setText(String.valueOf(item.getID()));
					textName.setText(item.getName());
					textPrice.setText(String.valueOf(item.getPrice()));
					textdepID.setText(String.valueOf(handler.getDeprtmentFromItem(item.getID()).getID()));
				}
				
			}
		});
		scrollPane.setViewportView(list);
		
		JLabel lblTitle = new JLabel("Wish List");
		lblTitle.setFont(new Font("Waree", Font.BOLD, 25));
		lblTitle.setBounds(340, 14, 150, 30);
		itemListPanel.add(lblTitle);
		
		JLabel lblItemId = new JLabel("item ID");
		lblItemId.setBounds(289, 82, 70, 15);
		itemListPanel.add(lblItemId);
		
		textItemID = new JTextField();
		textItemID.setBounds(283, 109, 76, 19);
		itemListPanel.add(textItemID);
		textItemID.setColumns(10);
		
		btnAdd = new JButton("ADD");
		btnAdd.setBounds(283, 140, 91, 25);
		btnAdd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == btnAdd) {
					if(textItemID.getText().isEmpty()) {
						JOptionPane.showMessageDialog(contentPane, 
								"Please insert the ID of the item you wish to add!", 
								"Error", JOptionPane.ERROR_MESSAGE);
					}else if(!validateItemId(textItemID.getText())) {
						JOptionPane.showMessageDialog(contentPane, 
								"Please insert a valid item ID!", 
								"Error", JOptionPane.ERROR_MESSAGE);
					}else {
						try {
							Item item = handler.addItem(Integer.parseInt(textItemID.getText()), 
									"WishList", currentCustomer.getName());
							((DefaultListModel<Item>)list.getModel()).addElement(item);
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(contentPane, 
									"Unable to add item!", 
									"Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
				
			}
		});
		itemListPanel.add(btnAdd);
		
		btnRemove = new JButton("REMOVE");
		btnRemove.setBounds(283, 190, 91, 25);
		btnRemove.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == btnRemove) {
					if(list.isSelectionEmpty()) {
						return;
					}else {
						Item item = list.getSelectedValue();
						try {
							list.clearSelection();
							((DefaultListModel<Item>)list.getModel()).removeElement(item);
							handler.delItem(item.getID(), "WishList", currentCustomer.getName());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(contentPane, 
									"Unable to remove item!", 
									"Error", JOptionPane.ERROR_MESSAGE);
							e1.printStackTrace();
						}
						
					}
				}
				
			}
		});
		itemListPanel.add(btnRemove);
	}
	
	/***************************************************************************
	 * AUXILIARY METHODS
	 ***************************************************************************/
	
	private DefaultListModel<Item> getAllItems() {
		DefaultListModel<Item> list = new DefaultListModel<>();
		Store store = Store.getInstance();
		for(Department dep: store.getDepartments()) {
			for(Item item: dep.getItems()) {
				list.addElement(item);
			}
		}
		return list;
	}
	
	private boolean validateItemId(String idString) {
		int id = -1;
		try {
			id = Integer.parseInt(idString);
		}catch(Exception e) {
			return false;
		}
		if(id < 0)
			return false;
		Store store = Store.getInstance();
		for(Department dep: store.getDepartments()) {
				for(Item item: dep.getItems()) {
					if(item.getID() == id) {
						return true;
					}
				}
		}
		return false;
	}
	
	private boolean validateProdId(String idString) {
		int id = -1;
		try {
			id = Integer.parseInt(idString);
		}catch(Exception e) {
			return false;
		}
		if(id < 0)
			return false;
		DefaultListModel<Item> model = (DefaultListModel<Item>)list.getModel();
		for(int i = 0; i < model.size(); i++) {
			if(id == model.get(i).getID()) {
				return false;
			}
		}
		return true;
	}
	
	private boolean validateDepId(String idString) {
		int id = -1;
		try {
			id = Integer.parseInt(idString);
		}catch(Exception e) {
			return false;
		}
		if(id < 0) {
			return false;
		}
		for(Department dep: Store.getInstance().getDepartments()) {
			if(dep.getID() == id) {
				return true;
			}
		}
		return false;
	}
	
	private boolean validatePrice(String priceString) {
		try {
			Float.parseFloat(priceString);
		}catch(Exception e){
			return false;
		}
		return true;
	}
	
	
	/************************************************************
	 * LISTENERS
	 ************************************************************/
	private class AscultatorStart implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() instanceof JRadioButton) {
				uploadBtn.setEnabled(true);
			}else if(e.getSource() == uploadBtn) {
				//UPLOAD FILES
				uploader = new Test();
				try {
					uploader.readStoreData();
					uploader.readCustomersData();					
				}catch(Exception ex){
					ex.printStackTrace();
					JOptionPane.showMessageDialog(contentPane, "Upload error!",
						    "Error", JOptionPane.ERROR_MESSAGE);
				}
				if(rdbtnAdministrator.isSelected()) {
					//UPLOAD adminPanel
					initAdminPanel();
					contentPane.add(adminPanel, "adminPanel");
					((CardLayout)contentPane.getLayout()).next(contentPane);
				}else {
					//UPLOAD customerPanel
					initCustomerPanel();
					contentPane.add(customerPanel, "customerPanel");
					((CardLayout)contentPane.getLayout()).next(contentPane);
				}
			}
		}
	}
	
	
	private class AscultatorAdmin implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == btnAbc) {
				DefaultListModel model = (DefaultListModel)list.getModel();
				Object[] array = model.toArray();
				Arrays.sort(array, new Comparator() {
					@Override
					public int compare(Object o1, Object o2) {
						return ((Item)o1).getName().compareTo(((Item)o2).getName());
					}
				});
				model.clear();
				for(int i = 0; i < array.length; i++) {
					model.addElement(array[i]);
				}
			}else if(e.getSource() == btnPriceAsc) {
				DefaultListModel model = (DefaultListModel)list.getModel();
				Object[] array = model.toArray();
				Arrays.sort(array, new Comparator() {
					@Override
					public int compare(Object o1, Object o2) {
						return ((Item)o1).getPrice() > (((Item)o2).getPrice()) ? 1 : -1;
					}
				});
				model.clear();
				for(int i = 0; i < array.length; i++) {
					model.addElement(array[i]);
				}
			}else if(e.getSource() == btnPriceDesc) {
				DefaultListModel model = (DefaultListModel)list.getModel();
				Object[] array = model.toArray();
				Arrays.sort(array, new Comparator() {
					@Override
					public int compare(Object o1, Object o2) {
						return ((Item)o1).getPrice() < (((Item)o2).getPrice()) ? 1 : -1;
					}
				});
				model.clear();
				for(int i = 0; i < array.length; i++) {
					model.addElement(array[i]);
				}
			}else if(e.getSource() == btnRemove) {
				if(list.isSelectionEmpty()) {
					return;
				}else {
					Item item = list.getSelectedValue();
					handler.delProduct(item.getID());
					((DefaultListModel<Item>)list.getModel()).removeElement(item);
				}
			}
		}
	}
}
