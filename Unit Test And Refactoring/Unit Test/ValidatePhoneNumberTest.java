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
			"12345678, false",
			"12345678900, false",
			"1123456789, false",
			"A12345678, false",
			"1b123456789, false",
			"B123456789, false",
			"01b3456789, false",
			"0123456789, success"

	})
	public void test(String phone, boolean expected) {
		// when
		boolean isValid = placeOrderController.validatePhoneNumber(phone);

		// then
		assertEquals(expected, isValid);
	}

}
