package service;

import dto.Mark;
import exception.service.ServiceException;

/**
 * Mark service
 *
 * @author Julian Kotrba
 */
public interface MarkService {

	/**
	 * Places a mark inside the game map
	 *
	 * @param mark the mark to place
	 * @throws ServiceException if placing mark fails
	 */
	void placeMark(Mark mark) throws ServiceException;
}
