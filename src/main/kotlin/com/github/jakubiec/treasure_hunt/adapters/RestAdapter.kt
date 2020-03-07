package com.github.jakubiec.treasure_hunt.adapters

import com.github.jakubiec.treasure_hunt.application.TreasureStorage
import com.github.jakubiec.treasure_hunt.application.functional.exploreTreasure
import com.github.jakubiec.treasure_hunt.domain.functional.TreasureFound
import com.github.jakubiec.treasure_hunt.domain.functional.TreasureNotFound
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Error
import io.micronaut.http.annotation.Get
import io.micronaut.http.hateoas.JsonError
import io.micronaut.http.hateoas.Link

@Controller("/treasure")
class RestAdapter(treasureStorage: TreasureStorage) {
    private val explore = exploreTreasure(treasureStorage)

    @Get("/{coordinates}", produces = [MediaType.APPLICATION_JSON])
    suspend fun getTreasure(coordinates: Int): HttpResponse<GetTreasureResponse> =
        when (val exploration = explore(coordinates)) {
            is TreasureFound -> respondWithSuccess(exploration)
            is TreasureNotFound -> respondNotFound()
        }

    private fun respondWithSuccess(exploration: TreasureFound) =
        HttpResponse
            .status<GetTreasureResponse>(HttpStatus.OK)
            .body(createResponse(exploration))

    private fun respondNotFound() =
        HttpResponse
            .status<GetTreasureResponse>(HttpStatus.NOT_FOUND)

    private fun createResponse(exploration: TreasureFound): GetTreasureResponse {
        return exploration
            .visitedCells
            .map { cell -> cell.coordinates }
            .map { coordinates -> "${coordinates.row} ${coordinates.column}" }
            .let { cells -> GetTreasureResponse(cells) }
    }

    @Error(status = HttpStatus.NOT_FOUND)
    fun notFound(request: HttpRequest<*>): HttpResponse<JsonError> =
        HttpResponse
            .notFound<JsonError>()
            .body(errorBody(request))


    private fun errorBody(request: HttpRequest<*>) =
        JsonError("Treasure Not Found")
            .link(Link.SELF, Link.of(request.uri))

    data class GetTreasureResponse(val treasureFoundAt: List<String>)

}
