package ua.kpi.its.lab.rest.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "trains")
class Train(
    @Column(nullable = false)
    var model: String,

    @Column(nullable = false)
    var manufacturer: String,

    @Column(nullable = false)
    var type: String,

    @Column(nullable = false)
    var commissioningDate: Date,

    @Column(nullable = false)
    var seatCount: Int,

    @Column(nullable = false)
    var weight: Double,

    @Column(nullable = false)
    var hasAirConditioning: Boolean,

    @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id")
    var route: Route
) : Comparable<Train> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = -1

    override fun compareTo(other: Train): Int {
        val modelComparison = this.model.compareTo(other.model)
        return if (modelComparison != 0) {
            modelComparison
        } else {
            this.seatCount.compareTo(other.seatCount)
        }
    }

    override fun toString(): String {
        return "Train(model='$model', manufacturer='$manufacturer', type='$type', commissioningDate=$commissioningDate, seatCount=$seatCount, weight=$weight, hasAirConditioning=$hasAirConditioning, route=$route)"
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
@Entity
@Table(name = "routes")
class Route(
    @Column(nullable = false)
    var departurePoint: String,

    @Column(nullable = false)
    var destinationPoint: String,

    @Column(nullable = false)
    var departureDate: Date,

    @Column(nullable = false)
    var distance: Double,

    @Column(nullable = false)
    var ticketPrice: Double,

    @Column(nullable = false)
    var isCircular: Boolean
) : Comparable<Route> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = -1

    override fun compareTo(other: Route): Int {
        val departureComparison = this.departurePoint.compareTo(other.departurePoint)
        return if (departureComparison != 0) {
            departureComparison
        } else {
            this.distance.compareTo(other.distance)
        }
    }

    override fun toString(): String {
        return "Route(departurePoint='$departurePoint', destinationPoint='$destinationPoint', departureDate=$departureDate, distance=$distance, ticketPrice=$ticketPrice, isCircular=$isCircular)"
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}