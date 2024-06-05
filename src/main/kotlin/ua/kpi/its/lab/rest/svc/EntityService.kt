package ua.kpi.its.lab.rest.svc

import ua.kpi.its.lab.rest.dto.TrainRequest
import ua.kpi.its.lab.rest.dto.TrainResponse

interface TrainService {
    fun create(train: TrainRequest): TrainResponse
    fun read(): List<TrainResponse>
    fun readById(id: Long): TrainResponse?
    fun updateById(id: Long, train: TrainRequest): TrainResponse?
    fun deleteById(id: Long): TrainResponse?
}