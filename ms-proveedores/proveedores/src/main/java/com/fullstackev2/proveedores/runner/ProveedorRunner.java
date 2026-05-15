package com.fullstackev2.proveedores.runner;

import com.fullstackev2.proveedores.model.Contrato;
import com.fullstackev2.proveedores.model.Proveedor;
import com.fullstackev2.proveedores.repository.ContratoRepository;
import com.fullstackev2.proveedores.repository.ProveedorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProveedorRunner implements CommandLineRunner {

    private final ProveedorRepository proveedorRepository;
    private final ContratoRepository contratoRepository;

    @Override
    public void run(String... args) throws Exception {
        if (proveedorRepository.count() == 0) {
            log.info("[ProveedorRunner] Insertando datos iniciales de proveedores...");

            Proveedor p1 = new Proveedor();
            p1.setNombre("TechSupply SpA");
            p1.setEmail("contacto@techsupply.cl");
            p1.setTelefono("+56912345678");
            p1.setAniosExperiencia(10);
            p1.setActivo(true);
            p1.setFechaRegistro(LocalDate.of(2020, 1, 15));
            proveedorRepository.save(p1);

            Proveedor p2 = new Proveedor();
            p2.setNombre("LogiPro Chile");
            p2.setEmail("ventas@logipro.cl");
            p2.setTelefono("+56987654321");
            p2.setAniosExperiencia(5);
            p2.setActivo(true);
            p2.setFechaRegistro(LocalDate.of(2022, 3, 10));
            proveedorRepository.save(p2);

            Proveedor p3 = new Proveedor();
            p3.setNombre("Distribuidora Norte");
            p3.setEmail("info@distnorte.cl");
            p3.setTelefono("+56911223344");
            p3.setAniosExperiencia(2);
            p3.setActivo(false);
            p3.setFechaRegistro(LocalDate.of(2023, 6, 1));
            proveedorRepository.save(p3);

            log.info("[ProveedorRunner] 3 proveedores insertados");

            if (contratoRepository.count() == 0) {
                Contrato c1 = new Contrato();
                c1.setNumeroContrato("CONT-2025-001");
                c1.setDescripcion("Suministro de equipos computacionales");
                c1.setMontoTotal(15000000.0);
                c1.setActivo(true);
                c1.setFechaInicio(LocalDate.of(2025, 1, 1));
                c1.setFechaFin(LocalDate.of(2026, 12, 31));
                c1.setProveedor(p1);
                contratoRepository.save(c1);

                Contrato c2 = new Contrato();
                c2.setNumeroContrato("CONT-2025-002");
                c2.setDescripcion("Servicio de logistica y despacho");
                c2.setMontoTotal(8500000.0);
                c2.setActivo(true);
                c2.setFechaInicio(LocalDate.of(2025, 3, 1));
                c2.setFechaFin(LocalDate.of(2027, 3, 1));
                c2.setProveedor(p2);
                contratoRepository.save(c2);

                Contrato c3 = new Contrato();
                c3.setNumeroContrato("CONT-2024-003");
                c3.setDescripcion("Distribucion regional zona norte");
                c3.setMontoTotal(3200000.0);
                c3.setActivo(false);
                c3.setFechaInicio(LocalDate.of(2024, 6, 1));
                c3.setFechaFin(LocalDate.of(2026, 6, 1));
                c3.setProveedor(p3);
                contratoRepository.save(c3);

                log.info("[ProveedorRunner] 3 contratos insertados");
            }
        } else {
            log.info("[ProveedorRunner] Datos ya existen, no se insertan duplicados");
        }
    }
}