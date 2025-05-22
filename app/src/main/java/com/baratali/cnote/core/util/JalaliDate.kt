package com.baratali.cnote.core.util

import java.time.LocalDateTime
import java.util.Calendar

data class JalaliDate(
    var year: Int = 0,
    var month: Int = 0, // 1-based (1 = Farvardin, 12 = Esfand)
    var day: Int = 0
) {
    companion object {
        internal val monthNames = arrayOf(
            "فروردین", "اردیبهشت", "خرداد", "تیر", "مرداد", "شهریور",
            "مهر", "آبان", "آذر", "دی", "بهمن", "اسفند"
        )

        internal val dayNames = arrayOf(
            "ش", "ی", "د", "س", "چ", "پ", "ج"
        )

        fun today(): JalaliDate {
            val calendar = Calendar.getInstance()
            return fromGregorian(
                gregorianYear = calendar.get(Calendar.YEAR),
                gregorianMonth = calendar.get(Calendar.MONTH) + 1,
                gregorianDay = calendar.get(Calendar.DAY_OF_MONTH)
            )
        }

        fun fromGregorian(gregorianYear: Int, gregorianMonth: Int, gregorianDay: Int): JalaliDate {
            val gregorianDaysInMonth = intArrayOf(
                0, 31, if (isGregorianLeapYear(gregorianYear)) 29 else 28, 31, 30, 31,
                30, 31, 31, 30, 31, 30, 31
            )

            val adjustedGregorianYear = gregorianYear - 1600
            val adjustedGregorianMonth = gregorianMonth - 1
            val adjustedGregorianDay = gregorianDay - 1

            var totalGregorianDays = 365 * adjustedGregorianYear +
                    (adjustedGregorianYear + 3) / 4 -
                    (adjustedGregorianYear + 99) / 100 +
                    (adjustedGregorianYear + 399) / 400
            for (i in 0 until adjustedGregorianMonth) {
                totalGregorianDays += gregorianDaysInMonth[i + 1]
            }
            totalGregorianDays += adjustedGregorianDay

            var jalaliDayCount = totalGregorianDays - 79
            val jalali33YearCycles = jalaliDayCount / 12053
            jalaliDayCount %= 12053

            var jalaliYear = 979 + 33 * jalali33YearCycles + 4 * (jalaliDayCount / 1461)
            jalaliDayCount %= 1461

            if (jalaliDayCount >= 366) {
                jalaliYear += (jalaliDayCount - 1) / 365
                jalaliDayCount = (jalaliDayCount - 1) % 365
            }

            val jalaliDaysInMonth = intArrayOf(31, 31, 31, 31, 31, 31, 30, 30, 30, 30, 30, 29)
            var monthIndex = 0
            while (monthIndex < 12 && jalaliDayCount >= jalaliDaysInMonth[monthIndex]) {
                jalaliDayCount -= jalaliDaysInMonth[monthIndex]
                monthIndex++
            }

            val jalaliMonth = monthIndex + 1
            val jalaliDay = jalaliDayCount + 1

            return JalaliDate(jalaliYear, jalaliMonth, jalaliDay)
        }

        fun fromLocalDateTime(localDateTime: LocalDateTime): JalaliDate {
            return fromGregorian(
                gregorianYear = localDateTime.year,
                gregorianMonth = localDateTime.monthValue,
                gregorianDay = localDateTime.dayOfMonth
            )
        }

        fun LocalDateTime.toJalaliString(): String {
            val jalaliDate = fromLocalDateTime(this)
            val hour = this.hour.toString().padStart(2, '0')
            val minute = this.minute.toString().padStart(2, '0')
            return "${jalaliDate.year}/${
                jalaliDate.month.toString().padStart(2, '0')
            }/${jalaliDate.day.toString().padStart(2, '0')}, $hour:$minute"
        }

        fun adjustDateToValidRange(
            date: JalaliDate,
            minDate: JalaliDate?,
            maxDate: JalaliDate?
        ): JalaliDate? {
            val daysInMonth = date.getDaysInJalaliMonth(date.year, date.month)
            for (day in 1..daysInMonth) {
                val candidate = date.copy(day = day)
                if ((minDate == null || candidate >= minDate) && (maxDate == null || candidate <= maxDate)) {
                    return candidate
                }
            }
            return null
        }

        private fun isGregorianLeapYear(year: Int): Boolean {
            return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
        }
    }

    init {
        if (year == 0) {
            val today = today()
            this.year = today.year
            this.month = today.month
            this.day = today.day
        }
    }

    fun toGregorian(): IntArray {
        val jalaliDaysInMonth = intArrayOf(31, 31, 31, 31, 31, 31, 30, 30, 30, 30, 30, 29)
        val adjustedJalaliYear = year - 979
        val adjustedJalaliMonth = month - 1
        val adjustedJalaliDay = day - 1

        var totalJalaliDays = 365 * adjustedJalaliYear + (adjustedJalaliYear / 33 * 8) +
                (adjustedJalaliYear % 33 + 3) / 4
        for (i in 0 until adjustedJalaliMonth) {
            totalJalaliDays += jalaliDaysInMonth[i]
        }
        totalJalaliDays += adjustedJalaliDay

        var totalGregorianDays = totalJalaliDays + 79
        var gregorianYear = 1600 + 400 * (totalGregorianDays / 146097)
        totalGregorianDays %= 146097

        var isLeap = true
        if (totalGregorianDays >= 36525) {
            totalGregorianDays--
            gregorianYear += 100 * (totalGregorianDays / 36524)
            totalGregorianDays %= 36524
            if (totalGregorianDays >= 365) totalGregorianDays++ else isLeap = false
        }

        gregorianYear += 4 * (totalGregorianDays / 1461)
        totalGregorianDays %= 1461

        if (totalGregorianDays >= 366) {
            isLeap = false
            totalGregorianDays--
            gregorianYear += totalGregorianDays / 365
            totalGregorianDays %= 365
        }

        val gregorianDaysInMonth = intArrayOf(
            31, if (isLeap) 29 else 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
        )
        var gregorianMonth = 0
        while (totalGregorianDays >= gregorianDaysInMonth[gregorianMonth]) {
            totalGregorianDays -= gregorianDaysInMonth[gregorianMonth]
            gregorianMonth++
        }

        val gregorianDay = totalGregorianDays + 1
        return intArrayOf(gregorianYear, gregorianMonth + 1, gregorianDay)
    }

    fun getDaysInJalaliMonth(jalaliYear: Int, jalaliMonth: Int): Int {
        return when (jalaliMonth) {
            in 1..6 -> 31
            in 7..11 -> 30
            12 -> if (isJalaliLeapYear(jalaliYear)) 30 else 29
            else -> 0
        }
    }

    fun monthLength(): Int = getDaysInJalaliMonth(year, month)

    fun isJalaliLeapYear(jalaliYear: Int): Boolean {
        val adjustedYear = jalaliYear - if (jalaliYear > 0) 474 else 473
        val cyclePosition = adjustedYear % 2820 + 474
        return ((cyclePosition * 682) % 2816) < 682
    }

    fun getMonthName(): String = monthNames[month - 1]

    fun getDayOfWeek(): Int {
        val calendar = Calendar.getInstance()
        val g = toGregorian()
        calendar.set(g[0], g[1] - 1, g[2])
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        return when (dayOfWeek) {
            Calendar.SATURDAY -> 0
            Calendar.SUNDAY -> 1
            Calendar.MONDAY -> 2
            Calendar.TUESDAY -> 3
            Calendar.WEDNESDAY -> 4
            Calendar.THURSDAY -> 5
            Calendar.FRIDAY -> 6
            else -> 0
        }
    }

    fun toCalendar(): Calendar {
        val gregorian = toGregorian()
        val calendar = Calendar.getInstance()
        calendar.set(gregorian[0], gregorian[1] - 1, gregorian[2])
        return calendar
    }

    fun tomorrow(): JalaliDate {
        val calendar = toCalendar()
        calendar.add(Calendar.DAY_OF_MONTH, 1)
        return fromGregorian(
            gregorianYear = calendar.get(Calendar.YEAR),
            gregorianMonth = calendar.get(Calendar.MONTH) + 1,
            gregorianDay = calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    fun yesterday(): JalaliDate {
        val calendar = toCalendar()
        calendar.add(Calendar.DAY_OF_MONTH, -1)
        return fromGregorian(
            gregorianYear = calendar.get(Calendar.YEAR),
            gregorianMonth = calendar.get(Calendar.MONTH) + 1,
            gregorianDay = calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    operator fun compareTo(other: JalaliDate): Int {
        return when {
            year != other.year -> year - other.year
            month != other.month -> month - other.month
            else -> day - other.day
        }
    }
}