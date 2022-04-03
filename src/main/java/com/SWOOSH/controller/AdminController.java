package com.SWOOSH.controller;


import com.SWOOSH.dto.CarWashDto;
import com.SWOOSH.dto.EmployeeDto;
import com.SWOOSH.dto.ServiceDto;
import com.SWOOSH.dto.UserDto;
import com.SWOOSH.dto.UserFullDto;
import com.SWOOSH.model.CarWash;
import com.SWOOSH.model.Employee;
import com.SWOOSH.model.Service;
import com.SWOOSH.model.User;
import com.SWOOSH.service.IAdminService;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final IAdminService adminService;
    private final ConversionService conversionService;

    @PostMapping("/createCarWash")
    public CarWashDto createCarWash(@RequestBody @Valid CarWashDto carWashDto) {
        CarWash carWash = conversionService.convert(carWashDto, CarWash.class);
        carWash = adminService.createCarWash(carWash);
        return conversionService.convert(carWash, CarWashDto.class);
    }

    @PostMapping("/addServices")
    public CarWashDto addServices(@RequestParam Long carWashId, @RequestBody List<ServiceDto> servicesDto) {
        List<Service> services = servicesDto.stream()
                .map(e -> conversionService.convert(e, Service.class))
                .collect(Collectors.toList());
        CarWash carWash = adminService.addServices(carWashId, services);
        return conversionService.convert(carWash, CarWashDto.class);
    }

    @PostMapping("/addEmployees")
    public CarWashDto addEmployees(@RequestParam Long carWashId, List<EmployeeDto> employeesDto) {
        List<Employee> employees = employeesDto.stream()
                .map(e -> conversionService.convert(e, Employee.class))
                .collect(Collectors.toList());
        CarWash carWash = adminService.addEmployees(carWashId, employees);
        return conversionService.convert(carWash, CarWashDto.class);
    }

    @GetMapping("/getAllCarWashes")
    public List<String> getAllCarWashes() {
        return adminService.getAllCarWashes();
    }

//    @PostMapping("/getAllEmployeesByCarWash")
//    public List<String> getAllEmployeesByCarWash(String location) {
//        return adminService.getAllEmployeesByCarWash(location);
//    }

    @GetMapping("/getNumberEmployeeOrders")
    public Integer getNumberEmployeeOrders(String name, String carWashLocation, @RequestParam(required = false) String passportData) {
        return adminService.getNumberEmployeeOrders(carWashLocation,name, passportData.isEmpty()? "" : passportData);
    }

    @PostMapping("/createEmployee")
    public EmployeeDto createEmployee(@RequestParam Long carWashId, @RequestBody @Valid UserDto userDto) {
        User user = conversionService.convert(userDto, User.class);
        Employee employee = adminService.createEmployee(carWashId, user);
        return conversionService.convert(employee, EmployeeDto.class);
    }

    @PostMapping("/createAdmin")
    public UserFullDto createAdmin(@RequestBody @Valid UserDto userDto) {
        User user = conversionService.convert(userDto, User.class);
        user = adminService.createAdmin(user);
        return conversionService.convert(user, UserFullDto.class);
    }
}
