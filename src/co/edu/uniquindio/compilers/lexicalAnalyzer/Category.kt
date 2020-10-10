package co.edu.uniquindio.compilers.lexicalAnalyzer

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
    CORCHETES,
    OPERADORES_RELACIONALES,
    DESCONOCIDO,
    CADENA_CARACTERES,
    DOS_PUNTOS,
    TERMINAL,
    PUNTO,
    SEPARADOR
}