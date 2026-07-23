package fr.meteordesign.eldritchhorrorcompanion

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform