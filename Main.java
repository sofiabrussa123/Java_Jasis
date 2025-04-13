package main;

import java.util.Scanner;
import java.util.InputMismatchException;

public class Main {

    public static void main(String[] args) {
        final int MIN_DNI = 10000000;
        final int MAX_DNI = 99999999;
        final int MIN_EDAD = 18;
        final int MAX_EDAD = 99;
        final int MIN_SUELDO = 0;
        final int MAX_SUELDO = 500000;
        final boolean MODO_PRUEBA = true;
        final int MAX_ENCUESTADOS = 60;
        final int ATRIBUTOS = 6;

        Scanner s = new Scanner(System.in);
        String[][] personas = new String[MAX_ENCUESTADOS][ATRIBUTOS];
        int cantidad = 0;

        if (MODO_PRUEBA) {
            cantidad = cargarDatosPrueba(personas, ATRIBUTOS);
            System.out.println("[INFO] Se cargaron " + cantidad + " personas de prueba.\n");
        }

        mostrarMenuYElegirOpcion(s, personas, cantidad, MAX_ENCUESTADOS, ATRIBUTOS, MIN_DNI, MAX_DNI, MIN_EDAD,
                MAX_EDAD, MIN_SUELDO, MAX_SUELDO);
        s.close();
    }

    private static int cargarDatosPrueba(String[][] personas, final int ATRIBUTOS) {
        String[][] datos = {
                { "12345678", "Juan Pérez", "1", "25", "1", "50000" },
                { "23456789", "María López", "2", "30", "1", "60000" },
                { "34567890", "Carlos García", "1", "22", "2", "0" },
                { "45678901", "Ana Martínez", "2", "28", "1", "70000" },
                { "56789012", "Luis Rodríguez", "1", "35", "2", "0" },
                { "67890123", "Elena Fernández", "2", "40", "1", "80000" },
                { "78901234", "Javier Sánchez", "1", "27", "1", "90000" },
                { "89012345", "Laura Gómez", "2", "33", "2", "0" },
                { "90123456", "Pedro Díaz", "1", "29", "1", "100000" },
                { "12345679", "Sofía Ruiz", "2", "31", "1", "110000" }
        };

        for (int i = 0; i < datos.length; i++) {
            System.arraycopy(datos[i], 0, personas[i], 0, ATRIBUTOS);
        }
        return datos.length;
    }

    private static void mostrarMenuYElegirOpcion(Scanner s, String[][] personas, int cantidadInicial,
            final int MAX_ENCUESTADOS, final int ATRIBUTOS, final int MIN_DNI, final int MAX_DNI, final int MIN_EDAD,
            final int MAX_EDAD, final int MIN_SUELDO, final int MAX_SUELDO) {
        int cantidad = cantidadInicial;
        int opcion;

        do {
            System.out.println("\n===== MENÚ PRINCIPAL =====");
            System.out.println("1. Agregar persona");
            System.out.println("2. Consultar persona");
            System.out.println("3. Modificar persona");
            System.out.println("4. Eliminar persona");
            System.out.println("5. Listar personas");
            System.out.println("6. Mostrar estadísticas");
            System.out.println("7. Buscar sueldos extremos");
            System.out.println("8. Salir");
            System.out.println("===========================");
            System.out.print("Seleccione una opción (1-8): ");

            opcion = ingresarEntero(s, 1, 8);
            cantidad = generarAccion(opcion, s, personas, cantidad, MAX_ENCUESTADOS, ATRIBUTOS, MIN_DNI, MAX_DNI,
                    MIN_EDAD, MAX_EDAD, MIN_SUELDO, MAX_SUELDO);
        } while (opcion != 8);
    }

    private static int generarAccion(int opcion, Scanner s, String[][] personas, int cantidad,
            final int MAX_ENCUESTADOS, final int ATRIBUTOS, final int MIN_DNI, final int MAX_DNI, final int MIN_EDAD,
            final int MAX_EDAD, final int MIN_SUELDO, final int MAX_SUELDO) {
        switch (opcion) {
            case 1:
                return ingresarPersona(s, personas, cantidad, MAX_ENCUESTADOS, MIN_DNI, MAX_DNI, MIN_EDAD, MAX_EDAD,
                        MIN_SUELDO, MAX_SUELDO);
            case 2:
                if (!sistemaVacio(cantidad))
                    consultarPersona(s, personas, cantidad, MIN_DNI, MAX_DNI);
                return cantidad;
            case 3:
                if (!sistemaVacio(cantidad))
                    modificarPersona(s, personas, cantidad);
                return cantidad;
            case 4:
                return sistemaVacio(cantidad) ? cantidad : eliminarPersona(s, personas, cantidad, ATRIBUTOS);
            case 5:
                if (!sistemaVacio(cantidad))
                    listarPersonas(personas, cantidad);
                return cantidad;
            case 6:
                if (!sistemaVacio(cantidad))
                    mostrarEstadisticas(personas, cantidad);
                return cantidad;
            case 7:
                if (!sistemaVacio(cantidad))
                    buscarSueldos(personas, cantidad);
                return cantidad;
            case 8:
                System.out.println("[INFO] Saliendo del sistema. Hasta pronto!");
                return cantidad;
            default:
                System.err.println("[ERROR] Opción inválida.");
                return cantidad;
        }
    }

