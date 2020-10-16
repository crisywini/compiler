package co.edu.uniquindio.compilers.lexicalAnalyzer

/**
 *
 * Lexical Analyzer class
 * @author Cristian Giovanny Sánchez Pineda
 * @author Anjully Tatiana Mora Acosta
 * @author Luisa Fernanda Cotte Sánchez
 * @version 1.0
 *
 */
class LexicalAnalyzer(var sourceCode: String) {
    var actualPosition = 0
    var currentCharacter = sourceCode[0] //First token of the source code
    var tokenList = ArrayList<Token>()
    var errorList = ArrayList<Error>()
    var endCode = 0.toChar()
    var currentRow = 0
    var currentColumn = 0

    /**
     * This method allows to analyze the source code and identify the tokens
     */
    fun analyze() {
        while (currentCharacter != endCode) {

            if (currentCharacter == ' ' || currentCharacter == '\t' || currentCharacter == '\n') {
                setNextCharacter()
                continue
            }
            if (isInteger()) continue
            if (isDecimal()) continue
            if (isString()) continue
            if (isIdentifier()) continue
            if (isArithmeticOperator()) continue
            if (isIncrementOperator()) continue
            if (isDecrementOperator()) continue
            if (isAssignmentOperator()) continue
            if (isRelationalOperator()) continue
            if (isColon()) continue
            if (isTerminal()) continue
            if (isDot()) continue
            if (isSeparator()) continue
            if (isLogicalOperator()) continue
            if (isComment()) continue
            if (isCharacter()) continue
            if (isGrouper()) continue
            if (isReservedWord()) continue

            storeToken("" + currentCharacter, Category.DESCONOCIDO, currentRow, currentColumn)
            setNextCharacter()
        }
    }

    /**
     * this method allows to identify if it is a decimal number
     */
    fun isDecimal(): Boolean {

        if (currentCharacter == '$') {
            var lexema = ""
            var initialRow = currentRow
            var initialColumn = currentColumn

            lexema += currentCharacter
            setNextCharacter()

            if (currentCharacter == ',' || currentCharacter.isDigit()) {

                if (currentCharacter == ',') {
                    lexema += currentCharacter
                    setNextCharacter()
                    if (currentCharacter.isDigit()) {
                        lexema += currentCharacter
                        setNextCharacter()
                    }
                } else {
                    lexema += currentCharacter
                    setNextCharacter()
                    while (currentCharacter.isDigit()) {
                        lexema += currentCharacter
                        setNextCharacter()
                    }
                    if (currentCharacter == ',') {

                        lexema += currentCharacter
                        setNextCharacter()

                    }
                }
                while (currentCharacter.isDigit()) {
                    lexema += currentCharacter
                    setNextCharacter()
                }

                storeToken(lexema, Category.DECIMAL, initialRow, initialColumn)
                return true
            }
        }
        return false
    }

    /**
     * This method allows to do backtracking to reset the values
     */
    fun doBackTracking(initialPosition: Int, initialRow: Int, initialColumn: Int) {
        actualPosition = initialPosition
        currentRow = initialRow
        currentColumn = initialColumn

        currentCharacter = sourceCode[actualPosition]
    }

    /**
     * This method allows to verify the String
     */
    fun isString(): Boolean {

        if (currentCharacter == '(') {
            var lexema = ""
            var initialRow = currentRow
            var initialColumn = currentColumn
            var initialPosition = actualPosition
            var isError = false
            lexema += currentCharacter
            setNextCharacter()//Cambiar nombre a actualizar caracter
            while (currentCharacter != ')') {
                lexema += currentCharacter
                setNextCharacter()//Cambiar nombre a actualizar caracter
                if (currentCharacter == '°') {
                    lexema += currentCharacter
                    setNextCharacter()
                    if (currentCharacter != 'n' && currentCharacter != 't' && currentCharacter != 'b'
                            && currentCharacter != 'r' && currentCharacter != '(' || currentCharacter != ')'
                            && currentCharacter != '?' && currentCharacter != '¿') {
                        isError = true
                    }
                }
            }
            if (isError) {
                lexema += currentCharacter
                setNextCharacter()
                storeError(lexema, initialRow, initialColumn, ErrorCategory.ERROR_LEXICO)
                return true
            }
            lexema += currentCharacter
            setNextCharacter()
            storeToken(lexema, Category.CADENA_CARACTERES, initialRow, initialColumn)
            return true
        }
        return false
    }

