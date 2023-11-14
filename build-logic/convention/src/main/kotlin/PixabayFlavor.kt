import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.ApplicationProductFlavor
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.ProductFlavor
import org.gradle.api.Project

@Suppress("EnumEntryName")
enum class FlavorDimension {
    contentType
}

@Suppress("EnumEntryName")
enum class PixabayFlavor(
    val dimension: FlavorDimension,
    val applicationIdSuffix: String? = null
) {
//    dev(FlavorDimension.contentType),
//    prod(FlavorDimension.contentType, ".prod")
}

fun Project.configureFlavors(
    commonExtension: CommonExtension<*, *, *, *>,
    flavorConfigurationBlock: ProductFlavor.(flavor: PixabayFlavor) -> Unit = {}
) {
    commonExtension.apply {
        flavorDimensions += FlavorDimension.contentType.name
        productFlavors {
            PixabayFlavor.values().forEach {
                create(it.name) {
                    dimension = it.dimension.name
                    flavorConfigurationBlock(this, it)
                    if (this@apply is ApplicationExtension && this is ApplicationProductFlavor) {
                        if (it.applicationIdSuffix != null) {
                            this.applicationIdSuffix = it.applicationIdSuffix
                        }
                    }
                }
            }
        }
    }
}
