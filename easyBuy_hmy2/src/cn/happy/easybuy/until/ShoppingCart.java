package cn.happy.easybuy.until;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import cn.happy.easybuy.entity.Product;


public class ShoppingCart {
	   // 购物车中商品信息集合
		private List<ShoppingCartItem> items = new ArrayList<ShoppingCartItem>();

		// 获得购物车中的商品集合
		public List<ShoppingCartItem> getItems() {
			return this.items;
		}
        
		//为购物车中的商品集合赋值
		public void setItems(List<ShoppingCartItem> item){
			this.items=item;
		}
		
		// 购物车中添加商品
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

		// 购物车中移除商品
		public void removeItem(int index) {
			for (ShoppingCartItem item : items) {
				if(item.getProduct().getId()==index){
					this.items.remove(item);
					break;
				}
			}
		}

		// 修改购物车中指定位置的商品数量
		public void modifyQuantity(int index){
			for (ShoppingCartItem item : items) {
				if(item.getProduct().getId()==index){
					item.setNum(1+item.getNum());
					item.setCost(item.getNum()*item.getProduct().getPrice());
				}
			}
		}

		// 获取购物车中商品总价
		public float getTotalCost() {
			float totalCost = 0;
			for(int i = 0; i < this.items.size(); i++){
				totalCost += this.items.get(i).getCost();
			}
			return totalCost;
		}
	
	
}
