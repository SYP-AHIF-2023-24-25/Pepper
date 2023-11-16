package com.example.connect_four

class Connectfour {
    public val rows = 6
    public val columns = 7
    public val board = Array(rows) { IntArray(columns) }

    public var currentPlayer = 1

    fun makeMove(column: Int): Boolean {
        for (row in rows - 1 downTo 0) {
            if (board[row][column] == 0) {
                board[row][column] = currentPlayer
                return true
            }
        }
        return false
    }

    fun switchPlayer() {
        currentPlayer = if (currentPlayer == 1) 2 else 1
    }

    fun checkForWin(): Boolean {
        // Überprüfung horizontal
        for (row in 0 until rows) {
            for (col in 0 until columns - 3) {
                if (board[row][col] == currentPlayer &&
                    board[row][col + 1] == currentPlayer &&
                    board[row][col + 2] == currentPlayer &&
                    board[row][col + 3] == currentPlayer
                ) {
                    return true
                }
            }
        }

        // Überprüfung vertikal
        for (row in 0 until rows - 3) {
            for (col in 0 until columns) {
                if (board[row][col] == currentPlayer &&
                    board[row + 1][col] == currentPlayer &&
                    board[row + 2][col] == currentPlayer &&
                    board[row + 3][col] == currentPlayer
                ) {
                    return true
                }
            }
        }

        // Überprüfung diagonal (nach rechts oben)
        for (row in 0 until rows - 3) {
            for (col in 0 until columns - 3) {
                if (board[row][col] == currentPlayer &&
                    board[row + 1][col + 1] == currentPlayer &&
                    board[row + 2][col + 2] == currentPlayer &&
                    board[row + 3][col + 3] == currentPlayer
                ) {
                    return true
                }
            }
        }

        // Überprüfung diagonal (nach rechts unten)
        for (row in 3 until rows) {
            for (col in 0 until columns - 3) {
                if (board[row][col] == currentPlayer &&
                    board[row - 1][col + 1] == currentPlayer &&
                    board[row - 2][col + 2] == currentPlayer &&
                    board[row - 3][col + 3] == currentPlayer
                ) {
                    return true
                }
            }
        }

        return false
    }

    fun isBoardFull(): Boolean {
        for (row in 0 until rows) {
            for (column in 0 until columns) {
                if (board[row][column] == 0) {
                    return false
                }
            }
        }
        return true
    }
}
