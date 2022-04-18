package server;

// Clase que encapsula el problema de lectores-escritores
// con la tabla del servidor que almacena la informacion
// de los usuarios del sistema, resuelto con SEMAFOROS

import java.util.HashMap;

public class UserTable {
    // Utilizamos como estructura de datos una tabla Hash
    // con clave = username
    // y   valor = User
    private HashMap<String, User> id_user;

}
