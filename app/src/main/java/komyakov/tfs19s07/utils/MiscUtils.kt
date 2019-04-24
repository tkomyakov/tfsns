package komyakov.tfs19s07.utils

import android.text.Html
import android.text.Spanned

fun fromHtml(html: String): Spanned
{
    val result: Spanned = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
        Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
    } else {
        Html.fromHtml(html)
    }
    return result
}