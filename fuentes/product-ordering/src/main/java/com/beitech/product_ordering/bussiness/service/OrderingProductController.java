package com.beitech.product_ordering.bussiness.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.beitech.product_ordering.bussiness.domain.CreateOrderResponse;
import com.beitech.product_ordering.bussiness.domain.SearchOrdersOrderResponse;
import com.beitech.product_ordering.bussiness.domain.SearchOrdersResponse;
import com.beitech.product_ordering.bussiness.service.Customer;
import com.beitech.product_ordering.bussiness.service.Order;
import com.beitech.product_ordering.bussiness.service.OrderDetail;
import com.beitech.product_ordering.bussiness.service.OrderDetailPK;
import com.beitech.product_ordering.bussiness.service.Product;
import com.beitech.product_ordering.bussiness.service.ProductCustomer;
import com.beitech.product_ordering.bussiness.service.ProductCustomerPK;

@RestController
public class OrderingProductController {

	@PersistenceContext
	private EntityManager em;

	private Order orden;
	private Customer cliente;
	private Product producto;
	private ProductCustomer productoCliente;
	private ProductCustomerPK pkProductoCliente;
	private OrderDetail detalleOrden;
	private OrderDetailPK pkDetalleOrden;
	private CreateOrderResponse createOrderResponse;
	private List<Integer> idProductos;
	private List<Integer> cantProductos;
	
	private SearchOrdersResponse searchOrdersResponse;
	private SearchOrdersOrderResponse searchOrdersOrderResponse;
	@Transactional
	@RequestMapping("/crear_orden")
	public CreateOrderResponse crearOrden(@RequestParam(value = "id_cliente", required = true) String idCliente,
			@RequestParam(value = "direccion", required = false) String direccion,
			@RequestParam(value = "prod1", required = true) String producto1,
			@RequestParam(value = "prod2", required = false) String producto2,
			@RequestParam(value = "prod3", required = false) String producto3,
			@RequestParam(value = "prod4", required = false) String producto4,
			@RequestParam(value = "prod5", required = false) String producto5,
			@RequestParam(value = "cant1", required = true) String cantidadProducto1,
			@RequestParam(value = "cant2", required = false) String cantidadProducto2,
			@RequestParam(value = "cant3", required = false) String cantidadProducto3,
			@RequestParam(value = "cant4", required = false) String cantidadProducto4,
			@RequestParam(value = "cant5", required = false) String cantidadProducto5) {

		try {
			idProductos = new ArrayList<Integer>();
			cantProductos = new ArrayList<Integer>();

			// Se crea el objeto de respuesta
			createOrderResponse = new CreateOrderResponse();

			// Se convierten parametros de string a entero
			if (producto1 != null) {
				idProductos.add(Integer.valueOf(producto1));
				cantProductos.add(Integer.valueOf(cantidadProducto1));
			}

			if (producto2 != null) {
				idProductos.add(Integer.valueOf(producto2));
				cantProductos.add(Integer.valueOf(cantidadProducto2));
			}

			if (producto3 != null) {
				idProductos.add(Integer.valueOf(producto3));
				cantProductos.add(Integer.valueOf(cantidadProducto3));
			}

			if (producto4 != null) {
				idProductos.add(Integer.valueOf(producto4));
				cantProductos.add(Integer.valueOf(cantidadProducto4));
			}

			if (producto5 != null) {
				idProductos.add(Integer.valueOf(producto5));
				cantProductos.add(Integer.valueOf(cantidadProducto5));
			}

			if (idCliente != null) {
				// Se busca el cliente en la BD
				cliente = em.find(Customer.class, Integer.valueOf(idCliente));
			} else {
				throw new Exception(CreateOrderResponse.errorClienteNulo);
			}

			// La creacion de la orden comienza si el cliente existe
			if (cliente != null) {

				// Se crea la orden
				orden = new Order();
				orden.setCustomer(cliente);
				orden.setCreationDate(new Date());
				orden.setDeliveryAddress(direccion);
				orden.setOrderDetails(new ArrayList<OrderDetail>());

				// Se validan los detalles de la orden
				for (int i = 0; i < idProductos.size(); i++) {

					producto = em.find(Product.class, idProductos.get(i));

					// Solo se almacenan detalles cuyo producto este registrado
					if (producto != null) {

						pkProductoCliente = new ProductCustomerPK();
						pkProductoCliente.setCustomerId(cliente.getCustomerId());
						pkProductoCliente.setProductId(producto.getProductId());

						productoCliente = em.find(ProductCustomer.class, pkProductoCliente);

						// El producto solicitado debe estar asociado al cliente
						if (productoCliente != null) {
							detalleOrden = new OrderDetail();
							detalleOrden.setOrderDetailPrice(cantProductos.get(i) * producto.getPrice());
							detalleOrden.setProductCustomer(productoCliente);
							detalleOrden.setProductQuantity(cantProductos.get(i));

							pkDetalleOrden = new OrderDetailPK();
							pkDetalleOrden.setCustomerId(cliente.getCustomerId());
							pkDetalleOrden.setProductId(producto.getProductId());

							detalleOrden.setId(pkDetalleOrden);
							orden.addOrderDetail(detalleOrden);
						} else {
							createOrderResponse.addError("No se ha incluido dentro de la orden el producto " + producto.getProductId() + " ("
									+ producto.getName() + ") porque no esta permitido para el cliente "
									+ cliente.getCustomerId() + " (" + cliente.getName() + ")");
							break;
						}
					} else {
						createOrderResponse.addError("No se ha incluido el producto " + idProductos.get(i) + " porque no existe");
						break;
					}
				}

				// Se registra la orden (solo si tiene al menos un detalle asociado)
				if (orden.getOrderDetails().size() > 0) {
					em.persist(orden);
					em.flush();
				} else {
					throw new Exception(CreateOrderResponse.errorOrdenSinProductos);
				}

				// Se registran los detalles de la orden en la BD
				for (OrderDetail orderDetail : orden.getOrderDetails()) {
					orderDetail.getId().setOrderId(orden.getOrderId());
					em.persist(orderDetail);
					em.flush();
				}
				createOrderResponse.setResultado(CreateOrderResponse.ordenRegistrada);

			} else {
				createOrderResponse.addError(CreateOrderResponse.errorClienteNoExiste);
			}
		} catch (Exception ex) {
			createOrderResponse.addError(ex.getMessage());
		}

		return createOrderResponse;
	}
	
