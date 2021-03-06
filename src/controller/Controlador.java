package controller;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import model.Cliente;
import javax.swing.table.TableRowSorter;
import model.OrdenCompra;
import model.Pelicula;
import model.Sala;
import model.Sala2D;
import model.Sala3D;
import model.Sala4DX;
import model.Sucursal;
import model.Ticket;
import model.Ticket2D;
import model.Ticket3D;
import model.Ticket4DX;
import structures.ArbolBB;
import structures.ListaDoble;
import structures.NodoDoble;
import view.*;

public class Controlador {
    // Creación de los 2 árboles principales del proyecto
    public static ArbolBB sucursales = new ArbolBB();
    public static ArbolBB clientes = new ArbolBB();
    public static ListaDoble<Pelicula> peliculas = new ListaDoble<>();
    //Esta estructura es solo para poder filtrar las tables :D
    List<RowFilter<Object,Object>> filtros = new ArrayList<RowFilter<Object,Object>>(2);
    
    public void abrirCarrito(Principal principal){
        //Se verifica si se seleccionó algún cliente
        if(principal.tableClientes.getSelectedRow() == -1){
            JOptionPane.showMessageDialog(principal, "Seleccione un Cliente para abrir el Carrito", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        //Se busca el cliente en el ABB "clientes" y se abre el carrito
        Cliente cliente = clientes.buscarCliente(clientes.getRoot(), Long.parseLong(String.valueOf( principal.tableClientes.getValueAt(principal.tableClientes.getSelectedRow(), 1) )));
        cliente.getCarrito().setPrincipal(principal);
        cliente.getCarrito().setControlador(this);
        cliente.getCarrito().iniciarCarrito();
        
        //Se vacía la tabla
        ((DefaultTableModel)cliente.getCarrito().tableCarrito.getModel()).setRowCount(0);
        
        //Se declara una orden auxiliar
        OrdenCompra aux;
        
        //Se le asigna una orden a aux y se muestra en la tabla del carrito
        for (int i = 0; i < cliente.getCarrito().getOrdenes().size(); i++) {
            aux = cliente.getCarrito().getOrdenes().getFirst().getData();
            mostrarOrdenEnTablaCarrito(cliente.getCarrito(), aux);
            cliente.getCarrito().getOrdenes().enqueue(cliente.getCarrito().getOrdenes().dequeue());
        }
        
        //Se limpia la selección de la tabla, se muestra en pantalla el GUI del Carrito
        principal.tableClientes.clearSelection();
        cliente.getCarrito().setVisible(true);
        principal.setVisible(false);
    }
    
    public void abrirPrincipal(){
        // Abre la ventana del JFrame Principal
        Principal principal = new Principal(this);
        principal.setVisible(true);
        
        // Creación de las Sucursales iniciales
            Sucursal sucursal1 = new Sucursal("La Bombilla");
                this.crearSucursal(sucursal1, principal);
            Sucursal sucursal2 = new Sucursal("Los Ocumitos");
                this.crearSucursal(sucursal2, principal);
            Sucursal sucursal3 = new Sucursal("Sótanos del Ávila");
                this.crearSucursal(sucursal3, principal);
                
        // Creacion de las Películas iniciales
            Pelicula pelicula1 = new Pelicula("Proyecto: 20 khe", "Acción", "Español");
                peliculas.addLast(pelicula1);
                this.crearPelicula(pelicula1, principal);
            Pelicula pelicula2 = new Pelicula("Quintero Entero", "Aventura", "Inglés");
                peliculas.addLast(pelicula2);
                this.crearPelicula(pelicula2, principal);
            Pelicula pelicula3 = new Pelicula("Quevetronix", "Suspenso", "Francés");
                peliculas.addLast(pelicula3);
                this.crearPelicula(pelicula3, principal);
            Pelicula pelicula4 = new Pelicula("Rafushi vs Carlushi", "Amor", "Español");
                peliculas.addLast(pelicula4);
                this.crearPelicula(pelicula4, principal);
            Pelicula pelicula5 = new Pelicula("50 Sombras de Fontes", "Acción", "Inglés");
                peliculas.addLast(pelicula5);
                this.crearPelicula(pelicula5, principal);
            
        // Creacion de las Salas iniciales
            Sala2D sala1 = new Sala2D(1);
                sala1.setPelicula(pelicula1);  // Seteamos la pelicula que tendrá la Sala
                this.crearSalaInicio(sala1, sucursal1, principal);
            Sala2D sala2 = new Sala2D(2);
                sala2.setPelicula(pelicula2);  // Seteamos la pelicula que tendrá la Sala
                this.crearSalaInicio(sala2, sucursal1, principal);
            Sala3D sala3 = new Sala3D(3);
                sala3.setPelicula(pelicula3);  // Seteamos la pelicula que tendrá la Sala
                this.crearSalaInicio(sala3, sucursal1, principal);
            Sala4DX sala4 = new Sala4DX(4);
                sala4.setPelicula(pelicula4);  // Seteamos la pelicula que tendrá la Sala
                this.crearSalaInicio(sala4, sucursal1, principal);
            Sala4DX sala5 = new Sala4DX(5);
                sala5.setPelicula(pelicula5);  // Seteamos la pelicula que tendrá la Sala
                this.crearSalaInicio(sala5, sucursal1, principal);
                
            Sala4DX sala6 = new Sala4DX(1);
                sala6.setPelicula(pelicula3);  // Seteamos la pelicula que tendrá la Sala
                this.crearSalaInicio(sala6, sucursal2, principal);
            Sala2D sala7 = new Sala2D(2);
                sala7.setPelicula(pelicula2);  // Seteamos la pelicula que tendrá la Sala
                this.crearSalaInicio(sala7, sucursal2, principal);
            Sala3D sala8 = new Sala3D(3);
                sala8.setPelicula(pelicula5);  // Seteamos la pelicula que tendrá la Sala
                this.crearSalaInicio(sala8, sucursal2, principal);
            Sala3D sala9 = new Sala3D(4);
                sala9.setPelicula(pelicula1);  // Seteamos la pelicula que tendrá la Sala
                this.crearSalaInicio(sala9, sucursal2, principal);
            Sala2D sala10 = new Sala2D(5);
                sala10.setPelicula(pelicula1);  // Seteamos la pelicula que tendrá la Sala
                this.crearSalaInicio(sala10, sucursal2, principal);
                
            Sala3D sala11 = new Sala3D(1);
                sala11.setPelicula(pelicula3);  // Seteamos la pelicula que tendrá la Sala
                this.crearSalaInicio(sala11, sucursal3, principal);
            Sala2D sala12 = new Sala2D(2);
                sala12.setPelicula(pelicula2);  // Seteamos la pelicula que tendrá la Sala
                this.crearSalaInicio(sala12, sucursal3, principal);
            Sala4DX sala13 = new Sala4DX(3);
                sala13.setPelicula(pelicula5);  // Seteamos la pelicula que tendrá la Sala
                this.crearSalaInicio(sala13, sucursal3, principal);
            Sala4DX sala14 = new Sala4DX(4);
                sala14.setPelicula(pelicula3);  // Seteamos la pelicula que tendrá la Sala
                this.crearSalaInicio(sala14, sucursal3, principal);
            Sala3D sala15 = new Sala3D(5);
                sala15.setPelicula(pelicula4);  // Seteamos la pelicula que tendrá la Sala
                this.crearSalaInicio(sala15, sucursal3, principal);
                
            // Creación de los Clientes iniciales
            Cliente cliente1 = new Cliente(26476344, "Carlos Fontes", "04122569675");
                this.mostrarClienteEnTablaClientes(cliente1, principal);
            Cliente cliente2 = new Cliente(21688326, "Rafael Quintero", "04243659125");
                this.mostrarClienteEnTablaClientes(cliente2, principal);
            Cliente cliente3 = new Cliente(12561795, "José Quevedo", "04269517596");
                this.mostrarClienteEnTablaClientes(cliente3, principal);
            Cliente cliente4 = new Cliente(2916256, "Elsa Pito", "04164206969");
                this.mostrarClienteEnTablaClientes(cliente4, principal);
            Cliente cliente5 = new Cliente(19532106, "Alfom Brita", "04245281496");
                this.mostrarClienteEnTablaClientes(cliente5, principal);
                
            // Seteamos los precios iniciales de los Tickets
            Ticket2D.setPrecio(10.0);
                principal.textFieldPrecio2DT.setText("10.0");
            Ticket3D.setPrecio(15.0);
                principal.textFieldPrecio3DT.setText("15.0");
            Ticket4DX.setPrecio(25.0);
                principal.textFieldPrecio4DT.setText("25.0");
    }
    
    private void actualizarTablaSucursales(Principal principal){
        int aux = 0;
        // Modificamos el valor en la tabla
        aux = Integer.parseInt(String.valueOf( ((DefaultTableModel)principal.tableSucursales.getModel()).getValueAt(principal.tableSucursales.getSelectedRow(), 0) ) );
        principal.tableSucursales.setValueAt(sucursales.buscarSucursal(sucursales.getRoot(), aux).getUbicacion(), principal.tableSucursales.getSelectedRow(), 1);        
    }
    
    public void agregarAlCarrito(Principal principal){
        // Validamos que se haya elegido un Cliente, Sucursal, Sala y una cantidad de Tickets
        if(String.valueOf(principal.comboClientesV.getSelectedItem()).equals("Clientes")){
            JOptionPane.showMessageDialog(principal, "No puede procesar la compra si no elige un Cliente", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(String.valueOf(principal.comboSucursalesV.getSelectedItem()).equals("Sucursal")){
            JOptionPane.showMessageDialog(principal, "No puede procesar la compra si no elige la Sucursal/Sala", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(String.valueOf(principal.comboSalasV.getSelectedItem()).equals("Sala")){
            JOptionPane.showMessageDialog(principal, "No puede procesar la compra si no elige la Sucursal/Sala", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(Integer.parseInt(String.valueOf(principal.spinnerTicketsV.getValue())) == 0){
            JOptionPane.showMessageDialog(principal, "Seleccione una cantidad de Tickets distinta de cero", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Validamos la fecha
        String fecha;
        if(principal.fecha3.isSelected()){
            fecha = "03/04/2018";
        }else if(principal.fecha4.isSelected()){
            fecha = "04/04/2018";
        }else if(principal.fecha5.isSelected()){
            fecha = "05/04/2018";
        }else{
            JOptionPane.showMessageDialog(principal, "Seleccione una fecha para la función", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Se verifica si se comprará de una vez o no
        boolean pagada;
        try {
            int resp = JOptionPane.showConfirmDialog(principal, "¿Desea pagar los Tickets de una vez?", "Pago de Tickets", JOptionPane.YES_NO_OPTION);
            
            if(resp == JOptionPane.YES_OPTION){
                pagada = true;
            }else if(resp == JOptionPane.NO_OPTION){
                pagada = false;
            }else{
                return;
            }
        } catch (Exception e) {
            return;
        }
        
        // Se guardan los datos de la Orden de Compra
        Cliente cliente = clientes.buscarCliente(clientes.getRoot(), Long.parseLong(String.valueOf(principal.comboClientesV.getSelectedItem())));
        Sucursal sucursal = sucursales.buscarSucursal(sucursales.getRoot(), Integer.parseInt(String.valueOf(principal.comboSucursalesV.getSelectedItem())) );
        Sala sala = sucursal.getSalas().buscarSala(sucursal.getSalas().getRoot(), Integer.parseInt(String.valueOf(principal.comboSalasV.getSelectedItem())) );
        int cantidad = Integer.parseInt(String.valueOf(principal.spinnerTicketsV.getValue()));
        
        // Se crean los tickets
        ListaDoble tickets = new ListaDoble();
        
        // Se llena la ListaDoble con los tickets comprados
        for (int i = 0; i < cantidad; i++) {
            if(sala instanceof Sala2D){
                tickets.addLast(new Ticket2D(cliente, sucursal, sala, fecha));
            }else if(sala instanceof Sala3D){
                tickets.addLast(new Ticket3D(cliente, sucursal, sala, fecha));
            }else{
                tickets.addLast(new Ticket4DX(cliente, sucursal, sala, fecha));
            }
        }
        
        // Se crea la orden de compra con la Lista de Tickets
        OrdenCompra orden = new OrdenCompra(tickets);
        // Se verifica si se pagó de una y se llama al metodo setPagada
        if(pagada){
            orden.setPagada();
        }
        
        //Se encola la orden en la cola de ordenes del carrito del cliente
        cliente.getCarrito().getOrdenes().enqueue(orden);
        
        //Se muestra la orden en la tabla del carrito del cliente
        this.mostrarOrdenEnTablaCarrito(cliente.getCarrito(), orden);
        
        //Se agrega a las salas frecuentes los tickets generados a partir de la nueva orden de compra
        this.mostrarSalasFrecuentes(principal, sala, sucursal, cantidad);
        
        //Se ordena la tabla de salas frecuentes de mayor a menor
        this.ordenarTablaAdmin(principal);
        
        // Se retornan los valores por defecto de los campos
        principal.spinnerTicketsV.setValue(0);
        principal.comboSucursalesV.setSelectedItem("Sucursal");
        principal.spinnerTicketsV.setEnabled(false);
    }
    
    private void agregarATablaSucursales(Sucursal sucursal, DefaultTableModel model){
        // Agrega la sala creada en la tabla
        
        model.addRow(new Object[]{
            sucursal.getCodigo(), sucursal.getUbicacion()
        });
    }
    
    public void botonAgregarPelicula(Principal principal){
        // Se pide el nombre por JOptionPane y se valida que el nombre sea adecuado (ni 0 caracteres, ni mayor a 20)
        String nombre = JOptionPane.showInputDialog("     Ingrese el nombre de la película\n        (No más de 20 caracteres)");
        
        // Se valida que la película no exista
        NodoDoble peli = peliculas.getHead();
        for (int i = 0; i < peliculas.size(); i++) {
            if(nombre.equals(((Pelicula)peli.getData()).getNombre())){
                JOptionPane.showMessageDialog(principal, "Esa película ya existe", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            peli = peli.getNext();
        }
        
        try {
            if(nombre.length() > 20 || nombre.length() == 0){
                JOptionPane.showMessageDialog(principal, "Ingrese el nombre de la película siguiendo las instrucciones", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(principal, "Ingrese el nombre de la película siguiendo las instrucciones", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Declaramos los idiomas y los géneros
        String[] idiomas = {"Español", "Inglés", "Francés"};
        String[] generos = {"Acción", "Amor", "Suspenso", "Aventura"};
        // Guardamos las variables en un JOption con ComboBox
        String idioma = (String)JOptionPane.showInputDialog(principal, "   Elija el idioma de la Película", "Selección Idioma", JOptionPane.QUESTION_MESSAGE, null, idiomas, idiomas[0]);
        String genero = (String)JOptionPane.showInputDialog(principal, "   Elija el género de la Película", "Selección Género", JOptionPane.QUESTION_MESSAGE, null, generos, generos[0]); 
        
        // Llamamos al metodo crearPelicula y añadimos la pelicula a la lista de peliculas
        Pelicula pelicula = new Pelicula(nombre, genero, idioma);
        peliculas.addLast(pelicula);
        this.crearPelicula(pelicula, principal);
    }
    
    public void botonCambiarPeliculasSalas(Principal principal){
        // Se verifica en que sucursal está
        if(String.valueOf(principal.comboSucursalesSalas.getSelectedItem()).equals("Sucursal")){
            JOptionPane.showMessageDialog(principal, "No se encuentra en ninguna Sucursal.\n             Seleccione una.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }else if(principal.tableSalas.getSelectedRow() == -1){
            JOptionPane.showMessageDialog(principal, "No seleccionó ninguna Sala.\n           Seleccione una.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }else if(String.valueOf(principal.comboPeliculasSa1.getSelectedItem()).equals("Películas") ){
            JOptionPane.showMessageDialog(principal, "Seleccione la película que desea poner.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }else{
            Sucursal sucursal = sucursales.buscarSucursal( sucursales.getRoot(), Integer.parseInt( String.valueOf( principal.comboSucursalesSalas.getSelectedItem() ) ) );
            Sala sala = sucursal.getSalas().buscarSala(sucursal.getSalas().getRoot(), principal.tableSalas.getSelectedRow() + 1);
            Pelicula pelicula = peliculas.buscarPelicula(String.valueOf(principal.comboPeliculasSa1.getSelectedItem()));
            // Modificamos la pelicula
            sala.setPelicula(pelicula);
            // Modificamos el valor en la tabla
            principal.tableSalas.setValueAt(sala.getPelicula().getNombre(), sala.getNumero() - 1, 2);
            
            principal.tableSalas.clearSelection();
            
            //Se declaran variables auxiliares
            Carrito cauxrrito;
            OrdenCompra compraux;
            NodoDoble<Ticket> aux;
            
            //Se recorren los clientes del arbol "clientes"
            for (int i = 0; i<principal.tableClientes.getModel().getRowCount(); i++) {
                //Se guarda el carrito del cliente
                cauxrrito = clientes.buscarCliente(clientes.getRoot(), Long.parseLong(String.valueOf(principal.tableClientes.getValueAt(i, 1)))).getCarrito();
                
                for (int j = 0; j < cauxrrito.getOrdenes().size(); j++) {
                    //Se guarda la orden de compra
                    compraux = cauxrrito.getOrdenes().getFirst().getData();
                    aux = compraux.getTickets().getHead();
                    //Si la película cambiada, tenía una orden de compra, se elimina esa orden
                    if(!aux.getData().getPelicula().equals(aux.getData().getSala().getPelicula().getNombre())){
                        cauxrrito.getOrdenes().dequeue();
                    }else{
                     //Se recorre la cola de ordenes   
                     cauxrrito.getOrdenes().enqueue(cauxrrito.getOrdenes().dequeue());   
                    }
                }
            }
            
            //Se declaran variables auxiliares
            String sucursaux, salaux;
            
            //Si existe una sala en una sucursal afectada por el cambio de película en las Salas Frecuentes, se borra la sala de frecuentes
            for (int i = 0; i < ((DefaultTableModel)principal.tableAdmin.getModel()).getRowCount(); i++) {
                sucursaux = String.valueOf(((DefaultTableModel)principal.tableAdmin.getModel()).getValueAt(i, 0));
                salaux = String.valueOf(((DefaultTableModel)principal.tableAdmin.getModel()).getValueAt(i, 1));
                //Consigue la fila donde está la sala afectada y la elimina
                if(sucursal.getUbicacion().equals(sucursaux) && String.valueOf(sala.getNumero()).equals(salaux)){
                    ((DefaultTableModel)principal.tableAdmin.getModel()).removeRow(i);
                    break;
                }
            }
        }
    }
    
    public void buscarCliente(Principal principal){
        // Se verifica si se ingresó alguna cédula
        if(principal.textFieldCedulaC.getText().equals("Ingrese Cédula")){
            JOptionPane.showMessageDialog(principal, "Ingrese la cédula del Cliente que desee buscar", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Se verifica si la cédula ingresada pertenece al de algun Cliente
        String cedulaBuscar = String.valueOf(principal.textFieldCedulaC.getText());
        for (int i = 0; i < principal.tableClientes.getRowCount(); i++) {
            if(String.valueOf(principal.tableClientes.getValueAt(i, 1)).contains(cedulaBuscar)){
                principal.tableClientes.changeSelection(i, 1, false, false);
                principal.textFieldCedulaC.setText("Ingrese Cédula");
                return;
            }
        }
        
       JOptionPane.showMessageDialog(principal, "La Cédula que ingresó no pertenece a la de ningún Cliente", "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    public void buscarClienteVentas(Principal principal){
        // Se verifica si se ingresó alguna cédula
        if(principal.textFieldClienteV.getText().equals("Ingrese Cédula")){
            JOptionPane.showMessageDialog(principal, "Ingrese la cédula del Cliente que desee buscar", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Se verifica si la cédula ingresada pertenece al de algun Cliente
        long cedulaBuscar = Long.parseLong(String.valueOf(principal.textFieldClienteV.getText()));
        if(clientes.buscarCliente(clientes.getRoot(), cedulaBuscar) == null){
            JOptionPane.showMessageDialog(principal, "La Cédula que ingresó no pertenece a la de ningún Cliente", "Error", JOptionPane.ERROR_MESSAGE);
            principal.textFieldClienteV.setText("Ingrese Cédula");
            return;
        }
        
        // Se selecciona el cliente buscado en el ComboBox
        for (int i = 0; i < principal.comboClientesV.getItemCount(); i++) {
            if(String.valueOf(principal.comboClientesV.getItemAt(i)).equals(String.valueOf(cedulaBuscar)) ){
                principal.comboClientesV.setSelectedIndex(i);
            }
        }
        
        principal.textFieldClienteV.setText("Ingrese Cédula");
        
    }
    
    public void buscarPelicula(Principal principal){
        // Se verifica si se ingresó algún nombre
        if(principal.textFieldPeliculaP.getText().equals("Ingrese Película")){
            JOptionPane.showMessageDialog(principal, "Ingrese el nombre de la Película que desee buscar", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Se verifica si el nombre ingresado pertenece al de alguna película
        String nombreBuscar = principal.textFieldPeliculaP.getText();
        
        for (int i = 0; i < principal.tablePeli.getRowCount(); i++) {
            if(String.valueOf(principal.tablePeli.getValueAt(i, 0)).contains(nombreBuscar)){
                principal.tablePeli.changeSelection(i, 1, false, false);
                principal.textFieldPeliculaP.setText("Ingrese Película");
                return;
            }
        }
        
        JOptionPane.showMessageDialog(principal, "La Película que ingresó no se encuentra en la Tabla", "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    public void calcularIngresos(Principal principal){
        //Se declaran variables auxiliares
        double ingresos = 0;
        Carrito cauxrrito;
        OrdenCompra aux;
        
        //Recorre los clientes del arbol "clientes"
        for (int i = 0; i<principal.tableClientes.getModel().getRowCount(); i++) {
            //Guarda su carrito
            cauxrrito = clientes.buscarCliente(clientes.getRoot(), Long.parseLong(String.valueOf(principal.tableClientes.getValueAt(i, 1)))).getCarrito();
                        
            for (int j = 0; j < cauxrrito.getOrdenes().size(); j++) {
                //Se guarda la orden de Compra
                aux = cauxrrito.getOrdenes().getFirst().getData();
                
                //Se revisa si se está pagada y se suman los ingresos
                if(aux.isPagada()){
                    ingresos += aux.getPrecioTotal();
                }
                //Se recorre la cola
                cauxrrito.getOrdenes().enqueue(cauxrrito.getOrdenes().dequeue());
            }
        }
        
        //Se coloca en el textFieldIngresos de Admin el valor de los ingresos
        principal.textFieldIngresosA.setText(String.valueOf(ingresos));
    }
    
    public void calcularPrecioVentas(Principal principal){
        /* Calcula, en base al tipo de Sala que se haya seleccionado y la cantidadde entradas que el clinete quiera, el precio Total
         * de la Orden de Compra y lo muestra en pantalla
        */
        
        // Se validad todos los campos necesarios
        if( !String.valueOf(principal.comboSalasV.getSelectedItem()).equals("Sala") && !String.valueOf(principal.comboSucursalesV.getSelectedItem()).equals("Sucursal") ){
            int numSucursal = Integer.parseInt(String.valueOf(principal.comboSucursalesV.getSelectedItem()));
            Sucursal sucursal = sucursales.buscarSucursal(sucursales.getRoot(), numSucursal);
            int numSala = Integer.parseInt(String.valueOf(principal.comboSalasV.getSelectedItem()));
            Sala sala = sucursal.getSalas().buscarSala(sucursal.getSalas().getRoot(), numSala);
            
            int cantidad = Integer.parseInt(String.valueOf(principal.spinnerTicketsV.getValue()));
                if(sala instanceof Sala2D){
                    Ticket2D ticket = new Ticket2D();
                    principal.textFieldPrecioV.setText(String.valueOf( (cantidad * ticket.getPrecio()) ));
                }else if(sala instanceof Sala3D){
                    Ticket3D ticket = new Ticket3D();
                    principal.textFieldPrecioV.setText(String.valueOf( (cantidad * ticket.getPrecio()) ));
                }else if(sala instanceof Sala4DX){
                    Ticket4DX ticket = new Ticket4DX();
                    principal.textFieldPrecioV.setText(String.valueOf( (cantidad * ticket.getPrecio()) ));
                }
        }
    }
    
    public void cambiarSalaVentas(Principal principal){
        // Pone el nombre de la película de la Sala que el cliente haya selecciopnado
        if(!String.valueOf(principal.comboSalasV.getSelectedItem()).equals("Sala")){
            int numSucursal = Integer.parseInt(String.valueOf(principal.comboSucursalesV.getSelectedItem()));
            Sucursal sucursal = sucursales.buscarSucursal(sucursales.getRoot(), numSucursal);
            int numSala = Integer.parseInt(String.valueOf(principal.comboSalasV.getSelectedItem()));
            
            //Se verifica que la película sea diferente de null
            try {
                principal.labelPelicula.setText(sucursal.getSalas().buscarSala(sucursal.getSalas().getRoot(), numSala).getPelicula().getNombre());
            } catch (Exception e) {
                 principal.labelPelicula.setText("-----------------------");
            }
            
        }else{
            principal.labelPelicula.setText("-----------------------");
        }
    }
    
    public void cerrar(Principal principal){
        // Metodo para cerrar el sistema
        try {
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int result = JOptionPane.showConfirmDialog(null, "¿Desea cerrar el sistema?", "Salir", dialogButton);
            if(result == 0){
                System.exit(0);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(principal, e);
        }
    }
    
    public void cerrarCarrito(Carrito carrito){
        // Metodo para cerrar el sistema
        try {
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int result = JOptionPane.showConfirmDialog(null, "¿Desea cerrar el sistema?", "Salir", dialogButton);
            if(result == 0){
                System.exit(0);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(carrito, e);
        }
    }
    
    public void crearCliente(Principal principal){
        // Variables auxiliares para poder validar todo de manera correcta
        boolean flag1, flag2;
        
        // Validamos la cédula
        long cedula = 0;
        
        do{
            flag1 = flag2 = false;
            try {
                    cedula = Integer.parseInt(JOptionPane.showInputDialog(principal, "        Ingrese la cédula del cliente\n          (De 1 millon a 30millones)", "Ingrese cédula", JOptionPane.QUESTION_MESSAGE));
                    if(!String.valueOf(cedula).matches("[0-9]*$")){
                        JOptionPane.showMessageDialog(principal, "Ingresó una cédula inválida\n  (No ingrese ni letras ni símbolos)", "Error", JOptionPane.ERROR_MESSAGE);
                        flag1= true;
                    }
                    if(cedula < 1000000 || cedula > 30000000){
                        JOptionPane.showMessageDialog(principal, "Ingresó un número inválido\n  (De 1 millon a 30millones)", "Error", JOptionPane.ERROR_MESSAGE);
                        flag2 = true;
                    }
            } catch (Exception e) {
                return;
            }
        }while(flag1 == true || flag2 == true);
        
        // Validamos el nombre
        String nombre = "";
        
        do{
            flag1 = false;
            try {
                nombre = JOptionPane.showInputDialog(principal, "    Ingrese el nombre del Cliente", "Ingrese nombre", JOptionPane.QUESTION_MESSAGE);
                if(!nombre.matches("[a-zA-Z ]*$")){
                    JOptionPane.showMessageDialog(principal, "Ingresó un nombre inválido\n  (No ingrese números ni símbolos)", "Error", JOptionPane.ERROR_MESSAGE);
                    flag1 = true;
                    System.out.println(flag1);
                }
            } catch (Exception e) {
                return;
            }
        }while(flag1 == true);
        
        // Validamos el teléfono
        String telefono = "";
        
        do{
            flag1 = flag2 = false;
            try {
                telefono = JOptionPane.showInputDialog(principal, "        Ingrese el teléfono del cliente", "Ingrese teléfono", JOptionPane.QUESTION_MESSAGE);
                if(!telefono.matches("[0-9]*$")){
                    JOptionPane.showMessageDialog(principal, "Ingresó un formato de teléfono incorrecto\n   No ponga ni letras ni símbolos", "Error", JOptionPane.ERROR_MESSAGE);
                    flag1 = true;
                }
                if(telefono.length() != 11){
                    JOptionPane.showMessageDialog(principal, "Su teléfono tiene más/menos números de los que debería", "Error", JOptionPane.ERROR_MESSAGE);
                    flag2 = true;
                }
            } catch (Exception e) {
                return;
            }
        }while(flag1 == true || flag2 == true);
        
        Cliente cliente = new Cliente(cedula, nombre, telefono);
        
        
        this.mostrarClienteEnTablaClientes(cliente, principal);
    }
    
    private void crearPelicula(Pelicula pelicula, Principal principal){
        // Creamos el objetos y llamamos al método mostrar en tabla
        this.mostrarPeliculasEnTablaPeliculas(pelicula, (DefaultTableModel)principal.tablePeli.getModel() );
        // Mostramos las peliculas en el comboBox de peliculas en la pestaña Salas
        principal.comboPeliculasSa1.addItem(pelicula.getNombre());
    }
    
    public void crearSala(Principal principal){
        // Si es una sala 2D
        if(principal.radioBoton2D.isSelected()){
            if(principal.tableSucursales.getSelectedRow() != -1){
                principal.grupoBotones.clearSelection();
                // Guardamos en una variable entera el código de la sucursal seleccionada en la tabla
                int numSucursal = Integer.parseInt(String.valueOf( ((DefaultTableModel)principal.tableSucursales.getModel()).getValueAt(principal.tableSucursales.getSelectedRow(), 0) ) );
                principal.tableSucursales.clearSelection();
                
                // Si ya hay alguna sucursal
                if(!sucursales.buscarSucursal(sucursales.getRoot(), numSucursal).getSalas().isEmpty()){
                    // Se crea la sala
                    Sala2D sala = new Sala2D(sucursales.buscarSucursal(sucursales.getRoot(), numSucursal).getSalas().size(sucursales.buscarSucursal(sucursales.getRoot(), numSucursal).getSalas().getRoot()) + 1 );      
                    // Se inserta en el árbol
                    sucursales.buscarSucursal(sucursales.getRoot(), numSucursal).getSalas().insertarSala(sucursales.buscarSucursal(sucursales.getRoot(), numSucursal).getSalas().getRoot(), sala);
                    JOptionPane.showMessageDialog(principal, "Sala número " + sala.getNumero() + " creada con éxito", "Sala creada", JOptionPane.INFORMATION_MESSAGE);
                    JOptionPane.showMessageDialog(principal, "Recuerde indicar la película que se verá en la Sala.\n                  Vaya a la pestaña Salas");
                }else{
                    // Lo mismo que arriba
                    Sala2D sala = new Sala2D(1);
                    sucursales.buscarSucursal(sucursales.getRoot(), numSucursal).getSalas().insertarSala(sucursales.buscarSucursal(sucursales.getRoot(), numSucursal).getSalas().getRoot(), sala);
                    JOptionPane.showMessageDialog(principal, "Sala número " + sala.getNumero() + " creada con éxito", "Sala creada", JOptionPane.INFORMATION_MESSAGE);
                    JOptionPane.showMessageDialog(principal, "Recuerde indicar la película que se verá en la Sala.\n                  Vaya a la pestaña Salas");
                }        
            }else{
                JOptionPane.showMessageDialog(principal, "Seleccione a que sucursal va a pertenecer la sala", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        
        // Si es una sala 3D
        else if(principal.radioBoton3D.isSelected()){
            if(principal.tableSucursales.getSelectedRow() != -1){
                principal.grupoBotones.clearSelection();
                // Guardamos en una variable entera el código de la sucursal seleccionada en la tabla
                int numSucursal = Integer.parseInt(String.valueOf( ((DefaultTableModel)principal.tableSucursales.getModel()).getValueAt(principal.tableSucursales.getSelectedRow(), 0) ) );
                principal.tableSucursales.clearSelection();
                
                // Si ya hay alguna sucursal
                if(!sucursales.buscarSucursal(sucursales.getRoot(), numSucursal).getSalas().isEmpty()){
                    // Se crea la sala
                    Sala3D sala = new Sala3D(sucursales.buscarSucursal(sucursales.getRoot(), numSucursal).getSalas().size(sucursales.buscarSucursal(sucursales.getRoot(), numSucursal).getSalas().getRoot()) + 1 );      
                    // Se inserta en el árbol
                    sucursales.buscarSucursal(sucursales.getRoot(), numSucursal).getSalas().insertarSala(sucursales.buscarSucursal(sucursales.getRoot(), numSucursal).getSalas().getRoot(), sala);
                    JOptionPane.showMessageDialog(principal, "Sala número " + sala.getNumero() + " creada con éxito", "Sala creada", JOptionPane.INFORMATION_MESSAGE);
                    JOptionPane.showMessageDialog(principal, "Recuerde indicar la película que se verá en la Sala.\n                  Vaya a la pestaña Salas");
                }else{
                    // Lo mismo que arriba
                    Sala3D sala = new Sala3D(1);
                    sucursales.buscarSucursal(sucursales.getRoot(), numSucursal).getSalas().insertarSala(sucursales.buscarSucursal(sucursales.getRoot(), numSucursal).getSalas().getRoot(), sala);
                    JOptionPane.showMessageDialog(principal, "Sala número " + sala.getNumero() + " creada con éxito", "Sala creada", JOptionPane.INFORMATION_MESSAGE);
                    JOptionPane.showMessageDialog(principal, "Recuerde indicar la película que se verá en la Sala.\n                  Vaya a la pestaña Salas");
                }
                
            }else{
                JOptionPane.showMessageDialog(principal, "Seleccione a que sucursal va a pertenecer la sala", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        
        // Si es una sala 4DX
        else if(principal.radioBoton4DX.isSelected()){
            if(principal.tableSucursales.getSelectedRow() != -1){
                principal.grupoBotones.clearSelection();
                // Guardamos en una variable entera el código de la sucursal seleccionada en la tabla
                int numSucursal = Integer.parseInt(String.valueOf( ((DefaultTableModel)principal.tableSucursales.getModel()).getValueAt(principal.tableSucursales.getSelectedRow(), 0) ) );
                principal.tableSucursales.clearSelection();
                
                // Si ya hay alguna sucursal
                if(!sucursales.buscarSucursal(sucursales.getRoot(), numSucursal).getSalas().isEmpty()){
                    // Se crea la sala
                    Sala4DX sala = new Sala4DX(sucursales.buscarSucursal(sucursales.getRoot(), numSucursal).getSalas().size(sucursales.buscarSucursal(sucursales.getRoot(), numSucursal).getSalas().getRoot()) + 1 );      
                    // Se inserta en el árbol
                    sucursales.buscarSucursal(sucursales.getRoot(), numSucursal).getSalas().insertarSala(sucursales.buscarSucursal(sucursales.getRoot(), numSucursal).getSalas().getRoot(), sala);
                    JOptionPane.showMessageDialog(principal, "Sala número " + sala.getNumero() + " creada con éxito", "Sala creada", JOptionPane.INFORMATION_MESSAGE);
                    JOptionPane.showMessageDialog(principal, "Recuerde indicar la película que se verá en la Sala.\n                  Vaya a la pestaña Salas");
                }else{
                    // Lo mismo que arriba
                    Sala4DX sala = new Sala4DX(1);
                    sucursales.buscarSucursal(sucursales.getRoot(), numSucursal).getSalas().insertarSala(sucursales.buscarSucursal(sucursales.getRoot(), numSucursal).getSalas().getRoot(), sala);
                    JOptionPane.showMessageDialog(principal, "Sala número " + sala.getNumero() + " creada con éxito", "Sala creada", JOptionPane.INFORMATION_MESSAGE);
                    JOptionPane.showMessageDialog(principal, "Recuerde indicar la película que se verá en la Sala.\n                  Vaya a la pestaña Salas");
                }
                
            }else{
                JOptionPane.showMessageDialog(principal, "Seleccione a que sucursal va a pertenecer la sala", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        
        // Si no se seleccionó ningún tipo de Sala
        else{
            JOptionPane.showMessageDialog(principal, "Seleccione el tipo de sala que desee crear", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
    private void crearSalaInicio(Sala sala, Sucursal sucursal, Principal principal){
        // Método que solo se utiliza para crear las salas que vienen desde el inicio del programa
        
        // Se inserta la Sala en el árbol respectivo
        sucursal.getSalas().insertarSala(sucursal.getSalas().getRoot(), sala);
        // Se muestra las Salas en la tabla
        this.mostrarSalasEnTablaSalas(principal, sucursal.getCodigo(), true);
    }
    
    public void crearSucursal(Sucursal sucursal, Principal principal){
        // Crea una nueva sucursal y se inserta en el arbol "salas"
        
        // Inserto la nueva sucursal en el árbol y lo añado al comboBox de ventas
        sucursales.insertarSucursal(sucursales.getRoot(), sucursal);
        principal.comboSucursalesV.addItem(String.valueOf(sucursal.getCodigo()));
        principal.comboSucursalesSalas.addItem(String.valueOf(sucursal.getCodigo()));
        // Agrego la nueva sucursal a la tabla
        this.agregarATablaSucursales(sucursal, (DefaultTableModel)principal.tableSucursales.getModel());
    }
    
    public void eliminarOrden(Carrito carrito){
        // Buscamos qué orden está seleccionada
        if(carrito.tableCarrito.getSelectedRow() != -1){
            if(String.valueOf( ((DefaultTableModel)carrito.tableCarrito.getModel()).getValueAt(carrito.tableCarrito.getSelectedRow(), 8) ).equals("Si")){
                JOptionPane.showMessageDialog(carrito, "Esa orden de compra ya está pagada, no se puede eliminar", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Se guarda el numero de orden en una variable auxiliar
            int numOrden = Integer.parseInt(String.valueOf( ((DefaultTableModel)carrito.tableCarrito.getModel()).getValueAt(carrito.tableCarrito.getSelectedRow(), 0) ) );
            
            String sala, sucursal, salaux, sucursaux;
            OrdenCompra ordeaux = carrito.getOrdenes().buscarOrden(numOrden);
            sala = String.valueOf(ordeaux.getTickets().getHead().getData().getSala().getNumero());
            sucursal = String.valueOf(ordeaux.getTickets().getHead().getData().getSucursal().getUbicacion());
            int tickets = ordeaux.getTickets().size();
            int ticketaux;
            
            // Eliminamos la orden de la Cola de ordenes
            carrito.getOrdenes().eliminarOrden(numOrden);
            
            DefaultTableModel model = (DefaultTableModel)carrito.tableCarrito.getModel();
            
            for (int i = 0; i < ((DefaultTableModel)carrito.tableCarrito.getModel()).getRowCount(); i++) {
                String numOrdenAux = String.valueOf(model.getValueAt(i, 0));
                //Consigue la fila donde está la orden que se desea eliminar
                if( numOrdenAux.equals(String.valueOf(numOrden)) ){
                    model.removeRow(i);
                    break;
                }
            }
            
            DefaultTableModel modeloAdmin = (DefaultTableModel)carrito.getPrincipal().tableAdmin.getModel();
            
            // Asigna el nuevo precio a la tabla de Administrar
            for (int i = 0; i < modeloAdmin.getRowCount(); i++) {
                sucursaux = String.valueOf(modeloAdmin.getValueAt(i, 0));
                salaux = String.valueOf(modeloAdmin.getValueAt(i, 1));
                ticketaux = Integer.parseInt(String.valueOf(modeloAdmin.getValueAt(i, 2)));
                
                if(sucursaux.equals(sucursal) && salaux.equals(sala)){
                    if(tickets == ticketaux){
                        modeloAdmin.removeRow(i);
                        break;
                    }else{
                        modeloAdmin.setValueAt(ticketaux - tickets, i, 2);
                    }
                }
            }
            
            JOptionPane.showMessageDialog(carrito, "¡Orden de Compra eliminada exitosamente!", "Eliminada", JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(carrito, "Seleccione la orden de compra que quiere eliminar", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void iniciarPrograma(){
        // Inicia el sistema
        Inicio inicio = new Inicio(this);
        inicio.setVisible(true);
    }
    
    public void iniciarRadioButons(Principal principal){
        //Se inicializan los radio button
        principal.radio2D.setEnabled(false);
        principal.radio3D.setEnabled(false);
        principal.radio4D.setEnabled(false);
        principal.radioALL.setEnabled(false);
        principal.radio2D.setSelected(false);
        principal.radio3D.setSelected(false);
        principal.radio4D.setSelected(false);
        principal.radioALL.setSelected(true);
    }
    
    public void iniciarSesion(Inicio inicio){
        // Validacion de usuario para acceder al sistema
        
        if(inicio.jTextFieldUsuario.getText().trim().equals("admin") && inicio.jPasswordField.getText().equals("admin")){
            // Inicio frame Loading
            Loading loading = new Loading();
            
            Runnable miRunnable = new Runnable(){
            @Override
            public void run(){
                try
                {
                    // Si no hay ningun error carga el juego
                    inicio.dispose();
                    loading.setVisible(true);
                    for (int i = 0; i <= 100; i++) {
                        // Tiempo que tardará la carga
                        Thread.sleep(10);
                        loading.loadingBar.setValue(i);

                        if(i==100){
                            Thread.sleep(100);
                        }
                    }
                    loading.dispose();
                    abrirPrincipal();
                }catch (InterruptedException e){
                    JOptionPane.showMessageDialog(null, e);
                }
                }
            };
            Thread hilo = new Thread (miRunnable);
            hilo.start();
            
            // Fin frame loading
        }else{
            JOptionPane.showMessageDialog(inicio, "Usuario o contraseña inválidos");
            inicio.jTextFieldUsuario.setText("Ingrese su usuario");
            inicio.jPasswordField.setText("jPasswordField1");
        }
    }
    
    //----------------- INICIALIZACIÓN DE TABLAS -----------------
    public void iniciarTablaAdministrar(Principal principal){
        // Permitir la selección de solo una fila
        principal.tableAdmin.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // Tamaño de cada columna
        principal.tableAdmin.getTableHeader().setReorderingAllowed(false);
        principal.tableAdmin.getTableHeader().setResizingAllowed(false);
        principal.tableAdmin.getColumnModel().getColumn(0).setPreferredWidth(170);
        principal.tableAdmin.getColumnModel().getColumn(1).setPreferredWidth(170);
        principal.tableAdmin.getColumnModel().getColumn(2).setPreferredWidth(187);
        // Altura de cada renglón
        principal.tableAdmin.setRowHeight(20);
    }
    
    public void iniciarTablaCarrito(Carrito carrito){
        // Permitir la selección de solo una fila
        carrito.tableCarrito.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // Tamaño de cada columna
        carrito.tableCarrito.getTableHeader().setReorderingAllowed(false);
        carrito.tableCarrito.getTableHeader().setResizingAllowed(false);
        carrito.tableCarrito.getColumnModel().getColumn(0).setPreferredWidth(62);
        carrito.tableCarrito.getColumnModel().getColumn(1).setPreferredWidth(58);
        carrito.tableCarrito.getColumnModel().getColumn(2).setPreferredWidth(78);
        carrito.tableCarrito.getColumnModel().getColumn(3).setPreferredWidth(32);
        carrito.tableCarrito.getColumnModel().getColumn(4).setPreferredWidth(32);
        carrito.tableCarrito.getColumnModel().getColumn(5).setPreferredWidth(185);
        carrito.tableCarrito.getColumnModel().getColumn(6).setPreferredWidth(80);
        carrito.tableCarrito.getColumnModel().getColumn(7).setPreferredWidth(80);
        carrito.tableCarrito.getColumnModel().getColumn(8).setPreferredWidth(75);
        // Altura de cada renglón
        carrito.tableCarrito.setRowHeight(20);
    }
    
    public void iniciarTablaClientes(Principal principal){
        // Permitir la selección de solo una fila
        principal.tableClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // Denegar el acceso a modificar
        principal.tableClientes.getTableHeader().setReorderingAllowed(false);
        principal.tableClientes.getTableHeader().setResizingAllowed(false);
        // Tamaño de cada columna
        principal.tableClientes.getColumnModel().getColumn(0).setPreferredWidth(170);
        principal.tableClientes.getColumnModel().getColumn(1).setPreferredWidth(175);
        principal.tableClientes.getColumnModel().getColumn(2).setPreferredWidth(187);
        // Altura de cada renglón
        principal.tableClientes.setRowHeight(20);
    }
    
    public void iniciarTablaPeliculas(Principal principal){
        // Permitir la selección de solo una fila
        principal.tablePeli.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // Denegar el acceso a modificar
        principal.tablePeli.getTableHeader().setReorderingAllowed(false);
        principal.tablePeli.getTableHeader().setResizingAllowed(false);
        // Tamaño de cada columna
        principal.tablePeli.getColumnModel().getColumn(0).setPreferredWidth(200);
        principal.tablePeli.getColumnModel().getColumn(1).setPreferredWidth(160);
        principal.tablePeli.getColumnModel().getColumn(2).setPreferredWidth(160);
        // Altura de cada renglón
        principal.tablePeli.setRowHeight(20);
    }
    
    public void iniciarTablaSalas(Principal principal){
        ((DefaultTableModel)principal.tableSalas.getModel()).setRowCount(0);
        // Permitir la selección de solo una fila
        principal.tableSalas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // Denegar el acceso a modificar
        principal.tableSalas.getTableHeader().setReorderingAllowed(false);
        principal.tableSalas.getTableHeader().setResizingAllowed(false);
        // Tamaño de cada columna
        principal.tableSalas.getColumnModel().getColumn(0).setPreferredWidth(140);
        principal.tableSalas.getColumnModel().getColumn(1).setPreferredWidth(150);
        principal.tableSalas.getColumnModel().getColumn(2).setPreferredWidth(232);
        // Altura de cada renglón
        principal.tableSalas.setRowHeight(20);
    }
    
    public void iniciarTablaSucursales(Principal principal){
        // Permitir la selección de solo una fila
        principal.tableSucursales.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // Denegar el acceso a modificar
        principal.tableSucursales.getTableHeader().setReorderingAllowed(false);
        principal.tableSucursales.getTableHeader().setResizingAllowed(false);
        // Tamaño de cada columna
        principal.tableSucursales.getColumnModel().getColumn(0).setPreferredWidth(155);
        principal.tableSucursales.getColumnModel().getColumn(1).setPreferredWidth(372);
        // Altura de cada renglón
        principal.tableSucursales.setRowHeight(20);
    }
    
    public void iniciarTablaTickets(Principal principal){
        // Permitir la selección de solo una fila
        principal.tableTickets.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // Denegar el acceso a modificar
        principal.tableTickets.getTableHeader().setReorderingAllowed(false);
        principal.tableTickets.getTableHeader().setResizingAllowed(false);
        // Tamaño de cada columna
        principal.tableTickets.getColumnModel().getColumn(0).setPreferredWidth(100);
        principal.tableTickets.getColumnModel().getColumn(1).setPreferredWidth(92);
        principal.tableTickets.getColumnModel().getColumn(2).setPreferredWidth(110);
        principal.tableTickets.getColumnModel().getColumn(3).setPreferredWidth(40);
        principal.tableTickets.getColumnModel().getColumn(4).setPreferredWidth(140);
        principal.tableTickets.getColumnModel().getColumn(5).setPreferredWidth(80);
        // Altura de cada renglón
        principal.tableTickets.setRowHeight(20);
    }
    //----------------- FIN INICIALIZACIÓN DE TABLAS -----------------
    
    public void minimizar(Principal principal){
        // Permite minimizar la ventana del sistema
        principal.setState(view.Inicio.ICONIFIED);
    }
    
    public void minimizarCarrito(Carrito carrito){
        // Permite minimizar la ventana del sistema
        carrito.setState(view.Inicio.ICONIFIED);
    }
    
    public void modificarPelicula(Principal principal){
        // Se crea los Strings para los combosBox del JOption con comboBox
        String[] idiomas = {"Español", "Inglés", "Francés"};
        String[] generos = {"Acción", "Amor", "Suspenso", "Aventura"};
        
        // Se valida si no se seleccionó ninguna pelicula, si no se seleccionó que se va a modificar y finalmente se cambia
        String modificar = String.valueOf(principal.comboModificarPelicula.getSelectedItem());
        if(principal.tablePeli.getSelectedRow() == -1){
            JOptionPane.showMessageDialog(principal, "Seleccione la Película que desea modificar", "Error", JOptionPane.ERROR_MESSAGE);
        }else if(modificar.equals("Modificar")){
            JOptionPane.showMessageDialog(principal, "Seleccione que desea modificar", "Error", JOptionPane.ERROR_MESSAGE);
        }else if(modificar.equals("Género")){
            String peliculaModificar = String.valueOf( ((DefaultTableModel)principal.tablePeli.getModel()).getValueAt(principal.tablePeli.getSelectedRow(), 0) );
            String genero = (String)JOptionPane.showInputDialog(principal, "Seleccione el nuevo género de la Película", "Modificación Género", JOptionPane.QUESTION_MESSAGE, null, generos, generos[0]);
            peliculas.buscarPelicula(peliculaModificar).setGenero(genero);
            principal.tablePeli.setValueAt(peliculas.buscarPelicula(peliculaModificar).getGenero() , principal.tablePeli.getSelectedRow(), 1);
            principal.tablePeli.clearSelection();
            principal.comboModificarPelicula.setSelectedIndex(0);
            principal.comboGeneroP.setSelectedIndex(0);
            principal.comboIdiomaP.setSelectedIndex(0);
        }else if(modificar.equals("Idioma")){
            String peliculaModificar = String.valueOf( ((DefaultTableModel)principal.tablePeli.getModel()).getValueAt(principal.tablePeli.getSelectedRow(), 0) );
            String idioma = (String)JOptionPane.showInputDialog(principal, "Seleccione el nuevo idioma de la Película", "Modificación Idioma", JOptionPane.QUESTION_MESSAGE, null, idiomas, idiomas[0]);
            peliculas.buscarPelicula(peliculaModificar).setIdioma(idioma);
            principal.tablePeli.setValueAt(peliculas.buscarPelicula(peliculaModificar).getIdioma(), principal.tablePeli.getSelectedRow(), 2);
            principal.tablePeli.clearSelection();
            principal.comboModificarPelicula.setSelectedIndex(0);
            principal.comboGeneroP.setSelectedIndex(0);
            principal.comboIdiomaP.setSelectedIndex(0);
        }
    }
    
    public void modificarTelefonoCliente(Principal principal, String nuevoTelefono, long cedula){
        // Modificamos el teléfono en el árbol de CLientes
        clientes.buscarCliente(clientes.getRoot(), cedula).setTelefono(nuevoTelefono);
        
        // Modificamos la tabla de CLientes
        for (int i = 0; i < principal.tableClientes.getRowCount(); i++) {
            if(cedula == Long.parseLong(String.valueOf(principal.tableClientes.getValueAt(i, 1)))){
                principal.tableClientes.setValueAt(nuevoTelefono, i, 2);
            }
        }
    }
    
    public void modificarUbicacionSucursal(Principal principal, String nuevaUbicacion, int numSucursal){
        sucursales.buscarSucursal(sucursales.getRoot(), numSucursal).setUbicacion(nuevaUbicacion);
        this.actualizarTablaSucursales(principal);
    }
    
    private void mostrarClienteEnTablaClientes(Cliente cliente, Principal principal){
        clientes.insertarCliente(clientes.getRoot(), cliente);
        
        // Se agrega el cliente al comboBox de Ventas
        principal.comboClientesV.addItem(String.valueOf(cliente.getCedula()));
        
        DefaultTableModel modelo = (DefaultTableModel)principal.tableClientes.getModel();
        
        modelo.addRow(new Object[]{
            cliente.getNombre(), cliente.getCedula(), cliente.getTelefono()
        });
    }
    
    private void mostrarOrdenEnTablaCarrito(Carrito carrito, OrdenCompra orden){
        // Se verifica si la orden está pagada o no
        String pago = "";
        String tipo = "";
        if(orden.isPagada()){
            pago = "Si";
        }else{
            pago = "No";
        }
        
        if( orden.getTickets().getHead().getData() instanceof Ticket2D ){
            tipo = "2D";
        }else if( orden.getTickets().getHead().getData() instanceof Ticket3D ){
            tipo = "3D";
        }else if( orden.getTickets().getHead().getData() instanceof Ticket4DX ){
            tipo = "4DX";
        }
        
        //Se crea la orden en la tabla de Carrito
        ((DefaultTableModel)carrito.tableCarrito.getModel()).addRow(new Object[]{
            orden.getNumero(), orden.getTickets().size(), ((Ticket)orden.getTickets().getHead().getData()).getSucursal().getUbicacion(), 
            ((Ticket)orden.getTickets().getHead().getData()).getSala().getNumero(), tipo, ((Ticket)orden.getTickets().getHead().getData()).getSala().getPelicula().getNombre(),
            ((Ticket)orden.getTickets().getHead().getData()).getFecha(), orden.getPrecioTotal(), pago
        });
    }
    
    public void mostrarPeliculasEnComboPeliculas(Principal principal){
        // Valida todo lo necesario para mostar las salas y peliculas
        principal.comboSalasV.removeAllItems();
        principal.comboSalasV.addItem("Sala");
        if(String.valueOf(principal.comboSucursalesV.getSelectedItem()).equals("Sucursal")){
            principal.comboSalasV.removeAllItems();
            principal.comboSalasV.addItem("Sala");
            principal.labelPelicula.setText("-----------------------");
        }else if(String.valueOf(principal.comboSalasV.getSelectedItem()).equals("Sala") && String.valueOf(principal.comboSucursalesV.getSelectedItem()).equals("Sucursal")){
            principal.labelPelicula.setText("-----------------------");
        }else if(!String.valueOf(principal.comboSucursalesV.getSelectedItem()).equals("Sucursal") && String.valueOf(principal.comboSalasV.getSelectedItem()).equals("Sala")){
            principal.comboSalasV.removeAllItems();
            principal.comboSalasV.addItem("Sala");
            principal.labelPelicula.setText("-----------------------");
            int numSucursal = Integer.parseInt(String.valueOf(principal.comboSucursalesV.getSelectedItem()));
            Sucursal sucursal = sucursales.buscarSucursal(sucursales.getRoot(), numSucursal);
            
            for (int i = 0; i < sucursal.getSalas().size(sucursal.getSalas().getRoot()); i++) {
                principal.comboSalasV.addItem(String.valueOf(i+1));
            }
        }
    }
    
    public void mostrarPeliculasEnTablaPeliculas(Principal principal){
        //Se establecen variables tipo String de los combobox
        String genero = String.valueOf(principal.comboGeneroP.getSelectedItem());
        String idioma = String.valueOf(principal.comboIdiomaP.getSelectedItem());
        
        //Creando filtros
        filtros.add(RowFilter.regexFilter(genero));
        filtros.add(RowFilter.regexFilter(idioma));
        
        //Se establecen las variables y métodos para el filtro
        DefaultTableModel dm = (DefaultTableModel) principal.tablePeli.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(dm);
        principal.tablePeli.setRowSorter(tr);
        
        //Se evalúan los posibles casos de filtros
        if(!genero.equals("Género") || !idioma.equals("Idioma")){
           if(!idioma.equals("Idioma")){
               if(!genero.equals("Género")){   
                tr.setRowFilter(RowFilter.andFilter(filtros));
               }else{
                tr.setRowFilter(RowFilter.regexFilter(idioma));
               }
           }else{
               if(!genero.equals("Género")){   
                tr.setRowFilter(RowFilter.regexFilter(genero));
               }
           }
        }else{
           principal.tablePeli.setRowSorter(tr);
        }
           
        //Se vacían los filtros
        filtros.clear();
    }
    
    private void mostrarPeliculasEnTablaPeliculas(Pelicula pelicula, DefaultTableModel modelo){
        //Se llena la tabla de películas
        modelo.addRow(new Object[]{
            pelicula.getNombre(), pelicula.getGenero(), pelicula.getIdioma()
        });
    }
    
    public void mostrarSalasEnTablaSalas(Principal principal, int numSucursal, boolean flag){
        // Se crea una Sucursal aux para ahorrar código
        Sucursal aux = sucursales.buscarSucursal(sucursales.getRoot(), numSucursal);
        // Se determina la cantidad de salas que hay en la sucursal seleccionada para así saber cuantas filas habrán
        int cantFilas = sucursales.buscarSucursal(sucursales.getRoot(), numSucursal).getSalas().size(sucursales.buscarSucursal(sucursales.getRoot(), numSucursal).getSalas().getRoot());
        DefaultTableModel model = (DefaultTableModel)principal.tableSalas.getModel();
        
        // Agregamos las salas a la Tabla
        for (int i = 0; i < cantFilas; i++) {
            // Si ya tienen nombre puesto
            if(aux.getSalas().buscarSala(aux.getSalas().getRoot(), i+1).getPelicula() != null){
                if( aux.getSalas().buscarSala(aux.getSalas().getRoot(), i+1) instanceof Sala2D){
                    model.addRow(new Object[]{
                        i+1, "2D", aux.getSalas().buscarSala(aux.getSalas().getRoot(), i+1).getPelicula().getNombre()
                    });  
                }else if(aux.getSalas().buscarSala(aux.getSalas().getRoot(), i+1) instanceof Sala3D){
                    model.addRow(new Object[]{
                        i+1, "3D", aux.getSalas().buscarSala(aux.getSalas().getRoot(), i+1).getPelicula().getNombre()
                    });
                }else if(aux.getSalas().buscarSala(aux.getSalas().getRoot(), i+1) instanceof Sala4DX){
                    model.addRow(new Object[]{
                        i+1, "4DX" , aux.getSalas().buscarSala(aux.getSalas().getRoot(), i+1).getPelicula().getNombre()
                    });
                }
            }else{
                if( aux.getSalas().buscarSala(aux.getSalas().getRoot(), i+1) instanceof Sala2D){
                    model.addRow(new Object[]{
                        i+1, "2D", "--------------------------------"
                    });  
                }else if(aux.getSalas().buscarSala(aux.getSalas().getRoot(), i+1) instanceof Sala3D){
                    model.addRow(new Object[]{
                        i+1, "3D", "--------------------------------"
                    });
                }else if(aux.getSalas().buscarSala(aux.getSalas().getRoot(), i+1) instanceof Sala4DX){
                    model.addRow(new Object[]{
                        i+1, "4DX" , "--------------------------------"
                    });
                }
            }
        }
        
        if(flag){
            this.iniciarTablaSalas(principal);
        }
        
        
    }
    
    public void mostrarSalasFrecuentes(Principal principal, Sala sala, Sucursal sucursal, int size){
        String salaux, sucursaux;
        boolean esta = false;
        
        //Se chequea si la tabla en la pestaña Admin contiene la sala de la sucursal enviada por parámetros
         for (int i = 0; i < ((DefaultTableModel)principal.tableAdmin.getModel()).getRowCount(); i++) {
            sucursaux = String.valueOf(((DefaultTableModel)principal.tableAdmin.getModel()).getValueAt(i, 0));
            salaux = String.valueOf(((DefaultTableModel)principal.tableAdmin.getModel()).getValueAt(i, 1));
            if(sucursal.getUbicacion().equals(sucursaux) && String.valueOf(sala.getNumero()).equals(salaux)){
                esta=true;
                break;
            }
         }
         int tickets;
         
         //Si está, se busca la fila en la que se encuentra y se suman los tickets con los nuevos tickets
         if(esta){
             for (int i = 0; i < ((DefaultTableModel)principal.tableAdmin.getModel()).getRowCount(); i++) {
                sucursaux = String.valueOf(((DefaultTableModel)principal.tableAdmin.getModel()).getValueAt(i, 0));
                salaux = String.valueOf(((DefaultTableModel)principal.tableAdmin.getModel()).getValueAt(i, 1));
                if(sucursal.getUbicacion().equals(sucursaux) && String.valueOf(sala.getNumero()).equals(salaux)){
                    tickets = Integer.parseInt(String.valueOf(((DefaultTableModel)principal.tableAdmin.getModel()).getValueAt(i, 2)));
                    ((DefaultTableModel)principal.tableAdmin.getModel()).setValueAt(size+tickets, i, 2);
                }
            }
        //Si no está, se crea un nuevo objeto en la tabla
         }else{
             ((DefaultTableModel)principal.tableAdmin.getModel()).addRow(new Object[]{
                 sucursal.getUbicacion(),String.valueOf(sala.getNumero()),String.valueOf(size)
                 });
         }
         
    }
    
    public void mostrarSoloSalas2D(Principal principal){
        // Mostrará solo las Salas 2D
        Sucursal sucursal = sucursales.buscarSucursal(sucursales.getRoot(), Integer.parseInt(String.valueOf(principal.comboSucursalesSalas.getSelectedItem())));
        int numSalas = sucursal.getSalas().size(sucursal.getSalas().getRoot());
        
        DefaultTableModel modelo = (DefaultTableModel)principal.tableSalas.getModel();
        modelo.setRowCount(0);
        
        // Busca las salas 2D en el árbol de salas y las muestra en la tabla
        for (int i = 0; i < numSalas; i++) {
            if(sucursal.getSalas().buscarSala(sucursal.getSalas().getRoot(), i+1) instanceof Sala2D){
                modelo.addRow(new Object[]{
                    sucursal.getSalas().buscarSala(sucursal.getSalas().getRoot(), i+1).getNumero(), "2D", sucursal.getSalas().buscarSala(sucursal.getSalas().getRoot(), i+1).getPelicula().getNombre()
                });
            }
        }
    }
            
    public void mostrarSoloSalas3D(Principal principal){
        // Mostrará solo las Salas 3D
        Sucursal sucursal = sucursales.buscarSucursal(sucursales.getRoot(), Integer.parseInt(String.valueOf(principal.comboSucursalesSalas.getSelectedItem())));
        int numSalas = sucursal.getSalas().size(sucursal.getSalas().getRoot());
        
        DefaultTableModel modelo = (DefaultTableModel)principal.tableSalas.getModel();
        modelo.setRowCount(0);
        
        // Busca las salas 3D en el árbol de salas y las muestra en la tabla
        for (int i = 0; i < numSalas; i++) {
            if(sucursal.getSalas().buscarSala(sucursal.getSalas().getRoot(), i+1) instanceof Sala3D){
                modelo.addRow(new Object[]{
                    sucursal.getSalas().buscarSala(sucursal.getSalas().getRoot(), i+1).getNumero(), "3D", sucursal.getSalas().buscarSala(sucursal.getSalas().getRoot(), i+1).getPelicula().getNombre()
                });
            }
        }
        
    }
            
    public void mostrarSoloSalas4DX(Principal principal){
        // Mostrará solo las Salas 4DX
        Sucursal sucursal = sucursales.buscarSucursal(sucursales.getRoot(), Integer.parseInt(String.valueOf(principal.comboSucursalesSalas.getSelectedItem())));
        int numSalas = sucursal.getSalas().size(sucursal.getSalas().getRoot());
        
        DefaultTableModel modelo = (DefaultTableModel)principal.tableSalas.getModel();
        modelo.setRowCount(0);
        
        // Busca las salas 4DX en el árbol de salas y las muestra en la tabla
        for (int i = 0; i < numSalas; i++) {
            if(sucursal.getSalas().buscarSala(sucursal.getSalas().getRoot(), i+1) instanceof Sala4DX){
                modelo.addRow(new Object[]{
                    sucursal.getSalas().buscarSala(sucursal.getSalas().getRoot(), i+1).getNumero(), "4DX", sucursal.getSalas().buscarSala(sucursal.getSalas().getRoot(), i+1).getPelicula().getNombre()
                });
            }
        }
    }
    
    public void mostrarTicketsAlTableTickets(Principal principal){
        // Agrega tickets en la tabla
        Carrito cauxrrito;
        OrdenCompra compraux;
        NodoDoble<Ticket> ticketaux;
        
        //Se vacía el table
       ((DefaultTableModel)principal.tableTickets.getModel()).setRowCount(0);
        
       //Recorremos los clientes en el arbol por cédula 
       for (int i = 0; i<principal.tableClientes.getModel().getRowCount(); i++) {
        //Guardamos el carrito en una variable auxiliar   
        cauxrrito = clientes.buscarCliente(clientes.getRoot(), Long.parseLong(String.valueOf(principal.tableClientes.getValueAt(i, 1)))).getCarrito();
        //Chequeamos que el carrito no esté vacío
        if(!cauxrrito.getOrdenes().isEmpty()){
            //Recorremos las ordenes de compra
            for (int j = 0; j < cauxrrito.getOrdenes().size(); j++) {
                compraux = cauxrrito.getOrdenes().getFirst().getData();
                ticketaux = compraux.getTickets().getHead();
                //Recorremos la lista de tickets y llenamos la tabla
                while(ticketaux!=null){
                    ((DefaultTableModel)principal.tableTickets.getModel()).addRow(new Object[]{
                            String.valueOf(ticketaux.getData().getIdentificador()),String.valueOf(ticketaux.getData().getCliente().getCedula()),ticketaux.getData().getSucursal().getUbicacion(),String.valueOf(ticketaux.getData().getSala().getNumero()),ticketaux.getData().getPelicula(), ticketaux.getData().getFecha()
                    });
                    ticketaux = ticketaux.getNext();
                }
                //Recorremos la cola
                cauxrrito.getOrdenes().enqueue(cauxrrito.getOrdenes().dequeue());
            }
        }
       }
    }
    
    public void ordenarTablaAdmin(Principal principal){
        //Guardamos variables para fácil acceso
        DefaultTableModel dm = (DefaultTableModel) principal.tableAdmin.getModel();
        int tickets;
        int aux;
        String sala, sucursal, salaux, sucursaux;
        
        //Recorremos la tabla 2 veces, comparando valores consigo misma
        for (int i = 0; i < dm.getRowCount(); i++) {
            tickets = Integer.parseInt(String.valueOf(dm.getValueAt(i, 2)));
            for (int j = 0; j < dm.getRowCount(); j++) {
                aux = Integer.parseInt(String.valueOf(dm.getValueAt(j, 2)));
                //Condición para ordenar de mayor a menor
                if(aux<tickets){
                    //Salvamos las variables
                    sucursal = String.valueOf(dm.getValueAt(i, 0));
                    sala = String.valueOf(dm.getValueAt(i, 1));
                    
                    sucursaux = String.valueOf(dm.getValueAt(j, 0));
                    salaux = String.valueOf(dm.getValueAt(j, 1));
                    
                    //Cambiamos de lugar las variables
                    dm.setValueAt(sucursal, j, 0);
                    dm.setValueAt(sala, j, 1);
                    dm.setValueAt(String.valueOf(tickets), j, 2);
                    
                    dm.setValueAt(sucursaux, i, 0);
                    dm.setValueAt(salaux, i, 1);
                    dm.setValueAt(String.valueOf(aux), i, 2);
                    
                    //Asignamos el nuevo valor de tickets
                    tickets = aux;
                }
            }
        }
    }
    
    public void organizarTickets(Principal principal){
        //Colocamos un TableRowSorter para poder organizar los tickets en la tabla
        DefaultTableModel dm = (DefaultTableModel) principal.tableTickets.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(dm);
        principal.tableTickets.setRowSorter(tr);
    }
    
    public void pagarOrden(OrdenCompra orden, Carrito carrito, Principal principal){
        //Se llama al metodo setPagada y se cambian los valores de precio y "¿Pagado?" en la tabla
        orden.setPagada();
        carrito.tableCarrito.setValueAt("Si",carrito.tableCarrito.getSelectedRow(), 8);
        carrito.tableCarrito.setValueAt(orden.getPrecioTotal(),carrito.tableCarrito.getSelectedRow(), 7);
    }
    
}
