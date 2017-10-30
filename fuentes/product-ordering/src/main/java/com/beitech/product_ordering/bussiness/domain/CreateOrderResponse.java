package com.beitech.product_ordering.bussiness.domain;

import java.util.ArrayList;
import java.util.List;

public class CreateOrderResponse {
	
	public static final String errorClienteNoExiste = "No se ha registrado la orden porque el cliente no existe";
	public static final String errorClienteNulo = "No se ha registrado la orden porque el parametro id_cliente no es valido";
	public static final String errorOrdenSinProductos = "No se ha registrado la orden porque no hay productos validos asociados";
	public static final String ordenRegistrada = "Se ha registrado la orden";

	private String resultado;
	private List<String> errores;
	
	public CreateOrderResponse() {
		setResultado("");
		setErrores(new ArrayList<String>());
	}
	
	public void addError(String error) {
		this.errores.add(error);
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public List<String> getErrores() {
		return errores;
	}

	public void setErrores(List<String> errores) {
		this.errores = errores;
	}
	
	
}
