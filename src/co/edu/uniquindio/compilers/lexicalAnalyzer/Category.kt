package co.edu.uniquindio.compilers.lexicalAnalyzer

/**
 *
 * enum category Class
 * @author Cristian Giovanny Sánchez Pineda
 * @author Anjully Tatiana Mora Acosta
 * @author Luisa Fernanda Cotte Sánchez
 * @version 1.0
 *
 */
enum class Category {
    ENTERO,
    DECIMAL,
    IDENTIFICADOR,
    OPERADOR_ARITMETICO,
    OPERADOR_INCREMENTO,
    OPERADOR_DECREMENTO,
    OPERADOR_ASIGNACION,
    OPERADOR_LOGICO,
    PALABRA_RESERVADA,
    PARENTESIS_IZQUIERDO,
    PARENTESIS_DERECHO,
    CORCHETE_IZQUIERDO,
    CORCHETE_DERECHO,
    LLAVE_IZQUIERDA,
    LLAVE_DERECHA,
    OPERADORES_RELACIONALES,
    DESCONOCIDO,
    CADENA_CARACTERES,
    DOS_PUNTOS,
    TERMINAL,
    PUNTO,
    SEPARADOR,
    COMENTARIO,
    CARACTER
}