    /**
     * This method allows to verify the arithmetic operator
     */
    fun isArithmeticOperator(): Boolean {
        if (currentCharacter == '*' || currentCharacter == '\'' || currentCharacter == '.' || currentCharacter == '-' || currentCharacter == '&') {
            var lexema = ""
            var initialRow = currentRow
            var initialColumn = currentColumn
            var initialPosition = actualPosition
            var currentCharacterCopy = currentCharacter
            lexema += currentCharacter
            setNextCharacter()//Cambiar nombre a actualizar caracter
            if (currentCharacterCopy == '*' && currentCharacter == '*' || currentCharacterCopy == '\'' && currentCharacter == '\'' || currentCharacter == ':'
                    || (currentCharacterCopy == '-' && currentCharacter == '>')) {
                doBackTracking(initialPosition, initialRow, initialColumn)
                return false
            }
            storeToken(lexema, Category.OPERADOR_ARITMETICO, initialRow, initialColumn)
            return true
        }
        return false
    }

    /**
     * This method allows to verify the increment operator
     */
    fun isIncrementOperator(): Boolean {
        if (currentCharacter == '*') {

            var lexema = ""
            var initialRow = currentRow
            var initialColumn = currentColumn
            var initialPosition = actualPosition
            lexema += currentCharacter
            setNextCharacter()//Cambiar nombre a actualizar caracter
            if (currentCharacter == '*') {
                lexema += currentCharacter
                setNextCharacter()//Cambiar nombre a actualizar caracter

            } else if (currentCharacter == ':') {
                doBackTracking(initialPosition, initialRow, initialColumn)
                return false
            }
            storeToken(lexema, Category.OPERADOR_INCREMENTO, initialRow, initialColumn)
            return true
        }
        return false
    }

    /**
     * This method allows to verify the decrement operator
     */
    fun isDecrementOperator(): Boolean {
        if (currentCharacter == '\'') {

            var lexema = ""
            var initialRow = currentRow
            var initialColumn = currentColumn
            var initialPosition = actualPosition
            lexema += currentCharacter
            setNextCharacter()//Cambiar nombre a actualizar caracter
            if (currentCharacter == '\'') {
                lexema += currentCharacter
                setNextCharacter()//Cambiar nombre a actualizar caracter
            } else if (currentCharacter == ':') {
                doBackTracking(initialPosition, initialRow, initialColumn)
                return false
            }
            storeToken(lexema, Category.OPERADOR_DECREMENTO, initialRow, initialColumn)
            return true
        }
        return false
    }

    /**
     * This method allows to verify the assignment operators
     */
    fun isAssignmentOperator(): Boolean {
        if (currentCharacter == '*'
                || currentCharacter == '\''
                || currentCharacter == '.'
                || currentCharacter == '-'
                || currentCharacter == '&'
                || currentCharacter == ':') {
            var lexema = ""
            var initialRow = currentRow
            var initialColumn = currentColumn
            var initialPosition = actualPosition
            var currentCharacterCopy = currentCharacter
            lexema += currentCharacter
            setNextCharacter()//Cambiar nombre a actualizar caracter

            if (currentCharacter == ':' && (currentCharacterCopy == '*'
                            || currentCharacterCopy == '\''
                            || currentCharacterCopy == '.'
                            || currentCharacterCopy == '-'
                            || currentCharacterCopy == '&')) {
                lexema += currentCharacter
                setNextCharacter()
            } else if ((currentCharacterCopy == ':' && currentCharacter == ':') ||
                    (currentCharacterCopy == '*' && currentCharacter == '*') ||
                    (currentCharacterCopy == '\'' && currentCharacter == '\'') ||
                    (currentCharacterCopy == '-' && currentCharacter == '>')) {
                doBackTracking(initialPosition, initialRow, initialColumn)
                return false
            }
            storeToken(lexema, Category.OPERADOR_ASIGNACION, initialRow, initialColumn)
            return true
        }
        return false
    }

