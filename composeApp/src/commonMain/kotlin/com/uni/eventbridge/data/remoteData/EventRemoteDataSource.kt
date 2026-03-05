import com.uni.eventbridge.data.remoteData.dto.CategoryDto
import com.uni.eventbridge.data.remoteData.dto.EventDto

class EventRemoteDataSource(
) {
    suspend fun getCategory(): List<CategoryDto> = listOf(
        CategoryDto(id = 1, name = "Music"),
        CategoryDto(id = 2, name = "Sports"),
        CategoryDto(id = 3, name = "Tech"),
        CategoryDto(id = 4, name = "Art"),
    )

    suspend fun getEventDeals(eventId: Long): EventDto = EventDto(
        id = eventId,
        name = "Sample Event",
        description = "This is a sample event description",
        bannerUrl = "https://picsum.photos/600/400",
        location = "Baghdad, Iraq",
        date = "2026-05-01",
        isActive = true,
        category = CategoryDto(id = 1, name = "Music"),
        time = "9:00",
        venueName = "lovely venue",
        venueDetail = "xxxxx",
        organizer = "CS department",
    )

    suspend fun getEventByCategory(categoryId: Long?): List<EventDto> {

        val allEvents = listOf(
            EventDto(
                id = 1,
                name = "Rock Concert",
                description = "An amazing rock concert",
                bannerUrl = "https://picsum.photos/600/400",
                location = "Erbil",
                date = "2026-04-10",
                isActive = true,
                CategoryDto(id = 2, name = "Sports"),
                time = "9:00",
                venueName = "lovely venue",
                venueDetail = "xxxxx",
                organizer = "CS department",
                ),
            EventDto(
                id = 2,
                name = "Jazz Night",
                description = "Smooth jazz evening",
                bannerUrl = "https://picsum.photos/600/401",
                location = "Baghdad",
                date = "2026-04-15",
                isActive = true,
                CategoryDto(id = 2, name = "Sports"),
                time = "9:00",
                venueName = "lovely venue",
                venueDetail = "xxxxx",
                organizer = "CS department",
            ),
            EventDto(
                id = 3,
                name = "Tech Summit",
                description = "Latest in tech",
                bannerUrl = "https://picsum.photos/600/402",
                location = "Basra",
                date = "2026-04-20",
                isActive = false, CategoryDto(id = 4, name = "Art"),
                time = "9:00",
                venueName = "lovely venue",
                venueDetail = "xxxxx",
                organizer = "CS department",
            ),
            )
        return if (categoryId == null) allEvents
        else allEvents.filter { it.category.id == categoryId }

    }

    suspend fun getEventBySearch(query: String): List<EventDto> = emptyList()


    suspend fun joinEvent(eventId: Long) {
        println("Joined event $eventId")
    }
}