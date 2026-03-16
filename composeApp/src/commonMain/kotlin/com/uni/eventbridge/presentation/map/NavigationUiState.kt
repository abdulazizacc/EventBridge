import com.uni.eventbridge.domain.model.Location
import com.uni.eventbridge.domain.model.MapRoute

data class NavigationUiState(
    val isLoading: Boolean = false,
    val userLocation: Location? = null,
    val route: MapRoute? = null,
    val error: String? = null,
)