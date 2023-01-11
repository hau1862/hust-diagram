import static org.junit.jupiter.api.Assertionsfalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controller.PlaceOrderController;

class ValidatePhoneNumberTest {
	private PlaceOrderController placeOrderController;

	@BeforeEach
	void setUp() throws Exception {
		placeOrderController = new PlaceOrderController();
	}

	@Test
	@ParameterizedTest
	@CsvSource({
			"12 @ Dinh Tien Hoang, false",
			"Thon yen ha xa hai boi, success"
	})
	public void test(String phone, boolean expected) {
		// when
		boolean isValid = placeOrderController.validatePhoneNumber(phone);

		// then
		assertEquals(expected, isValid);
	}

}
