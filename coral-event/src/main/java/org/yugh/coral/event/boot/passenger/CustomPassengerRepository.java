package org.yugh.coral.event.boot.passenger;

import java.util.List;

interface CustomPassengerRepository {

    List<Passenger> findOrderedBySeatNumberLimitedTo(int limit);
}
