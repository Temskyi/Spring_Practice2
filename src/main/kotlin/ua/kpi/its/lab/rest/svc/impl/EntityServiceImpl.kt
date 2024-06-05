package ua.kpi.its.lab.rest.svc.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ua.kpi.its.lab.rest.entity.Route
import ua.kpi.its.lab.rest.entity.Train
import ua.kpi.its.lab.rest.repo.TrainRepository
import ua.kpi.its.lab.rest.svc.TrainService
import ua.kpi.its.lab.rest.dto.RouteResponse
import ua.kpi.its.lab.rest.dto.TrainRequest
import ua.kpi.its.lab.rest.dto.TrainResponse
import java.text.SimpleDateFormat

@Service
class TrainServiceImpl @Autowired constructor(
    private val repository: TrainRepository
) : TrainService {

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd")

    override fun create(train: TrainRequest): TrainResponse {
        val route = train.route
        val newRoute = Route(
            departurePoint = route.departurePoint,
            destinationPoint = route.destinationPoint,
            departureDate = dateFormat.parse(route.departureDate),
            distance = route.distance,
            ticketPrice = route.ticketPrice,
            isCircular = route.isCircular
        )
        val newTrain = Train(
            model = train.model,
            manufacturer = train.manufacturer,
            type = train.type,
            commissioningDate = dateFormat.parse(train.commissioningDate),
            seatCount = train.seatCount,
            weight = train.weight,
            hasAirConditioning = train.hasAirConditioning,
            route = newRoute
        )
        val savedTrain = repository.save(newTrain)
        return trainEntityToDto(savedTrain)
    }

    override fun read(): List<TrainResponse> {
        return repository.findAll().map(this::trainEntityToDto)
    }

    override fun readById(id: Long): TrainResponse? {
        return repository.findById(id).orElse(null)?.let { trainEntityToDto(it) }
    }

    override fun updateById(id: Long, train: TrainRequest): TrainResponse? {
        return if (repository.existsById(id)) {
            val existingTrain = repository.findById(id).get()
            val route = train.route

            existingTrain.apply {
                model = train.model
                manufacturer = train.manufacturer
                type = train.type
                commissioningDate = dateFormat.parse(train.commissioningDate)
                seatCount = train.seatCount
                weight = train.weight
                hasAirConditioning = train.hasAirConditioning
            }
            existingTrain.route.apply {
                departurePoint = route.departurePoint
                destinationPoint = route.destinationPoint
                departureDate = dateFormat.parse(route.departureDate)
                distance = route.distance
                ticketPrice = route.ticketPrice
                isCircular = route.isCircular
            }
            val updatedTrain = repository.save(existingTrain)
            trainEntityToDto(updatedTrain)
        } else {
            null
        }
    }

    override fun deleteById(id: Long): TrainResponse? {
        return repository.findById(id).orElse(null)?.let {
            repository.delete(it)
            trainEntityToDto(it)
        }
    }

    private fun trainEntityToDto(train: Train): TrainResponse {
        return TrainResponse(
            id = train.id,
            model = train.model,
            manufacturer = train.manufacturer,
            type = train.type,
            commissioningDate = dateFormat.format(train.commissioningDate),
            seatCount = train.seatCount,
            weight = train.weight,
            hasAirConditioning = train.hasAirConditioning,
            route = routeEntityToDto(train.route)
        )
    }

    private fun routeEntityToDto(route: Route): RouteResponse {
        return RouteResponse(
            id = route.id,
            departurePoint = route.departurePoint,
            destinationPoint = route.destinationPoint,
            departureDate = dateFormat.format(route.departureDate),
            distance = route.distance,
            ticketPrice = route.ticketPrice,
            isCircular = route.isCircular
        )
    }
}