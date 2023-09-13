package com.example.carousellnews.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TimeFormatter {
    companion object {
        fun convertTimestampToAgo(timestamp: Long): String {
            val currentTimeMillis = System.currentTimeMillis()
            val timeDifferenceMillis = currentTimeMillis - (timestamp * 1000)
            val timeDifferenceSeconds = timeDifferenceMillis / 1000

            return when {
                timeDifferenceSeconds < 60 -> "$timeDifferenceSeconds seconds ago"
                timeDifferenceSeconds < 3600 -> "${timeDifferenceSeconds / 60} minutes ago"
                timeDifferenceSeconds < 86400 -> "${timeDifferenceSeconds / 3600} hours ago"
                timeDifferenceSeconds < 604800 -> "${timeDifferenceSeconds / 86400} days ago"
                timeDifferenceSeconds < 2419200 -> "${timeDifferenceSeconds / 604800} weeks ago"
                timeDifferenceSeconds < 29030400 -> "${timeDifferenceSeconds / 2419200} months ago"
                else -> "${timeDifferenceSeconds / 29030400} years ago"
            }
        }

        // We can also add a function to format the timestamp in a specific date format if needed.
        fun formatTimestamp(timestamp: Long, dateFormat: String): String {
            val date = Date(timestamp * 1000)
            val sdf = SimpleDateFormat(dateFormat, Locale.getDefault())
            return sdf.format(date)
        }
    }
}