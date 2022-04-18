package server;

import data.Data;

import java.util.HashMap;

// MONITOR que resuelve el problema de lectores-escritores
// con la tabla del servidor que almacena la informacion
// disponible en el sistema. Implementamos el monitor
// con un Lock y Variables Condicion.

public class DataMonitor {

    // Utilizamos como estructura de datos una tabla Hash
    // con clave = identificador del archivo
    // y   valor = archivo

    private HashMap<String, Data> id_file;
}
