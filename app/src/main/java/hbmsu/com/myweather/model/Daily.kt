package hbmsu.com.myweather.model

import java.io.Serializable
import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.asif.gsonpojogenerator")
class Daily : Serializable {

    @SerializedName("summary")
    var summary: String? = null

    @SerializedName("data")
    var data: List<DataItem>? = null

    @SerializedName("icon")
    var icon: String? = null

    override fun toString(): String {
        return "Daily{" +
                "summary = '" + summary + '\''.toString() +
                ",data = '" + data + '\''.toString() +
                ",icon = '" + icon + '\''.toString() +
                "}"
    }
}