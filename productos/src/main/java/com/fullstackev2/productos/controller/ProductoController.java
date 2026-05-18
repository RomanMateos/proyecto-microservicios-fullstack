package com.fullstackev2.productos.controller;


import com.fullstackev2.productos.dto.ProductoDTO;
import com.fullstackev2.productos.service.ProductoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;
@Slf4j
@RestController
@RequestMapping("api/v1")
public class ProductoController {
    @Autowired
    private ProductoService productoService;
    @GetMapping("/productos")
    public ResponseEntity<List<ProductoDTO>> obtenerProductos(){
        log.info("[Producto Controller] Iniciando obtener productos");
        List<ProductoDTO> productos = productoService.obtenerProductos();
        return ResponseEntity.ok(productos);
    }
    @GetMapping("/productos/{id}")
    public ResponseEntity<ProductoDTO> buscarPorId(@PathVariable Integer id){
        log.info("[Producto Controller] Iniciando buscar por id");
        Optional<ProductoDTO> producto = productoService.buscarPorId(id);
        return producto
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping("/productos")
    public ResponseEntity<ProductoDTO> guardar(@Valid @RequestBody ProductoDTO producto){
        log.info("[Producto Controller] Iniciando guardar producto");
        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.guardar(producto));
    }
    @PutMapping("/productos/{id}")
    public ResponseEntity<ProductoDTO> actualizar(@PathVariable Integer id,@Valid @RequestBody ProductoDTO producto){
        log.info("[Producto Controller] Iniciando actualizar producto");
        return productoService.actualizarPorId(id,producto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/productos/{id}")
    public ResponseEntity<Void> eliminarPorId(@PathVariable Integer id){
        log.info("[Producto Controller] Iniciando eliminar por id");
        boolean eliminado = productoService.eliminarPorId(id);
        if(eliminado){return ResponseEntity.noContent().build();}
        return ResponseEntity.notFound().build();

    }
    @GetMapping("/productos/buscar")
    public ResponseEntity<List<ProductoDTO>> buscarPorNombreYPrecio(@RequestParam String nombreProducto,@RequestParam Double precio){
        log.info("[Producto Controller] Iniciando buscar por nombre");
        List<ProductoDTO> productos = productoService.buscarPorNombreYPrecio(nombreProducto,precio);
        return ResponseEntity.ok(productos);
    }

}
