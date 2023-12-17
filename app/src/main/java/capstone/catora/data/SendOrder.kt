package capstone.catora.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SendOrder(
    val title: String,
    val description: String,
    val category: String
):Parcelable