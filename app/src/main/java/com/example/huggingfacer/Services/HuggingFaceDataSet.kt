import com.google.gson.annotations.SerializedName

data class HuggingFaceDataSet(
    @SerializedName("_id") val idd: String,
    val id: String,
    val author: String?,
    val disabled: Boolean?,
//    val gated: String?, // Commented out similar to your Swift code
    @SerializedName("lastModified") val lastModified: String?,
    val likes: Int?,
    @SerializedName("private") val isPrivate: Boolean?,
    val sha: String?,
    val description: String?,
    val downloads: Int?,
    @SerializedName("paperswithcode_id") val papersWithCodeId: String?,
    val tags: List<String>?,
    @SerializedName("createdAt") val createdAt: String?,
    val key: String?
)
