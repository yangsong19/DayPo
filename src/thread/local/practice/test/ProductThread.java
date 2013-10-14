package thread.local.practice.test;

import thread.local.practice.service.ProductService;

public class ProductThread extends Thread {
	private ProductService service;
	
	public ProductThread(ProductService service) {
		this.service = service;
	}
	
	public void run() {
		System.out.println(Thread.currentThread().getName());
		service.updateProduct(1, 666666);
	}
}
