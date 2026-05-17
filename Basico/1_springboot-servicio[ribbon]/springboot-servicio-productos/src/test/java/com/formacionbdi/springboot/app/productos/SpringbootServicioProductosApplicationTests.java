package com.formacionbdi.springboot.app.productos;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.formacionbdi.springboot.app.productos.models.entity.Producto;
import com.formacionbdi.springboot.app.productos.models.service.IProductoService;

@SpringBootTest
class SpringbootServicioProductosApplicationTests {

    @Autowired
    private IProductoService productoService;

    @Test
    void testCreate() {
        Producto p = new Producto();
        p.setMarca("Honda");
        p.setModelo("Civic");
        p.setAnio(2026);
        p.setPrecio(450000.0);
        
        Producto autoGuardado = productoService.save(p);
        assertNotNull(autoGuardado.getId(), "El ID no debe ser nulo al guardar un auto");
    }

    @Test
    void testRead() {
        List<Producto> productos = productoService.findAll();
        assertTrue(productos.size() > 0, "La lista de productos no debería estar vacía");
    }

    @Test
    void testUpdate() {
        Producto p = productoService.findById(1L);
        assertNotNull(p, "El producto con ID 1 debe existir");
        
        Double nuevoPrecio = 1500000.0;
        p.setPrecio(nuevoPrecio);
        productoService.save(p);
        
        Producto actualizado = productoService.findById(1L);
        assertEquals(nuevoPrecio, actualizado.getPrecio(), "El precio debería haberse actualizado al nuevo valor");
    }

    @Test
    void testDelete() {
        Producto p = new Producto();
        p.setMarca("Nissan");
        p.setModelo("Versa");
        p.setAnio(2025);
        p.setPrecio(300000.0);
        Producto guardado = productoService.save(p);
        
        Long idPrueba = guardado.getId();
        assertNotNull(idPrueba);
        
        productoService.delete(idPrueba);
        
        Producto borrado = productoService.findById(idPrueba);
        assertNull(borrado, "El producto debería ser nulo después de ser eliminado");
    }

}
