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
	
	public static String create_booking_url = "https://api.eventhub.rahulshettyacademy.com/api/bookings";
	public static String read_bookingByReference_url = base_url + "/bookings/ref/{bookingRef}";
	public static String read_booking_url = base_url + "/bookings/{bookingId}";
	public static  String deleteBook_url = base_url + "/bookings/{bookingId}";
	

}
