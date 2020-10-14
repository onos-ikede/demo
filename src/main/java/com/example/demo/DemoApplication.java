package com.example.demo;

import java.io.File;
import java.io.FileInputStream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRSaver;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	public void run(String... args) throws Exception {

		File jrxmlCustomers = new ClassPathResource("jasperreport/customers.jrxml").getFile();
		File jrxmlOrders = new ClassPathResource("jasperreport/order.jrxml").getFile();

		JasperReport jasperCustomers = JasperCompileManager.compileReport(new FileInputStream(jrxmlCustomers));
		JasperReport jasperOrders = JasperCompileManager.compileReport(new FileInputStream(jrxmlOrders));

		JRSaver.saveObject(jasperCustomers, new File(jrxmlCustomers.getAbsolutePath().replace("jrxml", "jasper")));
		JRSaver.saveObject(jasperOrders, new File(jrxmlOrders.getAbsolutePath().replace("jrxml", "jasper")));

	}

}
