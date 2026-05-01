package api.endpoints;

public class Roots {

//	 Auth module
	
	public static String base_url = "https://api.eventhub.rahulshettyacademy.com/api";
	
	public static String register_user = base_url +"/auth/register";
	public static String login_user = base_url + "/auth/login";
	public static String getAuthenticate_user = base_url + "/auth/me";

// Event  module
	
	public static String create_event_url = base_url +"/events";
	public static String read_event_url= base_url + "/events/{eventId}";
	public static String update_event_url = base_url + "/events/{eventId}";
	public static String delete_event_url= base_url + "/events/{eventId}";

// Bookings
	
	String create_booking_url = base_url + "/bookings";
	String read_bookingByReference_url = base_url + "/bookings/ref/{bookingRef}";
	String read_booking_url = base_url + "/bookings/{bookingId}";
	String deleteBook_url = base_url + "/bookings/{bookingId}";
	

}
