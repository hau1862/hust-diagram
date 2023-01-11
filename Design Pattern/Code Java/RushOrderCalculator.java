package shippingFee;

import java.util.Random;

import entity.order.Order;

public class RushOrderCalculator implements ShippingFeeCalculator{
	public int CalculateRushOrder(Order order) {
        	return calcBaseFee() + calcExtraFee();
	}

	private int calcExtraFee() {
		Random rand = new Random();
		double weight = (rand.nextFloat()*10);

		if(weight > 3) {
			return Math.floor((weight-3)/0.5)*2500;
		}
		
		return 0;
	}

	private int calcBaseFee() {
		return 32000;
	}
}