    private static boolean sistemaVacio(int cantidad) {
        if (cantidad == 0) {
            System.err.println("[ERROR] El sistema está vacío.");
            return true;
        }
        return false;
    }

    private static int ingresarPersona(Scanner s, String[][] personas, int cantidad, final int MAX_ENCUESTADOS,
            final int MIN_DNI, final int MAX_DNI, final int MIN_EDAD, final int MAX_EDAD, final int MIN_SUELDO,
            final int MAX_SUELDO) {
        if (cantidad >= MAX_ENCUESTADOS) {
            System.err.println("[ERROR] Capacidad máxima alcanzada.");
            return cantidad;
        }
        System.out.print("Ingrese DNI (8 dígitos): ");
        int dni = ingresarEntero(s, MIN_DNI, MAX_DNI);
        if (!chequearDNI(personas, cantidad, dni)) return cantidad;
        
        System.out.print("Ingrese nombre completo (ej. Juan Pérez): ");

        personas[cantidad][1] = String.valueOf(s.nextLine());
        System.out.print("Sexo (1: M, 2: F, 3: Otro): ");

        personas[cantidad][2] = String.valueOf(ingresarEntero(s, 1, 3));
        System.out.print("Edad (18-99): ");

        personas[cantidad][3] = String.valueOf(ingresarEntero(s, MIN_EDAD, MAX_EDAD));
        System.out.print("¿Trabaja? (1: Sí, 2: No): ");
        personas[cantidad][4] = String.valueOf(ingresarEntero(s, 1, 2));
        
        if (Integer.valueOf(personas[cantidad][4]) == 1) {
            System.out.print("Sueldo: ");
            personas[cantidad][5] = String.valueOf(ingresarEntero(s, MIN_SUELDO, MAX_SUELDO));
        }else personas[cantidad][5] = "0";
        System.out.println("[OK] Persona registrada con éxito.");
        return cantidad + 1;
    }

    public static boolean chequearDNI(String[][] personas, int cantidad, int dni) {
        if (buscarPersona(personas, cantidad, String.valueOf(dni)) != -1) {
            System.err.println("[ERROR] El DNI ingresado ya existe.");
            return false;
        }
        personas[cantidad][0] = String.valueOf(dni);
        return true;
    }

