package service;

import connection.Connection;
import dto.Mark;
import dto.messages.c2s.MarkPlacementMessage;
import exception.connection.ConnectionException;
import exception.connection.MessageException;
import exception.service.ServiceException;

/**
 * Implementation of the mark service
 *
 * @author Julian Kotrba
 */
public class MarkServiceImpl implements MarkService {

	private Connection connection;

	public MarkServiceImpl(Connection connection) {
		this.connection = connection;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void placeMark(Mark mark) throws ServiceException {
		try {
			this.connection.send(new MarkPlacementMessage(mark));
		} catch (MessageException | ConnectionException e) {
			throw new ServiceException("placeMark failed", e);
		}
	}
}
