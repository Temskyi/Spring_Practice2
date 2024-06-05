package ua.kpi.its.lab.rest.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ua.kpi.its.lab.rest.dto.TrainRequest
import ua.kpi.its.lab.rest.dto.TrainResponse
import ua.kpi.its.lab.rest.svc.TrainService

@RestController
@RequestMapping("/trains")
class TrainController @Autowired constructor(
    private val trainService: TrainService
) {

    @GetMapping("")
    fun getAllTrains(): List<TrainResponse> = trainService.read()

    @GetMapping("/{id}")
    fun getTrainById(@PathVariable id: Long): ResponseEntity<TrainResponse> {
        return trainService.readById(id)?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()
    }

    @PostMapping("")
    fun createTrain(@RequestBody trainRequest: TrainRequest): TrainResponse {
        return trainService.create(trainRequest)
    }

    @PutMapping("/{id}")
    fun updateTrain(
        @PathVariable id: Long,
        @RequestBody trainRequest: TrainRequest
    ): ResponseEntity<TrainResponse> {
        return trainService.updateById(id, trainRequest)?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()
    }

    @DeleteMapping("/{id}")
    fun deleteTrain(@PathVariable id: Long): ResponseEntity<TrainResponse> {
        return trainService.deleteById(id)?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()
    }
}