    /**
     * This method allows to verify the relational operators
     */
    fun isRelationalOperator(): Boolean {
        if (currentCharacter == '>' || currentCharacter == ':' || currentCharacter == '<'
                || currentCharacter == '-' || currentCharacter == '!') {
            var lexema = ""
            var initialRow = currentRow
            var initialColumn = currentColumn
            var initialPosition = actualPosition
            var currentCharacterCopy = currentCharacter
            lexema += currentCharacter
            setNextCharacter()//Cambiar nombre a actualizar caracter
            if ((currentCharacterCopy == '>' && currentCharacter == ':') ||
                    (currentCharacterCopy == '<' && (currentCharacter == '-' || currentCharacter == ':')) ||
                    (currentCharacterCopy == '-' && currentCharacter == '>') ||
                    (currentCharacterCopy == '!' && currentCharacter == ':') ||
                    (currentCharacterCopy == ':' && currentCharacter == ':')) {
                lexema += currentCharacter
                setNextCharacter()
            } else if ((currentCharacterCopy == '!' && currentCharacter != ':') ||
                    (currentCharacterCopy == '-' && currentCharacter == ':') ||
                    currentCharacterCopy == '>' || currentCharacterCopy == '<') {
                doBackTracking(initialPosition, initialRow, initialColumn)
                return false
            }

            storeToken(lexema, Category.OPERADORES_RELACIONALES, initialRow, initialColumn)
            return true
        }
        return false
    }

