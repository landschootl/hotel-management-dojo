package entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents a room in a hotel.
 */
@Getter
@Setter
@Builder
@ToString
public class Room {

    private int roomNumber;
    private int capacity;
    private double price;
}
