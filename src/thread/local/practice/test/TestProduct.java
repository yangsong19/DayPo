package thread.local.practice.test;

import thread.local.practice.action.ProductServiceImpl;
import thread.local.practice.service.ProductService;

public class TestProduct {
	public static void main(String[] args) {
		ProductService service = null;
//		service.updateProduct(1, 60000);
		
		Thread productThread = null;
		for(int i = 0; i < 10; i ++) {
			service = new ProductServiceImpl();
			productThread = new ProductThread(service);
			productThread.start();
		}
		
	}
}
