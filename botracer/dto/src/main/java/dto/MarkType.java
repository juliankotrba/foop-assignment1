package dto;

/**
 * MarkType
 *
 * @author David Walter
 */
public enum MarkType {
	STAY_IN_AREA, // stay in this area, an exit is close
	MOVE_AWAY_FROM_AREA, // move away from this area, there is no exit nearby
	TURN_LEFT, TURN_RIGHT, // turn left/right now
	CHANGE_ALGORITHM, // change the algorithm to ...
	CLEAR_MEMORY, // clear the memory
	REMOVE // remove mark
}
