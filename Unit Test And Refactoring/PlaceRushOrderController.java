package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import entity.cart.Cart;
import entity.cart.CartMedia;
import common.exception.InvalidDeliveryInfoException;
import entity.invoice.Invoice;
import entity.order.Order;
import entity.order.OrderMedia;
import views.screen.popup.PopupScreen;

/**
 * This class controls the flow of place order usecase in our AIMS project
 * 
 * @author nguyenlm
 */
public class PlaceRushOrderController extends BaseController {

    /**
     * Just for logging purpose
     */
    private static Logger LOGGER = utils.Utils.getLogger(PlaceOrderController.class.getName());

    /**
     * This method checks the avalibility of product when user click PlaceOrder
     * button
     * 
     * @throws SQLException
     */
    public void placeOrder(HashMap info, String instruction) throws SQLException {
        Cart.getCart().checkAvailabilityOfProduct();
        Order order = createOrder();

        try {
            processDeliveryInfo(info);
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method creates the new Order based on the Cart
     * 
     * @return Order
     * @throws SQLException
     */
    public Order createOrder() throws SQLException {
        Order order = new Order();
        for (Object object : Cart.getCart().getListMedia()) {
            CartMedia cartMedia = (CartMedia) object;
            OrderMedia orderMedia = new OrderMedia(cartMedia.getMedia(),
                    cartMedia.getQuantity(),
                    cartMedia.getPrice());
            order.getlstOrderMedia().add(orderMedia);
        }
        return order;
    }

    /**
     * This method creates the new Invoice based on order
     * 
     * @param order
     * @return Invoice
     */
    public Invoice createInvoice(Order order) {
        return new Invoice(order);
    }

    /**
     * This method takes responsibility for processing the shipping info from user
     * 
     * @param info
     * @throws InterruptedException
     * @throws IOException
     */
    public void processDeliveryInfo(HashMap info) throws InterruptedException, IOException {
        LOGGER.info("Process Delivery Info");
        LOGGER.info(info.toString());
        validateDeliveryInfo(info);
    }

    /**
     * The method validates the info
     * 
     * @param info
     * @throws InterruptedException
     * @throws IOException
     */
    public void validateDeliveryInfo(HashMap<String, String> info) throws InterruptedException, IOException {
        String name = info.get("name");
        String phoneNumber = info.get("phone");
        String address = info.get("address");
        LocalDate expectedDate = LocalDate.parse(info.get("expectedDate"));

        boolean result = validateName(name) && validatePhoneNumber(phoneNumber) && validateAddress(address)
                && validateExpectedDate(expectedDate);

        if (!result) {
            throw new InterruptedException("loi thong tin");
        }
    }

    /**
     * check phone number
     * 
     * @param phoneNumber
     * @return boolean
     */
    public boolean validatePhoneNumber(String phoneNumber) {
        if (phoneNumber != null && phoneNumber.length() == 10 && phoneNumber.startsWith("0")) {
            try {
                Integer.parseInt(phoneNumber);
                return true;
            } catch (Exception e) {
                return false;
            }
        }

        return false;
    }

    /**
     * check customer name
     * 
     * @param name
     * @return boolean
     */
    public boolean validateName(String name) {
        if (name != null && name.length() > 0) {
            return name.matches("[A-Za-z\\s]+");
        }

        return false;
    }

    /**
     * check customer address
     * 
     * @param address
     * @return boolean
     */

    public boolean validateAddress(String address) {
        if (address != null && address.length() > 0) {
            return address.matches("[A-Za-z0-9\\s,/]+");
        }

        return false;
    }

    /**
     * check expectedDate
     * 
     * @param expectedDate
     * @return boolean
     */
    public boolean validateExpectedDate(LocalDate expectedDate) {
        return expectedDate.isAfter(LocalDate.now());
    }

    /**
     * This method calculates the shipping fees of order
     * 
     * @param order
     * @return shippingFee
     */
    public int calculateShippingFee(Order order) {
        Random rand = new Random();
        int fees = (int) (((rand.nextFloat() * 10) / 100) * order.getAmount());
        LOGGER.info("Order Amount: " + order.getAmount() + " -- Shipping Fees: " + fees);
        return fees;
    }
}
