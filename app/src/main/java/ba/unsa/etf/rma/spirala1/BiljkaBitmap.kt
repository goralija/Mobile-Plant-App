package ba.unsa.etf.rma.spirala1

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName

@Entity(
    foreignKeys = [ForeignKey(
        entity = Biljka::class,
        parentColumns = ["id"],
        childColumns = ["idBiljke"],
        onDelete = ForeignKey.CASCADE
    )]
)
@TypeConverters(Converters::class)
data class BiljkaBitmap(
    @PrimaryKey(autoGenerate = true) var id:Long?=null,
    @ColumnInfo(name = "idBiljke") @SerializedName("idBiljke") val idBiljke: Long,
    @ColumnInfo(name = "bitmap") @SerializedName("bitmap") val bitmap: Bitmap
)