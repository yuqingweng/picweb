
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
/*
全部需要的构造器
 */
public class Photo {
    @SerializedName("id")
    private Integer id;
    @SerializedName("name")
    private String name;
    @SerializedName("photourl")
    private String photourl;
    @SerializedName("thumurl")
    private String thumurl;
}
