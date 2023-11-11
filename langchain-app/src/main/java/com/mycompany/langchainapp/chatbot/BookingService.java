package com.mycompany.langchainapp.chatbot;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class BookingService {

    public Booking getBookingDetails(String bookingNumber, String customerName, String customerSurname) {
        ensureExists(bookingNumber, customerName, customerSurname);

        // Imitating retrieval from DB
        LocalDate bookingFrom = LocalDate.now().plusDays(1);
        LocalDate bookingTo = LocalDate.now().plusDays(3);
        Customer customer = new Customer(customerName, customerSurname);
        return new Booking(bookingNumber, bookingFrom, bookingTo, customer);
    }

    public void cancelBooking(String bookingNumber, String customerName, String customerSurname) {
        ensureExists(bookingNumber, customerName, customerSurname);

        // Imitating cancellation
        throw new BookingCannotBeCancelledException(bookingNumber);
    }

    private void ensureExists(String bookingNumber, String customerName, String customerSurname) {
        // Imitating check
        if (!(bookingNumber.equals("1234")
                && customerName.equals("Tony")
                && customerSurname.equals("Stark"))) {
            throw new BookingNotFoundException(bookingNumber);
        }
    }
}