    /**
     * This method allows to verify the reserved words
     */
    fun isReservedWord(): Boolean {
        if (currentCharacter == 's' || currentCharacter == 't'
                || currentCharacter == 'o' || currentCharacter == 'l'
                || currentCharacter == 'p' || currentCharacter == 'a'
                || currentCharacter == 'G' || currentCharacter == 'c'
                || currentCharacter == 'r' || currentCharacter == 'e'
                || currentCharacter == 'd' || currentCharacter == 'b') {
            var lexema = ""
            var initialRow = currentRow
            var initialColumn = currentColumn
            var initialPosition = actualPosition
            var currentCharacterCopy = currentCharacter
            lexema += currentCharacter
            setNextCharacter()
            if (currentCharacterCopy == 's' && currentCharacter == 'o') {
                lexema += currentCharacter
                setNextCharacter()
                if (currentCharacter == 'l') {
                    lexema += currentCharacter
                    setNextCharacter()
                    if (currentCharacter == 'o') {
                        lexema += currentCharacter
                        setNextCharacter()
                    } else {
                        doBackTracking(initialPosition, initialRow, initialColumn)
                        return false
                    }
                } else {
                    doBackTracking(initialPosition, initialRow, initialColumn)
                    return false
                }
            } else if (currentCharacterCopy == 't' && currentCharacter == 'u') {
                lexema += currentCharacter
                setNextCharacter()
                if (currentCharacter == 't') {
                    lexema += currentCharacter
                    setNextCharacter()
                    if (currentCharacter == 't') {
                        lexema += currentCharacter
                        setNextCharacter()
                        if (currentCharacter == 'i') {
                            lexema += currentCharacter
                            setNextCharacter()
                        } else {
                            doBackTracking(initialPosition, initialRow, initialColumn)
                            return false
                        }
                    } else {
                        doBackTracking(initialPosition, initialRow, initialColumn)
                        return false
                    }
                }
            } else if (currentCharacterCopy == 'o' && currentCharacter == 'p') {
                lexema += currentCharacter
                setNextCharacter()
                if (currentCharacter == 'u') {
                    lexema += currentCharacter
                    setNextCharacter()
                    if (currentCharacter == 's') {
                        lexema += currentCharacter
                        setNextCharacter()
                    } else {
                        doBackTracking(initialPosition, initialRow, initialColumn)
                        return false
                    }
                } else if (currentCharacter == 't') {
                    lexema += currentCharacter
                    setNextCharacter()
                    if (currentCharacter == 'i') {
                        lexema += currentCharacter
                        setNextCharacter()
                        if (currentCharacter == 'o') {
                            lexema += currentCharacter
                            setNextCharacter()
                            if (currentCharacter == 'n') {
                                lexema += currentCharacter
                                setNextCharacter()
                            } else {
                                doBackTracking(initialPosition, initialRow, initialColumn)
                                return false
                            }
                        } else {
                            doBackTracking(initialPosition, initialRow, initialColumn)
                            return false
                        }
                    } else {
                        doBackTracking(initialPosition, initialRow, initialColumn)
                        return false
                    }
                } else {
                    doBackTracking(initialPosition, initialRow, initialColumn)
                    return false
                }
            } else if (currentCharacterCopy == 'l' && currentCharacter == 'a') {//Falta el resto desde la l de largo
                lexema += currentCharacter
                setNextCharacter()
                if (currentCharacter == 'r') {
                    lexema += currentCharacter
                    setNextCharacter()
                    if (currentCharacter == 'g') {
                        lexema += currentCharacter
                        setNextCharacter()
                        if (currentCharacter == 'o') {
                            lexema += currentCharacter
                            setNextCharacter()
                        } else {
                            doBackTracking(initialPosition, initialRow, initialColumn)
                            return false
                        }
                    } else {
                        doBackTracking(initialPosition, initialRow, initialColumn)
                        return false
                    }
                } else {
                    doBackTracking(initialPosition, initialRow, initialColumn)
                    return false
                }
            } else if (currentCharacterCopy == 'p' && currentCharacter == 'u') {
                lexema += currentCharacter
                setNextCharacter()
                if (currentCharacter == 'l') {
                    lexema += currentCharacter
                    setNextCharacter()
                    if (currentCharacter == 's') {
                        lexema += currentCharacter
                        setNextCharacter()
                        if (currentCharacter == 'o') {
                            lexema += currentCharacter
                            setNextCharacter()
                        } else {
                            doBackTracking(initialPosition, initialRow, initialColumn)
                            return false
                        }
                    } else {
                        doBackTracking(initialPosition, initialRow, initialColumn)
                        return false
                    }
                } else {
                    doBackTracking(initialPosition, initialRow, initialColumn)
                    return false
                }
            } else if (currentCharacterCopy == 'p' && currentCharacter == 'r') {
                lexema += currentCharacter
                setNextCharacter()
                if (currentCharacter == 'o') {
                    lexema += currentCharacter
                    setNextCharacter()
                    if (currentCharacter == 't') {
                        lexema += currentCharacter
                        setNextCharacter()
                        if (currentCharacter == 'e') {
                            lexema += currentCharacter
                            setNextCharacter()
                            if (currentCharacter == 't') {
                                lexema += currentCharacter
                                setNextCharacter()
                                if (currentCharacter == 'o') {
                                    lexema += currentCharacter
                                    setNextCharacter()
                                } else {
                                    doBackTracking(initialPosition, initialRow, initialColumn)
                                    return false
                                }
                            } else {
                                doBackTracking(initialPosition, initialRow, initialColumn)
                                return false
                            }
                        } else {
                            doBackTracking(initialPosition, initialRow, initialColumn)
                            return false
                        }
                    } else {
                        doBackTracking(initialPosition, initialRow, initialColumn)
                        return false
                    }
                } else {
                    doBackTracking(initialPosition, initialRow, initialColumn)
                    return false
                }
            } else if (currentCharacterCopy == 'a' && currentCharacter == 'n') {
                lexema += currentCharacter
                setNextCharacter()
                if (currentCharacter == 't') {
                    lexema += currentCharacter
                    setNextCharacter()
                    if (currentCharacter == 'e') {
                        lexema += currentCharacter
                        setNextCharacter()
                    } else {
                        doBackTracking(initialPosition, initialRow, initialColumn)
                        return false
                    }
                } else {
                    doBackTracking(initialPosition, initialRow, initialColumn)
                    return false
                }
            } else if (currentCharacterCopy == 'a' && currentCharacter == 'c') {
                lexema += currentCharacter
                setNextCharacter()
                if (currentCharacter == 'c') {
                    lexema += currentCharacter
                    setNextCharacter()
                    if (currentCharacter == 'a') {
                        lexema += currentCharacter
                        setNextCharacter()
                    } else {
                        doBackTracking(initialPosition, initialRow, initialColumn)
                        return false
                    }
                } else {
                    doBackTracking(initialPosition, initialRow, initialColumn)
                    return false
                }
            } else if (currentCharacterCopy == 'G' && currentCharacter == 'P') {
                lexema += currentCharacter
                setNextCharacter()
            } else if (currentCharacterCopy == 'c' && currentCharacter == 'o') {
                lexema += currentCharacter
                setNextCharacter()
                if (currentCharacter == 'd') {
                    lexema += currentCharacter
                    setNextCharacter()
                    if (currentCharacter == 'a') {
                        lexema += currentCharacter
                        setNextCharacter()
                    } else {
                        doBackTracking(initialPosition, initialRow, initialColumn)
                        return false
                    }
                } else if (currentCharacter == 'n') {
                    lexema += currentCharacter
                    setNextCharacter()
                    if (currentCharacter == 't') {
                        lexema += currentCharacter
                        setNextCharacter()
                        if (currentCharacter == 'r') {
                            lexema += currentCharacter
                            setNextCharacter()
                            if (currentCharacter == 'a') {
                                lexema += currentCharacter
                                setNextCharacter()
                            } else {
                                doBackTracking(initialPosition, initialRow, initialColumn)
                                return false
                            }
                        } else {
                            doBackTracking(initialPosition, initialRow, initialColumn)
                            return false
                        }
                    } else {
                        doBackTracking(initialPosition, initialRow, initialColumn)
                        return false
                    }
                } else {
                    doBackTracking(initialPosition, initialRow, initialColumn)
                    return false
                }
            } else if (currentCharacterCopy == 'c' && currentCharacter == 'e') {
                lexema += currentCharacter
                setNextCharacter()
                if (currentCharacter == 'd') {
                    lexema += currentCharacter
                    setNextCharacter()
                    if (currentCharacter == 'e') {
                        lexema += currentCharacter
                        setNextCharacter()
                        if (currentCharacter == 'z') {
                            lexema += currentCharacter
                            setNextCharacter()
                        } else {
                            doBackTracking(initialPosition, initialRow, initialColumn)
                            return false
                        }
                    } else {
                        doBackTracking(initialPosition, initialRow, initialColumn)
                        return false
                    }
                } else {
                    doBackTracking(initialPosition, initialRow, initialColumn)
                    return false
                }
            } else if (currentCharacterCopy == 'r' && currentCharacter == 'o') {
                lexema += currentCharacter
                setNextCharacter()
                if (currentCharacter == 'n') {
                    lexema += currentCharacterCopy
                    setNextCharacter()
                    if (currentCharacter == 'd') {
                        lexema += currentCharacter
                        setNextCharacter()
                        if (currentCharacter == 'o') {
                            lexema += currentCharacter
                            setNextCharacter()
                        } else {
                            doBackTracking(initialPosition, initialRow, initialColumn)
                            return false
                        }
                    } else {
                        doBackTracking(initialPosition, initialRow, initialColumn)
                        return false
                    }
                } else {
                    doBackTracking(initialPosition, initialRow, initialColumn)
                    return false
                }
            } else if (currentCharacterCopy == 'r' && currentCharacter == 'a') {
                lexema += currentCharacter
                setNextCharacter()
                if (currentCharacter == 's') {
                    lexema += currentCharacter
                    setNextCharacter()
                    if (currentCharacter == 't') {
                        lexema += currentCharacter
                        setNextCharacter()
                    } else {
                        doBackTracking(initialPosition, initialRow, initialColumn)
                        return false
                    }
                } else {
                    doBackTracking(initialPosition, initialRow, initialColumn)
                    return false
                }
            } else if (currentCharacterCopy == 'e' && currentCharacter == 'v') {
                lexema += currentCharacter
                setNextCharacter()
                if (currentCharacter == 'a') {
                    lexema += currentCharacter
                    setNextCharacter()

                } else {
                    doBackTracking(initialPosition, initialRow, initialColumn)
                    return false
                }
            } else if (currentCharacterCopy == 'e' && currentCharacter == 'n') {
                lexema += currentCharacter
                setNextCharacter()
                if (currentCharacter == 'c') {
                    lexema += currentCharacter
                    setNextCharacter()
                    if (currentCharacter == 'o') {
                        lexema += currentCharacter
                        setNextCharacter()
                        if (currentCharacter == 'r') {
                            lexema += currentCharacter
                            setNextCharacter()
                            if (currentCharacter == 'e') {
                                lexema += currentCharacter
                                setNextCharacter()
                            } else {
                                doBackTracking(initialPosition, initialRow, initialColumn)
                                return false
                            }
                        } else {
                            doBackTracking(initialPosition, initialRow, initialColumn)
                            return false
                        }
                    } else {
                        doBackTracking(initialPosition, initialRow, initialColumn)
                        return false
                    }
                } else {
                    doBackTracking(initialPosition, initialRow, initialColumn)
                    return false
                }
            } else if (currentCharacterCopy == 'd' && currentCharacter == 'a') {
                lexema += currentCharacter
                setNextCharacter()
                if (currentCharacter == 'c') {
                    lexema += currentCharacter
                    setNextCharacter()
                    if (currentCharacter == 'a') {
                        lexema += currentCharacter
                        setNextCharacter()
                        if (currentCharacter == 'p') {
                            lexema += currentCharacter
                            setNextCharacter()
                            if (currentCharacter == 'o') {
                                lexema += currentCharacter
                                setNextCharacter()
                            } else {
                                doBackTracking(initialPosition, initialRow, initialColumn)
                                return false
                            }
                        } else {
                            doBackTracking(initialPosition, initialRow, initialColumn)
                            return false
                        }
                    } else {
                        doBackTracking(initialPosition, initialRow, initialColumn)
                        return false
                    }
                } else {
                    doBackTracking(initialPosition, initialRow, initialColumn)
                    return false
                }
            } else if (currentCharacterCopy == 'b' && currentCharacter == 'e') {
                lexema += currentCharacter
                setNextCharacter()
                if (currentCharacter == 'c') {
                    lexema += currentCharacter
                    setNextCharacter()
                    if (currentCharacter == 'u') {
                        lexema += currentCharacter
                        setNextCharacter()
                    } else {
                        doBackTracking(initialPosition, initialRow, initialColumn)
                        return false
                    }
                } else if (currentCharacter == 'm') {
                    lexema += currentCharacter
                    setNextCharacter()
                    if (currentCharacter == 'o') {
                        lexema += currentCharacter
                        setNextCharacter()
                        if (currentCharacter == 'l') {
                            lexema += currentCharacter
                            setNextCharacter()
                        } else {
                            doBackTracking(initialPosition, initialRow, initialColumn)
                            return false
                        }
                    } else {
                        doBackTracking(initialPosition, initialRow, initialColumn)
                        return false
                    }
                } else {
                    doBackTracking(initialPosition, initialRow, initialColumn)
                    return false
                }
            } else if (currentCharacterCopy == 'b' && currentCharacter == 'r') {
                lexema += currentCharacter
                setNextCharacter()
                if (currentCharacter == 'i') {
                    lexema += currentCharacter
                    setNextCharacter()
                    if (currentCharacter == 'd') {
                        lexema += currentCharacter
                        setNextCharacter()
                        if (currentCharacter == 'g') {
                            lexema += currentCharacter
                            setNextCharacter()
                            if (currentCharacter == 'e') {
                                lexema += currentCharacter
                                setNextCharacter()
                            } else {
                                doBackTracking(initialPosition, initialRow, initialColumn)
                                return false
                            }
                        } else {
                            doBackTracking(initialPosition, initialRow, initialColumn)
                            return false
                        }
                    } else {
                        doBackTracking(initialPosition, initialRow, initialColumn)
                        return false
                    }
                } else {
                    doBackTracking(initialPosition, initialRow, initialColumn)
                    return false
                }
            } else {
                doBackTracking(initialPosition, initialRow, initialColumn)
                return false
            }

            storeToken(lexema, Category.PALABRA_RESERVADA, initialRow, initialColumn)
            return true
        }
        return false
    }

