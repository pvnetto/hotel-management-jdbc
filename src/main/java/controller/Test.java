package controller;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import model.Address;
import model.Bill;
import model.Booking;
import model.Consumption;
import model.Customer;
import model.Employee;
import model.Phone;
import model.Rating;
import model.Room;
import model.RoomService;
import model.RoomType;
import model.ServiceStatus;

public class Test {

	public static void main(String[] args) {
		try {
			CustomerDAO customerDAO = new CustomerDAO();
			EmployeeDAO employeeDAO = new EmployeeDAO();
			RoomDAO roomDAO = new RoomDAO();
			RoomServiceDAO roomServiceDAO = new RoomServiceDAO();
			BookingDAO bookingDAO = new BookingDAO();
			BillDAO billDAO = new BillDAO();
			ConsumptionDAO consumptionDAO = new ConsumptionDAO();
			RatingDAO ratingDAO = new RatingDAO();
			
			Customer customer = new Customer();
			customer.setName("Paiva");
			customer.setEmail("pvnetto1@gmail.com");
			customer.setCpf("12041654406");
			customer.setRg("02674259");
			customer.setBirthDate(new Date(1996, 1, 12));
			customer.setAddress(new Address("Rua 1", "Bairro 2", 1234, "59082000", "Apto 2"));
			customer.setPhone(new Phone("999294234"));
			customerDAO.insertCustomer(customer);
			
			Customer customer2 = new Customer();
			customer2.setName("Paiva2");
			customer2.setEmail("pvnetto2@gmail.com");
			customer2.setCpf("4213123124");
			customer2.setRg("02674259");
			customer2.setBirthDate(new Date(1969, 1, 12));
			customer2.setAddress(new Address("Rua dasdas", "Bairro fasdas", 1234, "59084777", "Apto dasdsa"));
			customer2.setPhone(new Phone("999977853"));
			customerDAO.insertCustomer(customer2);
			
			Employee employee = new Employee();
			employee.setName("PaivaEmpregado");
			employee.setEmail("pvnetto_empregado@gmail.com");
			employee.setCpf("41231232131");
			employee.setRg("213123123");
			employee.setBirthDate(new Date(1999, 8, 27));
			employee.setAddress(new Address("Rua dasdasdas", "Bairro dasfasdasdas", 1234, "6546546", ""));
			employee.setPhone(new Phone("948127398"));
			employeeDAO.insertEmployee(employee);
			
			RoomType roomType0 = new RoomType(100.0f, "Barato");
			RoomType roomType1 = new RoomType(200.0f, "Caro");
			roomDAO.insertRoomType(roomType0);
			roomDAO.insertRoomType(roomType1);
			
			for(int i = 0; i < 10; i++) {
				Room room = new Room();
				room.setRoomNumber(i + 1);
				room.setIdRoomType(1);
				roomDAO.insertRoom(room);
			}
			
			RoomService roomService = new RoomService(roomDAO.selectRoomByNumber(5), employeeDAO.selectEmployeeByCPF("41231232131"), "Clean the room.", ServiceStatus.PENDENTE);
			roomServiceDAO.insertRoomService(roomService);
			
			RoomService roomService2 = new RoomService(roomDAO.selectRoomByNumber(2), employeeDAO.selectEmployeeByCPF("41231232131"), "Clean the bathroom", ServiceStatus.PENDENTE);
			roomServiceDAO.insertRoomService(roomService2);
			
			//Bill bill = new Bill(0.0f);
			//int billID = billDAO.insertBill(bill);
			
			System.out.println(billDAO.selectAllBills().size());
			
			Booking booking = new Booking(new Date(2017, 8, 27), new Date(2017, 8, 29));
			Customer bookingCustomer = customerDAO.selectCustomerByCPF("4213123124");
			Room room = roomDAO.selectRoomByNumber(3);
			booking.setIdCustomer(bookingCustomer.getIdCustomer());
			booking.setIdRoom(room.getIdRoom());
			bookingDAO.insertBooking(booking);
			
			System.out.println(billDAO.selectAllBills().size());
			
			Consumption consumption = new Consumption(100.0f, "A bottle of champagne.");
			Bill consumptionBill = billDAO.selectBillByDateAndRoomNumber(3, new Date(2017, 8, 28));
			consumption.setIdBill(consumptionBill.getIdBill());
			consumptionDAO.insertConsumption(consumption);
			

			List<Booking> ratedBookings = bookingDAO.selectBookingsByCustomer(bookingCustomer);
					
			Rating rating = new Rating(5, "Muito bom!");
			rating.setIdBooking(ratedBookings.get(0).getIdBooking());
			rating.setRatingDate(new Date(2017, 8, 30));
			ratingDAO.insertRating(rating);
			
			roomServiceDAO.viewPendingServices();
			ratingDAO.viewPositiveRatings();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
