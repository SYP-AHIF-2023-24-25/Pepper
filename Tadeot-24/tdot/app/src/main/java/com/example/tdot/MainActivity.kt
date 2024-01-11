package com.example.tdot

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var boardTableLayout: TableLayout
    private lateinit var restartButton: Button
    private lateinit var winnerTextView: TextView
    private var currentPlayer: Int = 1
    private lateinit var lastPlayedButton: Button
    private var lowestEmptyRow: Int = -1
    private lateinit var currentImageView: ImageView
    private val buttonBackgrounds: MutableList<Drawable?> = mutableListOf()

    // Beispiel-Daten, du wirst deine eigene Logik dafür haben
    private val board: Array<Array<Int>> = Array(6) { Array(7) { 0 } }
    private var winner: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val connectFourButton: Button = findViewById(R.id.square1)
        connectFourButton.setOnClickListener {
            setContentView(R.layout.connect4view)
            boardTableLayout = findViewById(R.id.boardTableLayout)
            restartButton = findViewById(R.id.restartButton)
            winnerTextView = findViewById(R.id.winnerTextView)

            initializeBoard()
            saveButtonBackgrounds()
            val backToMainButton: Button = findViewById(R.id.backToMainButton)
            backToMainButton.setOnClickListener {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            restartButton.setOnClickListener {
                resetGame()
            }
        }

        val tdot2Button: Button = findViewById<Button>(R.id.square3)
        tdot2Button.setOnClickListener {
            val intent = Intent(this, TdotTwo::class.java)
            startActivity(intent)
        }

    }

    private fun initializeBoard() {
        for (rowIndex in 0 until 6) {
            val tableRow = TableRow(this)
            tableRow.layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
            )

            for (colIndex in 0 until 7) {
                val cellButton = Button(this, null, android.R.attr.buttonStyleSmall)
                cellButton.layoutParams = TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT
                )

                cellButton.setOnClickListener {
                    play(rowIndex, colIndex)
                }

                tableRow.addView(cellButton)
            }
            boardTableLayout.addView(tableRow)
        }
    }
    private fun play(row: Int, col: Int) {
        var lowestEmptyRow = 5 // Starte mit der untersten Reihe

        // Finde die unterste leere Reihe in der ausgewählten Spalte
        while (lowestEmptyRow >= 0 && board[lowestEmptyRow][col] != 0) {
            lowestEmptyRow--
        }

        if (lowestEmptyRow >= 0) {
            // Setze den Wert in der untersten leeren Zelle entsprechend dem aktuellen Spieler
            board[lowestEmptyRow][col] = currentPlayer

            // Aktualisiere die UI
            updateUI()

            // Überprüfe auf einen Gewinner
            if (checkForWin()) {
                winner = if (currentPlayer == 1) "Player X" else "Player O"
                showWinMessage()
            } else if (isBoardFull()) {
                showDrawMessage()
            } else {
                // Wechsle den Spieler
                switchPlayer()
            }
            if (winner.isNotEmpty()) {
                winnerTextView.text = "Winner: " + if (currentPlayer == 1) "Player X" else "Player O"
                winnerTextView.visibility = View.VISIBLE
                restartButton.visibility = View.VISIBLE
            } else {
                winnerTextView.visibility = View.GONE
                restartButton.visibility = View.GONE
            }
        } else {
            // Die Spalte ist bereits voll
            showToast("Invalid move. Column is already full.")
        }
    }

    private fun isBoardFull(): Boolean {
        for (row in 0 until 6) {
            for (col in 0 until 7) {
                if (board[row][col] == 0) {
                    // Es gibt eine leere Zelle, das Spielfeld ist nicht vollständig belegt
                    return false
                }
            }
        }
        // Alle Zellen sind belegt, das Spielfeld ist vollständig belegt
        return true
    }
    private fun checkForWin(): Boolean {
        // Überprüfe horizontal
        for (row in 0 until 6) {
            for (col in 0 until 4) {
                if (checkFourCells(board[row][col], board[row][col + 1], board[row][col + 2], board[row][col + 3])) {
                    return true
                }
            }
        }

        // Überprüfe vertikal
        for (col in 0 until 7) {
            for (row in 0 until 3) {
                if (checkFourCells(board[row][col], board[row + 1][col], board[row + 2][col], board[row + 3][col])) {
                    return true
                }
            }
        }

        // Überprüfe diagonal (nach rechts oben)
        for (row in 0 until 3) {
            for (col in 0 until 4) {
                if (checkFourCells(board[row][col], board[row + 1][col + 1], board[row + 2][col + 2], board[row + 3][col + 3])) {
                    return true
                }
            }
        }

        // Überprüfe diagonal (nach rechts unten)
        for (row in 3 until 6) {
            for (col in 0 until 4) {
                if (checkFourCells(board[row][col], board[row - 1][col + 1], board[row - 2][col + 2], board[row - 3][col + 3])) {
                    return true
                }
            }
        }

        return false
    }

    private fun checkFourCells(cell1: Int, cell2: Int, cell3: Int, cell4: Int): Boolean {
        return cell1 != 0 && cell1 == cell2 && cell2 == cell3 && cell3 == cell4
    }


    private fun updateUI() {
        for (rowIndex in 0 until 6) {
            val tableRow = boardTableLayout.getChildAt(rowIndex) as TableRow?

            if (tableRow != null) {
                for (colIndex in 0 until 7) {
                    val cellButton = tableRow.getChildAt(colIndex) as Button?

                    if (cellButton != null && winner == "") {
                        val cellValue = board[rowIndex][colIndex]
                        cellButton.text = if (cellValue == 1) "X" else if (cellValue == 2) "O" else ""

                        if (cellValue == 1 || cellValue == 2) {
                            val backgroundColor = if (cellValue == 1) Color.RED else Color.BLUE
                            cellButton.setBackgroundColor(backgroundColor)

                            if (rowIndex == lowestEmptyRow) {
                                //animateDrop(rowIndex, colIndex)
                                //lastPlayedButton = cellButton // Aktualisiere lastPlayedButton
                            }
                        }
                    } else {
                        Log.e("ConnectFour", "updateUI: Button at ($rowIndex, $colIndex) is null oder already a winner")
                    }
                }
            } else {
                Log.e("ConnectFour", "updateUI: TableRow at $rowIndex is null")
            }
        }
    }

    private fun showWinMessage() {
        winner = if (currentPlayer == 1) "Player X" else "Player O"
        showToast("$winner wins!")
    }

    private fun showDrawMessage() {
        winner = "Draw"
        showToast("It's a draw!")
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun switchPlayer() {
        // Wechsle den Spieler (Spieler 1 zu Spieler 2 und umgekehrt)
        currentPlayer = 3 - currentPlayer
    }

    private fun saveButtonBackgrounds() {
        for (rowIndex in 0 until 6) {
            val tableRow = boardTableLayout.getChildAt(rowIndex) as TableRow?
            if (tableRow != null) {
                for (colIndex in 0 until 7) {
                    val cellButton = tableRow.getChildAt(colIndex) as Button?
                    buttonBackgrounds.add(cellButton?.background?.constantState?.newDrawable())
                }
            }
        }
    }


    private fun resetGame() {
        // Hier setzt du das Spiel zurück
        currentPlayer = 1

        // Setze das Spielbrett zurück
        for (row in 0 until 6) {
            for (col in 0 until 7) {
                board[row][col] = 0
            }
        }

        for (rowIndex in 0 until 6) {
            val tableRow = boardTableLayout.getChildAt(rowIndex) as TableRow?
            if (tableRow != null) {
                for (colIndex in 0 until 7) {
                    val cellButton = tableRow.getChildAt(colIndex) as Button?

                    if (cellButton?.text == "X" || cellButton?.text == "O") {
                        cellButton.background = buttonBackgrounds.removeAt(0)
                    }

                    // Setze den Text und andere Eigenschaften auf die Werte in der XML
                    cellButton?.text = ""
                    cellButton?.visibility = View.VISIBLE
                    cellButton?.isClickable = true
                }
            }
        }
        // Setze den Gewinner zurück
        winner = ""

        // Aktualisiere die UI
        updateUI()

        // Deaktiviere die Sichtbarkeit der Winner-Anzeige und des Restart-Buttons
        winnerTextView.visibility = View.GONE
        restartButton.visibility = View.GONE
    }
}