    /**
     * This method allows to verify the logical operators
     */
    fun isLogicalOperator(): Boolean {
        if (currentCharacter == 'a' || currentCharacter == 'o') {
            var lexema = ""
            var initialRow = currentRow
            var initialColumn = currentColumn
            var initialPosition = actualPosition
            var currentCharacterCopy = currentCharacter
            lexema += currentCharacter
            setNextCharacter()//Cambiar nombre a actualizar caracter
            if ((currentCharacterCopy == 'a' && currentCharacter == 'n')) {
                lexema += currentCharacter
                setNextCharacter()
                if (currentCharacter == 'd') {
                    lexema += currentCharacter
                    setNextCharacter()
                } else {
                    doBackTracking(initialPosition, initialRow, initialColumn)
                    return false
                }
            } else if (currentCharacterCopy == 'o' && currentCharacter == 'r') {
                lexema += currentCharacter
                setNextCharacter()
            } else {
                doBackTracking(initialPosition, initialRow, initialColumn)
                return false
            }
            storeToken(lexema, Category.OPERADOR_LOGICO, initialRow, initialColumn)
            return true
        }
        return false
    }

    /**
     * this method allows to identify if it is a whole number
     */
    fun isInteger(): Boolean {
        if (currentCharacter == '#') {
            var lexema = ""
            var initialRow = currentRow
            var initialColumn = currentColumn
            var initialPosition = actualPosition

            lexema += currentCharacter
            setNextCharacter()//Cambiar nombre a actualizar caracter
            if (currentCharacter.isDigit()) {
                lexema += currentCharacter
                setNextCharacter()
                while (currentCharacter.isDigit()) {
                    lexema += currentCharacter
                    setNextCharacter()//Cambiar nombre a actualizar caracter
                }

                if (currentCharacter == ',') {
                    doBackTracking(initialPosition, initialRow, initialColumn)
                    return false
                }


                storeToken(lexema, Category.ENTERO, initialRow, initialColumn)
                return true
            }
        }
        //RI -> Rechazo Inmediato
        return false
    }

