package cn.happy.easybuy.until;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import cn.happy.easybuy.entity.Product;


public class ShoppingCart {
	   // ���ﳵ����Ʒ��Ϣ����
		private List<ShoppingCartItem> items = new ArrayList<ShoppingCartItem>();

		// ��ù��ﳵ�е���Ʒ����
		public List<ShoppingCartItem> getItems() {
			return this.items;
		}
        
		//Ϊ���ﳵ�е���Ʒ���ϸ�ֵ
		public void setItems(List<ShoppingCartItem> item){
			this.items=item;
		}
		
		// ���ﳵ�������Ʒ
		public void addItem(Product product, int num) {
			boolean flag=true;
			for (ShoppingCartItem item : items) {
				if(item.getProduct().getId()==product.getId()){
					flag=false;
				}
			}
			if(flag){
				ShoppingCartItem item = new ShoppingCartItem(product, num);
				this.items.add(item);
			}else{
				modifyQuantity(product.getId());
			}
		}

		// ���ﳵ���Ƴ���Ʒ
		public void removeItem(int index) {
			for (ShoppingCartItem item : items) {
				if(item.getProduct().getId()==index){
					this.items.remove(item);
					break;
				}
			}
		}

		// �޸Ĺ��ﳵ��ָ��λ�õ���Ʒ����
		public void modifyQuantity(int index){
			for (ShoppingCartItem item : items) {
				if(item.getProduct().getId()==index){
					item.setNum(1+item.getNum());
					item.setCost(item.getNum()*item.getProduct().getPrice());
				}
			}
		}

		// ��ȡ���ﳵ����Ʒ�ܼ�
		public float getTotalCost() {
			float totalCost = 0;
			for(int i = 0; i < this.items.size(); i++){
				totalCost += this.items.get(i).getCost();
			}
			return totalCost;
		}
	
	
}
