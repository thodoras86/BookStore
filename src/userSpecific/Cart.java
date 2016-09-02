package userSpecific;

import java.util.ArrayList;

import bean.UniqueItem;

public class Cart {
	private ArrayList<UniqueItem> items;
	
	public Cart() {
		items = new ArrayList<UniqueItem>();
	}

	public ArrayList<UniqueItem> getItems() {
		return items;
	}

	public void setItems(ArrayList<UniqueItem> items) {
		this.items = items;
	}
	
	public void addToCart(UniqueItem o) {
		items.add(o);
		System.out.println("Item added to cart");
	}
	
	public void emptyCart() {
		items.clear();
	}
	
	public void removeItem(int id) {
		
		for (int i=0; i<items.size(); i++) {
			if (items.get(i).getObjId() == id) {
				items.remove(i);
				break;
			}
		}
		
	}

}
