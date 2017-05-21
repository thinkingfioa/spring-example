package org.thinking.scopebeans.sessions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/** 
 * @author thinkingfioa
 * @Email thinking_fioa@163.com
 * @version May 21, 2017 11:12:08 AM
 */
@Component
public class StoreService {
	ShoppingCart shoppingCart;
	
	@Autowired
	public void setShoppingCard(ShoppingCart shoppingCart){
		this.shoppingCart = shoppingCart;
	}
}
