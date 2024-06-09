package ba.unsa.etf.rma.spirala1

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(
    tableName = "biljka_bitmap",
    foreignKeys = [ForeignKey(
        entity = Biljka::class,
        parentColumns = ["id"],
        childColumns = ["idBiljke"],
        onDelete = ForeignKey.CASCADE
    )]
)
@TypeConverters(Converters::class)
data class BiljkaBitmap(
    @PrimaryKey val idBiljke: Long,
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB) val bitmap: Bitmap
)