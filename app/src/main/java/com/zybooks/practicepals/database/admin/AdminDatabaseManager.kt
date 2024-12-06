package com.zybooks.practicepals.database.admin

import com.zybooks.practicepals.database.dao.PieceDao
import com.zybooks.practicepals.database.dao.PracticeLogDao
import com.zybooks.practicepals.database.entities.Piece
import com.zybooks.practicepals.database.entities.PracticeLog

class AdminDatabaseManager (
    private val pieceDao: PieceDao,
    private val practiceLogDao: PracticeLogDao
) {
    private val pieces = listOf(
        Piece(name = "Waltz Op. 64 No. 1", composer = "Frederic Chopin"),
        Piece(name = "Nocturne Op. 9 No. 2", composer = "Frederic Chopin"),
        Piece(name = "Ballade No. 1 in G minor, Op. 23", composer = "Frederic Chopin"),
        Piece(name = "Etude Op. 10 No. 12 'Revolutionary'", composer = "Frederic Chopin"),
        Piece(name = "Prelude Op. 28 No. 15 'Raindrop'", composer = "Frederic Chopin"),

        Piece(name = "Liebestraum No. 3 in A-flat Major", composer = "Franz Liszt"),
        Piece(name = "Hungarian Rhapsody No. 2", composer = "Franz Liszt"),
        Piece(name = "Piano Sonata in B minor", composer = "Franz Liszt"),
        Piece(name = "La Campanella", composer = "Franz Liszt"),
        Piece(name = "Un Sospiro", composer = "Franz Liszt"),

        Piece(name = "Symphony No. 40 in G minor, K. 550", composer = "Wolfgang Amadeus Mozart"),
        Piece(name = "Piano Sonata No. 16 in C major, K. 545", composer = "Wolfgang Amadeus Mozart"),
        Piece(name = "Requiem Mass in D minor, K. 626", composer = "Wolfgang Amadeus Mozart"),
        Piece(name = "Eine kleine Nachtmusik, K. 525", composer = "Wolfgang Amadeus Mozart"),
        Piece(name = "The Magic Flute, K. 620", composer = "Wolfgang Amadeus Mozart"),

        Piece(name = "Symphony No. 5 in C minor, Op. 67", composer = "Ludwig van Beethoven"),
        Piece(name = "Piano Sonata No. 14 'Moonlight'", composer = "Ludwig van Beethoven"),
        Piece(name = "Symphony No. 9 in D minor, Op. 125 'Choral'", composer = "Ludwig van Beethoven"),
        Piece(name = "Fur Elise", composer = "Ludwig van Beethoven"),
        Piece(name = "Piano Concerto No. 5 'Emperor'", composer = "Ludwig van Beethoven"),
    )

    suspend fun clearDatabase() {
        practiceLogDao.deleteAllPracticeLogs()
        pieceDao.deleteAllPieces()
    }

    suspend fun seedDatabase() {
        clearDatabase()
        val pieceIds = pieceDao.insertAll(pieces)

        // Create PracticeLogs with the generated piece IDs
        val practiceLogs = pieceIds.mapIndexed { index, pieceId ->
            PracticeLog(
                pieceId = pieceId.toInt(),
                timeLogged = (10*60*1000).toLong(), // Example elapsed time for each piece
                practiceLogDescription = "Practice log for ${pieces[index].name}"
            )
        }

        // Insert the PracticeLogs (assuming a PracticeLogDao exists)
        practiceLogDao.insertAll(practiceLogs)
    }
}