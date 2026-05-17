package com.formacionbdi.springboot.app.productos.controllers;


import java.util.List;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.bind.annotation.RequestMapping;
import com.formacionbdi.springboot.app.productos.models.entity.Producto;
import com.formacionbdi.springboot.app.productos.models.service.IProductoService;

@Controller
@RequestMapping("")
public class ProductoController {
	
	
	@Autowired
	private IProductoService productoService;
	
	@Autowired
	private Environment env;
	
	@Value("${server.port}")
	private String port;
	
	
	@GetMapping({"/", "/listar", ""})
    public String listar(Model model) {
        model.addAttribute("productos", productoService.findAll());
        return "lista-autos";
    }
    
    @GetMapping("/ver/{id}")
    @ResponseBody
	public Producto detalle(@PathVariable Long id) {
		Producto producto = productoService.findById(id);
		producto.setPort(Integer.parseInt(env.getProperty("local.server.port")));
		//producto.setPort(port);
		
		
		  try {
			Thread.sleep(1500L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return producto;
	}
	
	@GetMapping("/form")
    public String crear(Model model) {
        model.addAttribute("producto", new Producto());
        return "form-auto";
    }

    @GetMapping("/form/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Producto producto = productoService.findById(id);
        if(producto == null) {
            return "redirect:/listar";
        }
        model.addAttribute("producto", producto);
        return "form-auto";
    }

    @PostMapping("/form")
    public String guardar(Producto producto) {
    	if (producto.getId() != null) {
            Producto productoOriginal = productoService.findById(producto.getId());
            producto.setCreateAt(productoOriginal.getCreateAt());
        }
        productoService.save(producto);
        return "redirect:/listar";
    }

    @GetMapping("/eliminar/{id}")
    public String borrarWeb(@PathVariable Long id) {
        productoService.delete(id);
        return "redirect:/listar";
    }
	
}
