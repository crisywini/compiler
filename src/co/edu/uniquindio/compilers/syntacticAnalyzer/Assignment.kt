package co.edu.uniquindio.compilers.syntacticAnalyzer

import co.edu.uniquindio.compilers.lexicalAnalyzer.Token

class Assignment(var identifier:Token, var expression:Expression):Statement() {
}