    private static boolean validarDNI(String valor, final int MIN_DNI, final int MAX_DNI) {
        try {
            int dni = Integer.parseInt(valor);
            return dni >= MIN_DNI && dni <= MAX_DNI;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static int buscarPersona(String[][] personas, int cantidad, String dni) {
        for (int i = 0; i < cantidad; i++) {
            if (personas[i][0].equals(dni))
                return i;
        }
        return -1;
    }

    private static void consultarPersona(Scanner s, String[][] personas, int cantidad, final int MIN_DNI,
            final int MAX_DNI) {
        System.out.print("Ingrese DNI: ");
        String dni = s.nextLine();
        if (!validarDNI(dni, MIN_DNI, MAX_DNI)) {
            System.err.println("[ERROR] DNI inválido.");
            return;
        }
        int pos = buscarPersona(personas, cantidad, dni);
        if (pos == -1)
            System.err.println("[ERROR] Persona no encontrada.");
        else
            imprimirPersona(personas[pos]);
    }

    private static void modificarPersona(Scanner s, String[][] personas, int cantidad) {
        System.out.print("Ingrese DNI de la persona a modificar: ");
        String dni = s.nextLine();
        int pos = buscarPersona(personas, cantidad, dni);
        if (pos == -1) {
            System.err.println("[ERROR] Persona no encontrada.");
            return;
        }
        System.out.print("Nuevo nombre (Enter para mantener): ");
        String nuevoNombre = s.nextLine();
        if (!nuevoNombre.isEmpty())
            personas[pos][1] = nuevoNombre;
    }

    private static int eliminarPersona(Scanner s, String[][] personas, int cantidad, final int ATRIBUTOS) {
        System.out.print("Ingrese DNI: ");
        String dni = s.nextLine();
        int pos = buscarPersona(personas, cantidad, dni);
        if (pos == -1) {
            System.err.println("[ERROR] Persona no encontrada.");
            return cantidad;
        }
        for (int i = pos; i < cantidad - 1; i++) {
            personas[i] = personas[i + 1];
        }
        personas[cantidad - 1] = new String[ATRIBUTOS];
        System.out.println("[OK] Persona eliminada.");
        return cantidad - 1;
    }

    private static void listarPersonas(String[][] personas, int cantidad) {
        for (int i = 0; i < cantidad; i++) {
            imprimirPersona(personas[i]);
            System.out.println("------------------------");
        }
    }

    private static void imprimirPersona(String[] p) {
        System.out.println("DNI: " + p[0]);
        System.out.println("Nombre: " + p[1]);
        System.out.println("Sexo: " + p[2]);
        System.out.println("Edad: " + p[3]);
        System.out.println("Trabaja: " + (p[4].equals("1") ? "Sí" : "No"));
        System.out.println("Sueldo: $" + p[5]);
    }

    private static void mostrarEstadisticas(String[][] personas, int cantidad) {
        int[] genero = new int[3];
        int[] trabajaPorGenero = new int[3];
        int[] sueldoPorGenero = new int[3];
        int[] cuentaGeneroConSueldo = new int[3];
        int edadTotal = 0;

        for (int i = 0; i < cantidad; i++) {
            int g = Integer.parseInt(personas[i][2]) - 1;
            int edad = Integer.parseInt(personas[i][3]);
            int trabaja = Integer.parseInt(personas[i][4]);
            int sueldo = Integer.parseInt(personas[i][5]);

            genero[g]++;
            edadTotal += edad;
            if (trabaja == 1) {
                trabajaPorGenero[g]++;
                sueldoPorGenero[g] += sueldo;
                cuentaGeneroConSueldo[g]++;
            }
        }

        System.out.println("\n---- Estadísticas Generales ----");
        mostrarPorcentajeGeneros(genero, cantidad);
        mostrarPromedioEdad(edadTotal, cantidad);
        System.out.println("\n---- Estadísticas por Género ----");
        mostrarEstadisticasPorGenero(trabajaPorGenero, sueldoPorGenero, cuentaGeneroConSueldo, genero);
    }

    private static void mostrarPorcentajeGeneros(int[] genero, int cantidad) {
        System.out.printf("%% Hombres: %.2f%%\n", obtenerPorcentaje(genero[0], cantidad));
        System.out.printf("%% Mujeres: %.2f%%\n", obtenerPorcentaje(genero[1], cantidad));
        System.out.printf("%% Otros: %.2f%%\n", obtenerPorcentaje(genero[2], cantidad));
    }

    private static void mostrarPromedioEdad(int edadTotal, int cantidad) {
        System.out.println("Edad promedio: " + obtenerPromedio(edadTotal, cantidad));
    }

    private static void mostrarEstadisticasPorGenero(int[] trabajaPorGenero, int[] sueldoPorGenero,
            int[] cuentaGeneroConSueldo, int[] genero) {
        for (int i = 0; i < 3; i++) {
            System.out.printf("%% que trabaja (Género %d): %.2f%%\n", i + 1,
                    obtenerPorcentaje(trabajaPorGenero[i], genero[i]));
            System.out.printf("Sueldo promedio (Género %d): $%.2f\n", i + 1,
                    obtenerPromedio(sueldoPorGenero[i], cuentaGeneroConSueldo[i]));
        }
    }

    private static void buscarSueldos(String[][] personas, int cantidad) {
        int max = -1, min = Integer.MAX_VALUE;
        String maxPersona = "", minPersona = "";

        for (int i = 0; i < cantidad; i++) {
            if (personas[i][4].equals("1")) {
                int sueldo = Integer.parseInt(personas[i][5]);
                actualizarSueldoMaxMin(sueldo, personas[i][1], max, min, maxPersona, minPersona);
            }
        }

        System.out.println("\n---- Sueldos ----");
        mostrarSueldoMaxMin(maxPersona, max, minPersona, min);
    }

    private static void actualizarSueldoMaxMin(int sueldo, String persona, int max, int min, String maxPersona,
            String minPersona) {
        if (sueldo > max) {
            max = sueldo;
            maxPersona = persona;
        }
        if (sueldo < min) {
            min = sueldo;
            minPersona = persona;
        }
    }

    private static void mostrarSueldoMaxMin(String maxPersona, int max, String minPersona, int min) {
        System.out.println("Mayor sueldo: " + maxPersona + " ($" + max + ")");
        System.out.println("Menor sueldo: " + minPersona + " ($" + min + ")");
    }

    private static double obtenerPorcentaje(int parte, int total) {
        return total == 0 ? 0 : (parte * 100.0 / total);
    }

    private static double obtenerPromedio(int suma, int total) {
        return total == 0 ? 0 : (suma * 1.0 / total);
    }

    private static int ingresarEntero(Scanner s, int min, int max) {
        int numero;
        while (true) {
            try {
                numero = Integer.parseInt(s.nextLine());
                if (numero >= min && numero <= max) {
                    return numero;
                } else {
                    System.out.println("Por favor, ingrese un número entre " + min + " y " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Intente nuevamente.");
            }
        }
    }
}
