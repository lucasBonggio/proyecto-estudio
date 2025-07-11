package com.tercer.trabajo.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.tercer.trabajo.entidades.ReservaServicio;
import com.tercer.trabajo.repositories.interfaces.I_ReservaServicioRepository;

@Repository
public class ReservaServicioDAO implements I_ReservaServicioRepository{

    private final DataSource DATASOURCE;

    private final String SQL_CREATE = 
        "INSERT INTO reserva_servicios(id_reserva, id_servicio, cantidad) VALUES(?, ?, ?)";
    private final String SQL_FIND_BY_ID =
        "SELECT * FROM reserva_servicios WHERE id_reserva = ? AND id_servicio = ?";
    private final String SQL_FIND_ALL = 
        "SELECT * FROM reserva_servicios";
    private final String SQL_FIND_BY_RESERVA = 
        "SELECT * FROM reserva_servicios WHERE id_reserva = ?";
    private final String SQL_UPDATE = 
        "UPDATE reserva_servicios SET id_reserva = ?, id_servicio = ?, cantidad = ? WHERE id_reserva = ? AND id_servicio = ?";
    private final String SQL_DELETE = 
        "DELETE FROM reserva_servicios WHERE id_reserva = ? AND id_servicio = ?";

    public ReservaServicioDAO(DataSource dataSource){
        this.DATASOURCE = dataSource;
    }

    @Override
    public void create(ReservaServicio reservaServicio) throws SQLException {
        try (Connection conn = DATASOURCE.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_CREATE)) {
            ps.setInt(1, reservaServicio.getIdReserva());
            ps.setInt(2, reservaServicio.getIdServicio());
            ps.setInt(3, reservaServicio.getCantidad());

            ps.executeUpdate();
        }
    }

    @Override
    public ReservaServicio findById(int idReserva, int idServicio) throws SQLException {
        try (Connection conn = DATASOURCE.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, idReserva);
            ps.setInt(2, idServicio);

            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next()){
                    return mapRow(rs);
                }
            }
        } 
        return null;
    }

    @Override
    public List<ReservaServicio> findAll() throws SQLException {
        List<ReservaServicio> reservaServicios = new ArrayList<>();

        try (Connection conn = DATASOURCE.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_FIND_ALL);
                ResultSet rs = ps.executeQuery()) {
            
            while(rs.next()){
                reservaServicios.add(mapRow(rs));
            }
            
        }
        return reservaServicios;
    }

    @Override
    public List<ReservaServicio> findByReserva(int idReserva) throws SQLException {
        List<ReservaServicio> reservaServicios = new ArrayList<>();

        try (Connection conn = DATASOURCE.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_RESERVA, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, idReserva);
            
            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()){
                    reservaServicios.add(mapRow(rs));
                }
            }
        }
        return reservaServicios;
    }

    @Override
    public int update(ReservaServicio reservaServicios, int idReserva, int idServicio) throws SQLException {
        try (Connection conn = DATASOURCE.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_UPDATE)) {
            ps.setInt(1, reservaServicios.getIdReserva());
            ps.setInt(2, reservaServicios.getIdServicio());
            ps.setInt(3, reservaServicios.getCantidad());
            
            ps.setInt(4, idReserva);
            ps.setInt(5, idServicio);
                
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas;
        } 
    }

    @Override
    public int delete(int idReserva, int idServicio) throws SQLException {
        try (Connection conn = DATASOURCE.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_DELETE)) {
            
            ps.setInt(1, idReserva);
            ps.setInt(2, idServicio);

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas;
        }
    }

    public ReservaServicio mapRow(ResultSet rs) throws SQLException {
        ReservaServicio reservaServicios = new ReservaServicio();
        reservaServicios.setIdReserva(rs.getInt("id_reserva"));
        reservaServicios.setIdServicio(rs.getInt("id_servicio"));
        reservaServicios.setCantidad(rs.getInt("cantidad"));
        
        return reservaServicios;
    }

}