package service;

import dto.Mark;
import exception.service.ServiceException;

/**
 * Mark service
 *
 * @author  Julian Kotrba
 */
public interface MarkService {
    void placeMark(Mark mark) throws ServiceException;
}
