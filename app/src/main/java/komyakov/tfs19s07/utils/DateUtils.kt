package komyakov.tfs19s07.utils

import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME
import org.threeten.bp.temporal.ChronoUnit
import java.util.*

private const val humanReadableShort: String = "dd MMMM, yyyy"
private val days = arrayOf("Сегодня", "Вчера")
private val formatReadable = DateTimeFormatter.ofPattern(
    humanReadableShort,
    //TODO: пока игнорим
    //TODO: ACTION_LOCALE_CHANGED
    //TODO: вынести в компонент
    Locale.getDefault()
)!!
private val dateNow = LocalDate.now(ZoneId.systemDefault())

fun formatReadable(mills: Long): String {

    val date = Instant.ofEpochMilli(mills)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
    val compareResult = ChronoUnit.DAYS.between(date, dateNow)

    if (compareResult in 0..1) {
        return days[compareResult.toInt()]
    }

    return date.format(formatReadable)
}
