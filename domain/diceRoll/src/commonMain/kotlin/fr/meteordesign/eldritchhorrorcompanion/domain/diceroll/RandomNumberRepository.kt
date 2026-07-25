package fr.meteordesign.eldritchhorrorcompanion.domain.diceroll

interface RandomNumberRepository {
    fun nextInt(from: Int, until: Int): Int
}
