package ba.unsa.etf.rma.spirala1

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity
data class BiljkaBitmap(
    @PrimaryKey val idBiljke: Long,
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB) val bitmap: ByteArray //ili Bitmap
)