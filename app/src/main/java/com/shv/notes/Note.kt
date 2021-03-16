package com.shv.notes

class Note(var id: Int, var title: String, var description: String, var dayOfWeek: Int, var priority: Int) {

    companion object GetDayAsString {
        fun getDayAsString(position: Int): String = when (position) {
            1 -> "Понедельник"
            2 -> "Вторник"
            3 -> "Среда"
            4 -> "Четверг"
            5 -> "Пятница"
            6 -> "Суббота"
            else -> "Воскресенье"
        }
    }
}