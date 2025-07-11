package com.tercer.trabajo.tests;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.tercer.trabajo.entidades.Servicio;
import com.tercer.trabajo.repositories.interfaces.I_ServicioRepository;

@SpringBootApplication(scanBasePackages="com.tercer.trabajo")
public class TestServicioRepository {
    public static void main(String[] args) {
        try (ConfigurableApplicationContext context =  SpringApplication.run(TestServicioRepository.class, args)) {
            I_ServicioRepository servicioDAO = context.getBean(I_ServicioRepository.class);

            System.out.println("TEST 1: Crear un servicio");
            Servicio servicio = new Servicio(2, "Alquiler de instrumentos", 35500);
            servicioDAO.create(servicio);

            if(servicio.getId() > 0){
                System.out.println("Servicio creado exitosamente. ID: " + servicio.getId());
                System.out.println(servicio);
            }else{
                System.err.println("ERROR! No se pudo crear el servicio. ");
            }

            System.out.println("TEST 2: Buscamos por id");

            Servicio servicioEncontrado = servicioDAO.findById(servicio.getId());

            if(servicioEncontrado != null){
                System.out.println("Servicio encontrado exitosamente. ");
                System.out.println(servicioEncontrado);
            }else{
                System.err.println("ERROR! No se encontró el servicio. ");
            }

            System.out.println("TEST 3: Buscar servicios por reserva");

            List<Servicio> servicioPorReserva = servicioDAO.findByReserva(3);

            if(!servicioPorReserva.isEmpty()){
                for (Servicio ser : servicioPorReserva) {
                    System.out.println("Servicio " + ser.getId() + ": " + ser);
                    System.out.println("==================================");
                }
            }else{
                System.err.println("No hay servicios de la reserva. ");
            }

            System.out.println("TEST 4: Buscar todos los servicios");

            List<Servicio> servicios = servicioDAO.findAll();
            if(!servicios.isEmpty()){
                for (Servicio ser : servicios) {
                    System.out.println("Servicio " + ser.getId() + ": " + ser);
                    System.out.println("==================================");
                }
            }else{
                System.err.println("No hay servicios que mostrar. ");
            }

            System.out.println("TEST 5: Actualizar servicio");

            Servicio actualizar = new Servicio(22, "Venta de púas", 2000);

            int filasAfectadas = servicioDAO.update(actualizar, 6);

            if(filasAfectadas > 0){
                System.out.println("El servicio se actualizó exitosamente. ");
            }else{
                System.err.println("ERROR! No se pudo actualizar el servicio");
            }

            System.out.println("TEST 6: Eliminar servicio");

            int filasAfectadas2 = servicioDAO.delete(5);
            if(filasAfectadas2 > 0){
                System.out.println("El servicio se eliminó exitosamente. ");
            }else{
                System.err.println("ERROR! No se pudo eliminar el servicio");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
