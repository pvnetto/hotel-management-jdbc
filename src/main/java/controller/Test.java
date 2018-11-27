package controller;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import model.Address;
import model.Admin;
import model.Customer;
import model.Employee;
import model.Phone;
import model.Room;
import model.RoomService;

public class Test {

	public static void main(String[] args) {
		try {
			CustomerDAO customerDAO = new CustomerDAO();
			EmployeeDAO employeeDAO = new EmployeeDAO();
			AdminDAO adminDAO = new AdminDAO();
			RoomDAO roomDAO = new RoomDAO();
			RoomServiceDAO roomServiceDAO = new RoomServiceDAO();
			
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
			customer2.setBirthDate(new Date(1996, 1, 12));
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
			
			Admin admin = new Admin();
			admin.setCnpj("04780732000100");
			admin.setCompanyName("Hotel Natal LTDA");
			admin.setEmail("hotel@gmail.com");
			admin.setName("Hotel Nome Fantasia");
			admin.setAddress(new Address("Rua do adm", "Bairro do adm", 2, "59054720", ""));
			admin.setPhone(new Phone("36132707"));
			adminDAO.insertAdmin(admin);
			
			List<Admin> admins = adminDAO.listAdmins();
			
			System.out.println("> Listing admins: ");
			for (Admin adm : admins) {
				System.out.println("> Admin name: " + adm.getName());
			}
			
			for(int i = 0; i < 10; i++) {
				Room room = new Room();
				room.setRoomNumber(i + 1);
				roomDAO.insertRoom(room);
			}
			
			RoomService roomService = new RoomService(roomDAO.selectRoomByNumber(5), admins.get(0), employeeDAO.selectEmployeeByCPF("41231232131"), "Clean the room.");
			
			roomServiceDAO.insertRoomService(roomService);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}

}
