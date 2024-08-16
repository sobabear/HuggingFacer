import com.google.gson.annotations.SerializedName
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Body

// Data classes for the API responses
data class HuggingFaceModelResponse(
    val id: String,
    val author: String?,
    val gated: Boolean?,
    val lastModified: String?,
    val likes: Int?,
    @SerializedName("private") val privateModel: Boolean,
    val config: Config?,
    val downloads: Int,
    val tags: List<String>,
    @SerializedName("pipeline_tag") val pipelineTag: String?,
    @SerializedName("library_name") val libraryName: String?,
    val inference: String?,
    val createdAt: String?,
    val modelId: String?,
    val siblings: List<Sibling>?
)

data class Config(
    val architectures: List<String>,
    @SerializedName("model_type") val modelType: String,
)

data class Sibling(
    @SerializedName("rfilename") val rFilename: String
)

data class HuggingFaceDailyPaper(val title: String, val content: String)
data class HuggingFaceDataSet(val id: String, val name: String)
data class HuggingFaceSpace(val id: String, val name: String)
data class HuggingFaceUser(val username: String)

// Request body classes
data class HuggingFaceModelsRequestBody(
    val search: String? = null,
    val author: String? = null,
    val filter: String? = null,
    val sort: String? = null,
    val direction: String? = null,
    val limit: String? = null,
    val full: String? = null,
    val config: String? = null
)

data class HuggingFaceDataSetRequestBody(
    val search: String? = null,
    val author: String? = null,
    val filter: String? = null,
    val sort: String? = null,
    val direction: String? = null,
    val limit: String? = null,
    val full: String? = null,
    val config: String? = null
)

// Retrofit API interface
interface HuggingFaceService {
    @GET("api/models")
    suspend fun fetchModels(
        @Query("search") search: String? = null,
        @Query("author") author: String? = null,
        @Query("filter") filter: String? = null,
        @Query("sort") sort: String? = null,
        @Query("direction") direction: String? = null,
        @Query("limit") limit: String? = null,
        @Query("full") full: String? = null,
        @Query("config") config: String? = null
    ): List<HuggingFaceModelResponse>

    @GET("api/daily_papers")
    suspend fun getDailyPapers(): List<HuggingFaceDailyPaper>

    @GET("api/datasets")
    suspend fun fetchDataSets(
        @Query("search") search: String?,
        @Query("author") author: String?,
        @Query("filter") filter: String?,
        @Query("sort") sort: String?,
        @Query("direction") direction: String?,
        @Query("limit") limit: String?,
        @Query("full") full: String?,
        @Query("config") config: String?
    ): List<HuggingFaceDataSet>

    @GET("api/models/{id}")
    suspend fun fetchModelById(@Path("id") id: String): HuggingFaceModelResponse

    @GET("api/spaces")
    suspend fun fetchSpaces(
        @Query("search") search: String?,
        @Query("author") author: String?,
        @Query("filter") filter: String?,
        @Query("sort") sort: String?,
        @Query("direction") direction: String?,
        @Query("limit") limit: String?,
        @Query("full") full: String?,
        @Query("config") config: String?
    ): List<HuggingFaceSpace>

    @GET("api/datasets/{id}")
    suspend fun fetchDataSetById(@Path("id") id: String): HuggingFaceDataSet

    @GET("api/whoami-v2")
    suspend fun whoAmI(@Header("Authorization") token: String): HuggingFaceUser
}

// Singleton object for creating the Retrofit service
object HuggingFaceApi {
    private const val BASE_URL = "https://huggingface.co/"

    val service: HuggingFaceService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HuggingFaceService::class.java)
    }
}
