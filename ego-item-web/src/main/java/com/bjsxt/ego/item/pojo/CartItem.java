package com.bjsxt.ego.item.pojo;

/**
 * 放到cookie中的每一条购物车记录的封装类
 * @author Administrator
 *
 */
public class CartItem {
	//商品的ID
	private long id;
	//商品的标题
	private String title;
	//商品的数量
	private Integer num;
	//商品价格
	private long price;
	//商品的图片
	private String image;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