	@Transactional
	@RequestMapping("/listar_ordenes")
	public SearchOrdersResponse searchOrders(@RequestParam(value = "id_cliente", required = true) String idCliente,
								@RequestParam(value = "fecha_ini", required = true) String fechaIni,
								@RequestParam(value = "fecha_fin", required = true) String fechaFin) {
		try {
			
			//Se crea el objeto de respuesta
			this.searchOrdersResponse = new SearchOrdersResponse();
			
			int total;
			Product producto;
			
			TypedQuery<Order> queryOrdenes;
			List<Order> ordenes;
			
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			Date fechaInicio = formatter.parse(fechaIni);
			Date fechaFinal = formatter.parse(fechaFin);
			
			this.cliente = em.find(Customer.class, Integer.valueOf(idCliente));

			queryOrdenes = em.createNamedQuery("Order.findByCustomerId", Order.class);
			queryOrdenes.setParameter("customer", this.cliente);
			queryOrdenes.setParameter("startDate", fechaInicio);
			queryOrdenes.setParameter("endDate", fechaFinal);
			ordenes = queryOrdenes.getResultList();
			
			for (Order orden : ordenes) {
				total = 0;
				searchOrdersOrderResponse = new SearchOrdersOrderResponse();
				searchOrdersOrderResponse.setCreationDate(orden.getCreationDate());
				searchOrdersOrderResponse.setDeliveryAdress(orden.getDeliveryAddress());
				searchOrdersOrderResponse.setOrderId(orden.getOrderId());
				
				for (OrderDetail detalleOrden : orden.getOrderDetails()) {
					producto = em.find(Product.class, detalleOrden.getId().getProductId());
					total += detalleOrden.getOrderDetailPrice();
					
					searchOrdersOrderResponse.addProduct(detalleOrden.getProductQuantity(), producto.getName());
				}
				
				searchOrdersOrderResponse.setTotal(total);
				searchOrdersResponse.addOrder(searchOrdersOrderResponse);
			}
			
		} catch(Exception ex) {
			System.out.println(ex);
			ex.printStackTrace();
			this.searchOrdersResponse.setOrders(null);;
		}
		
		return this.searchOrdersResponse;
	}
}
