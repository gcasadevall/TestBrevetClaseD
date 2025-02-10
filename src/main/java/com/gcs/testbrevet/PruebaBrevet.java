package com.gcs.testbrevet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class PruebaBrevet {

    private final List<Pregunta> preguntas;

    public PruebaBrevet() {
        super();
        this.preguntas = cargarPreguntas();
    }

    public void listar() {

        System.out.println("Bienvenido al examen Brevet categoria D");

        int nroPregunta = 1;
        for (Pregunta pregunta : preguntas) {

            System.out.println("\n" + nroPregunta + ") " + pregunta.getPregunta());

            for (Opcion opcion : pregunta.getOpciones()) {
                if (opcion.isCorrecta()) {
                    System.out.println("\tR: " + opcion.getOpcion());
                }
            }

            nroPregunta++;
        }
    }

    public void realizar() {
        realizar(false);
    }

    public void realizar(boolean mostrarRespuestaErronea) {

        limpiarPantalla();

        int nroPregunta = 1;
        int nroPreguntasCorrectas = 0;
        int nroPreguntasIncorrectas = 0;
        int cantPreguntasCorrectasSalva = 70;
        boolean salir = false;

        for (Pregunta pregunta : preguntas) {

            while (true) {

                System.out.println("*** Bienvenido al examen Brevet categoria D ***");
                System.out.println("\nPregunta " + nroPregunta + " de " + preguntas.size());
                System.out.println("Preguntas correctas " + nroPreguntasCorrectas);
                System.out.println("Preguntas incorrectas " + nroPreguntasIncorrectas);

                // Inicializo 
                int nroOpcion = 0;
                int nroOpcionCorrecta = 0;

                // Imprimo pregunta
                System.out.println("\n" + nroPregunta + ") " + pregunta.getPregunta());
                for (Opcion opcion : pregunta.getOpciones()) {
                    System.out.println("\t[" + nroOpcion + "] - " + opcion.getOpcion());
                    if (opcion.isCorrecta()) {
                        nroOpcionCorrecta = nroOpcion;
                    }
                    nroOpcion++;
                }

                // Pregunto respuesta
                int nroOpcionElegida = preguntar(pregunta.getOpciones().size() - 1);
                if (nroOpcionElegida == -1) {
                    salir = true;
                    break;
                } else if (nroOpcionElegida == -2) {
                    System.out.println("\nOpción inválida, debe ingresar un número entre 0 y " + pregunta.getOpciones().size());
                    esperar();
                    limpiarPantalla();
                } else {

                    if (nroOpcionCorrecta == nroOpcionElegida) {
                        nroPreguntasCorrectas++;
                        limpiarPantalla();
                    } else {
                        if (mostrarRespuestaErronea) {
                            System.out.println("\nRespuesta equivocada la correcta era la nro " + nroOpcionCorrecta);
                            esperar();
                        }
                        nroPreguntasIncorrectas++;
                        limpiarPantalla();
                    }
                    break;
                }
            }

            if (salir) {
                break;
            }

            nroPregunta++;
        }

        if (salir) {
            System.out.println("\n*** Se abortó la prueba ***");
        } else {
            if (nroPreguntasCorrectas >= cantPreguntasCorrectasSalva) {
                System.out.println("\n\n F E L I C I T A C I O N E S   S A L V O  !!");
            } else {
                System.out.println("\n\n LO SIENTO A ESTUDIAR MAS o Comprarle un Chivas al profe. !!");
            }
            System.out.println("\n\n*** Fin de la prueba ***");
        }
    }

    /**
     * Pregunta un numero Si se ingresa "Salir" retorna -1 Si se ingresa una
     * opcion invalida entre 0 y max retorna -2
     *
     * @return un numero entre 0 y max o -1 o -2
     */
    private int preguntar(int max) {
        System.out.println("\nDigite el nro de la opción que considera correcta o Salir para terminar\n");
        Scanner scanner = new Scanner(System.in);
        try {
            final String option = scanner.nextLine();
            if (option.equalsIgnoreCase("Salir")) {
                return -1;
            }
            int nro = Integer.parseInt(option);
            if (nro >= 0 && nro <= max) {
                return nro;
            } else {
                return -2;
            }
        } catch (NumberFormatException e) {
            return -2;
        }
    }

    public void esperar() {
        System.out.println("Presione enter para continuar.");
        new java.util.Scanner(System.in).nextLine();
    }

    public void limpiarPantalla() {
        try {
            String sistemaOperativo = System.getProperty("os.name");
            ArrayList<String> comando = new ArrayList<>();
            if (sistemaOperativo.contains("Windows")) {
                comando.add("cmd");
                comando.add("/C");
                comando.add("cls");

            } else {
                comando.add("clear");
            }

            ProcessBuilder pb = new ProcessBuilder(comando);
            Process iniciarProceso = pb.inheritIO().start();
            iniciarProceso.waitFor();

        } catch (Exception e) {
            System.out.println("Error al limpiar la pantalla" + e.getMessage());
        }
    }

    private List<Pregunta> cargarPreguntas() {
        List<Pregunta> preguntas = new LinkedList<>();

        Pregunta pregunta;

        pregunta = new Pregunta("Cuál es la luz reglamentaria de tope");
        pregunta.addOpcion("Es una luz blanca", true);
        pregunta.addOpcion("Es una luz negra");
        pregunta.addOpcion("Es una luz verde");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Qué luz debe llevar una embarcación en la banda de estribor");
        pregunta.addOpcion("Una luz verde", true);
        pregunta.addOpcion("Una luz roja");
        pregunta.addOpcion("Una luz negra");
        pregunta.addOpcion("Ninguna luz");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Qué luz debe llevar una embarcación en la banda de babor");
        pregunta.addOpcion("Una luz roja", true);
        pregunta.addOpcion("Una luz verde");
        pregunta.addOpcion("Una luz negra");
        pregunta.addOpcion("Ninguna luz");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Qué señal exhibirá todo buque en tarea de remolque siendo éste superior a 183 mts., en horas del día");
        pregunta.addOpcion("Una señal bicónica", true);
        pregunta.addOpcion("Una señal roja");
        pregunta.addOpcion("Un balon negro");
        pregunta.addOpcion("Ninguna señal");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Qué luces debe exibir durante la noche una embarcación navegando a vela");
        pregunta.addOpcion("Luces de banda", true);
        pregunta.addOpcion("Luz de estribor");
        pregunta.addOpcion("Luz de babor");
        pregunta.addOpcion("Ninguna luz");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Qué señal exhibira un buque que navegue a vela y también mecánicamente durante el día");
        pregunta.addOpcion("Una señal cónica con el vértice hacia abajo.", true);
        pregunta.addOpcion("Un balon negro a babor");
        pregunta.addOpcion("Una señal bicónica");
        pregunta.addOpcion("Ninguna señal");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Qué señal deberá mostrar un pesquero de arrastre de 20 o más metros de eslora durante el día");
        pregunta.addOpcion("Dos conos negros unidos por sus puntas en línea vertical", true);
        pregunta.addOpcion("Un balon negro a babor");
        pregunta.addOpcion("Ninguna señal");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Qué señal deberá tener izada un pesquero de arrastre de menos de 20 metros de eslora durante el día");
        pregunta.addOpcion("Dos conos negros unidos por sus puntas o un canasto de pesca", true);
        pregunta.addOpcion("Un balon negro a babor");
        pregunta.addOpcion("Una señal bicónica");
        pregunta.addOpcion("Ninguna señal");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Qué señal deberá exhibir todo buque sin gobierno en horas del día");
        pregunta.addOpcion("Dos balones negros", true);
        pregunta.addOpcion("Una señal bicónica");
        pregunta.addOpcion("Ninguna señal");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Qué señal deberá tener izada durante el día toda embarcación que se encuentre fondeada");
        pregunta.addOpcion("Un balón negro", true);
        pregunta.addOpcion("Una señal bicónica");
        pregunta.addOpcion("Ninguna señal");

        preguntas.add(pregunta);

        pregunta = new Pregunta("Qué señal deberá exhibir todo buque varado durante el día");
        pregunta.addOpcion("Tres balones negros en linea vertical", true);
        pregunta.addOpcion("Una señal bicónica");
        pregunta.addOpcion("Ninguna señal");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Las embarcaciones de menos de 7 metros de eslora cuando están fondeadas o varadas fuera de la zona de navegación, paso o canales deberán de llevar las luces o señales de un buque varado o fondeado");
        pregunta.addOpcion("No", true);
        pregunta.addOpcion("Si");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Que se entiende por pitada corta");
        pregunta.addOpcion("Un sonido de aproximadamente un segundo de duración", true);
        pregunta.addOpcion("Un sonido de seis segundo");
        pregunta.addOpcion("Un sonido de 1 minuto");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Que se entiende por pitada larga");
        pregunta.addOpcion("Un sonido de aproximadamente cuatro a seis segundo", true);
        pregunta.addOpcion("Un sonido de seis segundo");
        pregunta.addOpcion("Un sonido de 1 minuto");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Que significa una pitada corta");
        pregunta.addOpcion("Caigo a estribor", true);
        pregunta.addOpcion("Caigo a babor");
        pregunta.addOpcion("Sigo de largo");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Que significan dos pitadas cortas");
        pregunta.addOpcion("Caigo a babor", true);
        pregunta.addOpcion("Sigo de largo");
        pregunta.addOpcion("Caigo a estribor");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Que significan tres pitadas cortas");
        pregunta.addOpcion("Estoy dando atrás", true);
        pregunta.addOpcion("Estoy cruzando");
        pregunta.addOpcion("No significa nada");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Cuando dos embarcaciones se encuentran a la vista en un paso o canal angosto. Que señales deberá realizar el buque que pretende alcanzar a otro.");
        pregunta.addOpcion("2 pitadas largas seguidas: pretendo alcanzarle por su banda de estribor", true);
        pregunta.addOpcion("2 pitadas largas seguidas de dos cortas: pretendo alcanzarle por su banda de babor", true);
        pregunta.addOpcion("1 pitada larga seguida de dos cortas: si pretende alcanzarle por su banda de estribor");
        pregunta.addOpcion("1 pitada larga: si pretendo alcanzarle por su banda de babor");
        pregunta.addOpcion("Ninguna");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Que señal deberá realizar todo buque que no entiende las intenciones del otro");
        pregunta.addOpcion("Cinco pitadas cortas y rápidas", true);
        pregunta.addOpcion("Tres pitadas largas");
        pregunta.addOpcion("Ninguna señal");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Que señal deberá realizar un buque navegando en zonas de visibilidad reducida");
        pregunta.addOpcion("Una pitada larga cada 2 minutos", true);
        pregunta.addOpcion("Dos pitadas cortas");
        pregunta.addOpcion("Ninguna señal");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Que señal sónica deberán realizar los buques sin gobierno, pesqueros, veleros con capacidad de maniobra reducido o todo buque remolcado cuando navegan en zonas de visibilidad reducida.");
        pregunta.addOpcion("Una pitada larga seguida de 2 cortas con un intervalo de 2 minutos", true);
        pregunta.addOpcion("Dos pitadas cortas");
        pregunta.addOpcion("Ninguna señal");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Que señal sónica deberá realizar todo buque fondeado en zona de visibilidad reducida");
        pregunta.addOpcion("Repiques de campana con una duración de 5 segundos con un intervalo de mas de un minuto", true);
        pregunta.addOpcion("Dos pitadas cortas");
        pregunta.addOpcion("Ninguna señal");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Que es la velocidad de seguridad");
        pregunta.addOpcion("Es la que permite efectuar la maniobra adecuada y eficaz para evitar un abordaje", true);
        pregunta.addOpcion("A 20 nudos");
        pregunta.addOpcion("La mas rapida");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Que elementos se deberán tener en cuenta para determinar la velocidad de seguridad");
        pregunta.addOpcion("Visibilidad, Densidad del trafico, Maniobrabilidad, Estado del viento y corriente, Calado", true);
        pregunta.addOpcion("Solamente la visibilidad");
        pregunta.addOpcion("Solamente Maniobrabilidad del buque");
        pregunta.addOpcion("Con el estado del viento y corriente alcanza");
        pregunta.addOpcion("Ninguna");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Como determina que existe el riesgo de colisión, cuando 2 buques navegan en rumbos encontrados");
        pregunta.addOpcion("Si la demora del buque que se aproxima es constante o sin variación apreciable", true);
        pregunta.addOpcion("Si se nota que el buque está varado");
        pregunta.addOpcion("No hay forma de determinarlo");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Como deberá ser realizada una buena maniobra para evitar un abordaje");
        pregunta.addOpcion("Debe ser realizada de tal forma que los buques pasen en distancias seguras", true);
        pregunta.addOpcion("Debe ser realizada de tal forma que los buques pasen lo mas cerca posible");
        pregunta.addOpcion("Ninguna maniobra sera posible");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Si navegando por un canal se encuentran dos embarcaciones con rumbos encontrados que maniobra deberán realizar.");
        pregunta.addOpcion("Se arrimaran lo mas posible al limite exterior del canal que quede por su banda de estribor", true);
        pregunta.addOpcion("Se arrimaran lo mas posible al limite interior del canal que quede por subanda de estribor");
        pregunta.addOpcion("Ninguna");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Pueden las embarcaciones menores de 20 metros o los buques de vela navegar por un canal dificultando la navegación de los buques de mayor tonelaje");
        pregunta.addOpcion("No", true);
        pregunta.addOpcion("Si");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Puede un buque cruzar un paso o canal si al hacerlo interfiere el transito de otro buque");
        pregunta.addOpcion("No", true);
        pregunta.addOpcion("Si");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Cuando un buque de fuerza motriz y un velero están avanzando en direcciones que involucren riesgo de colisión en aguas libres quien deberá mantenerse apartado de la ruta del otro");
        pregunta.addOpcion("El buque de fuerza motriz", true);
        pregunta.addOpcion("El velero");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Cuando dos veleros se aproximan uno al otro y reciben el viento por bandas contrarias cual de ellos deberá mantenerse apartado de la derrota del otro");
        pregunta.addOpcion("El que reciba el viento por babor", true);
        pregunta.addOpcion("El que reciba el viento por estribor");
        pregunta.addOpcion("Ninguno");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Puede una embarcación fondear en un canal angosto");
        pregunta.addOpcion("No deberá si es posible salir del canal para no estorbar la navegación", true);
        pregunta.addOpcion("Podrá hacerlo siempre que no estorbe la navegacion");
        pregunta.addOpcion("Ninguna");

        preguntas.add(pregunta);

        pregunta = new Pregunta("Cuando dos veleros que naveguen con rumbos cruzados y pudiera existir riesgo de abordaje y ambos reciban el viento por la misma banda cual de ellos se mantendrá apartado de la derrota del otro");
        pregunta.addOpcion("El buque que esté a barlovento se mantendrá apartado del que esté a sotavento", true);
        pregunta.addOpcion("El buque que esté a sotavento se mantendrá apartado del que esté a barlovento");
        pregunta.addOpcion("Ninguna");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Que significa barlovento");
        pregunta.addOpcion("Banda de donde viene el viento", true);
        pregunta.addOpcion("Banda opuesta a donde viene el viento");
        pregunta.addOpcion("Ninguna");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Que significa sotavento");
        pregunta.addOpcion("Banda opuesta a donde viene el viento", true);
        pregunta.addOpcion("Banda desde donde viene el viento");
        pregunta.addOpcion("Ninguna");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Que se considera 'Por buque que alcanza'");
        pregunta.addOpcion("A todo buque que se aproxima a otro viniendo de una marcación mayor de 22°5' a popa del través del buque alcanzado de tal forma que de noche solo sea visible la luz de coronamiento y ninguna de sus luces de costado.", true);
        pregunta.addOpcion("A todo buque que se aleja de otro en una marcación de 10°5' a proa del traves del buque alcanzado.");
        pregunta.addOpcion("Ninguna");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Entre un buque que alcanza y el alcanzado cual de los dos tiene que mantenerse apartado de la derrota del otro.");
        pregunta.addOpcion("El buque que alcanza", true);
        pregunta.addOpcion("El buque alcanzante");
        pregunta.addOpcion("Ninguna");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Cuando un buque tenga dudas de si es alcanzante o no como se considerará");
        pregunta.addOpcion("Como buque que alcanza", true);
        pregunta.addOpcion("Como buque alcanzante");
        pregunta.addOpcion("Ninguna");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Cuando dos buques naveguen de vuelta encontrada a rumbos opuestos o casi opuestos con riesgo de colisión que maniobra deberán realizar");
        pregunta.addOpcion("Cada uno de ellos deberá caer a estribor", true);
        pregunta.addOpcion("Uno de ellos debe caer a estribor");
        pregunta.addOpcion("Los dos deben caer a estribor");
        pregunta.addOpcion("Ninguna");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Como determina en horas de la noche cuando dos buques vienen de vuelta encontrada");
        pregunta.addOpcion("Cuando se ven por la proa las dos luces de banda y la luz de tope", true);
        pregunta.addOpcion("Cuando se ve por la popa la luz verde y blanca");
        pregunta.addOpcion("Ninguna");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Como determina en horas del día que dos buques están en situación de vuelta encontrada");
        pregunta.addOpcion("Cuando por la proa se ven enfilados los palos del otro buque", true);
        pregunta.addOpcion("Cuando por la popa se vea al mismo en la misma direccion");
        pregunta.addOpcion("Ninguna");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Que deberá hacer si tiene dudas de que existe una situación de vuelta encontrada");
        pregunta.addOpcion("Procederá como si fuera una situación de vuelta encontrada", true);
        pregunta.addOpcion("Procederá a detener el barco");
        pregunta.addOpcion("Ninguna");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Cuando dos buques se crucen con riesgo de colisión cual de los dos deberá mantenerse apartado de la derrota del otro");
        pregunta.addOpcion("El buque que tenga el otro por su banda de estribor deberá gobernar para mantenerse apartado de la derrota del otro", true);
        pregunta.addOpcion("El buque que tenga el otro por su banda de babor deberá gobernar para mantenerse apartado de la derrota del otro");
        pregunta.addOpcion("El buque que tenga el otro atras");
        preguntas.add(pregunta);

        pregunta = new Pregunta("De que buque se mantendrán apartados las embarcaciones en navegación libre con propulsión mecánica");
        pregunta.addOpcion("- De un buque sin gobierno", true);
        pregunta.addOpcion("- De un buque con capacidad de maniobra limitada", true);
        pregunta.addOpcion("- De un buque dedicado a la pesca", true);
        pregunta.addOpcion("- De un buque de vela", true);
        pregunta.addOpcion("Ninguna");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Que precauciones deberá tomar toda embarcación que se encuentre navegando en una zona de visibilidad reducida");
        pregunta.addOpcion("Reducir la velocidad a la de seguridad y realizar las señales correspondientes", true);
        pregunta.addOpcion("Pegar tres pitidos cortos cada 60 segundos");
        pregunta.addOpcion("Virar a esribor lo mas pronto posible");
        pregunta.addOpcion("Ninguna");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Que indica una boya de luz verde y como están pintadas para su reconocimiento diurno");
        pregunta.addOpcion("Indican casco a pique y están pintadas de verde", true);
        pregunta.addOpcion("Indican veriles con fondo de piedra y estan pintadas de rojo");
        pregunta.addOpcion("Ninguna");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Que indica una boya de luz roja y como están pintadas para su reconocimiento diurno");
        pregunta.addOpcion("Son usadas para indicar veriles de canales y bajos fondos de piedras y están pintadas de rojo.", true);
        pregunta.addOpcion("Son usadas para indicar fondos de arena y estan pintadas de negro");
        pregunta.addOpcion("Ninguna");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Que indica una boya de luz blanca y como están pintadas para su reconocimiento diurno");
        pregunta.addOpcion("Se usan para señalar los veriles de canales y para indicar bajos fondos que no sean de piedras y están pintadas de negro.", true);
        pregunta.addOpcion("Se usan para señalar los bancos");
        pregunta.addOpcion("Ninguna");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Que boya se debe dejar a estribor al entrar a un canal de acceso a un puerto o remontar un rio balizado.");
        pregunta.addOpcion("Las boyas rojas", true);
        pregunta.addOpcion("Lad boyas verdes");
        pregunta.addOpcion("Ninguna");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Como pueden ser las boyas por sus características luminosas");
        pregunta.addOpcion("a-Destellos y b-Ocultacion", true);
        pregunta.addOpcion("a-Color y b-Forma");
        pregunta.addOpcion("a-Tamaño y b-Color");
        preguntas.add(pregunta);

        pregunta = new Pregunta("De donde se pueden extraer las características de los faros");
        pregunta.addOpcion("De las cartas náuticas de las cuales dan las características lumínicas y los datos de alcance en millas y la altura del faro; de los derroteros y listas de faros.", true);
        pregunta.addOpcion("De la policia o intendencias de donde se requiera");
        pregunta.addOpcion("De internet");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Que normas deben seguirse para el estudio de una derrota");
        pregunta.addOpcion("Estudiar la derrota en la carta poniendo especial atención en que esta no pasa sobre los peligros a la navegación", true);
        pregunta.addOpcion("Ir viendo si por donde uno pasa hay profundidad suficiente con eso alcanza");
        pregunta.addOpcion("No hay forma de determinar o estudiar una derrota");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Que precauciones deberá tomar para realizar una entrada o salida a puerto");
        pregunta.addOpcion("a-Para salir del puerto deberá observar si este esta abierto o cerrado. Si tiene salida libre hacerlo con velocidad de seguridad.\nb-Para entrar, si tiene entrada libre hacerlo con velocidad de seguridad", true);
        pregunta.addOpcion("Hay que ver que no haya mucho oleaje", true);
        pregunta.addOpcion("Ninguna");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Que es un fondeadero");
        pregunta.addOpcion("Es aquel que asegura una buena protección para los vientos predominantes y ademas su fondo es un buen tenedero", true);
        pregunta.addOpcion("Es el lugar donde desee fondear");
        pregunta.addOpcion("No hay un lugar determinado");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Cuantas veces el fondo es conveniente fondear de cadena");
        pregunta.addOpcion("Por lo menos 3 veces el fondo determinado", true);
        pregunta.addOpcion("Por lo menos el doble del fondo determinado");
        pregunta.addOpcion("Se puede fondear a cadena siempre");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Como se debe tomar un fondeadero");
        pregunta.addOpcion("Se deberá aproar la embarcación a la corriente o al viento llegando al punto elegido sin arrancada o con arrancada atrás fondear el ancla y dar cadena a medida que vaya pidiendo", true);
        pregunta.addOpcion("Se deberá preguntar en la administracion del fondeadero para que te den instrucciones");
        pregunta.addOpcion("No hay una forma correcta");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Que significa garrear");
        pregunta.addOpcion("Cuando una embarcación fondeada no se aguante produciendo un arrastre del ancla por el fondo.", true);
        pregunta.addOpcion("Significa cuando la embarcacion esta contra corriente y boltea mucho");
        pregunta.addOpcion("No es un temino nautico");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Como determina que una embarcación garrea");
        pregunta.addOpcion("a-Cuando las marcaciones a dos puntos notables en tierra por lo menos varían en forma apreciable\nb-Tocando la cadena se notan sacudones", true);
        pregunta.addOpcion("a-Cuando el oleaje choca fuerte contra el casco y b-Ademas el viento toca de espaldas", true);
        pregunta.addOpcion("No hay forma de determinarlo");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Cuando estando fondeado se levante mal tiempo que precauciones deberá tomar");
        pregunta.addOpcion("Cuidar que la embarcación no garrea para ello es aconsejable aumentar la cantidad de cadena fondeada hasta por lo menos 5 veces el fondo.", true);
        pregunta.addOpcion("Levantar el ancla e irse inmediatamente");
        pregunta.addOpcion("No hacer nada ya que se esta fondeado");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Que indica un descenso rápido en la presión atmosférica");
        pregunta.addOpcion("Un cambio en las condiciones del tiempo generalmente con aportes de viento fuerte", true);
        pregunta.addOpcion("Un aumento en la humedad relativa y la escasez de vientos");
        pregunta.addOpcion("No significa nada");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Que aspectos presentan los cumulus nimbus");
        pregunta.addOpcion("Grandes masas nubosas que se alzan como montañas en el horizonte con tonalidades oscuras casi negras", true);
        pregunta.addOpcion("Son nubes muy altas de forma parecida a los cirrus");
        pregunta.addOpcion("Nuves poco compactas de baja altura y tonalidades gris claro");
        pregunta.addOpcion("Ninguna");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Que fenómenos meteorológicos acompañan la presencia de cumulus nimbus");
        pregunta.addOpcion("Vientos fuertes, turbonadas y en casos excepcionales tornados", true);
        pregunta.addOpcion("Vientos suaves y nubosidad en alza");
        pregunta.addOpcion("Lluvia y vientos suaves");
        pregunta.addOpcion("Ninguna");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Cual es la presión atmosférica media a nivel del mar");
        pregunta.addOpcion("760 mm", true);
        pregunta.addOpcion("650 mm");
        pregunta.addOpcion("300 mm");
        pregunta.addOpcion("Ninguna");
        preguntas.add(pregunta);

        pregunta = new Pregunta("En épocas de verano y en situaciones normales cual es el régimen de rotación de los vientos en el rio de la plata");
        pregunta.addOpcion("Durante la noche sopla del norte por la mañana rota por el oeste y se coloca al este-sureste", true);
        pregunta.addOpcion("Durante la noche sopla del sur y por la mañana rota por el este y se coloca en el oeste");
        pregunta.addOpcion("Durante la noche sopla del oeste y por la mañana cambia al sur");
        pregunta.addOpcion("Ninguna");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Por debajo de que valores la presión atmosférica puede indicar un cambio sensible en las condiciones meteorológicas");
        pregunta.addOpcion("Cuando la presión desciende por debajo de los 760 mm", true);
        pregunta.addOpcion("Por debajo de los 500 mm");
        pregunta.addOpcion("Por debajo de los 350 mm");
        pregunta.addOpcion("Ninguna");
        preguntas.add(pregunta);

        pregunta = new Pregunta("La persistencia de un viento del sector norte que trae generalmente aparejado");
        pregunta.addOpcion("La entrada de un frente frio con aporte de vientos fuertes del sector sur", true);
        pregunta.addOpcion("La entrada de un frente de calor con aporte de vientos leves del sector norte");
        pregunta.addOpcion("Ninguna");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Cuales son los tres elementos que determinan un incendio");
        pregunta.addOpcion("Combustible, aire y calor", true);
        pregunta.addOpcion("Sol, combustible y fuego");
        pregunta.addOpcion("Agua, calor y combustible");
        pregunta.addOpcion("Ninguna");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Cuales son las tres clases de incendios");
        pregunta.addOpcion("clase a - combustibles sólidos, clase b - combustibles líquidos y clase c - materiales eléctricos", true);
        pregunta.addOpcion("clase a - combustibles liquidos, clase b - combustible solidos y clase c - materiales eléctricos");
        pregunta.addOpcion("clase a - materiales electricos, clase b - combustibles solidos y c - combustibles liquidos");
        pregunta.addOpcion("Ninguna");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Que tipo de extintor debe usarse preferentemente en un incendio de combustible sólido");
        pregunta.addOpcion("Chorro de agua", true);
        pregunta.addOpcion("De Anhídrido Carbónico (CO2)");
        pregunta.addOpcion("De polvo");
        pregunta.addOpcion("Ninguna");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Que tipo de extintor debe usarse preferentemente en un incendio de combustible liquido");
        pregunta.addOpcion("Espuma mecánica", true);
        pregunta.addOpcion("De Anhídrido Carbónico (CO2)");
        pregunta.addOpcion("De polvo");
        pregunta.addOpcion("Ninguna");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Que tipo de extintor debe usarse preferentemente en un incendio de materiales eléctricos");
        pregunta.addOpcion("De Anhídrido Carbónico (CO2)", true);
        pregunta.addOpcion("De polvo");
        pregunta.addOpcion("De espuma");
        pregunta.addOpcion("Ninguna");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Cual es la primer medida a tomar al producirse un incendio de materiales eléctricos");
        pregunta.addOpcion("Desenergisar el sistema", true);
        pregunta.addOpcion("Usar el extintor de incendios");
        pregunta.addOpcion("Ninguna");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Donde se solicita el despacho de las embarcaciones deportivas");
        pregunta.addOpcion("Ante la repartición de la prefectura nacional naval correspondiente al puerto donde se halla surta la embarcación (En caso de que no existiera dependencia de la PN deberá efectuarlo en la mas próxima)", true);
        pregunta.addOpcion("Ante la reparticion de la prefectura de montevideo");
        pregunta.addOpcion("Ante la policia en caso de que no haya prefectura");
        pregunta.addOpcion("Ninguna");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Que documentación se deberá exhibir para solicitar despacho");
        pregunta.addOpcion("Certificado de navegabilidad y seguridad y brevet para patronear embarcaciones deportivas y cedula de identidad", true);
        pregunta.addOpcion("Certificado RED y cédula");
        pregunta.addOpcion("Certificado de salud, RED y cédula");
        pregunta.addOpcion("Ninguna");
        preguntas.add(pregunta);

        pregunta = new Pregunta("A cuanto equivale en metros una braza (FATHOM)");
        pregunta.addOpcion("Equivale a 1.83 mts", true);
        pregunta.addOpcion("Equivale a 2 mts");
        pregunta.addOpcion("Equivale a 2.5 mts");
        pregunta.addOpcion("Ninguna");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Cuantos metros tiene una milla marina");
        pregunta.addOpcion("Tiene 1852 mts", true);
        pregunta.addOpcion("Tiene 1500 mts");
        pregunta.addOpcion("Tiene 2500 mts");
        pregunta.addOpcion("Ninguna");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Dentro de que radio esta autorizado a navegar una embarcación habilitada para hacerlo en la zona D");
        pregunta.addOpcion("2 millas del puerto de despacho, Rio Uruguay, ríos arroyos y aguas interiores durante las horas de día", true);
        pregunta.addOpcion("5 millas desde el puerto mas cercano");
        pregunta.addOpcion("Ninguna");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Cuantos salvavidas deberá llevar una embarcación");
        pregunta.addOpcion("Tantos como numero de tripulantes lleve", true);
        pregunta.addOpcion("La cantidad especificada para la cantidad maxima segun la embarcacion");
        pregunta.addOpcion("Ninguna");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Cual es la aplicación a dar a las tiras de tela color naranja que deben llevar las embarcaciones");
        pregunta.addOpcion("Colocarlas formando cruz sobre cubierta en caso de solicitar auxilio", true);
        pregunta.addOpcion("Se colocan a lo largo de la embarcacion a los efectos de visualizarlas desde lejos");
        pregunta.addOpcion("Ninguna");
        preguntas.add(pregunta);

        pregunta = new Pregunta("Cual es la aplicación a dar al espejo que deben llevar las embarcaciones");
        pregunta.addOpcion("Llamar la atención reflejando en este los rayos solares", true);
        pregunta.addOpcion("Usarlo para encandilar al barco que se acerca");
        pregunta.addOpcion("Ninguna");
        preguntas.add(pregunta);

        // Nuevas completan las 100
        // TODO: INCLUIR OPCIONES FALSAS.
        pregunta = new Pregunta("¿Qué arco de horizonte visible debe tener la luz de tope?");
        pregunta.addOpcion("225° repartidos desde la proa, 112°5’ a cada banda.", true);
        preguntas.add(pregunta);

        pregunta = new Pregunta("¿Qué arco de horizonte visible deben tener las luces de banda?");
        pregunta.addOpcion("112°5 desde la proa.", true);
        preguntas.add(pregunta);

        pregunta = new Pregunta("¿Qué alcance lumínico deben tener la luz de tope? en buques de:");
        pregunta.addOpcion("a. 50 o más metros de eslora....................... 6 millas.", true);
        pregunta.addOpcion("b. Entre 20 y 50 metros de eslora................ 5 millas", true);
        pregunta.addOpcion("c. Entre 12 y 20 metros de eslora................ 3 millas.", true);
        pregunta.addOpcion("d. Menos de 12 metros de eslora................. 2 millas.", true);
        preguntas.add(pregunta);

        pregunta = new Pregunta("¿Qué alcance lumínico debe tener las luces de banda de un buque de:");
        pregunta.addOpcion("a. 50 o más metros de eslora....................... 3 millas.", true);
        pregunta.addOpcion("b. Entre 12 y 50 metros de eslora................ 2 millas.", true);
        pregunta.addOpcion("c. Menos de 12 metros de eslora................. 1 millas.", true);
        preguntas.add(pregunta);

        pregunta = new Pregunta("¿Qué luces adicionales deberá llevar un buque que realiza tareas de remolque, siendo la longitud de este superior a 183 metros?");
        pregunta.addOpcion("3 luces blancas verticales.", true);
        preguntas.add(pregunta);

        pregunta = new Pregunta("¿Qué arco de horizonte deben tener las luces que indican buque en tareas de remolque?");
        pregunta.addOpcion("Iguales a las luces de tope, 225°.", true);
        preguntas.add(pregunta);

        pregunta = new Pregunta("¿Qué luces exhibirá todo buque remolcado durante la noche?");
        pregunta.addOpcion("Luces de banda y luz de alcance.", true);
        preguntas.add(pregunta);

        pregunta = new Pregunta("¿Qué luces adicionales exhibirán durante la noche los buques pesqueros dedicados a la pesca de arrastre?");
        pregunta.addOpcion("Dos luces en línea vertical todo el horizonte, verde la superior y blanca la inferior.", true);
        preguntas.add(pregunta);

        pregunta = new Pregunta("¿Qué luces exhibirán además de la reglamentaria los buques pesqueros que no sean de arrastre?");
        pregunta.addOpcion("2 luces todo horizonte en línea vertical, roja la superior y blanca la inferior.", true);
        preguntas.add(pregunta);

        pregunta = new Pregunta("¿Qué luces deberá exhibir un buque sin gobierno que no tenga arrancada?");
        pregunta.addOpcion("2 luces rojas visible en todo el horizonte en línea vertical en lugar más visible", true);
        preguntas.add(pregunta);

        pregunta = new Pregunta("¿Qué luz deberán tener durante la noche todo buque fondeado de menos de 45 ,75 metros de eslora?");
        pregunta.addOpcion("Una luz blanca todo horizonte en proa en el lugar más visible.", true);
        preguntas.add(pregunta);

        pregunta = new Pregunta("¿Qué luces deberán mostrar todo buque de menos de 45,75 metros de eslora varado durante la noche?");
        pregunta.addOpcion("Una luz blanca a proa todo horizonte y dos luces rojas en línea vertical todo horizonte.", true);
        preguntas.add(pregunta);

        pregunta = new Pregunta("¿ Cómo indica por medio de destellos que caerá a estribor?");
        pregunta.addOpcion("Un destello.", true);
        preguntas.add(pregunta);

        pregunta = new Pregunta("¿Cómo indica por destellos que caerá a babor?");
        pregunta.addOpcion("Dos destellos.", true);
        preguntas.add(pregunta);

        pregunta = new Pregunta("¿Cómo indica por medio de destellos que está dando maquina atrás?");
        pregunta.addOpcion("Tres destellos.", true);
        preguntas.add(pregunta);

        pregunta = new Pregunta("¿Cuál es la duración aproximada de un destello?");
        pregunta.addOpcion("1 segundo.", true);
        preguntas.add(pregunta);

        pregunta = new Pregunta("¿Cuál es el intervalo entre destello y destello?");
        pregunta.addOpcion("1 segundo.", true);
        preguntas.add(pregunta);

        pregunta = new Pregunta("¿Cuál es el intervalo entre señales sucesivas?");
        pregunta.addOpcion("No menor de 10 segundos.", true);
        preguntas.add(pregunta);

        pregunta = new Pregunta("¿En el sistema de boyado IALA B de nuestra zona qué tres elementos caracterizan las boyas?");
        pregunta.addOpcion("La forma, el color y los destellos en color y frecuencia.", true);
        preguntas.add(pregunta);

        pregunta = new Pregunta("¿Una boya Cardinal Norte qué indica y cómo está marcada?");
        pregunta.addOpcion("Indica Aguas Seguras al Norte y está indicada por color Negro arriba y Amarillo abajo, extremo con forma de dos conos punta hacia arriba y destellos blancos continuos cada un segundo.", true);
        preguntas.add(pregunta);

        pregunta = new Pregunta("En IALA B en donde se encuentran nuestras aguas saliendo de puerto o navegando aguas abajo ¿cómo se indica el margen lateral de babor? (indicar color, forma y destellos)");
        pregunta.addOpcion("Con boya roja forma de extremo cónico destellos rojos en grupo cada un periodo determinado.", true);
        preguntas.add(pregunta);

        pregunta = new Pregunta("¿Qué características tiene una boya de Peligro Aislado?");
        pregunta.addOpcion("En el extremo superior tiene dos bolas negras, pintada de colores negro arriba y rojo abajo, y destellos blancos de dos cada cierto periodo.", true);
        preguntas.add(pregunta);

        pregunta = new Pregunta("¿Qué es un RACON y por su característica como se distingue de otro en la zona?");
        pregunta.addOpcion("Es un dispositivo Respondedor de la emisión de un radar generando una imagen en la pantalla de este indicando una letra del código Morse.", true);
        preguntas.add(pregunta);

        pregunta = new Pregunta("¿Qué significa una boya amarilla y que características de forma y destello tiene?");
        pregunta.addOpcion("Indica una marca especial con una cruz en la parte superior y destellos continuos color amarillo.", true);
        preguntas.add(pregunta);

        pregunta = new Pregunta("¿Dentro de que zona está autorizada a navegar una embarcación habilitada para zona 'B'?");
        pregunta.addOpcion("En todo el Río de la Plata y una franja costera oceánica de 15 millas de ancho hasta la desembocadura del Arroyo Chuy.", true);
        preguntas.add(pregunta);

        pregunta = new Pregunta("¿Dentro de que radio está autorizada a navegar una embarcación habilitada para hacerlo en la zona 'C'?");
        pregunta.addOpcion("Estará autorizada a navegar en un radio de 15 millas de puerto de despacho, en el Río de la Plata interior y Océano Atlántico. En el Río de la Plata superior y el Río Uruguay dicho radio será de 20 millas.", true);
        preguntas.add(pregunta);

        Collections.shuffle(preguntas);

        return preguntas;
    }

}
