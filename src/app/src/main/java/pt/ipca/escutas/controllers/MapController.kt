package pt.ipca.escutas.controllers

import pt.ipca.escutas.models.Location
import pt.ipca.escutas.views.fragments.MapFragment
import java.util.*

/**
 * Defines the [MapFragment] controller.
 *
 */
class MapController : BaseController() {
    /**
     * Gets the stored locations.
     *
     * @return A list containing the stored locations.
     */
    fun getLocations(): List<Location>
    {
        // TODO: Invoke database service in order to fetch locations. Response is mocked for now.

        return listOf(
            Location(
                UUID.randomUUID(),
                "Sede do Agrupamento 1 - Sé de Braga",
                41.5518896,
                -8.4309099)
        )
    }
}