    /**
     * this method allows to identify if it is an identifier
     */
    fun isIdentifier(): Boolean {

        if (currentCharacter == '~') {

            var lexema = ""
            var initialRow = currentRow
            var initialColumn = currentColumn
            var actual = actualPosition
            var count = 0
            lexema += currentCharacter
            setNextCharacter()

            if (currentCharacter.isLetter()) {
                lexema += currentCharacter
                setNextCharacter()

                while ((currentCharacter.isLetter() || currentCharacter.isDigit()) && count <= 8) {
                    count++
                    lexema += currentCharacter
                    setNextCharacter()
                }
                storeToken(lexema, Category.IDENTIFICADOR, initialRow, initialColumn)
                return true
            } else {
                doBackTracking(actual, initialRow, initialColumn)
            }
        }
        return false
    }

    /**
     * this method allows to identify if it is a colon
     */
    fun isColon(): Boolean {

        if (currentCharacter == ';') {
            var lexema = ""
            var initialRow = currentRow
            var initialColumn = currentColumn
            var actual = actualPosition
            lexema += currentCharacter
            setNextCharacter()

            storeToken(lexema, Category.DOS_PUNTOS, initialRow, initialColumn)
            return true
        }
        return false
    }

    /**
     * this method allows to identify if it is a terminal
     */
    fun isTerminal(): Boolean {

        if (currentCharacter == '\\') {
            var lexema = ""
            var initialRow = currentRow
            var initialColumn = currentColumn
            var actual = actualPosition
            lexema += currentCharacter
            setNextCharacter()

            storeToken(lexema, Category.TERMINAL, initialRow, initialColumn)
            return true
        }
        return false
    }

