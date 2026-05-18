package com.fullstackev2.sucursales.runner;

import com.fullstackev2.sucursales.model.Region;
import com.fullstackev2.sucursales.model.Sucursal;
import com.fullstackev2.sucursales.repository.RegionRepository;
import com.fullstackev2.sucursales.repository.SucursalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@Slf4j
public class SucursalRunner implements CommandLineRunner {

    private final RegionRepository regionRepository;
    private final SucursalRepository sucursalRepository;

    @Override
    public void run(String... args) throws Exception {
        if (regionRepository.count() == 0) {
            log.info("[SucursalRunner] Insertando datos iniciales de regiones...");

            Region r1 = new Region();
            r1.setNombre("Región Metropolitana");
            r1.setCodigo("RM");
            r1.setDescripcion("Region capital de Chile");
            r1.setNumeroComunas(52);
            r1.setActivo(true);
            r1.setFechaCreacion(LocalDate.of(2020, 1, 1));
            regionRepository.save(r1);

            Region r2 = new Region();
            r2.setNombre("Región de Valparaíso");
            r2.setCodigo("V");
            r2.setDescripcion("Region costera del centro de Chile");
            r2.setNumeroComunas(38);
            r2.setActivo(true);
            r2.setFechaCreacion(LocalDate.of(2020, 1, 1));
            regionRepository.save(r2);

            Region r3 = new Region();
            r3.setNombre("Región del Biobío");
            r3.setCodigo("VIII");
            r3.setDescripcion("Region del sur de Chile");
            r3.setNumeroComunas(33);
            r3.setActivo(false);
            r3.setFechaCreacion(LocalDate.of(2020, 1, 1));
            regionRepository.save(r3);

            log.info("[SucursalRunner] 3 regiones insertadas");

            if (sucursalRepository.count() == 0) {
                Sucursal s1 = new Sucursal();
                s1.setNombre("Sucursal Santiago Centro");
                s1.setDireccion("Av. Libertador Bernardo O'Higgins 1234");
                s1.setTelefono("+56222345678");
                s1.setCapacidad(50);
                s1.setActivo(true);
                s1.setFechaApertura(LocalDate.of(2021, 3, 15));
                s1.setRegion(r1);
                sucursalRepository.save(s1);

                Sucursal s2 = new Sucursal();
                s2.setNombre("Sucursal Viña del Mar");
                s2.setDireccion("Av. Libertad 567");
                s2.setTelefono("+56322456789");
                s2.setCapacidad(30);
                s2.setActivo(true);
                s2.setFechaApertura(LocalDate.of(2022, 6, 1));
                s2.setRegion(r2);
                sucursalRepository.save(s2);

                Sucursal s3 = new Sucursal();
                s3.setNombre("Sucursal Concepción");
                s3.setDireccion("Calle Freire 890");
                s3.setTelefono("+56412567890");
                s3.setCapacidad(25);
                s3.setActivo(false);
                s3.setFechaApertura(LocalDate.of(2020, 9, 10));
                s3.setRegion(r3);
                sucursalRepository.save(s3);

                log.info("[SucursalRunner] 3 sucursales insertadas");
            }
        } else {
            log.info("[SucursalRunner] Datos ya existen, no se insertan duplicados");
        }
    }
}