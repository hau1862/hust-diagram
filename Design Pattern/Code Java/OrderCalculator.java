package shippingFee;

import java.util.Random;

import entity.order.Order;

public class OrderCalculator implements CalcShippingFee{
	public int calculateShippingFee(Order order) {
		String province = order.getDeliveryInfo().get("province").toString();

        	return calcBaseFee(province) + calcExtraFee(province);
	}
	
	private int calcExtraFee(String province) {
		Random rand = new Random();
		double weight = (rand.nextFloat()*10);

		if(weight > 3) {
			if ( province == "Hà Nội" || province == "Hồ Chí Minh") {
				return Math.floor((weight-3)/0.5)*2500;
			}
			else {
				return Math.floor((weight-0.5)/0.5)*2500;
			}
		}
		
		return 0;
	}

	private int calcBaseFee(String province) {
		if ( province == "Hà Nội" || province == "Hồ Chí Minh") {
			return 22000;
		}
		else {
			return 30000;
		}
	}
}