    /**
     * this method allows to identify if it is a dot
     */
    fun isDot(): Boolean {

        if (currentCharacter == ',') {
            var lexema = ""
            var initialRow = currentRow
            var initialColumn = currentColumn
            var actual = actualPosition
            lexema += currentCharacter
            setNextCharacter()

            storeToken(lexema, Category.PUNTO, initialRow, initialColumn)
            return true
        }
        return false
    }

    /**
     * this method allows to identify if it is a separator
     */
    fun isSeparator(): Boolean {

        if (currentCharacter == '_') {
            var lexema = ""
            var initialRow = currentRow
            var initialColumn = currentColumn
            var actual = actualPosition
            lexema += currentCharacter
            setNextCharacter()

            storeToken(lexema, Category.SEPARADOR, initialRow, initialColumn)
            return true
        }
        return false
    }

    /**
     * this method allows to identify comments
     */
    fun isComment(): Boolean {

        if (currentCharacter == '[') {

            var lexema = ""
            var initialRow = currentRow
            var initialColumn = currentColumn
            var initialPosition = actualPosition
            lexema += currentCharacter
            setNextCharacter()//Cambiar nombre a actualizar caracter

            if(currentCharacter == '['){
                while (currentCharacter != '\n') {
                    lexema += currentCharacter
                    setNextCharacter()//Cambiar nombre a actualizar caracter
                }
                lexema += currentCharacter
                setNextCharacter()
                storeToken(lexema, Category.COMENTARIO, initialRow, initialColumn)
                return true
            }
            else{
                doBackTracking(initialPosition, initialRow, initialColumn)
                return false
            }

        }

        if (currentCharacter == '^') {

            var lexema = ""
            var initialRow = currentRow
            var initialColumn = currentColumn
            var initialPosition = actualPosition
            lexema += currentCharacter
            setNextCharacter()//Cambiar nombre a actualizar caracter

            while (currentCharacter != '^') {
                lexema += currentCharacter
                setNextCharacter()//Cambiar nombre a actualizar caracter
            }
            lexema += currentCharacter
            setNextCharacter()
            storeToken(lexema, Category.COMENTARIO, initialRow, initialColumn)
            return true
        }
        return false
    }

