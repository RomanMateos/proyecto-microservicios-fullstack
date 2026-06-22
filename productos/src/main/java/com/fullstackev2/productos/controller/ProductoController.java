package com.fullstackev2.productos.controller;


import com.fullstackev2.productos.dto.ProductoDTO;
import com.fullstackev2.productos.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Productos", description = "Operaciones CRUD de productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("/productos")
    @Operation(
            summary = "Listar productos",
            description = "Obtiene todos los productos registrados en el sistema"
    )
    @ApiResponse(responseCode = "200", description = "Productos obtenidos correctamente")
    @ApiResponse(responseCode = "404", description = "Productos no encontrados")
    @ApiResponse(responseCode = "500", description = "Error inesperado")
    public ResponseEntity<List<ProductoDTO>> obtenerProductos() {
        log.info("[Producto Controller] Iniciando obtener productos");
        List<ProductoDTO> productos = productoService.obtenerProductos();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/productos/{id}")
    @Operation(
            summary = "Buscar producto por ID",
            description = "Obtiene un producto por su identificador único"
    )
    @ApiResponse(responseCode = "200", description = "Producto encontrado correctamente")
    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    @ApiResponse(responseCode = "500", description = "Error inesperado")
    public ResponseEntity<ProductoDTO> buscarPorId(@PathVariable Integer id) {
        log.info("[Producto Controller] Iniciando buscar por id");
        Optional<ProductoDTO> producto = productoService.buscarPorId(id);
        return producto
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/productos")
    @Operation(
            summary = "Guardar producto",
            description = "Registra un nuevo producto en la base de datos"
    )
    @ApiResponse(responseCode = "201", description = "Producto creado correctamente")
    @ApiResponse(responseCode = "400", description = "Uno o más datos ingresados son inválidos")
    @ApiResponse(responseCode = "500", description = "Error inesperado")
    public ResponseEntity<ProductoDTO> guardar(@Valid @RequestBody ProductoDTO producto) {
        log.info("[Producto Controller] Iniciando guardar producto");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productoService.guardar(producto));
    }

    @PutMapping("/productos/{id}")
    @Operation(
            summary = "Actualizar producto",
            description = "Actualiza los datos de un producto registrado previamente"
    )
    @ApiResponse(responseCode = "200", description = "Producto actualizado correctamente")
    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    @ApiResponse(responseCode = "400", description = "Uno o más datos ingresados son inválidos")
    @ApiResponse(responseCode = "500", description = "Error inesperado")
    public ResponseEntity<ProductoDTO> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody ProductoDTO producto
    ) {
        log.info("[Producto Controller] Iniciando actualizar producto");
        return productoService.actualizarPorId(id, producto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/productos/{id}")
    @Operation(
            summary = "Eliminar producto",
            description = "Elimina un producto registrado previamente mediante su identificador único"
    )
    @ApiResponse(responseCode = "204", description = "Producto eliminado correctamente")
    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    @ApiResponse(responseCode = "500", description = "Error inesperado")
    public ResponseEntity<Void> eliminarPorId(@PathVariable Integer id) {
        log.info("[Producto Controller] Iniciando eliminar por id");
        boolean eliminado = productoService.eliminarPorId(id);

        if (eliminado) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/productos/buscar")
    @Operation(
            summary = "Buscar producto por nombre y precio",
            description = "Busca productos filtrando por nombre del producto y precio"
    )
    @ApiResponse(responseCode = "200", description = "Búsqueda realizada correctamente")
    @ApiResponse(responseCode = "404", description = "Productos no encontrados")
    @ApiResponse(responseCode = "500", description = "Error inesperado")
    public ResponseEntity<List<ProductoDTO>> buscarPorNombreYPrecio(
            @RequestParam String nombreProducto,
            @RequestParam Double precio
    ) {
        log.info("[Producto Controller] Iniciando buscar por nombre y precio");
        List<ProductoDTO> productos = productoService.buscarPorNombreYPrecio(nombreProducto, precio);
        return ResponseEntity.ok(productos);
    }
}