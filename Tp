
// SistemaEncuestas.java
import java.util.Scanner;
import java.util.InputMismatchException;

public class Main {

    private static final boolean MODO_PRUEBA = true;
    private static final int MAX_ENCUESTADOS = 60;
    private static final int ATRIBUTOS = 6;

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        String[][] personas = new String[MAX_ENCUESTADOS][ATRIBUTOS];
        int cantidad = 0;

        if (MODO_PRUEBA) {
            cantidad = cargarDatosPrueba(personas);
            System.out.println("[INFO] Se cargaron " + cantidad + " personas de prueba.\n");
        }

        mostrarMenuYElegirOpcion(s, personas, cantidad);
        s.close();
    }

    private static int cargarDatosPrueba(String[][] personas) {
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

    private static void mostrarMenuYElegirOpcion(Scanner s, String[][] personas, int cantidadInicial) {
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
            System.out.print("Seleccione una opción (1-8): ");

            opcion = ingresarEntero(s, 1, 8);
            cantidad = generarAccion(opcion, s, personas, cantidad);
        } while (opcion != 8);
    }

    private static int generarAccion(int opcion, Scanner s, String[][] personas, int cantidad) {
        switch (opcion) {
            case 1:
                return ingresarPersona(s, personas, cantidad);
            case 2:
                if (!sistemaVacio(cantidad))
                    consultarPersona(s, personas, cantidad);
                return cantidad;
            case 3:
                if (!sistemaVacio(cantidad))
                    modificarPersona(s, personas, cantidad);
                return cantidad;
            case 4:
                return sistemaVacio(cantidad) ? cantidad : eliminarPersona(s, personas, cantidad);
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
                System.out.println("[ERROR] Opción inválida.");
                return cantidad;
        }
    }

    private static boolean sistemaVacio(int cantidad) {
        if (cantidad == 0) {
            System.out.println("[ERROR] El sistema está vacío.");
            return true;
        }
        return false;
    }

    private static int ingresarPersona(Scanner s, String[][] personas, int cantidad) {
        if (cantidad >= MAX_ENCUESTADOS) {
            System.out.println("[ERROR] Capacidad máxima alcanzada.");
            return cantidad;
        }
        System.out.print("Ingrese DNI (8 dígitos): ");
        int dni = ingresarEntero(s, 10000000, 99999999);
        if (buscarPersona(personas, cantidad, String.valueOf(dni)) != -1) {
            System.out.println("[ERROR] El DNI ingresado ya existe.");
            return cantidad;
        }
        s.nextLine();
        System.out.print("Ingrese nombre completo (ej. Juan Pérez): ");
        String nombre = s.nextLine();
        System.out.print("Sexo (1: M, 2: F, 3: Otro): ");
        int sexo = ingresarEntero(s, 1, 3);
        System.out.print("Edad (18-99): ");
        int edad = ingresarEntero(s, 18, 99);
        System.out.print("¿Trabaja? (1: Sí, 2: No): ");
        int trabaja = ingresarEntero(s, 1, 2);
        int sueldo = 0;
        if (trabaja == 1) {
            System.out.print("Sueldo: ");
            sueldo = ingresarEntero(s, 0, 500000);
        }
        personas[cantidad][0] = String.valueOf(dni);
        personas[cantidad][1] = nombre;
        personas[cantidad][2] = String.valueOf(sexo);
        personas[cantidad][3] = String.valueOf(edad);
        personas[cantidad][4] = String.valueOf(trabaja);
        personas[cantidad][5] = String.valueOf(sueldo);
        System.out.println("[OK] Persona registrada con éxito.");
        return cantidad + 1;
    }

    private static boolean validarDNI(String valor) {
        try {
            int dni = Integer.parseInt(valor);
            return dni >= 10000000 && dni <= 99999999;
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

    private static void consultarPersona(Scanner s, String[][] personas, int cantidad) {
        System.out.print("Ingrese DNI: ");
        String dni = s.nextLine();
        if (!validarDNI(dni)) {
            System.out.println("[ERROR] DNI inválido.");
            return;
        }
        int pos = buscarPersona(personas, cantidad, dni);
        if (pos == -1)
            System.out.println("[ERROR] Persona no encontrada.");
        else
            imprimirPersona(personas[pos]);
    }

    private static void modificarPersona(Scanner s, String[][] personas, int cantidad) {
        System.out.print("Ingrese DNI de la persona a modificar: ");
        String dni = s.nextLine();
        int pos = buscarPersona(personas, cantidad, dni);
        if (pos == -1) {
            System.out.println("[ERROR] Persona no encontrada.");
            return;
        }
        System.out.print("Nuevo nombre (Enter para mantener): ");
        String nuevoNombre = s.nextLine();
        if (!nuevoNombre.isEmpty())
            personas[pos][1] = nuevoNombre;
    }

    private static int eliminarPersona(Scanner s, String[][] personas, int cantidad) {
        System.out.print("Ingrese DNI: ");
        String dni = s.nextLine();
        int pos = buscarPersona(personas, cantidad, dni);
        if (pos == -1) {
            System.out.println("[ERROR] Persona no encontrada.");
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

        System.out.printf("%% Hombres: %.2f%%\n", obtenerPorcentaje(genero[0], cantidad));
        System.out.printf("%% Mujeres: %.2f%%\n", obtenerPorcentaje(genero[1], cantidad));
        System.out.printf("%% Otros: %.2f%%\n", obtenerPorcentaje(genero[2], cantidad));
        System.out.println("Edad promedio: " + obtenerPromedio(edadTotal, cantidad));

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
                if (sueldo > max) {
                    max = sueldo;
                    maxPersona = personas[i][1];
                }
                if (sueldo < min) {
                    min = sueldo;
                    minPersona = personas[i][1];
                }
            }
        }

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
        int valor;
        while (true) {
            try {
                valor = s.nextInt();
                s.nextLine();
                if (valor >= min && valor <= max) {
                    return valor;
                } else {
                    System.out.print("[ERROR] Valor fuera de rango. Ingrese entre " + min + " y " + max + ": ");
                }
            } catch (InputMismatchException e) {
                System.out.print("[ERROR] Debe ingresar un número válido: ");
                s.nextLine();
            }
        }
    }
}