    /**
     * this method allows to identify if it is a character
     */
    fun isCharacter(): Boolean {

        if (currentCharacter == '¿') {

            var lexema = ""
            var initialRow = currentRow
            var initialColumn = currentColumn
            var initialPosition = actualPosition
            var isError=false
            lexema += currentCharacter
            setNextCharacter()//Cambiar nombre a actualizar caracter

            if (currentCharacter !='?' && currentCharacter !='°' ) {
                lexema += currentCharacter
                setNextCharacter()//Cambiar nombre a actualizar caracter

                if(currentCharacter !='?') {
                    isError=true

                    while(currentCharacter !='?'){
                        lexema += currentCharacter
                        setNextCharacter()//Cambiar nombre a actualizar caracter
                    }
                }
            }
            else {

                if (currentCharacter == '°') {
                    lexema += currentCharacter
                    setNextCharacter()//Cambiar nombre a actualizar caracter
                    if (currentCharacter == 'n' || currentCharacter == 't' || currentCharacter == 'b'
                            || currentCharacter == 'r' || currentCharacter == '(' || currentCharacter == ')'
                            || currentCharacter == '?' || currentCharacter == '¿') {

                        lexema += currentCharacter
                        setNextCharacter()//Cambiar nombre a actualizar caracter
                        if(currentCharacter != '?')
                        {
                            isError=true

                            while(currentCharacter !='?'){
                                lexema += currentCharacter
                                setNextCharacter()//Cambiar nombre a actualizar caracter
                            }
                        }

                    }
                    else {
                        isError=true
                        while(currentCharacter !='?'){
                            lexema += currentCharacter
                            setNextCharacter()//Cambiar nombre a actualizar caracter
                        }
                    }
                }
            }


            if (isError) {
                lexema += currentCharacter
                setNextCharacter()
                storeError(lexema, initialRow, initialColumn, ErrorCategory.ERROR_LEXICO)
                return true
            }

            lexema += currentCharacter
            setNextCharacter()
            storeToken(lexema, Category.CARACTER, initialRow, initialColumn)
            return true

        }
        return false
    }

    /**
     * this method identifies groupers
     */
    fun isGrouper(): Boolean {

        if(currentCharacter == '|')
        {
            var lexema = ""
            var initialRow = currentRow
            var initialColumn = currentColumn
            var actual = actualPosition
            lexema += currentCharacter
            setNextCharacter()

            storeToken(lexema, Category.AGRUPADOR, initialRow, initialColumn)
            return true

        }
        if(currentCharacter == '/')
        {
            var lexema = ""
            var initialRow = currentRow
            var initialColumn = currentColumn
            var actual = actualPosition
            lexema += currentCharacter
            setNextCharacter()

            storeToken(lexema, Category.AGRUPADOR, initialRow, initialColumn)
            return true


        }
        if(currentCharacter == '<')
        {
            var lexema = ""
            var initialRow = currentRow
            var initialColumn = currentColumn
            var actual = actualPosition
            lexema += currentCharacter
            setNextCharacter()

            if (currentCharacter == ':' || currentCharacter == '-') {
                doBackTracking(actual, initialRow, initialColumn)
                return false
            }

            storeToken(lexema, Category.AGRUPADOR, initialRow, initialColumn)
            return true


        }
        if(currentCharacter == '>')
        {
            var lexema = ""
            var initialRow = currentRow
            var initialColumn = currentColumn
            var actual = actualPosition
            lexema += currentCharacter
            setNextCharacter()

            if (currentCharacter == ':') {
                doBackTracking(actual, initialRow, initialColumn)
                return false
            }

            storeToken(lexema, Category.AGRUPADOR, initialRow, initialColumn)
            return true


        }
        return false
    }
    /**
     * This method allows to:
     * increment the actual position,
     * update the current character,
     * verify the correct index of the source code
     */
    fun setNextCharacter() {
        if (actualPosition == sourceCode.length - 1) {
            currentCharacter = endCode
        } else {

            if (currentCharacter == '\n') {
                currentRow++
                currentColumn = 0
            } else {
                currentColumn++
            }

            actualPosition++
            currentCharacter = sourceCode[actualPosition]
        }
    }

    /**
     * This method allows to fill the token list
     */
    fun storeToken(lexema: String, category: Category, row: Int, column: Int) =
            tokenList.add(Token(lexema, category, row, column))

    /**
     * This method allows to fill the error list
     */
    fun storeError(error: String, row: Int, column: Int, errorCategory: ErrorCategory) =
            errorList.add(Error(error, row, column, errorCategory))
}