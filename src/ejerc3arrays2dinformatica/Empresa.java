package ejerc3arrays2dinformatica;

import Utilidades.Alfanumerico;
import Utilidades.Fecha;
import Utilidades.Numero;
import java.util.Arrays;

/**
 *
 * @author dam
 */
public class Empresa {

    Empleado[] empleados;

    final Zona[] ZONAS = {
        new Zona("CyL", 2000f),
        new Zona("Andalucia", 2200f),
        new Zona("Levante", 2500f),
        new Zona("Madrid", 3000f),};

    final float[][] IRPF = {
        {0.10f, 0.15f, 0.20f, 0.25f},
        {0.12f, 0.14f, 0.16f, 0.18f},
        {0.05f, 0.07f, 0.10f, 0.15f},};

    String[] situacion = {"Soltero", "Casado", "Otra situacion"};
    float[] limites = {3000f, 5000f, 10000f, Float.MAX_VALUE};

    public Empresa(int nEmpleados) {
        empleados = new Empleado[nEmpleados];
    }

    public void cargaDatos() {
        String nombre;
        int posZona, posSit;
        Fecha fAlta;
        float[] ventas;
        for (int b = 0; b < empleados.length; b++) {
            ventas = new float[6];
            nombre = Alfanumerico.pedirAlfanumerico("Introduce el nombre del empleado");
            posSit = buscarSituacion("Introduce la situacion del empleado");
            posZona = pedirZona("Introduce la zona del empleado");
            fAlta = Fecha.pedirFecha("Introduce la fecha de alta del empleado");
            for (int c = 0; c < ventas.length; c++) {
                ventas[c] = Numero.pedirNumeroRealPositivo("La venta del mes de " + fAlta.MESES[c]);
            }
            empleados[b] = new Empleado(nombre, ZONAS[posZona], posSit, fAlta, ventas);
        }
    }

    /*
    Este metodo escrito anteriormente no funciona, las ventas que guarda en los empleados,
    siempre son las ultimas ventas introducidas, porque al crear el array y reservar el 
    espacio de memoria fuera del bucle ese espacio de memoria reservado siempre sera el mismo,
    y el array al ser un objeto al constructor no le pasamos los datos que contiene el array
    si no que le pasamos la direccion de memoria donde guardamos los datos de ese array, por lo
    que guardaremos solo los ultimos datos. Para solucionar esto debemos crear el espacio en memoria
    dentro del bucle y no fuera.
     */
    public void cargaDatosMal() {
        String nombre;
        int posZona, posSit;
        Fecha fAlta;
        float[] ven = new float[6]; //Esta es la linea que falla aqui tendria que ser -> float[] ventas;
        for (int b = 0; b < empleados.length; b++) {
            //Y aqui reservamos el espacio en memoria para que cada que repitamos cree un espacio nuevo
            //ventas = new float[6];
            nombre = Alfanumerico.pedirAlfanumerico("Introduce el nombre del empleado");
            posSit = buscarSituacion("Introduce la situacion del empleado");
            posZona = pedirZona("Introduce la zona del empleado");
            fAlta = Fecha.pedirFecha("Introduce la fecha de alta del empleado");
            for (int c = 0; c < ven.length; c++) {
                ven[c] = Numero.pedirNumeroRealPositivo("La venta del mes de " + fAlta.MESES[c]);
            }
            empleados[b] = new Empleado(nombre, ZONAS[posZona], posSit, fAlta, ven);
        }
    }

    /*
    Otra solucion para que no machaque los datos es trabajar directamente, 
    con el array ventas del empelado. Para ello necesitariamos un constructor diferente
    que no reciba las ventas para poderlas introducir posteriormente.
    De esta forma no necesitamos crear ningun array externo. Esta opcion es mas versatil,
    ya que nos permite poder extraer la parte del codigo donde introducimos las ventas
    y crear un metodo aparte donde introducirlas.
    */
    public void cargaDatosPrueba() {
        String nombre;
        int posZona, posSit;
        Fecha fAlta;
        for (int b = 0; b < empleados.length; b++) {
            nombre = Alfanumerico.pedirAlfanumerico("Introduce el nombre del empleado");
            posSit = buscarSituacion("Introduce la situacion del empleado");
            posZona = pedirZona("Introduce la zona del empleado");
            fAlta = Fecha.pedirFecha("Introduce la fecha de alta del empleado");
            empleados[b] = new Empleado(nombre, ZONAS[posZona], posSit, fAlta);
            for (int c = 0; c < empleados[b].getVentas().length; c++) {
                empleados[b].setVentaMes(c, Numero.pedirNumeroRealPositivo("La venta del mes de " + fAlta.MESES[c]));
            }
            System.out.print(""); //esta linea es solo para poder saltar directamente a ella en la depuracion
        }
    }

    private int buscarSituacion(String mensaje) {
        String cadena;
        boolean ok = false;
        int pos;
        do {
            pos = 0;
            cadena = Alfanumerico.pedirAlfanumerico(mensaje);
            while (!ok && pos < situacion.length) {
                if (cadena.equalsIgnoreCase(situacion[pos])) {
                    ok = true;
                } else {
                    pos++;
                }
            }
            if (!ok) {
                System.out.println("No encontrado.");
            }
        } while (!ok);
        return pos;
    }

    private int pedirZona(String mensaje) {
        int pos;
        String cadena;
        boolean ok = false;
        do {
            pos = 0;
            cadena = Alfanumerico.pedirAlfanumerico(mensaje);
            while (!ok && pos < ZONAS.length) {
                if (cadena.equalsIgnoreCase(ZONAS[pos].getNombre())) {
                    ok = true;
                } else {
                    pos++;
                }
            }
            if (!ok) {
                System.out.println("Zona no encontrada");
            }
        } while (!ok);
        return pos;
    }

    public void informe() {
        System.out.println("INFORME DE EMPLEADO");

        float salario, sumaVentas;
        int posIRPF;
        for (int b = 0; b < empleados.length; b++) {
            sumaVentas = empleados[b].sumaVentas();
            salario = empleados[b].sueldoBruto(sumaVentas);
            posIRPF = posIRPF(salario);
            salario = salario * (1 - IRPF[empleados[b].getEstado()][posIRPF]);
            System.out.println("Zona\tNombre Empleado\t\tFecha Alta\tTotal Ventas\tSueldo a Percibir");
            System.out.println(empleados[b].getZona().getNombre() + "\t"
                    + empleados[b].getNombre() + "\t\t\t"
                    + empleados[b].getfAlta().fechaCompleta() + "\t" + sumaVentas + "\t"
                    + salario);
            System.out.print("Ventas ->");
            for (int c = 0; c < empleados[b].getVentas().length; c++) {
                System.out.print(Fecha.MESES[c] + ": " + empleados[b].getVenta(c));
            }
            System.out.print("\n\n");
        }
    }

    private int posIRPF(float salario) {
        int pos = 0;
        boolean ok = false;
        while (!ok) {
            if (salario < limites[pos]) {
                ok = true;
            } else {
                pos++;
            }
        }
        return pos;
    }
}
